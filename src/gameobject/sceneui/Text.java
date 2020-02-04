/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobject.sceneui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import utils.Global;

/**
 *
 * @author 奈米
 */
public class Text {

    private int x;
    private int y;
    private Color color;
    private Font font;
    private String str;
    private ArrayList<String> strArray;
    private int count;

    // 要使用時寫法~        座標 x y  顏色              字體       style    大小  文字內容
    // Text text = new Text(325, 300, red, new Font("monospaced", Font.BOLD, 50), "START");
    // 要記得 text.paint(g);
    public Text(int x, int y, Color color, Font font, String str) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.font = font;
        this.str = str;
        strArray = new ArrayList<>();
    }

    public Text(int x, int y, Color color, Font font, int count) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.font = font;
        this.count = count;
        this.str = String.valueOf(count);
    }

    // GameScene 印分數用   // 不會小於 0
    public void decreaseCount() {
        if (count <= 0) {
            count = 0;
            return;
        }
        count--;
    }

    public void addCount(int i ) {
        count+=i;
    }

    public int getCount() {
        return count;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public void setStr(int count) {
        this.count = count;
        this.str = String.valueOf(count);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getStr() {
        return str;
    }

    public String getStrs() {
        String str = "";
        for (int i = 0; i < strArray.size(); i++) {
            str += strArray.get(i);
        }
        return str;
    }

    public ArrayList<String> getStrArray() {
        return strArray;
    }

    public void addString(String str) {
        if (strArray.size() < Global.Player.LIMIT_NAME_LENGTH) {    // 限制玩家名字最長字數 // 可拉成 Global 參數
            strArray.add(str);
        }
    }

    public void paint(Graphics g) {
        g.setColor(color);
        g.setFont(font);
        g.drawString(str, x, y);
    }

    public void paintStrings(Graphics g) {
        g.setColor(color);
        g.setFont(font);
        g.drawString(getStrs(), x, y);
    }

    public void paintHint(Graphics g, int x, int y) {
        g.setColor(color);
        g.setFont(font);
        g.drawString(str, x, y);
    }
}
