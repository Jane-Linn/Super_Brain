/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobject.ball;

import gameobject.GameObject;
import gameobject.sceneui.Text;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import utils.DelayCounter;
import utils.Global;

/**
 *
 * @author Wan Zhen Lin
 */
public class SlowDown extends GameObject{//ball1.png(15,16)
    private static int[] Act = {15,16};
    private int actIndex;
     private DelayCounter delayCounter;
    private BallHelper ballHelper;
    private int gameLevel;//第幾關-->每一關都是這顆球~顏色不同
    private boolean isCollisioned;//是否做過效果了(不一定有被碰撞到)
    private String name;//名字(用於ElementEffect
    private boolean needPrint;//如果被撞到就要變false 離開frame再重設

//(印出的x座標(左右輪流)，印出的y座標(從大到小)，印出的寬，印出的高，印出大圖的第幾排，那一排的第幾個(選顏色))
//色球ballNumber只能是-->1,2,6,7,8,9(色球不能是第一個)

    public SlowDown(int x, int y, int width, int height, int gameLevel, int ballNumber) {
        super(x, y, width, height);
        ballHelper = new BallHelper(width, height, ballNumber);
        isCollisioned = false;
        needPrint = true;
        this.gameLevel = gameLevel;
        name = Global.Element.SLOW_DOWN;//在碰撞判斷時使用
        delayCounter = new DelayCounter(4);
        actIndex = 0;
    }

    //getter
    public int getGameLevel() {
        return gameLevel;
    }

    @Override
    public String getName() {
        return name;
    }


    //判斷是否有被碰撞了start
    //1.從外面判斷是否被角色碰撞了
    //2,如果撞到了要確認是否做過效果了，從這裡傳出被碰撞的狀態給外面
    @Override
    public boolean getisCollisioned() {
        return isCollisioned;//一開始是false
    }

    @Override
    public void changeNeedPrint(boolean boo) {//超出frame時重新設定要不要印
        needPrint = boo;
    }

    @Override
    public boolean getNeedPrint() {
        return needPrint;
    }


//    被碰撞後要做甚麼效果由每一個element自己決定(創不同class)
//    3.當y值大於角色但沒有被碰撞過 -->扣分
    @Override
    public void effect(Bar bloodBar, Text scoreCounter) {
        //sameColor(true)表示同色沒撞到
        //sameColor(false)表示不同色卻撞到
//        bloodBar.minusX();
//        scoreCounter.decreaseCount();
//        scoreCounter.setStr(scoreCounter.getCount());
       
        setisCollisioned(1);//扣過分歸類為撞到了(避免重複扣分)
    }

//    public void bingo(Text scoreCounter) {
//        scoreCounter.addCount(2);
//        scoreCounter.setStr(scoreCounter.getCount());
//        setisCollisioned(1);//扣過分歸類為撞到了(避免重複扣分)
//    }

    //4.碰撞到要把碰撞過的狀態改成已碰撞(才不會y大於角色後一值重複扣血)
    @Override
    public void setisCollisioned(int yesOrNo) {
        if (yesOrNo == 1) {
            isCollisioned = true;
        } else {
            isCollisioned = false;
        }
    }
    //碰撞並作效果 end

    @Override
    public void update() {
      if (delayCounter.update()) {
            actIndex = ++actIndex %2;
            ballHelper.changeColor(Act[actIndex]);
        }
      
    }

    @Override//透過camera
    public void paint(int x, int y, int width, int height, Graphics g) {
        if (needPrint) {//撇除被碰撞的不要印
            ballHelper.paintElement(g, x, y, width, height);
        }
       
    }

    @Override//不投影
    public void paint(Graphics g) {
      if (needPrint) {//撇除被碰撞的不要印
            ballHelper.paintElement(g, x, y, width, height);
        }
    }
    
}
