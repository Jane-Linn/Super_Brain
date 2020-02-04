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
import utils.Global;
import values.ImagePath;

/**
 *
 * @author Wan Zhen Lin
 */
//八個關卡的道路畫在一起 
//每個關卡有兩種道路(沒投影跟有投影)
public class TrackHelper {
    private BufferedImage img;
    private int gameLevel;
   

    //建構子
    public TrackHelper(int width, int height, int number) {
        img = getWall(number);  // 依照使用者指定的number去抓大圖
       gameLevel = number % 4;  // 計算這個number在大圖中的位置
       
    }

    private BufferedImage getWall(int number) {//得到哪張原圖
        ImageResourceController irc = ImageResourceController.getInstance();
        if (number >= 0 && number < 4) {
            return irc.tryGetImage(PathBuilder.getImg(ImagePath.Background.Track.TRACK1));
        }
//        if (number < 16) {
//            return irc.tryGetImage(PathBuilder.getImg(ImagePath.Character.Actor.A2));
//        }
//        if (number < 24) {
//            return irc.tryGetImage(PathBuilder.getImg(ImagePath.Character.Actor.A3));
//        }
        return null;
    }
    
    //換關換樣式
    public void changeTrack(int gameLevel){
        img = getWall(gameLevel);
    }
    

    //如果經過camera就要用透式的道路圖
    //沒有經過camera就不用透式的道路圖
    //projection 沒投影是0 有投影是1
    //carNum 一台是0;兩台是1;
    //act是讓現有往下移的感覺
    public void paintTrack(Graphics g, int x, int y, int width, int height, int projection, int carNum, int act) {
        if (img == null) {
            return;
        }
        int dx = 3200 * (gameLevel % 2);
        int dy = 1200* (gameLevel / 2);
        g.drawImage(img, x, y, x + width, y + height,
                dx+ projection*1600+act*800, dy+carNum*600,
                dx +800+projection*1600+act*800, dy + 600+600*carNum, null);
    }
}
