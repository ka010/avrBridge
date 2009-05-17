/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package avrBridgeJavaDev.graph;

import avrBridgeJavaDev.graph.widgets.genericWidget;
import avrBridgeJavaDev.graph.widgets.inputPinWidget;
import avrBridgeJavaDev.graph.widgets.outputPinWidget;
import avrBridgeJavaDev.graph.widgets.pinWidget;
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
        String src;
        String target;
        String counter = String.valueOf(scene.getEdgeCounter());
        String edge = "edge"+counter;
        ConnectionWidget con = (ConnectionWidget) scene.addEdge(edge);

        genericWidget gwSrc = ((genericWidget) arg0).getParent();
        genericWidget gwTarget = ((genericWidget) arg1).getParent();
        System.out.println(gwSrc.getId());
        gwTarget.addInput(gwSrc);
         scene.attachEdgeSourceAnchor(edge, gwSrc.getId(),arg0);
         scene.attachTargetSourceAnchor(edge, gwTarget.getId(),arg1);

    }

}
