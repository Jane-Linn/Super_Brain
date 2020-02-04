/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobject.ball;

import controllers.ImageResourceController;
import controllers.PathBuilder;
import gameobject.sceneui.Text;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import utils.Global;
import values.ImagePath;

/**
 *
 * @author Wan Zhen Lin
 */
public class BallHelper{
    private BufferedImage img;
    private int imageY;//你要大圖哪一行(y)
    private int imageX;//你要甚麼顏色的球(大圖的x)
    //建構子
    //所有關卡的圖都放一起 一張圖有4關的
    //從外面傳入imageX, imageY 表示要哪顆球(0,0) ~ (4,4)
    public BallHelper(int width, int height, int colorNum) {
        img = getActor(colorNum);  // 依照使用者指定的number去抓
        this.imageY = colorNum /5;  // 計算這個number在圖中的位置(哪一關的圖)
        this.imageX = colorNum %5; //第0、1排-->彩球(0) 顏色(1~4) //第2排--> 色球(0~4)
    }

    private BufferedImage getActor(int colorNum) {//得到哪張原圖
        ImageResourceController irc = ImageResourceController.getInstance();
        if (colorNum >= 0 && colorNum < 24) {
            return irc.tryGetImage(PathBuilder.getImg(ImagePath.Character.ElementPic.E1));
        }
//        if (gameLevel < 16) {
//            return irc.tryGetImage(PathBuilder.getImg(ImagePath.Character.Actor.A2));
//        }
//        if (gameLevel < 24) {
//            return irc.tryGetImage(PathBuilder.getImg(ImagePath.Character.Actor.A3));
//        }
        return null;
    }
    //想要隨機產生球的顏色 或是敵人使用技能變色
    public void changeColor(int colorNum){
        this.imageX = colorNum%5;
        this.imageY = colorNum/5;
    }

    
    //選擇圖
    //這裡的x, y , width, height都是從camera傳入的，是修改過的相對座標和相對大小
    //column 哪一關(y) (在外面呼叫的時候給予-->txt檔)0~3
    //line   是你要這一關的哪一個小圖(哪一個顏色)(X) (在舀面呼叫的時候給予--txt檔)0~3
    public void paintElement(Graphics g, int x, int y, int width, int height ) {
        if (img == null) {
            return;
        }
        //(dx,dy)這一關的左上角point
        int dx = Global.Element.ELEMENT_X_OFFSET * (imageX);
        int dy = Global.Element.ELEMENT_Y_OFFSET  * (imageY);
        g.drawImage(img, x, y, x + width, y + height,
                dx , dy ,
                dx +  Global.Element.ELEMENT_X_OFFSET  , dy + Global.Element.ELEMENT_Y_OFFSET , null);
       
    }

}
