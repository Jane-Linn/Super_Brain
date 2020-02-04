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
public class ButtonHelper {

    private BufferedImage img;
    private int actorPosition;

    //建構子
    public ButtonHelper(int width, int height, int actor) {
        img = getActor(actor);// 依照使用者指定的Actor去抓圖
        actorPosition = actor;// 計算這個actor在圖中的位置
    }

    private BufferedImage getActor(int actor) {//得到哪張原圖
        ImageResourceController irc = ImageResourceController.getInstance();
        if (actor >= 0 && actor < 16) {
            return irc.tryGetImage(PathBuilder.getImg(ImagePath.UI.Button.BUTTON));
        }
//        if (actor < 16) {
//
//        }
//        if (actor < 24) {
//        }
        return null;
    }

    // 動畫效果
    //這裡的x, y , width, height都是從camera傳入的，是修改過的相對座標和相對大小
    public void paintButton(Graphics g, int x, int y, int width, int height) {
        if (img == null) {
            return;
        }
        int dx = 216 * (actorPosition % 4);
        int dy = 117 * (actorPosition / 4);
        g.drawImage(img, x, y, x + width, y + height,
                dx, dy,
                dx + 216, dy + 117, null);
    }
}
