package com.asierso.vortexengine.window;

import com.asierso.vortexengine.sceneObjects.GameObject;
import java.util.ArrayList;

/**
 *
 * @author Asierso
 */
public class Layer extends ArrayList<GameObject> {
    public void instantiate(Window win){
        for(GameObject handle : this)
            handle.instantiate(win);
    }
    public GameObject[] getGameObjects(Class type){
        return (GameObject[])this.stream().filter(obj -> obj.getClass() == type).toArray();
    }
}