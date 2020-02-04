/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobject.ball;

import gameobject.GameObject;
import gameobject.sceneui.Text;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import utils.Global;
//外面要呼叫第0張大圖的第0小圖的第1,0張

public class ChangeBall extends GameObject {

    private BallHelper ballHelper;
    private int gameLevel;//第幾關-->每一關都是這顆球~顏色不同
    private boolean isCollisioned;//是否撞過了
    private boolean needPrint;//撞過不要印
    private String name;//名字(用於ElementEffect
    private ArrayList<Integer> colors;//每關可能的顏色
//(印出的x座標(左右輪流)，印出的y座標(從大到小)，印出的寬，印出的高，印出大圖的第幾排(gameLevel)，那一排的第幾個(選顏色ballNumber))
//色球ballNumber只能是-->0,10(色球不能是第一個)

    public ChangeBall(int x, int y, int width, int height, int gameLevel, int ballNumber) {
        super(x, y, width, height);
        ballHelper = new BallHelper(width, height, ballNumber);//0 or 10        
        isCollisioned = false;
        needPrint = true;
        this.gameLevel = gameLevel;
        name = Global.Element.CHANGE;
        colors = setColor(gameLevel);
    }

    //getter
    public int getGameLevel() {
        return gameLevel;
    }

    @Override
    public String getName() {
        return name;
    }
    //顏色管理 start
    //1. 設這關會有的顏色
    //換關要換

    public ArrayList<Integer> setColor(int gameLevel) {
        switch (gameLevel) {
            case 0://第一關 
                colors = new ArrayList<>();
                colors.add(MyColor.BALL_MIX);//這裡放的是colorNum(0~14)
                return colors;

            case 1:
                colors = new ArrayList<>();
                colors.add(MyColor.BALL_MIX2);
                return colors;
            case 2:
                colors = new ArrayList<>();
                return colors;
        }
        return null;
    }

//    //把顏色傳給BrainHelper 標示現在是甚麼顏色(color)
//    //2.把顏色傳給BallHelper
////一開始的顏色 和每次重複時重新隨機出顏色
//    public void changeColor() {
//        int j = (int) (Math.random() * colors.size());
//        ballHelper.changeColor(colors.get(j));
//        currentBallColor = colors.get(j);
//    }
//
//    //3. 給ElementEffect判斷顏色是否相同(數字不同所以用&&比)(
//    public int getCurrentColor() {
//        return currentBallColor;
//    }
    //顏色管理 End
    //判斷是否有被碰撞了start
    //1.從外面判斷是否被角色碰撞了
    //2,如果撞到了要確認是否做過效果了，從這裡傳出被碰撞的狀態給外面
    @Override
    public boolean getisCollisioned() {
        return isCollisioned;
    }

    //被碰撞後要做甚麼效果由每一個element自己決定(創不同class)
    //3.外面得到碰撞狀態後 觸發這個影響-->扣血
    @Override
    public void effect( Bar bloodBar, Text scoreCounter) {
        bloodBar.minusX();
        scoreCounter.decreaseCount();
        scoreCounter.setStr(scoreCounter.getCount());
    }

//    // test
//    @Override
//    public void effect(ArrayList<String> scoreStrings, int score, Text scoreHint){
//        score -= 10;
//        scoreStrings.set(1, String.valueOf(score));
//        scoreHint.setStr(scoreStrings.get(0) + scoreStrings.get(1));
//    }
    //4.做完一次效果要把碰撞過的狀態改成已碰撞(才不會重疊時一直重複扣分)
    @Override
    public void setisCollisioned(int yesOrNo) {
        if (yesOrNo == 1) {
            isCollisioned = true;
        } else {
            isCollisioned = false;
        }
    }
    @Override
    public void changeNeedPrint(boolean boo){
        needPrint = boo;
    }
    @Override
    public boolean getNeedPrint(){
        return needPrint;
    }
    //碰撞並作效果 end 
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
