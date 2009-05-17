/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package avrBridgeJavaDev.graph.menus;

import avrBridgeJavaDev.graph.avrScene;
import avrBridgeJavaDev.graph.utils.MenuFactory;
import avrBridgeJavaDev.graph.widgets.genericWidget;
import java.awt.Point;
import java.awt.event.ActionEvent;
import javax.swing.JPopupMenu;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author blahlol
 */
public class pinMenu extends genericMenu {
    private JPopupMenu menu;
    private genericWidget widget;
    public pinMenu() {
        this.menu = new JPopupMenu("pinMenu");
        menu.add(MenuFactory.createMenuItem("make Output", "MAKE_OUTPUT", this));
        menu.add(MenuFactory.createMenuItem("make Input", "MAKE_INPUT", this));
        menu.add(MenuFactory.createMenuItem("make ADC", "MAKE_ADC", this));
        menu.add(MenuFactory.createMenuItem("make DAC", "MAKE_DAC", this));
    }


    public JPopupMenu getPopupMenu(Widget arg0, Point arg1) {
        widget = (genericWidget) arg0;
        
        return menu;
    }

    public void actionPerformed(ActionEvent e) {
        avrScene scene = (avrScene) widget.getScene();
        int pin=0;
        int port=0;
        String tmp = String.valueOf(widget.getId().charAt(3));
        pin = Integer.parseInt(tmp);

        String prt = widget.getParent().getId();
        if(prt.endsWith("B")) port=0;
        else if(prt.endsWith("C")) port =1;
        else if(prt.endsWith("D")) port = 2;
        if (e.getActionCommand().equals("MAKE_OUTPUT")) {
            port = widget.getAvrPort();
            System.out.println(pin);
            widget.getM8().setPortPinDir(port, pin,widget.getM8().OUT);
             if (scene.findWidget("adc"+pin)!=null) scene.removeNode("adc"+pin);
        }
        else if (e.getActionCommand().equals("MAKE_INPUT")) {
            System.out.println("in " +pin + port);
            widget.getM8().setPortPinDir(port, pin,widget.getM8().IN);
            if (scene.findWidget("adc"+pin)!=null) scene.removeNode("adc"+pin);
        }
        else if (e.getActionCommand().equals("MAKE_ADC")) {
            System.out.println("acd" + pin + port);
            widget.getM8().setPortPinDir(port, pin,widget.getM8().OUT);
            widget.getM8().setPortPin(port, pin,widget.getM8().LOW);
            scene.addNode("adc"+pin);

        }
    }

}
