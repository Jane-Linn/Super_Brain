/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scene.menu;

import controllers.ImageResourceController;
import controllers.PathBuilder;
import controllers.SceneController;
import gameobject.player.Player;
import io.CommandSolver;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import scene.MainScene;
import scene.Scene;
import scene.SceneHelper;
import utils.DelayCounter;
import utils.Global;
import utils.MusicPlayer;
import values.ImagePath;

/**
 *
 * @author Wan Zhen Lin
 */
public class HowToPlayScene extends Scene {

    private CommandSolver.KeyCommandListener keyCommandListener;
    private BufferedImage img;
    private BufferedImage scene2Image;
    private Player player;
    private static final int[] ACT = {0, 1};
    private int actIndex;
    private SceneHelper sceneHelper;
    private DelayCounter delayCounter;
    private BufferedImage buttonImage;
    private int buttonImageIndex;   // 0 1 2 3 4
    private BufferedImage howToPlayImage;
    private MusicPlayer musicPlayer;

    public HowToPlayScene(SceneController sceneController, Player player) {
        super(sceneController);
        this.player = player;
        ImageResourceController irc = ImageResourceController.getInstance();
        scene2Image = irc.tryGetImage(PathBuilder.getImg(ImagePath.Background.HowToPlayScene.SCENE2));
        buttonImage = irc.tryGetImage(PathBuilder.getImg(ImagePath.Background.HowToPlayScene.BUTTON));
        howToPlayImage = irc.tryGetImage(PathBuilder.getImg(ImagePath.Background.HowToPlayScene.HOWTOPLAY));
        sceneHelper = new SceneHelper(800, 600, 4);
        actIndex = 0;
        delayCounter = new DelayCounter(3);
        buttonImageIndex = 0;
        musicPlayer = MusicPlayer.getInstance();
        musicPlayer.loop(9);

        keyCommandListener = new CommandSolver.KeyCommandListener() {
            @Override
            public void keyPressed(int commandCode, long time) {
            }

            @Override
            public void keyReleased(int CommandCode, long time) {
                switch (CommandCode) {
                    case Global.KeyNumber.LEFT:
                        if (buttonImageIndex > 0) {
                            buttonImageIndex--;
                            musicPlayer.play(11);
                            System.out.println(buttonImageIndex);
                        }
                        break;
                    case Global.KeyNumber.RIGHT:
                        if (buttonImageIndex < 4) {
                            buttonImageIndex++;
                            musicPlayer.play(11);
                            System.out.println(buttonImageIndex);
                        }
                        break;
                    case Global.KeyNumber.SPACE://回遊戲主選單
                        musicPlayer.play(11);
                        sceneController.changeScene(new MainScene(sceneController, player));
                        break;
                }
            }
        };
    }

    @Override
    public void sceneBegin() {

    }

    @Override
    public void sceneUpdate() {
        if (delayCounter.update()) {
            actIndex = ++actIndex % 2;
        }
    }

    @Override
    public void sceneEnd() {
        musicPlayer.stop(9);
    }

    @Override
    public void paint(Graphics g) {
        sceneHelper.paintHowToPlay(g, 0, 0, 800, 600, ACT[actIndex]);

        if (buttonImageIndex != 0) {
            paintButtonLEFT(g);
        }
        if (buttonImageIndex != 4) {
            paintButtonRIGHT(g);
        }

        paintHowToPlayImage(g);
    }

    private void paintHowToPlayImage(Graphics g) {
        int dx = buttonImageIndex % 2 * 683;
        int dy = buttonImageIndex / 2 * 318;
        g.drawImage(howToPlayImage, 70, 185, 70 + 638, 185 + 318,
                dx, dy, dx + 670, dy + 318, null);
    }

    private void paintButtonLEFT(Graphics g) {
        g.drawImage(buttonImage, 29, 300, 29 + 50, 300 + 50,
                0, 50, 50, 100, null);
    }

    private void paintButtonRIGHT(Graphics g) {
        g.drawImage(buttonImage, 717, 300, 717 + 50, 300 + 50,
                50, 50, 100, 100, null);
    }

    public CommandSolver.KeyCommandListener getKeyCommandListener() {
        return keyCommandListener;
    }
}
