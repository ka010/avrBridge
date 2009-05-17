/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package avrBridgeJavaDev.graph.widgets;

import avrBridgeJavaDev.avr;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.lang.String;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author blahlol
 */
public class plotWidget  extends genericWidget {
private int step=0;
private int[] array =new int[255];
    public plotWidget(Scene sc, String id, avr m8, Widget parent) {
        super(sc, id, m8, parent);
        this.addDisplay();
        this.addInputPin();
        this.setPreferredSize(new Dimension(260,260));

        for (int i =0;i<255;i++) {
            array[i] =0;
        }
    }

    @Override
    protected void paintWidget() {
        
        Graphics2D g = this.getGraphics();
        g.setColor(this.getForeground());
        for (int i=0;i<array.length-1;i++) {
          g.drawLine(i, 255-array[i], i+1, (255-array[i+1]));
        }
       // getScene().validate(g);
       
    }

    @Override
    protected Rectangle calculateClientArea() {
        return new Rectangle(260, 260);
    }

    @Override
    public void tick() {
        getScene().repaint();
        step = (step+1)%255;
        if (this.getInput()!=null) {
            array[step] = this.getInput().getVal();
        }
        
        
    }

    
    

}
