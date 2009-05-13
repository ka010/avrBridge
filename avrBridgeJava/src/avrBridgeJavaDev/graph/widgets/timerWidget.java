/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package avrBridgeJavaDev.graph.widgets;

import avrBridgeJavaDev.avr;
import java.awt.event.ActionEvent;
import javax.swing.Timer;
import org.netbeans.api.visual.widget.Scene;

/**
 *
 * @author blahlol
 */
public class timerWidget extends genericWidget {
private javax.swing.Timer timer;
    public timerWidget(Scene sc,String id, avr m8) {
        super(sc,id,m8);
        this.timer = new Timer(500, new timerListener() {

            public void actionPerformed(ActionEvent e) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
    }


}
