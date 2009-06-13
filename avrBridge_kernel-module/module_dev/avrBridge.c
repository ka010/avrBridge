#include <linux/kernel.h>
#include <linux/errno.h>
#include <linux/init.h>
#include <linux/slab.h>
#include <linux/module.h>
#include <linux/usb.h>
#include <linux/kref.h>
#include <linux/fs.h>
#include <linux/mutex.h>
#include <asm/uaccess.h>
#define DRIVER_AUTHOR "Kai Aras, ka010@hdm-stuttgart.de"
#define DRIVER_DESC "USB LED Driver"

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
	unsigned char *         bulk_in_buffer;		/* the buffer to receive data */
	size_t			bulk_in_size;		/* the size of the receive buffer */
	__u8			bulk_in_endpointAddr;	/* the address of the bulk in endpoint */
	__u8			bulk_out_endpointAddr;	/* the address of the bulk out endpoint */
	int			errors;			/* the last request tanked */
	int			open_count;		/* count the number of openers */
	spinlock_t		err_lock;		/* lock for errors */
	struct kref		kref;
	struct mutex		io_mutex;		/* synchronize I/O with disconnect */ 
};
#define to_avrBridge_dev(d) container_of(d, struct avrBridge, kref)

static struct usb_driver avrBridge_driver;

static void avrBridge_delete(struct kref *kref) {
	struct avrBridge *dev = to_avrBridge_dev(kref);
	
	usb_put_dev(dev->udev);
	kfree(dev->bulk_in_buffer);
	kfree(dev);
}

static int avrBridge_open(struct inode *inode, struct file *file)
{
	struct avrBridge *dev;
	struct usb_interface *interface;
	int subminor;
	int retval = 0;

	subminor = iminor(inode);

	interface = usb_find_interface(&avrBridge_driver, subminor);
	if (!interface) {
		err ("%s - error, can't find device for minor %d",
		     __func__, subminor);
		retval = -ENODEV;
		goto exit;
	}

	dev = usb_get_intfdata(interface);
	if (!dev) {
		retval = -ENODEV;
		goto exit;
	}

	/* increment our usage count for the device */
	kref_get(&dev->kref);

	/* lock the device to allow correctly handling errors
	 * in resumption */
	mutex_lock(&dev->io_mutex);

	if (!dev->open_count++) {
		retval = usb_autopm_get_interface(interface);
			if (retval) {
				dev->open_count--;
				mutex_unlock(&dev->io_mutex);
				kref_put(&dev->kref, avrBridge_delete);
				goto exit;
			}
	} /* else { //uncomment this block if you want exclusive open
		retval = -EBUSY;
		dev->open_count--;
		mutex_unlock(&dev->io_mutex);
		kref_put(&dev->kref, skel_delete);
		goto exit;
	} */
	/* prevent the device from being autosuspended */

	/* save our object in the file's private structure */
	file->private_data = dev;
	mutex_unlock(&dev->io_mutex);

exit:
	return retval;
}

static int avrBridge_release(struct inode *inode, struct file *file)
{
	struct avrBridge *dev;

	dev = (struct avrBridge *)file->private_data;
	if (dev == NULL)
		return -ENODEV;

	/* allow the device to be autosuspended */
	mutex_lock(&dev->io_mutex);
	if (!--dev->open_count && dev->interface)
		usb_autopm_put_interface(dev->interface);
	mutex_unlock(&dev->io_mutex);

	/* decrement the count on our device */
	kref_put(&dev->kref, avrBridge_delete);
	return 0;
}

static ssize_t avrBridge_read(struct file *file, char __user *buffer, size_t count,
				loff_t * ppos)
{
	struct avrBridge *dev;
	int retval;
	int bytes_read;
	unsigned char buff[8];
	dev = (struct avrBridge *)file->private_data;

	mutex_lock(&dev->io_mutex);
	if (!dev->interface) {		/* disconnect() was called */
		retval = -ENODEV;
		goto exit;
	}
	
	if (*ppos != 0)
		return 0;

	retval = usb_control_msg (dev->udev, usb_rcvctrlpipe (dev->udev, 0),
			CMD_ADC,
			USB_TYPE_VENDOR | USB_RECIP_DEVICE | USB_DIR_IN,
			0,0,
			(char*)buff,sizeof(buff) , 5000);
	
	unsigned char msb=buff[0];
	unsigned char lsb=buff[1];
	unsigned char res=0;
	res = (msb<<8);
	res |=lsb;

	char str_buff[8]; // small buffer
	sprintf(str_buff,"%d \n",res); // print the returned value into the buffer
	char *result = &str_buff; 
	int len =strlen(result); // get length

	//if (retval) {
		if(copy_to_user(buffer, result,len))	// copy buffer to userspace
			retval = -EFAULT;
		else
			retval = retval;
	//}
	//*ppos = retval;
	printk("retval:%d buffer:%d :%d res:%d \n",retval, buff[0], buff[1], res);
exit:
	mutex_unlock(&dev->io_mutex);
	return len;
}

static ssize_t avrBridge_write(struct file *file, const char *user_buffer, size_t count, loff_t *ppos)
{
	struct avrBridge *dev;
	int retval = 0;
	struct urb *urb = NULL;
	char *buf = NULL;
	size_t writesize = min(count, (size_t)MAX_TRANSFER);

	dev = (struct avrBridge *)file->private_data;

	/* verify that we actually have some data to write */
	if (count == 0)
		goto exit;

	/* limit the number of URBs in flight to stop a user from using up all RAM */
	if (down_interruptible(&dev->limit_sem)) {
		retval = -ERESTARTSYS;
		goto exit;
	}

	spin_lock_irq(&dev->err_lock);
	if ((retval = dev->errors) < 0) {
		/* any error is reported once */
		dev->errors = 0;
		/* to preserve notifications about reset */
		retval = (retval == -EPIPE) ? retval : -EIO;
	}
	spin_unlock_irq(&dev->err_lock);
	if (retval < 0)
		goto error;


error:
	if (urb) {
		usb_buffer_free(dev->udev, writesize, buf, urb->transfer_dma);
		usb_free_urb(urb);
	}
	up(&dev->limit_sem);

exit:
	return retval;
}

static const struct file_operations avrBridge_fops = {
	.owner = THIS_MODULE,
	.read = avrBridge_read,
	.open = avrBridge_open,
	.write = avrBridge_write,
	.release = avrBridge_release,
};

static struct usb_class_driver avrBridge_class = {
        .name =         "avrBridge%d",
        .fops =         &avrBridge_fops,
        .minor_base =   192,
};



static void avrBridge_disconnect(struct usb_interface *interface)
{
	struct avrBridge *dev;

	dev = usb_get_intfdata (interface);

	usb_deregister_dev(interface,&avrBridge_class);
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
	dev->bulk_in_buffer = kmalloc(le16_to_cpu(512), GFP_KERNEL);

	usb_set_intfdata (interface, dev);
	retval = usb_register_dev(interface,&avrBridge_class);
		if (retval) {
		/* something prevented us from registering this device */
		err("Unble to allocate minor number.");
		usb_set_intfdata(interface, NULL);
		//idmouse_delete(dev);
		return retval;
	}

	dev_info(&interface->dev, "avrBridge device now attached\n");
	return 0;

error:
	usb_deregister_dev(interface,&avrBridge_class);
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

