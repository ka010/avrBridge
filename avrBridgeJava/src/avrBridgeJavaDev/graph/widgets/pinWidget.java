/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package avrBridgeJavaDev.graph.widgets;

import avrBridgeJavaDev.avr;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.layout.LayoutFactory.SerialAlignment;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Scene;

/**
 *
 * @author blahlol
 */
public class pinWidget extends genericWidget {
private outputPinWidget outputPin;
private inputPinWidget inputPin;
private LabelWidget title;
private LabelWidget display;
private int avrPort =0;
private int avrPin =0;
    public pinWidget(Scene sc, String id, avr m8) {
        super(sc,id,m8);

        avrPin = id.charAt(id.length()-1);
        
        display= new LabelWidget(sc,"");
        title = new LabelWidget(sc,id);
        inputPin = new inputPinWidget(sc,"",m8);
        outputPin = new outputPinWidget(sc,"",m8);
        this.setLayout(LayoutFactory.createHorizontalFlowLayout(SerialAlignment.CENTER, 5));
        this.setTitle("");
        this.addChild(inputPin);
        this.addChild(title);
        this.addChild(display);
        this.addChild(outputPin);
    }

    @Override
    public void tick() {
        display.setLabel(Integer.toBinaryString(m8.getPortPin(val, val)));
    }

    public void setAvrPin(int avrPin) {
        this.avrPin = avrPin;
    }

    public void setAvrPort(int avrPort) {
        this.avrPort = avrPort;
    }

    

}
