/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package avrBridgeJavaDev;

import avrBridgeJavaDev.ui.gui;

/**
 *
 * @author blahlol
 */
public class Main {


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
        avr m8 = new avr();
         m8.test();
         gui guiinstance = new gui(m8);
         guiinstance.setVisible(true);
   
       
    }

    

 

}
