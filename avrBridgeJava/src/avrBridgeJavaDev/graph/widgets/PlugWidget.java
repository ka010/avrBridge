package avrBridgeJavaDev.graph.widgets;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;

import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.ConnectProvider;
import org.netbeans.api.visual.action.ConnectorState;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.anchor.AnchorFactory;
import org.netbeans.api.visual.anchor.AnchorShape;
import org.netbeans.api.visual.router.RouterFactory;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;


/**
 *
 * @author nacho
 */
public abstract class PlugWidget extends Widget {


   private Scene scene;
    //private MaidDOM dom = MaidDOM.getInstance();
    
    private WidgetAction connectAction;
    private WidgetAction reconnectAction;
    //private WidgetAction reconnectAction = ActionFactory.createReconnectAction(new SceneReconnectProvider());

    private double radius = 5;
    
    private LayerWidget interactionLayer;
    private LayerWidget connectionLayer;

    private Widget parent;



    /**
     *
     * @param scene
     */
    public PlugWidget(Scene scene, Widget parent) {
        super(scene);
        this.scene = scene;
        this.parent = parent;

       // connectAction = ActionFactory.createConnectAction(interactionLayer, new SceneConnectProvider(scene));
//        reconnectAction = ActionFactory.createReconnectAction(new SceneReconnectProvider());
        
        getActions().addAction(connectAction);
//        getActions().addAction(reconnectAction);
    }
    
    // ------------------------------------------------------------------------
    // ---------   ABSTRACT CLASSES  ------------------------------------------
    // ------------------------------------------------------------------------
    /**
     *
     * @param sourceWidget
     * @param targetWidget
     * @return
     */
    protected abstract ConnectorState isTarget(Widget sourceWidget, Widget targetWidget);

    public Widget getParent() {
        return parent;
    }

    public void setParent(Widget parent) {
        this.parent = parent;
    }
    // ------------------------------------------------------------------------

 
    

    
    // ------------------------------------------------------------------------
    
    /**
     *
     * @return
     */
    @Override
    protected Rectangle calculateClientArea() {
        int r = (int) Math.ceil(radius);
        return new Rectangle(-r, -r, 2 * r + 1, 2 * r + 1);
    }
    
    /**
     *
     */
    @Override
    protected void paintWidget() {
        int r = (int) Math.ceil(radius);
        Graphics2D g = getGraphics();
        g.setColor(getForeground());
        g.drawOval(-r, -r, 2 * r, 2 * r);
    }
}
