/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobject.sceneui;

import java.awt.Graphics;
import java.util.ArrayList;
import utils.DelayCounter;
import utils.Global;

/**
 *
 * @author Wan Zhen Lin
 */
//可能會受的影響:關卡(同一關內也可以自己有動畫) & 有沒有投影 & OneCar or TwoCar & 玩家人數
//待整理!!!!!!!!!!!!!!!!!!!!!!!!!!!
public class BackgroundImg {

    //放背景圖 道路圖 背景物
    private Track track;
    private Wallpaper wallpaper;
    private int gameLevel;//第幾關
    private int carNum;//一台 = 0; 兩台 = 1;//可以從GameScene接到指令後修改
    private int projectionOrNot;//無投影 = 0; 有投影 = 1;
    private DelayCounter delayCounter;

    public BackgroundImg(int gameLevel,int projectionOrNot,int playerNum,boolean oneBrain) {
        gameLevel = 0;
        if(oneBrain == true){
        carNum = 0;
        }else{
            carNum = 1;
        }
        delayCounter = new DelayCounter(Global.Actor.NORMAL_DELAY);
        this.projectionOrNot = projectionOrNot;
        //Track(在跑道上的x座標, 在跑道上的y座標, 印出的高, 印出的寬, 哪一關, 有無投影,一台還是兩台車)
        track = new Track(0, 0, 800, 600, gameLevel, projectionOrNot, carNum);
        //wallpaper(在跑道上的x座標, 在跑道上的y座標, 印出的高, 印出的寬, 哪一關)
        wallpaper = new Wallpaper(0, 0, Global.SceneValue.FRAME_X_OFFSET, Global.SceneValue.FRAME_Y_OFFSET, gameLevel,projectionOrNot);
 
    }

//    //影響所有 現在沒有要關中換關
//    public void changeLevel(int i) {//第幾關(0開始)
//        gameLevel = i;
//        wallpaper.changeNumber(i);
//        track.changeTrackType(i);
//        passedObjects = setPassedObject(i);
//    }

    //換操作方法
    //影響車道的線道 和鍵盤指令
    public void changeTrackType(int i) {//一台車(0)或是兩台車(1)(鍵盤的指令要不同)&(車道線要不同)
        track.changeCarNum(i);
    }

    public void changeProjrction(int i) {//無投影(0)或是有投影(1)(鍵盤的指令要不同)&(車道線要不同)
        wallpaper.changeProjection(i);
        track.changeProjection(i);
    }



    public void changeCarNum(int carNum) {
        this.carNum = carNum;
        track.changeCarNum(carNum);
    }

    public void changeDelay(int i) {
        track.changeDelay(i);
    }

    //每次更新要做的事
    public void update() {
        wallpaper.animation();
        track.trackMove();
    }

    //每次更新
    //直接用物件的method(setter們換，不用另外再new新實體)
//    public void update() {
//  好像也不用特別寫(在外面遇到要更換的時機直接呼叫這個CLASS的其他method直接改值並印出)
    public void paint(Graphics g) {
        wallpaper.paint(g);
        track.paint(g);
    }
////passedObject直接經camera出來
//    public void paint(int index, int x, int y, int width, int height,Graphics g) {
//           passedObjects.get(index).paint(x, y, width, height, g);
//    }
}
