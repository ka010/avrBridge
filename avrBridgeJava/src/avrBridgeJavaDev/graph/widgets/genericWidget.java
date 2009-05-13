/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package avrBridgeJavaDev.graph.widgets;

import avrBridgeJavaDev.avr;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
public class genericWidget  extends Widget {
protected avr m8;
protected int val=0;
private String id;
private ArrayList<String> inputs = new ArrayList();
private ArrayList<String> outputs = new ArrayList();
private LabelWidget title;
    public genericWidget(Scene sc, String id, avr m8) {
        super(sc);
        this.m8 = m8;
        this.id = id;
        this.title = new LabelWidget(sc);
        this.setTitle(id);
        this.addChild(title);
        this.setBorder(BorderFactory.createLineBorder());
        this.setLayout(LayoutFactory.createVerticalFlowLayout(SerialAlignment.CENTER, 5));
    }

    public void tick(){

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


    
    public abstract class timerListener implements ActionListener{
        
    };
}
