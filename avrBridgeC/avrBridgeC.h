/* 
 * File:   avrBridge.h
 * Author: blahlol
 *
 * Created on May 20, 2009, 7:48 PM
 */

#ifndef _AVRBRIDGE_H
#define	_AVRBRIDGE_H

#ifdef	__cplusplus
extern "C" {
#endif
    extern int initUsbLib();

    extern int testUsbLib();

    extern int statusUsbLib();

    extern int setPort(int prt, int val);

    extern int setPortPin(int prt, int pin, int on);

    extern int setPortPinDir(int prt, int pin, int dir);

    extern int getPort(int prt);

    extern int getPortPin(int prt, int pin);

    extern int getAdcPortPin(int prt, int pin);

    extern int setDac(int prt, int value);

    extern int get_devs(void);

    extern int getDevPort(int dev, int prt);

    extern int setCurrentDev(int dev);

    extern int setDevPort(int dev, int prt, int val);

    extern int setDevPortPin(int dev, int prt, int pin, int on);

    extern int setDevPortPinDir(int dev, int prt, int pin, int dir);

    extern int getDevPortPin(int dev, int prt, int pin);

    extern int getDevAdcPortPin(int dev, int prt, int pin);

    extern int setDevDac(int dev, int prt, int value);

#ifdef	__cplusplus
}
#endif

#endif	/* _AVRBRIDGE_H */

