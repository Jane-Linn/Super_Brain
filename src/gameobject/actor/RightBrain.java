/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobject.actor;

import gameobject.MovableGameObject;
import gameobject.ball.BallHelper;
import gameobject.ball.MyColor;
import java.awt.Graphics;
import java.util.ArrayList;
import utils.DelayCounter;
import utils.Global;

/**
 *
 * @author Wan Zhen Lin
 */
public class RightBrain extends MovableGameObject {

    private static final int[] ACT = {0, 1, 2, 1};
    private int actIndex;
//    private int direction;
    private BrainHelper brainHelper;
    private DelayCounter delayCounter;
    private boolean isMove;
    private double moveunit;
    private double deltaY;
    private ArrayList<Integer> colors;//左腦在這一關可能會有的顏色
    private int currentLeftColor;//用int表示現在左腦的顏色
    private int gameLevel;
    private int playerNum;
    //被雷打到 
    private boolean printThunder;
    private BallHelper thunderEffect;
    private DelayCounter thunderDelay;

    //actor-->左腦還是右腦(右腦=2)
    //LeftBrain(印x , 印y, 印寬, 印高, 速度, 遊戲關卡 , 腦的類別)
    public RightBrain(int x, int y, int width, int height, int speed, int gameLevel, int colorNumber, int playerNum) {
        super(x, y, width, height, speed);
        actIndex = 0;
//        direction = Global.KeyNumber.UP;
        delayCounter = new DelayCounter(Global.Actor.NORMAL_DELAY);
        brainHelper = new BrainHelper(width, height, 2, colorNumber);
        isMove = false;
        moveunit = (Global.SceneValue.SCENE_ACCELARATION * Global.Update.MILLISEC_PER_UPDATE * Global.Update.MILLISEC_PER_UPDATE / 1000);
        deltaY = 0;
        this.gameLevel = gameLevel;
        colors = setColor(gameLevel);//這關的腦可能會有的顏色
        currentLeftColor = colorNumber;//一開始為0
        this.playerNum = playerNum;
        printThunder = false;
        thunderEffect = new BallHelper(x + width / 2, y - height, 20);
        thunderDelay = new DelayCounter(16);
    }

    //顏色管理 Start 
    //1. 設這關會有的顏色
    //換關時要重新設定 & 切換oneBrain twoBrain時要放入當前的顏色
    public ArrayList<Integer> setColor(int gameLevel) {
        switch (gameLevel) {
            case 0://第一關 
                colors = new ArrayList<>();
                colors.add(MyColor.BLACK);
                colors.add(MyColor.WHITE);
                return colors;

            case 1:
                colors = new ArrayList<>();
                colors.add(MyColor.RED);
                colors.add(MyColor.YELLOW);
                colors.add(MyColor.BLUE);
                colors.add(MyColor.GREEN);
                return colors;
            case 2:
                colors = new ArrayList<>();
                colors.add(MyColor.SKIN);
                colors.add(MyColor.BLACK);
                colors.add(MyColor.WHITE);
                colors.add(MyColor.RED);
                colors.add(MyColor.YELLOW);
                colors.add(MyColor.BLUE);
                colors.add(MyColor.GREEN);
                colors.add(MyColor.PURPLE);
                colors.add(MyColor.ORANGE);
                colors.add(MyColor.GRAY);
                colors.add(MyColor.LACK);
                return colors;
        }
        return null;
    }

    //2.碰到彩球時要換顏色
    //把顏色傳給BrainHelper 標示現在是甚麼顏色(color)
    @Override
    public void changeColor() {
        int j = (int) (Math.random() * colors.size());
        brainHelper.setColorNumber(colors.get(j));
        currentLeftColor = colors.get(j);

    }

    //在GameScene中交換oneCar or twoCar狀態的時候統一回到第一個顏色
    public void changeColor(int i) {
        brainHelper.setColorNumber(colors.get(i));
        currentLeftColor = colors.get(i);
    }

    @Override
    public void changeColor(String questionColorName) {
        int i = 0;
        switch (questionColorName) {
            case "purple":
                i = 7;
                break;
            case "orange":
                i = 8;
                break;
            case "gray":
                i = 9;
                break;
            case "lack":
                i = 10;
                break;
        }
        brainHelper.setColorNumber(colors.get(i));
        currentLeftColor = colors.get(i);
    }

