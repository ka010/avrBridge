/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package avrBridgeJavaDev.graph.widgets;

import avrBridgeJavaDev.avr;
import avrBridgeJavaDev.graph.menus.menu;
import java.util.Iterator;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author blahlol
 */
public class addWidget extends genericWidget {
private int x =0;
public addWidget(Scene sc, String id, avr m8,Widget parent) {
        super(sc,id,m8,parent);
        this.addDisplay();
        this.addValueLabel();
        this.createLabelEditor(this.getValueLabel());
        this.addInputPin();
        this.addInputPin();
        this.addOutputPin();
        this.getActions().addAction(ActionFactory.createPopupMenuAction(new menu()));
    }

    @Override
    public void tick() {
        int tmp=0;
        Iterator inputIT = this.getInputs().iterator();
        if (this.getInput()!=null) {
            x = Integer.parseInt(this.getValueLabel().getLabel());
            while (inputIT.hasNext()) {
                genericWidget gw = (genericWidget) inputIT.next();
                tmp = tmp + gw.getVal();
            }
            val = tmp+x;
            this.setDisplay(String.valueOf(val));
        }
    }


}
