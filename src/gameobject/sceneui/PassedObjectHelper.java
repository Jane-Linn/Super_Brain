/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobject.sceneui;

import controllers.ImageResourceController;
import controllers.PathBuilder;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import values.ImagePath;

/**
 *
 * @author Wan Zhen Lin
 */
public class PassedObjectHelper {

    private BufferedImage img;
    private int gameLevel;//你要第幾個關卡

    //建構子
    //所有關卡的圖都放一起 一張圖有8關的
    public PassedObjectHelper(int width, int height, int number) {
        img = getActor(number);  // 依照使用者指定的number去抓
        gameLevel = number % 8;  // 計算這個number在圖中的位置(哪一關的圖)
    }

    private BufferedImage getActor(int gameLevel) {//得到哪張原圖
        ImageResourceController irc = ImageResourceController.getInstance();
        if (gameLevel >= 0 && gameLevel < 8) {
            return irc.tryGetImage(PathBuilder.getImg(ImagePath.Background.PassObject.PASSEDOBJECT1));
        }
//        if (gameLevel < 16) {
//            return irc.tryGetImage(PathBuilder.getImg(ImagePath.Character.Actor.A2));
//        }
//        if (gameLevel < 24) {
//            return irc.tryGetImage(PathBuilder.getImg(ImagePath.Character.Actor.A3));
//        }
        return null;
    }

    //選擇圖
    //這裡的x, y , width, height都是從camera傳入的，是修改過的相對座標和相對大小
    //column 是你要這一關的哪一個小圖(x) (在外面呼叫的時候給予-->txt檔)0~3
    //line   是你要這一關的哪一個小圖(y) (在舀面呼叫的時候給予--txt檔)0~3
    public void paintPassedObject(Graphics g, int x, int y, int width, int height, int column, int line) {
        if (img == null) {
            return;
        }
        //(dx,dy)這一關的左上角point
        int dx = 50 * (gameLevel % 4);
        int dy = 50 * (gameLevel / 4);
        g.drawImage(img, x, y, x + width, y + height,
                dx + column * 50, dy + line * 50,
                dx + 50 + column * 50, dy + 50 + line * 50, null);
    }
}
