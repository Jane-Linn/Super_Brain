/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobject.ball;

import gameobject.GameObject;
import gameobject.sceneui.Text;
import java.awt.Graphics;
import java.util.ArrayList;
import utils.Global;

/**
 *
 * @author Wan Zhen Lin
 */
public class QuestionBall extends GameObject {

    private BallHelper ballHelper;
    private int gameLevel;//第幾關-->每一關都是這顆球~顏色不同//第三關才做事
    private boolean isMixed;//是否配色成功
    private String name;//名字(用於ElementEffect)
    private ArrayList<Integer> colors;//每關可能的顏色
    private int currentBallColor;//標示這顆球當前的顏色
    private String containColor1;//組成的顏色1
    private String containColor2;//組成的顏色2
    private boolean isCollision;//判斷有沒
    private boolean hasContainColor1;
    private boolean hasContainColor2;
    private int bingoNum;//2的時候代表一題結束
//(印出的x座標(左右輪流)，印出的y座標(從大到小)，印出的寬，印出的高，球的號碼-->(印出大圖的第幾排，那一排的第幾個(選顏色)))
//色球ballNumber只能是-->9,10,11,13,14

    public QuestionBall(int x, int y, int width, int height, int gameLevel, int ballNumber) {
        super(x, y, width, height);
        //不是每一關都需要此球
        ballHelper = new BallHelper(width, height, ballNumber);
        isMixed = false;
        this.gameLevel = gameLevel;
        name = Global.Element.QUESTION;//在ElementEffect時使用
        colors = setColor(gameLevel);
        currentBallColor = ballNumber;//每顆球一開始的顏色(csv決定)
        setContainColor();
        isCollision = false;
        bingoNum = 0;
        hasContainColor1 = false;
        hasContainColor2 = false;
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
                return colors;

            case 1:
                colors = new ArrayList<>();
                return colors;
            case 2:
                colors = new ArrayList<>();
                colors.add(MyColor.BALL_PURPLE);//這裡放的是colorNum(10~13)
                colors.add(MyColor.BALL_ORANGE);
                colors.add(MyColor.BALL_GRAY);
                colors.add(MyColor.BALL_LACK);
                return colors;
        }
        return null;
    }

    //2.把顏色傳給BallHelper
