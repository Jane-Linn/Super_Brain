/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scene;

import controllers.SceneController;
import io.CommandSolver.KeyCommandListener;
import io.CommandSolver.MouseCommandListener;
import io.CommandSolver.TypedListener;
import java.awt.Graphics;

public abstract class Scene{
    protected SceneController sceneController;
    
    public Scene(SceneController sceneController){
        this.sceneController = sceneController;
    }
    
    public abstract void sceneBegin();//Scene變成currentScene
    public abstract void sceneUpdate();//做這個Scene要做的事
    public abstract void sceneEnd();//結束這個Scene
    public abstract void paint(Graphics g);
    public KeyCommandListener getKeyCommandListener(){return null;}
    public MouseCommandListener getMouseCommandListener(){return null;}    
    public TypedListener getTypedListener(){return null;}   
}
