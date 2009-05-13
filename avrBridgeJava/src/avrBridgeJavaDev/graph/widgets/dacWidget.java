/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package avrBridgeJavaDev.graph.widgets;

import avrBridgeJavaDev.avr;
import java.awt.event.ActionEvent;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author blahlol
 */
public class dacWidget  extends genericWidget{
    public dacWidget(Scene sc, String id, avr m8,Widget parent) {
        super(sc,id,m8,parent);
        this.addDisplay();
        this.addInputPin();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void tick() {
        val = 0;
        setDisplay(Integer.toBinaryString(val));
    }

}