    //3. 給ElementEffect判斷顏色是否相同(
    @Override
    public String getCurrentColor() {
        return transformNum(currentLeftColor);
    }

    //4.轉換currentBallColor成String
    public String transformNum(int currentLeftColor) {
        if (currentLeftColor == 1) {
            return "black";
        } else if (currentLeftColor == 2) {
            return "white";
        } else if (currentLeftColor == 3) {
            return "red";
        } else if (currentLeftColor == 4) {
            return "yellow";
        } else if (currentLeftColor == 5) {
            return "green";
        } else if (currentLeftColor == 6) {
            return "blue";
        } else if (currentLeftColor == 0) {
            return "skin";
        }
        return null;
    }
    //顏色管理 End

    //getter
    public String getName() {
        return "rightBrain";
    }

    //往前移動 Start
    //這裡可以調整妳要整體的前進速度上限
    public void getDisplacement() {
        if (deltaY < Global.Actor.NORMAL_DELTA_Y-1) {
            deltaY += moveunit;
        }
    }

    public void brainMoveY() {
        setY(getY() - (int) deltaY);
        if (y < Global.SceneValue.ACTUALL_Y_LENGTH) {
          y =  5 +Global.SceneValue.FRAME_Y_OFFSET-(y % Global.SceneValue.ACTUALL_Y_LENGTH);
        }
    }

    public int getDeltaY() {
        return (int) deltaY;
    }
      public void changedeltaY(int i){//吃烏龜要變少，分數每50分要加速
        //i是改變的值
        deltaY += i;
    }

    //往前移動 End
    //走路 start
    public void changeDelay(int i) {
        delayCounter.setdelay(i);
    }

    public int getDelay() {
        return delayCounter.getDelay();
    }
    @Override
    public void move() {
        if (delayCounter.update()) {
            actIndex = ++actIndex % 4;
        }
    }

    //一個玩家時的左右移動判斷Start
    //按鍵的當下(判斷有沒有移動過-->沒有)會往左移動-->移動完標示為[往左移動=true]了(就不會一直往左移動)-->直到釋放按鍵時重新設定[往左移動了=false]
    //兩台車的動作(無左右之分，按一下換左換右)
    //左腦是否移動過判斷使用isMoveLeft
    public void changeRightBrainPosition() {
        if (x < 550) {//確認現在所在的跑道在左道
            moveRight();//往右移
        } else if (x > 550) {
            moveLeft();
        }

    }

    public void moveRight() {
        int x = this.x + 150;//加看看
        if (x >= 700) {//確認真的可以往右移，
            return;
        }
        this.x = x;//真的給移動後的位置
        isMove = true;//已觸發這次的移動
    }

    public void moveLeft() {
        int x = this.x - 150;//移動
        if (x <= 400) {//判斷移動完會在哪(超出範圍就不動)
            return;
        }
        this.x = x;//真的給移動完的值
        isMove = true;//已觸發這次的移動
    }

    public void resetIsMove() {
        isMove = false;//按鍵release會重新設成還沒移動
    }

    public boolean isMove() {//給GameScene的KeyCommander判斷的
        return isMove;
    }

    //一個玩家左右移動 End
    //被雷打到
    public void thunderHit() {
        printThunder = true;
    }

    @Override
    public void update() {
        //印打雷
        if (printThunder == true && thunderDelay.isPause()) {
            thunderDelay.reset();
            thunderDelay.start();
        }
        if (thunderDelay.update()) {
            printThunder = false;
        }
    }

    @Override
    public void paint(Graphics g) {
        brainHelper.paintAnimation(g, x, y, width, height, ACT[actIndex]);
        if (printThunder == true) {
            thunderEffect.paintElement(g, x + width / 2, y - height, width, height);
        }
    }

    @Override
    public void paint(int x, int y, int width, int height, Graphics g) {
        brainHelper.paintAnimation(g, x, y, width, height, ACT[actIndex]);
        if (printThunder == true) {
            thunderEffect.paintElement(g, x + width / 2, y - height, width, height);
        }
    }
//     @Override
//    public void paint(Graphics g) {
//        actorHelper.paintAnimation(g, x, y, width, height, ACT[actIndex], direction);
//    }
//
//    @Override
//    public void paint(int x, int y, int width, int height, Graphics g) {
//        actorHelper.paintAnimation(g, x, y, width, height, ACT[actIndex], direction);
//    }
}
