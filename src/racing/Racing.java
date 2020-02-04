/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racing;

import io.CommandSolver;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import utils.Global;

public class Racing {

    public static void main(String args[]) {
        JFrame j = new JFrame();
        j.setTitle("Falldown");
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        j.setSize(Global.SceneValue.FRAME_X_OFFSET, Global.SceneValue.FRAME_Y_OFFSET);

        GameJPanel panel = new GameJPanel();
        j.add(panel);
        j.setVisible(true);

        CommandSolver cs = new CommandSolver.Builder(Global.Update.MILLISEC_PER_UPDATE, new int[][]{
            // 玩家按 BACK_SPACE 刪字
            {KeyEvent.VK_BACK_SPACE, Global.KeyNumber.BACK_SPACE},
            // 結算畫面按 ESC 退回主畫面 
            {KeyEvent.VK_ESCAPE, Global.KeyNumber.ESCAPE},
            //左玩家
            {KeyEvent.VK_W, Global.KeyNumber.W},
            {KeyEvent.VK_A, Global.KeyNumber.A},
            {KeyEvent.VK_S, Global.KeyNumber.S},
            {KeyEvent.VK_D, Global.KeyNumber.D},
            {KeyEvent.VK_Q, Global.KeyNumber.Q}, // 切換場景
            {KeyEvent.VK_E, Global.KeyNumber.E},
            //右玩家
            {KeyEvent.VK_LEFT, Global.KeyNumber.LEFT},
            {KeyEvent.VK_RIGHT, Global.KeyNumber.RIGHT},
            {KeyEvent.VK_UP, Global.KeyNumber.UP},
            {KeyEvent.VK_DOWN, Global.KeyNumber.DOWN},
            {KeyEvent.VK_SPACE, Global.KeyNumber.SPACE},
            {KeyEvent.VK_ENTER, Global.KeyNumber.ENTER}, // 切換場景 
            //{KeyEvent.VK_O, Global.KeyNumber.O}, // 切換投影 => 有需要再補回來
            {KeyEvent.VK_Z, Global.KeyNumber.Z}, // 切換狗狗

            //主選單
            //左玩家
            {KeyEvent.VK_M, Global.KeyNumber.M},
            {KeyEvent.VK_N, Global.KeyNumber.N},
            {KeyEvent.VK_J, Global.KeyNumber.J},
            //MENUE選擇
            {KeyEvent.VK_1, Global.KeyNumber.ONE},
            {KeyEvent.VK_2, Global.KeyNumber.TWO},
            {KeyEvent.VK_3, Global.KeyNumber.THREE},
            {KeyEvent.VK_4, Global.KeyNumber.FOUR},
            {KeyEvent.VK_5, Global.KeyNumber.FIVE},
            {KeyEvent.VK_U, Global.KeyNumber.U},}).enableMouseTrack(j).keyCleanMode().trackChar().gen();
        addKeyListener(j, cs);
        cs.start();

        long startTime = System.currentTimeMillis();
        long lastRepaintTime = System.currentTimeMillis();
        long passedFrames = 0;
        while (true) {
            long currentTime = System.currentTimeMillis();
            long totalTime = currentTime - startTime;
            long targetTotalFrames = totalTime / Global.Update.MILLISEC_PER_UPDATE;

            while (passedFrames < targetTotalFrames) {
                panel.update(cs.update());
                passedFrames++;
            }
            if (Global.Update.LIMIT_DELTA_TIME <= currentTime - lastRepaintTime) {
                lastRepaintTime = currentTime;
                j.repaint();
            }
        }
    }

    public static void addKeyListener(JFrame j, CommandSolver cs) {
        j.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {  // 傳進來使用者按了啥
                cs.trig(e.getKeyChar(), true);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                cs.trig(e.getKeyCode(), true);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                cs.trig(e.getKeyCode(), false);
            }
        });
        j.setFocusable(true);
    }
}
