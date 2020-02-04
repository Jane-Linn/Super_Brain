/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobject;

import gameobject.ball.Bar;
import gameobject.sceneui.Text;
import java.awt.Graphics;
import java.util.ArrayList;

public abstract class GameObject {

    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected boolean isCollisioned;
    protected String name;
    protected boolean needPrint;

    public GameObject(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    // Bound start
    public int getBottom() {
        return y + height;
    }

    public int getTop() {
        return y;
    }

    public int getLeft() {
        return x;
    }

    public int getRight() {
        return x + width;
    }

    // Bound end
    //顏色管理 start
    public void changeColor() {
    }

    public void changeColor(String colorName) {
    }
      public void changeColor(int colorName) {
    }

    public String getCurrentColor() {
        return null;
    } //測試

    public ArrayList getColors() {
        return null;
    }
    //顏色管理  end

    public boolean isCollision(GameObject obj) {
        if (getLeft() > obj.getRight()) {
            return false;
        }
        if (getRight() < obj.getLeft()) {
            return false;
        }
        if (getTop() > obj.getBottom()) {
            return false;
        }
        if (getBottom() < obj.getTop()) {
            return false;
        }
        return true;
    }

    //element專用
    //判斷是否有做過效果了start
    //1.從外面判斷是否被角色碰撞了
    //2,如果撞到了要確認是否做過效果了，從這裡傳出做過效果了沒的狀態給外面
    public boolean getisCollisioned() {
        return isCollisioned;
    }

    //被碰撞後要做甚麼效果由每一個element自己決定(創不同class)
    //3.外面得到碰撞狀態後 觸發這個影響-->扣血
    public void effect(Bar bloodBar, Text scoreCounter) {
    }
//加分

    public void bingo(Text scoreCounter, boolean oneBrain) {
    }

    //4.做完一次效果要把碰撞過的狀態改成已碰撞(才不會重疊時一直重複扣分)
    public void setisCollisioned(int yesOrNo) {
        if (yesOrNo == 1) {
            isCollisioned = true;
        } else {
            isCollisioned = false;
        }
    }

    public boolean getNeedPrint() {//被撞到要消失
        return needPrint;
    }

    public void changeNeedPrint(boolean boo) {//超過frame要重設定
        needPrint = boo;
    }
    //碰撞並作效果 end

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getName() {
        return name;
    }

    //被雷打到
    public void thunderHit() {
    }

    public void update() {
    }

    // 這裡會傳入 Camera 修改後的座標給 Scene 內的 Object    // 不是每一個 Scene 內都有 camera 來修改
    public void paint(int x, int y, int width, int height, Graphics g) {
        paint(g);
    }

    // 實際印出
    public abstract void paint(Graphics g);

//    // test 測試
//    //當背景物離開frame後絕對座標的y軸-700
//    //為了重複出現
//    //從camera判斷是否出frame了(絕對座標的top比camera的絕對座標底還大) 呼叫這個funtion更改一次
//    public void changeObjectY() {
//        this.setY(this.getY() - 700);
//    }
}
