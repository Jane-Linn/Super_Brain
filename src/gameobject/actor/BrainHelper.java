/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobject.actor;

import controllers.ImageResourceController;
import controllers.PathBuilder;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import utils.Global;
import values.ImagePath;

/**
 *
 * @author Wan Zhen Lin
 */
public class BrainHelper {
     private BufferedImage img;
     private int realColorNum;
     private int colorNum ;


    //建構子
    //actor --> 左腦(1) 右腦(2)
    public BrainHelper(int width, int height, int whichBrain, int colorNum) {
        img = getActor(whichBrain);  // 依照使用者指定的Actor去抓
      
        this.colorNum = colorNum; //大圖的哪一個顏色的腦(粉(0)黑(1)白(2)紅(3)黃(4)綠(5)藍(6)
    }

    private BufferedImage getActor(int whichBrain) {//得到哪張原大圖
        ImageResourceController irc = ImageResourceController.getInstance();
        if (whichBrain ==1) {
           
            return irc.tryGetImage(PathBuilder.getImg(ImagePath.Character.Brain.LEFT_BRAIN1));
            
        }if(whichBrain==2){
         
            return irc.tryGetImage(PathBuilder.getImg(ImagePath.Character.Brain.RIGHT_BRAIN1));
          
        }if(whichBrain ==3){
        
            return irc.tryGetImage(PathBuilder.getImg(ImagePath.Character.Brain.WHOLE_BRAIN1));
          
        }
//以下看之後是否要擴充有不同角色圖(肌肉型 萎縮型....)
//        if (actor < 16) {
//            return irc.tryGetImage(PathBuilder.getImg(ImagePath.Character.Actor.A2));
//        }
//        if (actor < 24) {
//            return irc.tryGetImage(PathBuilder.getImg(ImagePath.Character.Actor.A3));
//        }
        return null;
    }
    
    public void setColorNumber(int newColorNum ){
        this.colorNum = newColorNum;
    }
  
    // 動畫效果
    public void paintAnimation(Graphics g, int x, int y, int width, int height, int act) {
        if (img == null) {
            return;
        }
         int dx = 0;
        int dy = Global.Actor.ACTOR_Y_OFFSET * (colorNum);//(和腦是0, 左腦是1, 右腦是2)
        g.drawImage(img, x, y, x + width, y + height,
                dx + act *Global.Actor.ACTOR_X_OFFSET , dy,
                dx + Global.Actor.ACTOR_X_OFFSET + act *Global.Actor.ACTOR_X_OFFSET , dy + Global.Actor.ACTOR_Y_OFFSET , null);
    }
}
