/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package avrBridgeJavaDev.graph.widgets;

import avrBridgeJavaDev.avr;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author blahlol
 */
public class adcWidget extends genericWidget{
private int avrPin=0;
    public adcWidget(Scene sc, String id, avr m8,Widget parent) {
        super(sc,id,m8,parent);
        this.addDisplay();
        this.addOutputPin();

        this.getTitle().getActions().addAction(ActionFactory.createInplaceEditorAction(new LabelTextFieldEditor()));
        if (id.endsWith("0")) avrPin=0;
        if (id.endsWith("1")) avrPin=1;
        if (id.endsWith("2")) avrPin=2;
        if (id.endsWith("3")) avrPin=3;
        if (id.endsWith("4")) avrPin=4;
        if (id.endsWith("5")) avrPin=5;
    }

    @Override
    public void tick() {

        val = m8.getAdc(avrPin);
        this.setDisplay(String.valueOf(val));
    }



}
