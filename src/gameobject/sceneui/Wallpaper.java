/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobject.sceneui;

import gameobject.GameObject;
import java.awt.Graphics;
import utils.DelayCounter;

/**
 *
 * @author Wan Zhen Lin
 */
public class Wallpaper extends GameObject {

    private int[] PIC_NUM = {0, 1, 2, 3, 4};
    private int picNumIndex;
    private int projection;//有無投影
    private int gameLevel;//關卡 第幾關
    private WallpaperHelper wallpaperHelper;
    private DelayCounter delayCounter;

//Element(在跑道上的x座標, 在跑道上的y座標, 印出的高, 印出的寬, 哪一關)
    public Wallpaper(int x, int y, int width, int height, int gameLevel, int projection) {
        super(x, y, width, height);
        this.gameLevel = gameLevel;
        this.projection = projection;
        picNumIndex = 0;
        delayCounter = new DelayCounter(4);
        wallpaperHelper = new WallpaperHelper(width, height, gameLevel);//用哪一關選好大圖(每一關的圖畫不同圖)
    }
    //動畫
    public void animation() {
        if (delayCounter.update()) {
            picNumIndex = ++picNumIndex % 3;    // 0 1 2
        }
    }
    //投影
    public void changeProjection(int number) {//無投影 0 有投影 1 
        this.projection = number;
    }

    @Override
    public void paint(Graphics g) {
        wallpaperHelper.paintWall(g, x, y, width, height, picNumIndex, projection);//用picNumIndex決定要哪一個動畫的分鏡 , projection 表示圖的視角
        // wallpaperHelper待改
    }

}
