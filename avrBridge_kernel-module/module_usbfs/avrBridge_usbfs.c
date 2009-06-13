#include <linux/kernel.h>
#include <linux/errno.h>
#include <linux/init.h>
#include <linux/slab.h>
#include <linux/module.h>
#include <linux/usb.h>
#include <linux/kref.h>
#include <linux/fs.h>
#include <linux/mutex.h>
#include <linux/proc_fs.h>
#include <asm/uaccess.h>
#define DRIVER_AUTHOR "Kai Aras, ka010@hdm-stuttgart.de"
#define DRIVER_DESC "avrBridge Driver"

#define VENDOR_ID	0x16C0
#define PRODUCT_ID	0x05DC

#define MAX_TRANSFER		(PAGE_SIZE - 512)

#define CMD_ECHO  0
#define CMD_GET   1
#define CMD_ON    2
#define CMD_OFF   3
#define CMD_PORT  4
#define CMD_PIN   5
#define CMD_DDR   6
#define CMD_GPORT 7
#define CMD_GPIN  8
#define CMD_ADC   9
#define CMD_DAC   10

#define _PORTB 0
#define _PORTC 1
#define _PORTD 2

#define _PINB 0
#define _PINC 1
#define _PIND 2

#define _DDRB 0
#define _DDRC 1
#define _DDRD 2

#define PIN0 0
#define PIN1 1
#define PIN2 2
#define PIN3 3
#define PIN4 4
#define PIN5 5
#define PIN6 6
#define PIN7 7

#define DAC0 0;
#define DAC1 1;
#define DAC2 2;

#define ON 1
#define OFF 0
#define HIGH 1
#define LOW 0



/* table of devices that work with this driver */
static struct usb_device_id id_table [] = {
	{ USB_DEVICE(VENDOR_ID, PRODUCT_ID) },
	{ },
};
MODULE_DEVICE_TABLE (usb, id_table);


struct avrBridge {
	struct usb_device	*udev;			/* the usb device for this device */
	struct usb_interface	*interface;		/* the interface for this device */
	struct semaphore	limit_sem;		/* limiting the number of writes in progress */
	struct usb_anchor	submitted;
	int			errors;			/* the last request tanked */
	int			open_count;		/* count the number of openers */
	spinlock_t		err_lock;		/* lock for errors */
	struct kref		kref;
	struct mutex		io_mutex;		/* synchronize I/O with disconnect */ 
	unsigned char		portB;	
	unsigned char		portC;
	unsigned char		portD;
	unsigned char		adc0;
	unsigned char		adc1;
	unsigned char		adc2;
	unsigned char		adc3;
	unsigned char		adc4;
	unsigned char		adc5;
	unsigned char		dac0;
	unsigned char		dac1;
	unsigned char		dac2;
	unsigned char		pin;
};
#define to_avrBridge_dev(d) container_of(d, struct avrBridge, kref)

static struct usb_driver avrBridge_driver;

#define get_(value) \
static void avrBridge_get_##value(struct avrBridge *dev,unsigned char inmode, unsigned char prt, unsigned char pin) \
{							\
	int retval;					 \
	unsigned char *buffer;				\
	buffer = kmalloc(8,GFP_KERNEL);			\
	unsigned char res=0;				\
	if(!buffer) {					\
		dev_err(&dev->udev->dev, "out-of-memory\n"); \
		return;					\
	}						\
	int val=0;						\
	val =(prt<<6);					\
	val |= (pin<<2); 					\
	retval = usb_control_msg (dev->udev, usb_rcvctrlpipe (dev->udev, 0), \
			inmode,						\
			USB_TYPE_VENDOR | USB_RECIP_DEVICE | USB_DIR_IN,	\
			val,0,							\
			(char*)buffer,sizeof(buffer) , 2500);			\
										\
	if(inmode==CMD_GPORT) {							\
		res = buffer[0];									\
	} else {									\
		unsigned char msb=buffer[0];						\
		unsigned char lsb=buffer[1];						\
										\
		res = (msb<<8);								\
		res |=lsb;								\
	}									\
										\
	dev->value=res;								\
										\
	kfree(buffer);								\
	printk("value: %#0x inmode: %d prt: %d state:%d\n",res, inmode, prt,dev->value); \
}						 	\

