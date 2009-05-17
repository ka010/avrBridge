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
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.TextFieldInplaceEditor;
import org.netbeans.api.visual.action.WidgetAction;
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
private ArrayList<genericWidget> inputs = new ArrayList();
private ArrayList<genericWidget> outputs = new ArrayList();
private LabelWidget title;
private Scene scene;
private inputPinWidget inPin;
private outputPinWidget outPin;
private LabelWidget display;
private LabelWidget valueLabel;
private int avrPin;
private int avrPort;
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

    public Widget addValueLabel() {
        Widget w = new LabelWidget(scene);
        this.valueLabel = (LabelWidget) w;
        this.valueLabel.setLabel("0");
        w.setBorder(BorderFactory.createLineBorder());
        this.addChild(w);
        return w;
    }

    public WidgetAction createLabelEditor(Widget w) {
        WidgetAction a = ActionFactory.createInplaceEditorAction(new LabelTextFieldEditor(w));
        w.getActions().addAction(a);
        return a;
    }

    public LabelWidget getValueLabel() {
        return valueLabel;
    }

    public void setValueLabel(LabelWidget valueLabel) {
        this.valueLabel = valueLabel;
    }

    public void setValueLabel(String valueLabel) {
        this.valueLabel.setLabel(valueLabel);
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

    

    public ArrayList<genericWidget> getInputs() {
        return inputs;
    }

    public ArrayList<genericWidget> getOutputs() {
        return outputs;
    }

    public void addInput(genericWidget input) {
        this.inputs.add(input);
    }

    public void addOutput(genericWidget output) {
        this.outputs.add(output);
    }

    public void removeOutput(String output) {
        this.outputs.remove(output);
    }

    public void removeInput(String input) {
        this.inputs.remove(input);
    }

    public genericWidget getInput() {
        if (inputs.size()>0) return inputs.get(0);
        else return null;
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

    public int getAvrPin() {
        return avrPin;
    }

    public void setAvrPin(int avrPin) {
        this.avrPin = avrPin;
    }

    public int getAvrPort() {
        return avrPort;
    }

    public void setAvrPort(int avrPort) {
        this.avrPort = avrPort;
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

        public LabelTextFieldEditor(Widget host) {
            this.host =  host;
        }

        public LabelTextFieldEditor() {
        }



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
            if (host instanceof scaleWidget) ((scaleWidget)host).setFactor(Integer.parseInt(text));
            
        }

    }
    
    public abstract class timerListener implements ActionListener{

        public void actionPerformed(ActionEvent e) {
            tick();
        }
        
    };
}