//答對後要換顏色(不能是剛剛的顏色)
    public void changeColor() {
        int j = 0;
        do {
            j = (int) (Math.random() * colors.size());
        } while (colors.get(j) == currentBallColor);
        ballHelper.changeColor(colors.get(j));
        currentBallColor = colors.get(j);
        setContainColor();
    }

    //3. 給ElementEffect判斷顏色是否答對(判斷有沒有碰到兩個containColor)(
    public String getCurrentColor() {
        return transformNum(currentBallColor);
    }

    private void setContainColor() {
        switch (currentBallColor) {

            case 10:
                containColor1 = "red";
                containColor2 = "blue";
                break;
            case 11:
                containColor1 = "red";
                containColor2 = "yellow";
                break;
            case 12:
                containColor1 = "white";
                containColor2 = "black";
                break;
            case 13:
                containColor1 = "green";
                containColor2 = "blue";
                break;
        }
    }
   
    public int getContain1Num() {
        return transformNum(containColor1);
    }
      public int getContain2Num() {
        return transformNum(containColor2);
    }

    //4.轉換currentBallColor成int給腦變色
    public int transformNum(String containColor) {
        if (containColor.equals("black")) {
            return 1 ;
        } else if (containColor.equals( "white")) {
            return 2;
        } else if (containColor.equals( "red")) {
            return 3;
        } else if (containColor.equals("yellow")) {
            return 4;
        } else if (containColor.equals("blue")) {
            return 5;
        } else if (containColor.equals( "green")) {
            return 6;
        }
        return 0;
    }

    //ElementEffect有沒有撞到這兩個顏色
    public boolean checkContainColor1(String ballColor) {//進入判定區的球應不應該撞(收到true要撞 false不能撞)
        if (ballColor.equals(containColor1)) {//進入判定區的球和第一個顏色依樣
            if (hasContainColor1 == false) {//第一個顏色還沒被撞到
                return true;//應該要撞到!-->外面判斷: 沒有撞到要扣血; 有撞到要把hasContainColor設成true; 換下一張時要重新設false
            }
            return false;//第一個顏色已經被撞過( 可以再撞-->true(bingoNum要重新判定, 不能再撞-->設成false)
        }
        return false;//這顆球跟第一個顏色不同 不能撞
    }

    public void setHasContainColor1(boolean boo) { //ElementEffect判斷 : 有撞到要把hasContainColor1設成true; 換下一張時要重新設false
        hasContainColor1 = boo;
    }

    public boolean checkContainColor2(String ballColor) {//進入判定區的球應不應該撞(收到true要撞 false不能撞)
        if (ballColor.equals(containColor2)) {//進入判定區的球和第二個顏色依樣
            if (hasContainColor2 == false) {//第二個顏色還沒被撞到
                return true;//應該要撞到!-->外面判斷: 沒有撞到要扣血; 有撞到要把hasContainColor設成true; 換下一張時要重新設false
            }
            return false;//第二個顏色已經被撞過 ( 可以再撞-->true(bingoNum要重新判定), 不能再撞-->設成false)
        }
        return false;//這顆球跟第二個顏色不同 不能撞
    }

    public void setHasContainColor2(boolean boo) { //ElementEffect判斷 : 有撞到要把hasContainColor2設成true; 換下一張時要重新設false
        hasContainColor2 = boo;
    }

    public String getColor1() {
        return containColor1;
    }

    public String getColor2() {
        return containColor2;
    }

    public void setBingoNum(int num) {

        bingoNum += num;

    }

    public int getBingoNum() {
        return bingoNum;
    }

    //4.轉換currentBallColor成String
    public String transformNum(int currentBallColor) {
        if (currentBallColor == 10) {
            return "purple";
        } else if (currentBallColor == 11) {
            return "orange";
        } else if (currentBallColor == 12) {
            return "gray";

        } else if (currentBallColor == 13) {
            return "lack";
        }
        return null;
    }
    //顏色管理 End

    //判斷是否有被碰撞了start
    //1.從外面判斷是否被角色碰撞了
    //2,如果撞到了要確認是否做過效果了，從這裡傳出被碰撞的狀態給外面
    @Override
    public boolean getisCollisioned() {
        return isCollisioned;//一開始是false
    }

//    被碰撞後要做甚麼效果由每一個element自己決定(創不同class)
//    3.當y值大於角色但沒有被碰撞過 -->扣分
//    public void effect(Bar bloodBar, Text scoreCounter) {
//        bloodBar.minusX();
////        scoreCounter.decreaseCount();
////        scoreCounter.setStr(scoreCounter.getCount());
//        setisCollisioned(1);//扣過分歸類為撞到了(避免重複扣分)
//    }
    @Override
    //當使用者答對這題 
    public void bingo(Text scoreCounter, boolean oneBrain) {
        setHasContainColor1(false);
        setHasContainColor2(false);//回復設定
        changeColor();//換題
        if (oneBrain) {
            scoreCounter.addCount(5);//加5分
        } else if (!oneBrain) {
            scoreCounter.addCount(10);
        }
        scoreCounter.setStr(scoreCounter.getCount());
        setisCollisioned(1);//得分歸類為撞到了(避免重複扣分)
        bingoNum = 0;
    }

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

    @Override//透過camera
    public void paint(int x, int y, int width, int height, Graphics g) {
        ballHelper.paintElement(g, x, y, width, height);
    }

    @Override//不投影
    public void paint(Graphics g) {
        ballHelper.paintElement(g, x, y, width, height);
    }

}
