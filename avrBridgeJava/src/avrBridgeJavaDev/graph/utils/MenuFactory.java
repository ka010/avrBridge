/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package avrBridgeJavaDev.graph.utils;

import java.awt.event.ActionListener;
import javax.swing.JMenuItem;

/**
 *
 * @author blahlol
 */
public  class MenuFactory {
    public static JMenuItem createMenuItem(String title, String cmd, ActionListener listener) {
        JMenuItem item = new JMenuItem(title);
        item.setActionCommand(cmd);
        item.addActionListener(listener);
        return item;
    }
}
