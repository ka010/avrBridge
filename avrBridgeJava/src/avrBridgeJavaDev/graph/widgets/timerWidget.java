/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package avrBridgeJavaDev.graph.widgets;

import avrBridgeJavaDev.avr;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.Timer;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author blahlol
 */
public class timerWidget extends genericWidget {
private javax.swing.Timer timer;

private int counter=0;
private int bottom=0;
private int top=255;
    public timerWidget(Scene sc,String id, avr m8,Widget parent) {
        super(sc,id,m8,parent);
        this.addDisplay();
        this.addOutputPin();
        this.getDisplay().setPreferredSize(new Dimension(80,14));
        this.timer = new Timer(500, new timerListener() {

            public void actionPerformed(ActionEvent e) {
                if (counter <top) counter++;
                else counter =bottom;
                val = counter;
                setDisplay(Integer.toBinaryString(val));
                getScene().validate();
            }
        });
        this.timer.start();
    }


}
