/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package avrBridgeJavaDev.graph.widgets;

import avrBridgeJavaDev.avr;
import avrBridgeJavaDev.graph.clockListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import org.netbeans.api.visual.action.TextFieldInplaceEditor;
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
public class genericWidget  extends Widget implements clockListener {
protected avr m8;
protected int val=0;
private String id;
private Widget parent;
private ArrayList<String> inputs = new ArrayList();
private ArrayList<String> outputs = new ArrayList();
private LabelWidget title;
private Scene scene;
private inputPinWidget inPin;
private outputPinWidget outPin;
private LabelWidget display;
    public genericWidget(Scene sc, String id, avr m8, Widget parent) {
        super(sc);
        this.scene = sc;
        this.m8 = m8;
        this.id = id;
        this.title = new LabelWidget(sc);
        
        this.parent = parent;
        this.setTitle(id);
        this.addChild(title);
        this.setBorder(BorderFactory.createLineBorder());
        this.setLayout(LayoutFactory.createVerticalFlowLayout(SerialAlignment.CENTER, 3));
    }

    public void tick(){

    }

    public Widget addInputPin() {
        Widget w = new inputPinWidget(scene,id,m8,this);
        this.inPin = (inputPinWidget) w;
        this.addChild(w);
        return w;
    }

    public Widget addOutputPin() {
        Widget w = new outputPinWidget(scene,id,m8,this);
        this.outPin = (outputPinWidget) w;
        this.addChild(w);
        return w;
    }

    public Widget addDisplay() {
        Widget w = new LabelWidget(scene);
        this.display = (LabelWidget) w;
        w.setBorder(BorderFactory.createLineBorder());
        this.addChild(w);
        return w;
    }

    public void setDisplay(String display) {
        this.display.setLabel(display);
    }


    public inputPinWidget getInPin() {
        return inPin;
    }

    public outputPinWidget getOutPin() {
        return outPin;
    }

    

    public ArrayList<String> getInputs() {
        return inputs;
    }

    public ArrayList<String> getOutputs() {
        return outputs;
    }

    public void addInput(String input) {
        this.inputs.add(input);
    }

    public void addOutput(String output) {
        this.outputs.add(output);
    }

    public void removeOutput(String output) {
        this.outputs.remove(output);
    }

    public void removeInput(String input) {
        this.inputs.remove(input);
    }

    public void setTitle(String title) {
        this.title.setLabel(title);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public genericWidget getParent() {
        return (genericWidget) parent;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public LabelWidget getDisplay() {
        return display;
    }

    public void setDisplay(LabelWidget display) {
        this.display = display;
    }

    public avr getM8() {
        return m8;
    }

    public void setM8(avr m8) {
        this.m8 = m8;
    }



    public void setParent(Widget parent) {
        this.parent = parent;
    }

    public LabelWidget getTitle() {
        return title;
    }

    public void setTitle(LabelWidget title) {
        this.title = title;
    }

    public void actionPerformed(ActionEvent e) {
       this.tick();
    }


    
public class LabelTextFieldEditor implements TextFieldInplaceEditor {
    private Widget host;
    private Widget widget;

        /**
         *
         * @param widget
         * @return
         */
        public boolean isEnabled(Widget widget) {
            this.widget = widget;
            return true;
        }

        /**
         *
         * @param widget
         * @return
         */
        public String getText(Widget widget) {
           return ((LabelWidget) widget).getLabel ();
           // return  ((LabelWidget)widget.getChildren().get(0)).getLabel();
        }

        /**
         *
         * @param widget
         * @param text
         */
        public void setText(Widget widget, String text) {
            ((LabelWidget) widget).setLabel (text);
            
        }

    }
    
    public abstract class timerListener implements ActionListener{

        public void actionPerformed(ActionEvent e) {
            tick();
        }
        
    };
}
