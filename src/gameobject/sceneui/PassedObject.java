/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobject.sceneui;

import gameobject.GameObject;
import java.awt.Graphics;

/**
 *
 * @author Wan Zhen Lin
 */
public class PassedObject extends GameObject {

    //某一關卡的所有旁景物(樹阿 房子阿....)
    //用讀檔的方式印?????
    //要經過camera
    private PassedObjectHelper passedObjectHelper;
    private int number;//第幾關
    private int column;
    private int line;

    //PassedObject(印出的x座標(左右輪流)，印出的y座標(從大到小)，印出的寬，印出的高，第幾關，印出小原圖組的第幾個x，印出小原圖組的第幾個y)
    //x是左右輪流
    //y是按大到小加數字
    public PassedObject(int x, int y, int width, int height, int number, int column, int line) {
        super(x, y, width, height);
        this.column = column;
        this.line = line;
        passedObjectHelper = new PassedObjectHelper(width, height, number);
    }

    //當背景物離開frame後絕對座標的y軸-700
    //為了重複出現
    //從camera判斷是否出frame了(絕對座標的top比camera的絕對座標底還大) 呼叫這個funtion更改一次
    public void changeObjectY() {
        this.setY(this.getY() - 700);
    }

    // 實質的y
    @Override
    public void paint(Graphics g) {
        passedObjectHelper.paintPassedObject(g, x, y, width, height, column, line);
    }

    //要經過camera的狀況
    @Override
    public void paint(int x, int y, int width, int height, Graphics g) {
        passedObjectHelper.paintPassedObject(g, x, y, width, height, column, line);
    }
}
