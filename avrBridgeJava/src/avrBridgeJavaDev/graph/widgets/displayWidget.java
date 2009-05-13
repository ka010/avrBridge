/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package avrBridgeJavaDev.graph.widgets;

import avrBridgeJavaDev.avr;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author blahlol
 */
public class displayWidget extends genericWidget {

    public displayWidget(Scene sc, String id, avr m8,Widget parent) {
        super(sc,id,m8,parent);
        this.addDisplay();
        this.addInputPin();
    }

    @Override
    public void tick() {
        this.setDisplay(Integer.toBinaryString(val));
    }



}
