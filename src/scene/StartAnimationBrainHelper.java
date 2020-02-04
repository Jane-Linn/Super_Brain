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
import values.ImagePath;

/**
 *
 * @author Nano
 */
public class StartAnimationBrainHelper {

    private BufferedImage img;

    //建構子
    public StartAnimationBrainHelper(int width, int height, int actor) { // actor = 0
        img = getActor(actor);  // 依照使用者指定的Actor去抓
    }

    private BufferedImage getActor(int actor) {//得到哪張原圖
        ImageResourceController irc = ImageResourceController.getInstance();
        return irc.tryGetImage(PathBuilder.getImg(ImagePath.Background.StartScene.BRAINANIMATION));
    }

    // 印開始畫面大腦動畫
    public void paintStartAnimationBrain(Graphics g, int x, int y, int width, int height, int act, int yChangeIndex) {
        if (img == null) {
            return;
        }
        g.drawImage(img, x, y, x + width, y + height,
                act * 200, yChangeIndex * 200,
                act * 200 + 200, yChangeIndex * 200 + 200, null);
    }
}
