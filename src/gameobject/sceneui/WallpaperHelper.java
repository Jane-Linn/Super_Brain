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
public class WallpaperHelper {
//8關畫在一起

    private BufferedImage img;
    private int wallPosition;//哪一關

    //建構子
    public WallpaperHelper(int width, int height, int number) {
        img = getWall(number);  // 依照使用者指定的Actor去抓圖
        wallPosition = number % 8;  // 計算這個actor在圖中的位置
    }

    private BufferedImage getWall(int number) {//得到哪張原圖
        ImageResourceController irc = ImageResourceController.getInstance();
        if (number >= 0 && number < 8) {
            return irc.tryGetImage(PathBuilder.getImg(ImagePath.Background.GameScene.GAMESCENE));
        }
//        if (number < 16) {
//            return irc.tryGetImage(PathBuilder.getImg(ImagePath.Character.Actor.A2));
//        }
//        if (number < 24) {
//            return irc.tryGetImage(PathBuilder.getImg(ImagePath.Character.Actor.A3));
//        }
        return null;
    }

    //不用經過camera修改
    //依遊戲場景指定的projection選擇分鏡的視角
    //依Wallpaper指定的動畫分鏡選小圖                                   // 0 1 2
    public void paintWall(Graphics g, int x, int y, int width, int height, int picNumIndex, int projection) {
        if (img == null) {
            return;
        }
        g.drawImage(img, x, y, x + width, y + height,
                800 * picNumIndex, 0, 800 * picNumIndex + 800, 600, null);
    }
//    paint待改
}
