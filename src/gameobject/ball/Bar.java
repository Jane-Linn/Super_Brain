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

/**
 *
 * @author Wan Zhen Lin
 */
// 隱藏問題: 排行榜的 height / count / Global.Object.DATA_PER_PAGE 要是倍數關係才不會有問題
public class Bar extends GameObject {

    private Color color;
    private int changeX;    // 讓 fillRect y 值變動 (排行榜用)
    private int changeY;    // 讓 fillRect x 值變動 (角色血條用)
    private int hp;  // 排行榜的輸入資料筆數 // 角色血有多少滴
    private int page;   // 頁數
    private int scrollValue;    // 每次捲動移動的值
    private int scrollValueCount;   // 計算捲動的次數

    public Bar(int x, int y, int width, int height, int hp, Color color) {
        super(x, y, width, height);
        this.color = color;
        changeX = 0;
        //changeY = 0;
        this.hp = hp;

//        // rankBar
//        page = hp / Global.Object.DATA_PER_PAGE;
//        scrollValue = height / page / Global.Object.DATA_PER_PAGE;
//        scrollValueCount = 0;
    }

    public void minusX() {
        if (changeX >= width / hp) {
            return;
        }
        ++changeX;
    }

    public void plusX() {
        if (changeX <= 0) {
            return;
        }
        --changeX;
    }

    public int getChangeX() {
        return changeX;
    }

//    public void scrollDown() {
//        if (scrollValueCount >= (hp / Global.Object.DATA_PER_PAGE - 1) * Global.Object.DATA_PER_PAGE) {
//            return;
//        }
//        this.scrollValueCount += 1;
//    }
//
//    public void scrollUp() {
//        if (scrollValueCount == 0) {
//            return;
//        }
//        this.scrollValueCount -= 1;
//    }
    public int getHP() {
        return hp;
    }

    // 畫排行榜的 bar
    @Override
    public void paint(Graphics g) {
        g.setColor(color);
        g.drawRect(x, y, width, height);    // bar 畫出來的 座標 x y，寬高
        g.fillRect(x, y + scrollValue * scrollValueCount,
                width, height / page);
    }

    // 角色血條
    public void paintHP(Graphics g) {
        g.setColor(color);
        g.drawRoundRect(x, y, width, height, 10, 10);
        g.fillRoundRect(x, y, width - (width / hp) * changeX, height, 10, 10);
//        Text hp = new Text(x + width + 10, y + 23, Color.black, new Font("Calabri", Font.BOLD, 25), changeX % 10 + "/" + 10);
//        hp.paint(g);
        Text hp = new Text(x + width + 10, y + 23, Color.black, new Font("Calabri", Font.BOLD, 25), (10 - changeX) + "/" + 10);
        hp.paint(g);
    }
}
