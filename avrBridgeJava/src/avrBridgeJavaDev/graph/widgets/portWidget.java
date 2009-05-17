/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package avrBridgeJavaDev.graph.widgets;

import avrBridgeJavaDev.avr;
import avrBridgeJavaDev.graph.avrScene;
import java.util.Iterator;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

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
private avrScene scene;
private int avrPort=0;
    public portWidget(Scene sc, String id, avr m8,Widget parent) {
        super(sc,id,m8,parent);
        this.scene = (avrScene) sc;
        this.setTitle(id);
        this.addDisplay();

        this.addInputPin();
        this.addOutputPin();

        if (id.endsWith("B")) avrPort = 0;
        if (id.endsWith("C")) avrPort = 1;
        if (id.endsWith("D")) avrPort = 2;
        this.pin0 = new pinWidget(sc,"pin0",m8,this);
        this.pin1 = new pinWidget(sc,"pin1",m8,this);
        this.pin2 = new pinWidget(sc,"pin2",m8,this);
        this.pin3 = new pinWidget(sc,"pin3",m8,this);
        this.pin4 = new pinWidget(sc,"pin4",m8,this);
        this.pin5 = new pinWidget(sc,"pin5",m8,this);
        this.pin6 = new pinWidget(sc,"pin6",m8,this);
        this.pin7 = new pinWidget(sc,"pin7",m8,this);

        pin0.setAvrPort(avrPort);
        pin1.setAvrPort(avrPort);
        pin2.setAvrPort(avrPort);
        pin3.setAvrPort(avrPort);
        pin4.setAvrPort(avrPort);
        pin5.setAvrPort(avrPort);
        pin6.setAvrPort(avrPort);
        pin7.setAvrPort(avrPort);

        this.addChild(pin0);
        this.addChild(pin1);
        this.addChild(pin2);
        this.addChild(pin3);
        this.addChild(pin4);
        this.addChild(pin5);
        this.addChild(pin6);
        this.addChild(pin7);
        

    }

    @Override
    public void tick() {
       Widget w = null;
            genericWidget gw = null;

            Iterator it = getChildren().iterator();
            while(it.hasNext()) {
                w = (Widget) it.next();
                if (w instanceof genericWidget ) {

                    gw = (genericWidget) w;
                    gw.tick();
                }
            }

            if (this.getInput()!=null) {
                val = this.getInput().getVal();
                this.m8.setPort(avrPort, val);
            }
            val = this.m8.getPort(avrPort);
            this.setDisplay(Integer.toBinaryString(val));

    }


    
}
