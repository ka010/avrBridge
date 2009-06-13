

#include "avrBridgeC.h"
#include <libusb-1.0/libusb.h>


#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "/usr/local/AVRMacPack-20090319/include/usb.h";
#include <sys/types.h>


#define USBDEV_SHARED_VENDOR    0x16C0  /* VOTI */
#define USBDEV_SHARED_PRODUCT   0x05DC  /* Obdev's free shared PID */
/* Use obdev's generic shared VID/PID pair and follow the rules outlined
 * in firmware/usbdrv/USBID-License.txt.
 */

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

#define PORTB 0
#define PORTC 1
#define PORTD 2

#define PINB 0
#define PINC 1
#define PIND 2

#define DDRB 12
#define DDRC 22
#define DDRD 32

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

#define DEBUG_LEVEL 0

/* These are the vendor specific SETUP commands implemented by our USB device */
usb_dev_handle      *handle = NULL;
unsigned char       buffer[8];
int                 nBytes;

unsigned char		PORTMASK = 196;
unsigned char		PINMASK = 60;
unsigned char		STATMASK = 3;

typedef struct _validDev {
    int idVendor;
    int idProduct;
    int busNumber;
    int devAddress;
} validDev;

usb_dev_handle *devList[5];

int devCounter=0;

int currentDev=0;

static void usage(char *name)
{
    fprintf(stderr, "usage:\n");
    fprintf(stderr, "  %s status\n", name);
}


static int  usbGetStringAscii(usb_dev_handle *dev, int index, int langid, char *buf, int buflen)
{
char    buffer[256];
int     rval, i;

    if((rval = usb_control_msg(dev, USB_ENDPOINT_IN, USB_REQ_GET_DESCRIPTOR, (USB_DT_STRING << 8) + index, langid, buffer, sizeof(buffer), 1000)) < 0)
        return rval;
    if(buffer[1] != USB_DT_STRING)
        return 0;
    if((unsigned char)buffer[0] < rval)
        rval = (unsigned char)buffer[0];
    rval /= 2;
    /* lossy conversion to ISO Latin1 */
    for(i=1;i<rval;i++){
        if(i > buflen)  /* destination buffer overflow */
            break;
        buf[i-1] = buffer[2 * i];
        if(buffer[2 * i + 1] != 0)  /* outside of ISO Latin1 range */
            buf[i-1] = '?';
    }
    buf[i-1] = 0;
    return i-1;
}


#define USB_ERROR_NOTFOUND  1
#define USB_ERROR_ACCESS    2
#define USB_ERROR_IO        3

static int usbOpenDevice(usb_dev_handle **device, int vendor, char *vendorName, int product, char *productName)
{
struct usb_bus      *bus;
struct usb_device   *dev;
usb_dev_handle      *handle = NULL;
int                 errorCode = USB_ERROR_NOTFOUND;
static int          didUsbInit = 0;

    if(!didUsbInit){
        didUsbInit = 1;
        usb_init();
    }
    usb_find_busses();
    usb_find_devices();
    for(bus=usb_get_busses(); bus; bus=bus->next){											// iterate over busses
        for(dev=bus->devices; dev; dev=dev->next){											// iterate over devices
            if(dev->descriptor.idVendor == vendor && dev->descriptor.idProduct == product){ // if we have found our device
                char    string[256];
                int     len;
                handle = usb_open(dev);														// open it
                devList[devCounter] = handle;
                devCounter++;
                if(!handle){																// ooops
                    errorCode = USB_ERROR_ACCESS;
                    fprintf(stderr, "Warning: cannot open USB device: %s\n", usb_strerror());
                    continue;
                }
                if(vendorName == NULL && productName == NULL){  /* name does not matter */
                    break;
                }
                /* now check whether the names match: */
                len = usbGetStringAscii(handle, dev->descriptor.iManufacturer, 0x0409, string, sizeof(string));
                if(len < 0){
                    errorCode = USB_ERROR_IO;
                    fprintf(stderr, "Warning: cannot query manufacturer for device: %s\n", usb_strerror());
                }else{
                    errorCode = USB_ERROR_NOTFOUND;
                    /* fprintf(stderr, "seen device from vendor ->%s<-\n", string); */
                    if(strcmp(string, vendorName) == 0){
                        len = usbGetStringAscii(handle, dev->descriptor.iProduct, 0x0409, string, sizeof(string));
                        if(len < 0){
                            errorCode = USB_ERROR_IO;
                            fprintf(stderr, "Warning: cannot query product for device: %s\n", usb_strerror());
                        }else{
                            errorCode = USB_ERROR_NOTFOUND;
                            /* fprintf(stderr, "seen product ->%s<-\n", string); */
                            if(strcmp(string, productName) == 0)
                                break;
                        }
                    }
                }
                usb_close(handle);
                handle = NULL;
            }
        }
        //if(handle)
            //break;
    }
    if(handle != NULL){
        errorCode = 0;
        *device = handle;
        printf("devcounter %d:", devCounter);
        printf("devlist %d:", devList[0]);
        printf("devlist %d:", devList[1]);
    }
    return errorCode;
}


