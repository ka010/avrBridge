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
    private int avrPin=0;
    public dacWidget(Scene sc, String id, avr m8,Widget parent) {
        super(sc,id,m8,parent);
        this.addDisplay();
        this.addInputPin();

        if (id.endsWith("0")) avrPin =0;
        if (id.endsWith("1")) avrPin =1;
        if (id.endsWith("2")) avrPin =2;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void tick() {
        if (this.getInput()!=null) {
            val = this.getInput().getVal();
        }
        setDisplay(Integer.toBinaryString(val));
        m8.setDac(avrPin, val);
    }

}
