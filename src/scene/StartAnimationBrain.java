/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scene;

import gameobject.MovableGameObject;
import java.awt.Graphics;
import utils.DelayCounter;

/**
 *
 * @author Nano
 */
public class StartAnimationBrain extends MovableGameObject {

    private static final int[] ACT = {0, 1, 2, 3, 4, 0, 1, 2, 3, 4, 0, 1, 2, 3, 4, 0, 1, 2, 3,4};   // 0 1 2 3 4
    private int actIndex;
    private StartAnimationBrainHelper startAnimationBrainHelper;
    private DelayCounter delayCounter;

    public StartAnimationBrain(int x, int y, int width, int height, int speed, int actor) {
        super(x, y, width, height, speed);
        actIndex = 0;
        delayCounter = new DelayCounter(4);
        startAnimationBrainHelper = new StartAnimationBrainHelper(width, height, actor);
    }

    @Override
    public void move() {
        if (delayCounter.update()) {
            actIndex = ++actIndex % 15; // 1 2 3 4 5 6...14 0 1 2 3...
        }
    }

    // ACT[actIndex] => 變化 0 1 2 3 4
    // actIndex / 5 => 變化 0000 11111 22222 33333 44444 0
    @Override
    public void paint(Graphics g) {
        startAnimationBrainHelper.paintStartAnimationBrain(g, x, y, width, height, ACT[actIndex], actIndex / 5);
    }
}
