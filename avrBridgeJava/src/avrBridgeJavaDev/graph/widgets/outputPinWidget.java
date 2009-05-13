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

/**
 *
 * @author blahlol
 */
public class outputPinWidget extends genericWidget {

    public outputPinWidget(Scene sc, String id, avr m8) {
        super(sc,id,m8);
        this.setBorder(BorderFactory.createEmptyBorder());
        this.setTitle("[o]");
        avrScene scene = (avrScene) sc;

        this.getActions().addAction(ActionFactory.createConnectAction(scene.getConnectionLayer(), new avrSceneConnectProvider(sc)));

    }

}
