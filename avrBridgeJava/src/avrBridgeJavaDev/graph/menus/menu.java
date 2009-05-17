/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package avrBridgeJavaDev.graph.menus;

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
public class menu extends genericMenu {
private JPopupMenu menu;
private genericWidget widget;

    public menu() {
        this.menu = new JPopupMenu("");
        menu.add(MenuFactory.createMenuItem("addInput", "ADD_INPUT", this));
    }


    public JPopupMenu getPopupMenu(Widget arg0, Point arg1) {
       if (arg0 instanceof genericWidget) widget = (genericWidget) arg0;
      
       return menu;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("ADD_INPUT")) widget.addInputPin();
    }

}
