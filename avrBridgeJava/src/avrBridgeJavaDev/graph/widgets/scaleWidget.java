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
public class scaleWidget extends genericWidget {
private int factor=1;

    public scaleWidget(Scene sc, String id, avr m8, Widget parent) {
        super(sc, id, m8, parent);
        this.addDisplay();
        this.addValueLabel();
        this.addInputPin();
        this.addOutputPin();
        

        this.setValueLabel(String.valueOf(factor));
        //this.getTitle().getActions().addAction(ActionFactory.createInplaceEditorAction(new LabelTextFieldEditor(this)));
        this.getValueLabel().getActions().addAction(ActionFactory.createInplaceEditorAction(new LabelTextFieldEditor(this)));
    }

    @Override
    public void tick() {
       // getScene().repaint();
        int tmp=0;
        if (this.getInput()!=null) {
            tmp = this.getInput().getVal();
            val = tmp*factor;
            this.setDisplay(String.valueOf(val));
        }
    }

    public void setFactor(int factor) {
        this.factor = factor;
    }

    public int getFactor() {
        return factor;
    }


    

}
