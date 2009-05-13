/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package avrBridgeJavaDev.graph;

import avrBridgeJavaDev.avr;
import avrBridgeJavaDev.graph.widgets.adcWidget;
import avrBridgeJavaDev.graph.widgets.constantWidget;
import avrBridgeJavaDev.graph.widgets.dacWidget;
import avrBridgeJavaDev.graph.widgets.displayWidget;
import avrBridgeJavaDev.graph.widgets.genericWidget;
import avrBridgeJavaDev.graph.widgets.pinWidget;
import avrBridgeJavaDev.graph.widgets.portWidget;
import avrBridgeJavaDev.graph.widgets.timerWidget;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.anchor.Anchor;
import org.netbeans.api.visual.anchor.AnchorFactory;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.layout.SceneLayout;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.EventProcessingType;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author blahlol
 */


public class avrScene extends GraphScene<String, String> {
    private LayerWidget backgroundLayer;
    private LayerWidget mainLayer;
    private LayerWidget connectionLayer;
    private LayerWidget interactionLayer;

    private SceneLayout scene_layout;
    private Scene scene;
    private int widgetCounter=0;
    private int edgeCounter=0;

    private avr m8;

    private WidgetAction selectAction = createSelectAction ();
    private WidgetAction hoverAction = createObjectHoverAction ();
    //private WidgetAction moveAction =(ActionFactory.createMoveAction (ActionFactory.createSnapToGridMoveStrategy (16, 16),new MultiMoveProvider()));
    private WidgetAction moveAction2 =ActionFactory.createMoveAction();
    public avrScene(avr m8) {
        this.m8 =m8;
        backgroundLayer = new LayerWidget(this);
        mainLayer = new LayerWidget(this);
        connectionLayer = new LayerWidget(this);
        interactionLayer = new LayerWidget(this);
        scene = this;
        scene.setKeyEventProcessingType(EventProcessingType.ALL_WIDGETS);
        this.addChild(backgroundLayer);
        this.addChild(mainLayer);
        this.addChild(interactionLayer);
        this.addChild(connectionLayer);

        this.addNode("portB");
        this.addNode("portC");
        this.addNode("portD");

        this.addNode("display0");
        this.addNode("timer0");
        this.addNode("dac0");
        this.addNode("adc0");
    }


    @Override
    protected Widget attachNodeWidget(String node) {
       genericWidget gw=null;

       if(node.startsWith("port")) gw = new portWidget(this,node,this.m8);
       else if(node.startsWith("pin")) gw = new pinWidget(this,node,this.m8);
       else if(node.startsWith("timer")) gw = new timerWidget(this,node,this.m8);
       else if(node.startsWith("display")) gw = new displayWidget(this,node,this.m8);
       else if(node.startsWith("constant")) gw = new constantWidget(this,node,this.m8);
       else if(node.startsWith("dac")) gw = new dacWidget(this,node,this.m8);
       else if(node.startsWith("adc")) gw = new adcWidget(this,node,this.m8);

       gw.getActions().addAction(moveAction2);
       this.mainLayer.addChild(gw);
       return gw;

    }

    @Override
    protected Widget attachEdgeWidget(String edge) {
        ConnectionWidget con = new ConnectionWidget(this);
        connectionLayer.addChild (con);


        return con;
    }

    @Override
    protected void attachEdgeSourceAnchor(String edge, String arg1, String sourceNode) {
         Widget src = this.findWidget(sourceNode);
         ConnectionWidget edgeWidget = (ConnectionWidget) this.findWidget(edge);
        Anchor sourceAnchor = AnchorFactory.createCenterAnchor(src);
        edgeWidget.setSourceAnchor(sourceAnchor);

    }

    @Override
    protected void attachEdgeTargetAnchor(String arg0, String arg1, String arg2) {
       Anchor targetAnchor = null;
       Widget target = this.findWidget(arg2);
       targetAnchor = AnchorFactory.createCenterAnchor(target);
       ConnectionWidget edgeWidget = (ConnectionWidget) this.findWidget(arg0);
       edgeWidget.setTargetAnchor(targetAnchor);
    }

    public int getEdgeCounter() {
        return edgeCounter;
    }

    public int getWidgetCounter() {
        return widgetCounter;
    }

    public void setEdgeCounter(int edgeCounter) {
        this.edgeCounter = edgeCounter;
    }

    public void setWidgetCounter(int widgetCounter) {
        this.widgetCounter = widgetCounter;
    }

    public LayerWidget getBackgroundLayer() {
        return backgroundLayer;
    }

    public void setBackgroundLayer(LayerWidget backgroundLayer) {
        this.backgroundLayer = backgroundLayer;
    }

    public LayerWidget getConnectionLayer() {
        return connectionLayer;
    }

    public void setConnectionLayer(LayerWidget connectionLayer) {
        this.connectionLayer = connectionLayer;
    }

    public WidgetAction getHoverAction() {
        return hoverAction;
    }

    public void setHoverAction(WidgetAction hoverAction) {
        this.hoverAction = hoverAction;
    }

    public LayerWidget getInteractionLayer() {
        return interactionLayer;
    }

    public void setInteractionLayer(LayerWidget interactionLayer) {
        this.interactionLayer = interactionLayer;
    }

    public avr getM8() {
        return m8;
    }

    public void setM8(avr m8) {
        this.m8 = m8;
    }

    public LayerWidget getMainLayer() {
        return mainLayer;
    }

    public void setMainLayer(LayerWidget mainLayer) {
        this.mainLayer = mainLayer;
    }

    public WidgetAction getMoveAction2() {
        return moveAction2;
    }

    public void setMoveAction2(WidgetAction moveAction2) {
        this.moveAction2 = moveAction2;
    }

    public SceneLayout getScene_layout() {
        return scene_layout;
    }

    public void setScene_layout(SceneLayout scene_layout) {
        this.scene_layout = scene_layout;
    }

    public WidgetAction getSelectAction() {
        return selectAction;
    }

    public void setSelectAction(WidgetAction selectAction) {
        this.selectAction = selectAction;
    }



}
