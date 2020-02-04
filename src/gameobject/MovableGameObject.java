/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobject;

import utils.Global;

public abstract class MovableGameObject extends GameObject {

    private int speed;

    public MovableGameObject(int x, int y, int width, int height, int speed) {
        super(x, y, width, height);
        setSpeed(speed);
    }

    //背景物件speed給1
    //車子可以依種類給不同數值
    public final void setSpeed(int speed) {
        this.speed = speed * Global.ObjectMoveValue.OBJECT_SPEED_REVISED_VALUE;
    }

    public final int getSpeed() {
        return speed;
    }

    public abstract void move();
}
