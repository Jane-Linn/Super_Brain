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
public class SceneButton extends GameObject {    // 把按鈕視為一個物件

    private ButtonHelper buttonHelper;

    public interface ButtonListener {

        public void onClick(int x, int y);

        public void hover(int x, int y);
    }

//Button(在跑道上的x座標, 在跑道上的y座標, 印出的高, 印出的寬,  來源圖的哪一個角色)
    public SceneButton(int x, int y, int width, int height, int actor) {
        super(x, y, width, height);
        buttonHelper = new ButtonHelper(width, height, actor);
    }

    @Override
    public void paint(Graphics g) {
        buttonHelper.paintButton(g, x, y, width, height);
    }
}