void print_devs(libusb_device **devs)
{
	libusb_device *dev;
	int i = 0;
        validDev avrDev;
	while ((dev = devs[i++]) != NULL) {
		struct libusb_device_descriptor desc;
		int r = libusb_get_device_descriptor(dev, &desc);
		if (r < 0) {
			fprintf(stderr, "failed to get device descriptor");
			return;
		}
                if (desc.idVendor == USBDEV_SHARED_VENDOR && desc.idProduct == USBDEV_SHARED_PRODUCT) {
                    printf("found a MetaBoard!");
                    avrDev.idVendor = desc.idVendor;
                    avrDev.idProduct = desc.idProduct;
                    avrDev.busNumber = libusb_get_bus_number(dev);
                    avrDev.devAddress = libusb_get_device_address(dev);
                  //  devList[devCounter] = avrDev;
                    devCounter++;
                }
		printf("%04x:%04x (bus %d, device %d)\n",
			desc.idVendor, desc.idProduct,
			libusb_get_bus_number(dev), libusb_get_device_address(dev));
	}
}

int get_devs(void)
{
	libusb_device **devs;
	int r;
	ssize_t cnt;

	r = libusb_init(NULL);
	if (r < 0)
		return r;

	cnt = libusb_get_device_list(NULL, &devs);
	if (cnt < 0)
		return (int) cnt;

	print_devs(devs);
	libusb_free_device_list(devs, 1);

	libusb_exit(NULL);
	return 0;
}

int initUsbLib() {
//aquire device handles
	if(usbOpenDevice(&handle, USBDEV_SHARED_VENDOR, "www.obdev.at", USBDEV_SHARED_PRODUCT, "PowerSwitch") != 0){
        fprintf(stderr, "Could not find USB device \"metaboard\" with vid=0x%x pid=0x%x\n", USBDEV_SHARED_VENDOR, USBDEV_SHARED_PRODUCT);
        exit(1);
    }

}

int testUsbLib() {
	int v, r;
	v = rand() & 0xffff;
	nBytes = usb_control_msg(devList[currentDev], USB_TYPE_VENDOR | USB_RECIP_DEVICE | USB_ENDPOINT_IN, CMD_ECHO, v, 0, (char *)buffer, sizeof(buffer), 5000);
	if(nBytes < 2){
		if(nBytes < 0)
			fprintf(stderr, "USB error: %s\n", usb_strerror());
		fprintf(stderr, "only %d bytes received", nBytes);
		fprintf(stderr, "value sent = 0x%x\n", v);
		exit(1);
	}
	r = buffer[0] | (buffer[1] << 8);
	if(r != v){		// if sent and received value don 8't match
		fprintf(stderr, "data error: received 0x%x instead of 0x%x", r, v);
		exit(1);
	}
	printf("test succeeded\n");
}

int statusUsbLib() {
	int i;
	unsigned int val =0xff;

	val = (PORTD << 6);
	val |= (PIN3 << 2);
	val |= ON;

	unsigned int port = (val & PORTMASK)>>6;
	unsigned int pin = (val & PINMASK)>>2;
	unsigned int stat = (val & STATMASK);

	printf("val= %d %d %d %d ", val ,port, pin, stat);

	nBytes = usb_control_msg(devList[currentDev], USB_TYPE_VENDOR | USB_RECIP_DEVICE | USB_ENDPOINT_IN, CMD_GET, val, 0, (char *)buffer, sizeof(buffer), 5000);
	if(nBytes < 4){
		if(nBytes < 0)
			fprintf(stderr, "USB error: %s\n", usb_strerror());
		fprintf(stderr, "only %d bytes status received\n", nBytes);
		exit(1);
	}
	int b0 = buffer[0];
	int b1 = buffer[1];
	int b2 = buffer[2];
	int b3 = buffer[3];
	printf("Buffer received: %d %d %d %d \n", b0,b1,b2,b3);

}

