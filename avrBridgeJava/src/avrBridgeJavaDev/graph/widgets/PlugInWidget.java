/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package avrBridgeJavaDev.graph.widgets;

import org.netbeans.api.visual.action.ConnectorState;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author nacho
 */
public class PlugInWidget extends PlugWidget {
    
    /**
     *
     * @param scene
     */
    public PlugInWidget(Scene scene, Widget parent) {
        super(scene, parent);
        
        
    }

    /**
     *
     * @param sourceWidget
     * @param targetWidget
     * @return
     */
    @Override
    protected ConnectorState isTarget(Widget sourceWidget, Widget targetWidget) {
        if ((sourceWidget instanceof PlugInWidget && targetWidget instanceof PlugOutWidget)
                && (sourceWidget.getParentWidget().getParentWidget() != targetWidget.getParentWidget().getParentWidget())
                
                ) {
            return ConnectorState.ACCEPT;
        } else {
            return ConnectorState.REJECT;
        }
    }
}
