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
public class constantWidget extends genericWidget {

    public constantWidget(Scene sc, String id, avr m8,Widget parent) {
        super(sc,id,m8,parent);
        //this.addDisplay();
        this.addValueLabel();

        this.createLabelEditor(this.getValueLabel());
        this.addOutputPin();
    }

    @Override
    public void tick() {
     val =Integer.parseInt(this.getValueLabel().getLabel());
    }



}