int setCurrentDev(int dev) {
    currentDev=dev;
}

int setPort(int prt, int val) {
	int send = prt;
	send |= (val << 8);


	nBytes = usb_control_msg(devList[currentDev], USB_TYPE_VENDOR | USB_RECIP_DEVICE | USB_ENDPOINT_IN, CMD_PORT, send, 0, (char *)buffer, sizeof(buffer), 5000);

	return nBytes;
}

int setDevPort(int dev, int prt, int val) {
	int send = prt;
	send |= (val << 8);


	nBytes = usb_control_msg(devList[dev], USB_TYPE_VENDOR | USB_RECIP_DEVICE | USB_ENDPOINT_IN, CMD_PORT, send, 0, (char *)buffer, sizeof(buffer), 5000);

	return nBytes;
}

int setPortPin(int prt, int pin, int on) {

	//val=00 0000 00
	//  port pin  on
	unsigned char val=0xff;
	val =  (prt << 6);
	val |= (pin << 2);
	val |= on;

	unsigned int port2 = (val & PORTMASK)>>6;
	unsigned int pin2 = (val & PINMASK)>>2;
	unsigned int stat2 = (val & STATMASK);

	if (DEBUG_LEVEL>0) fprintf(stderr, "val= %d %d %d %d\n", val, port2, pin2, stat2);

	nBytes = usb_control_msg(devList[currentDev], USB_TYPE_VENDOR | USB_RECIP_DEVICE | USB_ENDPOINT_IN, CMD_PIN, val, 0, (char *)buffer, sizeof(buffer), 5000);

	return nBytes;
}

int setDevPortPin(int dev, int prt, int pin, int on) {

	//val=00 0000 00
	//  port pin  on
	unsigned char val=0xff;
	val =  (prt << 6);
	val |= (pin << 2);
	val |= on;

	unsigned int port2 = (val & PORTMASK)>>6;
	unsigned int pin2 = (val & PINMASK)>>2;
	unsigned int stat2 = (val & STATMASK);

	if (DEBUG_LEVEL>0) fprintf(stderr, "val= %d %d %d %d\n", val, port2, pin2, stat2);

	nBytes = usb_control_msg(devList[dev], USB_TYPE_VENDOR | USB_RECIP_DEVICE | USB_ENDPOINT_IN, CMD_PIN, val, 0, (char *)buffer, sizeof(buffer), 5000);

	return nBytes;
}

int setPortPinDir(int prt, int pin, int dir) {
	//val=00 0000 00
	//  port pin  dir
	unsigned char val=0xff;
	val =  (prt << 6);
	val |= (pin << 2);
	val |= dir;

	unsigned int port2 = (val & PORTMASK)>>6;
	unsigned int pin2 = (val & PINMASK)>>2;
	unsigned int stat2 = (val & STATMASK);

	if (DEBUG_LEVEL>0) fprintf(stderr, "val= %d %d %d %d\n", val, port2, pin2, stat2);

	nBytes = usb_control_msg(devList[currentDev], USB_TYPE_VENDOR | USB_RECIP_DEVICE | USB_ENDPOINT_IN, CMD_DDR, val, 0, (char *)buffer, sizeof(buffer), 5000);

	return nBytes;
}

int setDevPortPinDir(int dev, int prt, int pin, int dir) {
	//val=00 0000 00
	//  port pin  dir
	unsigned char val=0xff;
	val =  (prt << 6);
	val |= (pin << 2);
	val |= dir;

	unsigned int port2 = (val & PORTMASK)>>6;
	unsigned int pin2 = (val & PINMASK)>>2;
	unsigned int stat2 = (val & STATMASK);

	if (DEBUG_LEVEL>0) fprintf(stderr, "val= %d %d %d %d\n", val, port2, pin2, stat2);

	nBytes = usb_control_msg(devList[dev], USB_TYPE_VENDOR | USB_RECIP_DEVICE | USB_ENDPOINT_IN, CMD_DDR, val, 0, (char *)buffer, sizeof(buffer), 5000);

	return nBytes;
}

int getPort(int prt) {

	nBytes = usb_control_msg(devList[currentDev], USB_TYPE_VENDOR | USB_RECIP_DEVICE | USB_ENDPOINT_IN, CMD_GPORT, prt, 0, (char *)buffer, sizeof(buffer), 5000);
	int res = buffer[0];
	if (DEBUG_LEVEL>0) fprintf(stderr, "port %d = %d\n", prt, res);

	return res;
}

