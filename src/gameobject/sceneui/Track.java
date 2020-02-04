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
public class Track extends GameObject {

    private static final int[] ACT = {0, 1};
    private int actIndex;
    private int projection;//有無投影
    private int gameLevel;//關卡 第幾關
    private int carNum;
    private TrackHelper trackHelper;
    private DelayCounter delayCounter;
//    private boolean isCollisioned;

    //Track(在跑道上的x座標, 在跑道上的y座標, 印出的高, 印出的寬, 哪一關, 有無投影,一台還是兩台車)
    public Track(int x, int y, int width, int height, int gameLevel, int projection, int carNum) {
        super(x, y, width, height);
        this.gameLevel = gameLevel;//原大圖的x和y
        this.projection = projection;// 0 or 1(原小圖的x)
        this.carNum = carNum;//0 or 1(原小圖的y)
        delayCounter = new DelayCounter(4);
        trackHelper = new TrackHelper(width, height, gameLevel);
        actIndex = 0;
//        isCollisioned = false;
    }

    //換關換車道樣式
    public void changeTrackType(int number) {
        trackHelper.changeTrack(number);
    }

    //切換一台or兩台車
    //0是一台 1是兩台
    public void changeCarNum(int number) {
        this.carNum = number;
    }

    //切換有投影無投影
    //無投影 0 有投影 1 
    public void changeProjection(int number) {
        this.projection = number;
    }

    public void changeDelay(int i) {
        delayCounter.setdelay(i);
    }

    //每刷新一次要讓車道會動
    public void trackMove() {
        if (delayCounter.update()) {
            actIndex = ++actIndex % 2;
        }

    }

    public void setCarNum(int carNum) {
        this.carNum = carNum;
    }

//    //判斷是否有被碰撞了
//    public void setisCollisioned(int yesOrNo) {
//        if (yesOrNo == 1) {
//            isCollisioned = true;
//        } else {
//            isCollisioned = false;
//        }
//    }
//    public boolean getisCollisioned() {
//        return isCollisioned;
//    }
//    public void effect(Bar bloodBar) {
//        bloodBar.minusX();
//    }
    @Override
    public void paint(Graphics g) {
        trackHelper.paintTrack(g, x, y, width, height, projection, carNum, ACT[actIndex]);
    }

}