#define put_(value) \
static void avrBridge_put_##value(struct avrBridge *dev,int outmode,unsigned char prt, unsigned char pin,unsigned char state) \
{							\
	int retval;					 \
	unsigned char *buffer;				\
	buffer = kmalloc(8,GFP_KERNEL);			\
	if(!buffer) {					\
		dev_err(&dev->udev->dev, "out-of-memory\n"); \
		return;					\
	}						\
	int val=0;						\
	val =(prt<<6);					\
	val |= (pin<<2); 					\
						\
	val |= (state<<8);						\
	retval = usb_control_msg (dev->udev, usb_rcvctrlpipe (dev->udev, 0), \
			outmode,						\
			USB_TYPE_VENDOR | USB_RECIP_DEVICE | USB_DIR_IN,	\
			val,0,							\
			(char*)buffer,sizeof(buffer) , 2500);			\
										\
										\
	dev->value=state;							\
										\
										\
	printk("buf: %d value: %#0x outmode: %d prt: %d state: %d\n",buffer[0], val, outmode, prt,dev->value); \
	kfree(buffer); 								\
}

#define show_set(type,inmode,outmode,prt,pin)	\
static ssize_t show_##type(struct device *dev, struct device_attribute *attr, char *buf)		\
{									\
	struct usb_interface *intf = to_usb_interface(dev);		\
	struct avrBridge *udev = usb_get_intfdata(intf);			\
	avrBridge_get_##type(udev,inmode,prt,pin);					\
	\
	return sprintf(buf, "%d \n", udev->type);			\
}									\
static ssize_t set_##type(struct device *dev, struct device_attribute *attr, const char *buf, size_t count)	\
{									\
	struct usb_interface *intf = to_usb_interface(dev);		\
	struct avrBridge *udev = usb_get_intfdata(intf);			\
	int temp = simple_strtoul(buf, NULL, 10);			\
									\
							\
	avrBridge_put_##type(udev,outmode,prt,pin,temp);						\
	return count;							\
}									\
static DEVICE_ATTR(type , S_IWUGO | S_IRUGO, show_##type, set_##type);

//setup input functions
get_(portB);
get_(portC);
get_(portD);
get_(adc0);
get_(adc1);
get_(adc2);
get_(adc3);
get_(adc4);
get_(adc5);
get_(dac0);
get_(dac1);
get_(dac2);
get_(pin);

//setup output function
put_(portB);
put_(portC);
put_(portD);
put_(adc0);
put_(adc1);
put_(adc2);
put_(adc3);
put_(adc4);
put_(adc5);
put_(dac0);
put_(dac1);
put_(dac2);
put_(pin);


//setup device files and register read/write functions
show_set(portB,CMD_GPORT,CMD_PORT,0,0); 
show_set(portC,CMD_GPORT,CMD_PORT,1,1);
show_set(portD,CMD_GPORT,CMD_PORT,2,2);
show_set(adc0,CMD_ADC,CMD_ADC,1,0);
show_set(adc1,CMD_ADC,CMD_ADC,1,1);
show_set(adc2,CMD_ADC,CMD_ADC,1,2);
show_set(adc3,CMD_ADC,CMD_ADC,1,3);
show_set(adc4,CMD_ADC,CMD_ADC,1,4);
show_set(adc5,CMD_ADC,CMD_ADC,1,5);
show_set(dac0,CMD_DAC,CMD_DAC,0,0)
show_set(dac1,CMD_DAC,CMD_DAC,0,1);
show_set(dac2,CMD_DAC,CMD_DAC,0,2);
show_set(pin,CMD_GPIN,CMD_PIN,0,0);



static void avrBridge_disconnect(struct usb_interface *interface)
{
	struct avrBridge *dev;

	dev = usb_get_intfdata (interface);

	

	device_remove_file(&interface->dev, &dev_attr_portB);
	device_remove_file(&interface->dev, &dev_attr_portC);
	device_remove_file(&interface->dev, &dev_attr_portD);
	device_remove_file(&interface->dev, &dev_attr_adc0);
	device_remove_file(&interface->dev, &dev_attr_adc1);
	device_remove_file(&interface->dev, &dev_attr_adc2);
	device_remove_file(&interface->dev, &dev_attr_adc3);
	device_remove_file(&interface->dev, &dev_attr_adc4);
	device_remove_file(&interface->dev, &dev_attr_adc5);
	device_remove_file(&interface->dev, &dev_attr_dac0);
	device_remove_file(&interface->dev, &dev_attr_dac1);
	device_remove_file(&interface->dev, &dev_attr_dac2);	
	device_remove_file(&interface->dev, &dev_attr_pin);
	/* first remove the files, then set the pointer to NULL */
	usb_set_intfdata (interface, NULL);

	usb_put_dev(dev->udev);

	kfree(dev);

	dev_info(&interface->dev, "avrBridge now disconnected\n");
}


static int avrBridge_probe(struct usb_interface *interface, const struct usb_device_id *id)
{
	struct usb_device *udev = interface_to_usbdev(interface);
	struct avrBridge *dev = NULL;
	struct usb_host_interface *iface_desc;
	struct usb_endpoint_descriptor *endpoint;
	size_t buffer_size;
	int i;
	int retval = -ENOMEM;

	dev = kzalloc(sizeof(struct avrBridge), GFP_KERNEL);
	if (dev == NULL) {
		dev_err(&interface->dev, "Out of memory\n");
		goto error_mem;
	}
	
	kref_init(&dev->kref);
	sema_init(&dev->limit_sem, 8);
	mutex_init(&dev->io_mutex);
	spin_lock_init(&dev->err_lock);
	init_usb_anchor(&dev->submitted);
	dev->udev = usb_get_dev(udev);
	dev->interface = interface;
	

	usb_set_intfdata (interface, dev);

	retval = device_create_file(&interface->dev, &dev_attr_portB);
	retval = device_create_file(&interface->dev, &dev_attr_portC);
	retval = device_create_file(&interface->dev, &dev_attr_portD);
	retval = device_create_file(&interface->dev, &dev_attr_adc0);
	retval = device_create_file(&interface->dev, &dev_attr_adc1);
	retval = device_create_file(&interface->dev, &dev_attr_adc2);
	retval = device_create_file(&interface->dev, &dev_attr_adc3);
	retval = device_create_file(&interface->dev, &dev_attr_adc4);
	retval = device_create_file(&interface->dev, &dev_attr_adc5);
	retval = device_create_file(&interface->dev, &dev_attr_dac0);	
	retval = device_create_file(&interface->dev, &dev_attr_dac1);
	retval = device_create_file(&interface->dev, &dev_attr_dac2);
	retval = device_create_file(&interface->dev, &dev_attr_pin);

	dev_info(&interface->dev, "avrBridge device now attached\n");
	return 0;

error:
	device_remove_file(&interface->dev, &dev_attr_portB);
	device_remove_file(&interface->dev, &dev_attr_portC);
	device_remove_file(&interface->dev, &dev_attr_portD);
	device_remove_file(&interface->dev, &dev_attr_adc0);
	device_remove_file(&interface->dev, &dev_attr_adc1);
	device_remove_file(&interface->dev, &dev_attr_adc2);
	device_remove_file(&interface->dev, &dev_attr_adc3);
	device_remove_file(&interface->dev, &dev_attr_adc4);
	device_remove_file(&interface->dev, &dev_attr_adc5);
	device_remove_file(&interface->dev, &dev_attr_dac0);
	device_remove_file(&interface->dev, &dev_attr_dac1);
	device_remove_file(&interface->dev, &dev_attr_dac2);
	usb_set_intfdata (interface, NULL);
	usb_put_dev(dev->udev);
	kfree(dev);
error_mem:
	return retval;
}


static struct usb_driver avrBridge_driver = {
	.name =		"avrBridge",	
	.probe =	avrBridge_probe,	// gets called when a device is attached
	.disconnect =	avrBridge_disconnect,	// gets called when a device is disconnected
	.id_table =	id_table,		// working device ids

};

static int __init avrBridge_init(void)
{
	int retval = 0;
	
	retval = usb_register(&avrBridge_driver);
	if (retval)
		err("usb_register failed. Error number %d", retval);
	return retval;
}

static void __exit avrBridge_exit(void)
{
	usb_deregister(&avrBridge_driver);
}

module_init(avrBridge_init);
module_exit(avrBridge_exit);

MODULE_LICENSE("GPL");
MODULE_AUTHOR("Kai Aras ka010@hdm-stuttgart.de");
MODULE_DESCRIPTION("\"avrBridge\"");
MODULE_VERSION("dev");

