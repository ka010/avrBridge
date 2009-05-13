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
public class PlugOutWidget extends PlugWidget {
    
    /**
     *
     * @param scene
     */
    public PlugOutWidget(Scene scene, Widget parent) {
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
        if ((sourceWidget instanceof PlugOutWidget && targetWidget instanceof PlugInWidget)
                && (sourceWidget.getParentWidget().getParentWidget() != targetWidget.getParentWidget().getParentWidget())
                
                ) {
            return ConnectorState.ACCEPT;
        } else {
            return ConnectorState.REJECT;
        }
    }
    
}
