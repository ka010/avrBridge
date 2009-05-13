/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package avrBridgeJavaDev.graph.widgets;

import avrBridgeJavaDev.avr;
import avrBridgeJavaDev.avr;
import javax.swing.BorderFactory;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author blahlol
 */
public class inputPinWidget extends genericWidget{

    public inputPinWidget(Scene sc, String id, avr m8,Widget parent) {
        super(sc,id,m8,parent);
        this.setBorder(BorderFactory.createEmptyBorder());
        this.setTitle("[+]");
    }


}