int getDevPort(int dev, int prt) {

	nBytes = usb_control_msg(devList[dev], USB_TYPE_VENDOR | USB_RECIP_DEVICE | USB_ENDPOINT_IN, CMD_GPORT, prt, 0, (char *)buffer, sizeof(buffer), 5000);
	int res = buffer[0];
	if (DEBUG_LEVEL>0) fprintf(stderr, "port %d = %d\n", prt, res);

	return res;
}

int getPortPin(int prt, int pin) {
	unsigned char val=0xff;
	val =  (prt << 6);
	val |= (pin << 2);

	nBytes = usb_control_msg(devList[currentDev], USB_TYPE_VENDOR | USB_RECIP_DEVICE | USB_ENDPOINT_IN, CMD_GPIN, val, 0, (char *)buffer, sizeof(buffer), 5000);
	int res = buffer[0];
	if (DEBUG_LEVEL>0) fprintf(stderr, "pin %d at port %d = %d\n", pin,prt, res);

	return res;
}

int getDevPortPin(int dev, int prt, int pin) {
	unsigned char val=0xff;
	val =  (prt << 6);
	val |= (pin << 2);

	nBytes = usb_control_msg(devList[dev], USB_TYPE_VENDOR | USB_RECIP_DEVICE | USB_ENDPOINT_IN, CMD_GPIN, val, 0, (char *)buffer, sizeof(buffer), 5000);
	int res = buffer[0];
	if (DEBUG_LEVEL>0) fprintf(stderr, "pin %d at port %d = %d\n", pin,prt, res);

	return res;
}

int getAdcPortPin(int prt, int pin) {
        unsigned char val=0xff;
	val =  (prt << 6);
	val |= (pin << 2);

	nBytes = usb_control_msg(devList[currentDev], USB_TYPE_VENDOR | USB_RECIP_DEVICE | USB_ENDPOINT_IN, CMD_ADC, val, 0, (char *)buffer, sizeof(buffer), 5000);
	int msb = buffer[0];
        int lsb = buffer[1];
	
        int res = 0;
        res   = (msb << 8);
        res  |= lsb;
        
        if (DEBUG_LEVEL>0) fprintf(stderr, "pin %d at port %d = %d\n", pin,prt, res);
	return res;
}

int getDevAdcPortPin(int dev, int prt, int pin) {
        unsigned char val=0xff;
	val =  (prt << 6);
	val |= (pin << 2);

	nBytes = usb_control_msg(devList[dev], USB_TYPE_VENDOR | USB_RECIP_DEVICE | USB_ENDPOINT_IN, CMD_ADC, val, 0, (char *)buffer, sizeof(buffer), 5000);
	int msb = buffer[0];
        int lsb = buffer[1];

        int res = 0;
        res   = (msb << 8);
        res  |= lsb;

        if (DEBUG_LEVEL>0) fprintf(stderr, "pin %d at port %d = %d\n", pin,prt, res);
	return res;
}

int setDac(int prt, int value) {
    unsigned char val=0xff;
         val = (value);
	 val |=  (prt << 8);
	//val |= (pin << 2);

	nBytes = usb_control_msg(devList[currentDev], USB_TYPE_VENDOR | USB_RECIP_DEVICE | USB_ENDPOINT_IN, CMD_DAC, val, 0, (char *)buffer, sizeof(buffer), 5000);
	int msb = buffer[0];
        int lsb = buffer[1];

        int res = 0;
        res   = (msb << 8);
        res  |= lsb;

       // if (DEBUG_LEVEL>0) fprintf(stderr, "pin %d at port %d = %d\n", pin,prt, res);
	return res;

}

int setDevDac(int dev, int prt, int value) {
    unsigned char val=0xff;
         val = (value);
	 val |=  (prt << 8);
	//val |= (pin << 2);

	nBytes = usb_control_msg(devList[dev], USB_TYPE_VENDOR | USB_RECIP_DEVICE | USB_ENDPOINT_IN, CMD_DAC, val, 0, (char *)buffer, sizeof(buffer), 5000);
	int msb = buffer[0];
        int lsb = buffer[1];

        int res = 0;
        res   = (msb << 8);
        res  |= lsb;

       // if (DEBUG_LEVEL>0) fprintf(stderr, "pin %d at port %d = %d\n", pin,prt, res);
	return res;

}


