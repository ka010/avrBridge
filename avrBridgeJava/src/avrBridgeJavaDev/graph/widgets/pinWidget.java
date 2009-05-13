/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package avrBridgeJavaDev.graph.widgets;

import avrBridgeJavaDev.avr;
import java.awt.Color;
import java.awt.Dimension;

import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.layout.LayoutFactory.SerialAlignment;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

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
    public pinWidget(Scene sc, String id, avr m8,Widget parent) {
        super(sc,id,m8,parent);

        avrPin = id.charAt(id.length()-1);
        
        display= new LabelWidget(sc,"");
        title = new LabelWidget(sc,id);
        inputPin = (inputPinWidget) this.addInputPin();//new inputPinWidget(sc,id+"input",m8,this.getParent());
        this.addChild(title);
        this.addChild(display);
       
        display.setForeground(Color.black);
        display.setBackground(Color.black);
        
        outputPin = (outputPinWidget) this.addOutputPin();//new outputPinWidget(sc,id+"output",m8,this.getParent());
        
        this.setLayout(LayoutFactory.createHorizontalFlowLayout(SerialAlignment.CENTER, 10));
        //this.setPreferredSize(new Dimension(150,16));
        this.setTitle("");

        if (((genericWidget)parent).getId().endsWith("B")) avrPort =0;
        else if (((genericWidget)parent).getId().endsWith("C")) avrPort =1;
        else if (((genericWidget)parent).getId().endsWith("D")) avrPort =2;

        for (int i =0; i<8;i++) {
            if (id.endsWith(String.valueOf(i))) avrPin =i;
        }

        

        
    }

    @Override
    public void tick() {
        int tmp = m8.getPortPin(avrPort, avrPin);
        int res;
        if (tmp>0) res =1;
        else res=0;
        display.setLabel(Integer.toBinaryString(res));
    }

    public void setAvrPin(int avrPin) {
        this.avrPin = avrPin;
    }

    public void setAvrPort(int avrPort) {
        this.avrPort = avrPort;
    }

    

}
