/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package avrBridgeJavaDev;

/**
 *
 * @author blahlol
 */
public class avr {
    /**
     * defines a HIGH pin state
     */
    public final int ON =1;
    /**
     * defines a LOW pin state
     */
    public final int OFF =0;
    /**
     * defines a HIGH pin state
     */
    public final int HIGH =1;
    /**
     * defines a HIGH pin state
     */
    public final int LOW =0;
    /**
     * defines an INPUT pin
     */
    public final int IN =0;
    /**
     * defines an OUTPUT pin
     */
    public final int OUT=1;

    /**
     *
     */
    public final int PORTB = 0;
    /**
     *
     */
    public final int PORTC = 1;
    /**
     *
     */
    public final int PORTD = 2;

    /**
     *
     */
    public final int PIN0 = 0;
    /**
     *
     */
    public final int PIN1 = 1;
    /**
     *
     */
    public final int  PIN2 = 2;
    /**
     *
     */
    public final int PIN3 = 3;
    /**
     *
     */
    public final int PIN4 = 4;
    /**
     *
     */
    public final int PIN5 = 5;
    /**
     *
     */
    public final int PIN6 = 6;
    /**
     *
     */
    public final int PIN7 = 7;

    /*    static {
        System.load("/Users/blahlol/Share/workspace/cpp/avrBridgeJNI/dist/libavrBridgeJNI.so");
       }
*/
    public avr() {
         System.load("/Users/blahlol/Share/workspace/cpp/avrBridgeJNI/dist/libavrBridgeJNI.so");
    }

        
        /**
         *
         */
        public native void test();

        /**
         *
         * @return
         */
        public native int[] getStatus();

        /**
         * writes a given byte to a given port
         * @param port
         * @param val
         * @return
         */
        public native int setPort(int port, int val);

    /**
     * sets a given state to a given pin at a given port
     * @param port
     * @param pin
     * @param on
     * @return
     */
    public native int setPortPin(int port, int pin, int on);

    /**
     * configures a given pin's direction at a given port
     * @param port
     * @param pin
     * @param dir
     * @return
     */
    public native int setPortPinDir(int port, int pin, int dir);

    /**
     * returns a byte representing a given port's register
     * @param port
     * @return
     */
    public native int getPort(int port);

    /**
     * returnes a given pin's state at a given port
     * @param port
     * @param pin
     * @return
     */
    public native int getPortPin(int port, int pin);

    /**
     * returns a 10bit value, read at a given adc-enabled pin
     * @param pin
     * @return
     */
    public native int getAdc(int pin);

    /**
     * 
     * @param pin
     * @param val
     * @return
     */
    public native int setDac(int pin, int val);

    /**
     *
     * @return
     */
    public native int get();
}
