/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package avrBridgeJavaDev;

import avrBridgeJavaDev.graph.avrScene;
import avrBridgeJavaDev.util.SceneSupport;

/**
 *
 * @author blahlol
 */
public class sceneTest {

    public static void main(String[] args) {
     avr m8 = new avr();
     m8.test();
     avrScene scene = new avrScene(m8);
     SceneSupport.show(scene);
    }

}
