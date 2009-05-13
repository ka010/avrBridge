/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package avrBridgeJavaDev.graph;

import avrBridgeJavaDev.graph.widgets.genericWidget;
import avrBridgeJavaDev.graph.widgets.inputPinWidget;
import avrBridgeJavaDev.graph.widgets.outputPinWidget;
import java.awt.Point;
import org.netbeans.api.visual.action.ConnectProvider;
import org.netbeans.api.visual.action.ConnectorState;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author blahlol
 */
public class avrSceneConnectProvider implements ConnectProvider {
private avrScene scene;
    public avrSceneConnectProvider(Scene sc) {
        scene =(avrScene) sc;
    }

    public avrSceneConnectProvider() {
    }

    

    public boolean isSourceWidget(Widget arg0) {
        if (arg0 instanceof outputPinWidget) return true;
        else return false;
    }

    public ConnectorState isTargetWidget(Widget arg0, Widget arg1) {
        if (arg0 instanceof outputPinWidget && arg1 instanceof inputPinWidget) return ConnectorState.ACCEPT;
        else return ConnectorState.REJECT;
    }

    public boolean hasCustomTargetWidgetResolver(Scene arg0) {
        return false;
    }

    public Widget resolveTargetWidget(Scene arg0, Point arg1) {
        return null;
    }

    public void createConnection(Widget arg0, Widget arg1) {

        String counter = String.valueOf(scene.getEdgeCounter());
        String edge = "edge"+counter;
        ConnectionWidget con = (ConnectionWidget) scene.addNode(edge);

        genericWidget gwSrc = (genericWidget) arg0;
        genericWidget gwTarget = (genericWidget) arg1;
        scene.setEdgeSource(edge, gwSrc.getId());
        scene.setEdgeTarget(edge, gwTarget.getId());

    }

}
