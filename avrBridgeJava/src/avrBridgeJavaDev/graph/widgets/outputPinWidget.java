/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package avrBridgeJavaDev.graph.widgets;

import avrBridgeJavaDev.avr;
import avrBridgeJavaDev.graph.avrScene;
import avrBridgeJavaDev.graph.avrSceneConnectProvider;
import javax.swing.BorderFactory;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author blahlol
 */
public class outputPinWidget extends genericWidget {

    public outputPinWidget(Scene sc, String id, avr m8,Widget parent) {
        super(sc,id,m8,parent);
        this.setBorder(BorderFactory.createEmptyBorder());
        this.setTitle("[o]");
        avrScene scene = (avrScene) sc;

        this.getActions().addAction(ActionFactory.createConnectAction(scene.getConnectionLayer(), new avrSceneConnectProvider(sc)));

    }

}
