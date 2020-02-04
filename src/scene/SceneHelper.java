/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scene;

import controllers.ImageResourceController;
import controllers.PathBuilder;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import utils.Global;
import values.ImagePath;

/**
 *
 * @author Wan Zhen Lin
 */
public class SceneHelper {
    //一關3張(橫的) 800*600
    private BufferedImage thisNameHaveExistedText;
    private boolean haveName;
    private BufferedImage img;
    private int sceneNum;//哪一關(Start(0),Friend(1)

    //建構子
    public SceneHelper(int width, int height, int sceneNum) {
        thisNameHaveExistedText = getWall(11);
        haveName = false;
        img = getWall(sceneNum);  // 依照使用者指定的Actor去抓圖
        this.sceneNum = sceneNum % 3;  // 計算這個actor在圖中的位置
    }

    private BufferedImage getWall(int sceneNum) {//得到哪張原圖
        ImageResourceController irc = ImageResourceController.getInstance();
        if (sceneNum >= 0 && sceneNum < 3) {
            return irc.tryGetImage(PathBuilder.getImg(ImagePath.Background.StartScene.SCENE1));
        }
        if (sceneNum >= 3 && sceneNum < 6) {
            return irc.tryGetImage(PathBuilder.getImg(ImagePath.Background.Instruction.SCENE2));
        }
        if (sceneNum >= 6 && sceneNum < 9) {
            return irc.tryGetImage(PathBuilder.getImg(ImagePath.Background.GameScene.GAMESCENE));
        }
        if(sceneNum == 11){
            return irc.tryGetImage(PathBuilder.getImg(ImagePath.Background.StartScene.RETYPE_NAME));
        }

        return null;
    }

    //不用經過camera修改
    public void paintInstruction(Graphics g, int x, int y, int width, int height, int act) {
        if (img == null) {
            return;
        }
        int dx = 0;
        int dy = Global.SceneValue.FRAME_Y_OFFSET * (sceneNum % 3);
        g.drawImage(img, x, y, x + width, y + height,
                dx + act * 800, dy, dx + act * 800 + 800, dy + 600, null);
    }
    public void paintHowToPlay(Graphics g, int x, int y, int width, int height, int act) {
        if (img == null) {
            return;
        }
        int dx = 0;
        int dy = Global.SceneValue.FRAME_Y_OFFSET * (sceneNum % 3);
        g.drawImage(img, x, y-10, x + width, y + height-10,
                dx + act * 800, dy, dx + act * 800 + 800, dy + 600, null);
    }

    public void paintGameScene(Graphics g, int x, int y, int width, int height, int act) {
        if (img == null) {
            return;
        }
        int dx = act * 800;
        int dy = Global.SceneValue.FRAME_Y_OFFSET * (sceneNum % 3);
        g.drawImage(img, x, y, x + width, y + height,
                dx, dy, dx + 800, dy + 600, null);
    }

    public void setHaveName(boolean boo){
        haveName = boo;
    }
    public void paintScene(Graphics g, int x, int y, int width, int height, int act) {
        if (img == null) {
            return;
        }
        int dx = 0 + 10;
        int dy = 25 + Global.SceneValue.FRAME_Y_OFFSET * (sceneNum % 3);
        g.drawImage(img, x, y, x + width, y + height,
                dx + act * 800, dy, dx + act * 800 + 800, dy + 600, null);
        if(haveName == true){
            g.drawImage(thisNameHaveExistedText, 525, 240, 200, 100, null);
        }
    }

    public void paintMainScene(Graphics g, int x, int y, int width, int height, int act) {
        if (img == null) {
            return;
        }
        int dx = 0;
        int dy = Global.SceneValue.FRAME_Y_OFFSET * (sceneNum % 3);
        g.drawImage(img, x, y, x + width, y + height,
                dx + act * 800, dy, dx + act * 800 + 800, dy + 600 + 40, null); // 額外 + 40 修正 => 因為圖格式好像有跑掉
    }

    public void paintRankScene(Graphics g, int x, int y, int width, int height, int act) {
        if (img == null) {
            return;
        }
        int dx = 0;
        int dy = 20 + Global.SceneValue.FRAME_Y_OFFSET * (sceneNum % 3);    // 20 稍微修正
        g.drawImage(img, x, y, x + width, y + height,
                dx + act * 800, dy, dx + act * 800 + 800, dy + 600, null);
    }
}
