/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package avrBridgeJavaDev.graph.widgets;

import avrBridgeJavaDev.avr;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Scene;

/**
 *
 * @author blahlol
 */
public class portWidget extends genericWidget {
private pinWidget pin0;
private pinWidget pin1;
private pinWidget pin2;
private pinWidget pin3;
private pinWidget pin4;
private pinWidget pin5;
private pinWidget pin6;
private pinWidget pin7;

    public portWidget(Scene sc, String id, avr m8) {
        super(sc,id,m8);
        this.setTitle(id);

        this.pin0 = new pinWidget(sc,"pin0",m8);
        this.pin1 = new pinWidget(sc,"pin1",m8);
        this.pin2 = new pinWidget(sc,"pin2",m8);
        this.pin3 = new pinWidget(sc,"pin3",m8);
        this.pin4 = new pinWidget(sc,"pin4",m8);
        this.pin5 = new pinWidget(sc,"pin5",m8);
        this.pin6 = new pinWidget(sc,"pin6",m8);
        this.pin7 = new pinWidget(sc,"pin7",m8);

        pin0.setAvrPort(id.charAt(id.length()-1));
        this.addChild(pin0);
        this.addChild(pin1);
        this.addChild(pin2);
        this.addChild(pin3);
        this.addChild(pin4);
        this.addChild(pin5);
        this.addChild(pin6);
        this.addChild(pin7);
    }

}
