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
import gameobject.sceneui.Text;
import io.CommandSolver;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import scene.MainScene;
import scene.Scene;
import scene.SceneHelper;
import utils.DelayCounter;
import utils.PlayerFileDealer;
import utils.Global;
import utils.MusicPlayer;
import values.ImagePath;

/**
 *
 * @author Wan Zhen Lin
 */
public class RankScene extends Scene {

    private CommandSolver.KeyCommandListener keyCommandListener;
    private BufferedImage img;
    private ArrayList<Player> playerArr; // 存使用者檔案的 list
    private PlayerFileDealer fileDealer;
    private Text gameLevelName;
    private Text text1;
    private Text text2;
    private Text text3;
    private Text text4;
    private int textIndex;
    private int gameLevel;
    private Player player;
    private MusicPlayer musicPlayer;
    private DelayCounter delayCounter;
    private SceneHelper sceneHelper;
    private static final int[] ACT = {0, 1};
    private int actIndex;
    private BufferedImage selection2Image;
    private BufferedImage buttonImage;
    private int buttonImageIndex;

    public RankScene(SceneController sceneController, Player player) {
        super(sceneController);
        this.player = player;
        fileDealer = new PlayerFileDealer();
        playerArr = fileDealer.readFile();
        gameLevel = 0;
        delayCounter = new DelayCounter(3);
        sceneHelper = new SceneHelper(800, 600, 2);
        actIndex = 0;
        ImageResourceController irc = ImageResourceController.getInstance();
        selection2Image = irc.tryGetImage(PathBuilder.getImg(ImagePath.Background.RankScene.SELECTION2));
        buttonImage = irc.tryGetImage(PathBuilder.getImg(ImagePath.Background.RankScene.BUTTON));
        buttonImageIndex = 1;
        musicPlayer = MusicPlayer.getInstance();
        musicPlayer.play(10);

        keyCommandListener = new CommandSolver.KeyCommandListener() {
            @Override
            public void keyPressed(int commandCode, long time) {
            }

            @Override
            public void keyReleased(int CommandCode, long time) {
                switch (CommandCode) {
                    case Global.KeyNumber.SPACE: //回遊戲主選單
                        musicPlayer.play(11);
                        sceneController.changeScene(new MainScene(sceneController, player));
                        break;
                    case Global.KeyNumber.UP: //排行榜上下捲動效果
                        buttonImageIndex = 1;
                        if (textIndex < 5 && textIndex > 0) {
                            textIndex -= 4;
                            setText();
                            musicPlayer.play(11);
                        }
                        break;
                    case Global.KeyNumber.DOWN:
                        buttonImageIndex = 0;
                        if (textIndex < 1) {
                            textIndex += 4;
                            setText();
                            musicPlayer.play(11);
                        }
                        break;
                    case Global.KeyNumber.LEFT:
                        if (gameLevel > 0) {
                            gameLevel--;
                            sortByScore(playerArr);
                            setText();                            
                            musicPlayer.play(11);
                        }
                        break;
                    case Global.KeyNumber.RIGHT:
                        if (gameLevel < 2) {
                            gameLevel++;
                            sortByScore(playerArr);
                            setText();                            
                            musicPlayer.play(11);
                        }
                        break;
                }
            }
        };
    }

    @Override
    public void sceneBegin() {
        textIndex = 0;
        sortByScore(playerArr);
        text1 = new Text(120, 310, Color.black, new Font(Global.TextType.TEXTSTYLE, Font.BOLD, 30), "No." + (textIndex + 1) + " " + playerArr.get(textIndex).toString(gameLevel));
        text2 = new Text(120, 365, Color.black, new Font(Global.TextType.TEXTSTYLE, Font.BOLD, 30), "No." + (textIndex + 2) + " " + playerArr.get(textIndex + 1).toString(gameLevel));
        text3 = new Text(120, 420, Color.black, new Font(Global.TextType.TEXTSTYLE, Font.BOLD, 30), "No." + (textIndex + 3) + " " + playerArr.get(textIndex + 2).toString(gameLevel));
        text4 = new Text(120, 475, Color.black, new Font(Global.TextType.TEXTSTYLE, Font.BOLD, 30), "No." + (textIndex + 4) + " " + playerArr.get(textIndex + 3).toString(gameLevel));
    }

    @Override
    public void sceneUpdate() {
        // 背景動畫刷新
        if (delayCounter.update()) {
            actIndex = ++actIndex % 2;
        }
    }

    @Override
    public void sceneEnd() {
        musicPlayer.stop(10);
    }

    @Override
    public void paint(Graphics g) {
        sceneHelper.paintRankScene(g, 0, 0, 800, 600, ACT[actIndex]);

        text1.paint(g);
        text2.paint(g);
        text3.paint(g);
        text4.paint(g);

        paintSelection2(g);
        if (buttonImageIndex == 1) {
            paintButtonDOWN(g);
        }
        if (buttonImageIndex == 0) {
            paintButtonUP(g);
        }
    }

    private void paintButtonUP(Graphics g) {
        g.drawImage(buttonImage, 380, 225, 380 + 50, 225 + 50,
                0, 0, 50, 50, null);
    }

    private void paintButtonDOWN(Graphics g) {
        g.drawImage(buttonImage, 380, 485, 380 + 50, 485 + 50,
                50, 0, 100, 50, null);
    }

    private void paintSelection2(Graphics g) {
        int dx = 90 + 216 * gameLevel;
        int dy = 143;
        g.drawImage(selection2Image, dx, dy, dx + 180, dy + 70,
                0, 0, 0 + 180, 0 + 70, null);
    }

    public CommandSolver.KeyCommandListener getKeyCommandListener() {
        return keyCommandListener;
    }

    private void sortByScore(ArrayList<Player> playerArr) {
        switch (gameLevel) {
            case 0:
                for (int i = 1; i < playerArr.size(); i++) {
                    for (int j = 0; j < playerArr.size() - i; j++) {
                        if (playerArr.get(j).getEasyModeScore() < playerArr.get(j + 1).getEasyModeScore()) {
                            swap(playerArr, j, j + 1);
                        }
                    }
                }
                break;
            case 1:
                for (int i = 1; i < playerArr.size(); i++) {
                    for (int j = 0; j < playerArr.size() - i; j++) {
                        if (playerArr.get(j).getMiddleModeScore() < playerArr.get(j + 1).getMiddleModeScore()) {
                            swap(playerArr, j, j + 1);
                        }
                    }
                }
                break;
            case 2:
                for (int i = 1; i < playerArr.size(); i++) {
                    for (int j = 0; j < playerArr.size() - i; j++) {
                        if (playerArr.get(j).getHardModeScore() < playerArr.get(j + 1).getHardModeScore()) {
                            swap(playerArr, j, j + 1);
                        }
                    }
                }
                break;
        }
    }

    private void swap(ArrayList<Player> playerArr, int i, int j) {
        Player tmp = playerArr.get(i);
        playerArr.set((i), playerArr.get(j));
        playerArr.set((j), tmp);
    }

    private void setText() {
        text1.setStr("No." + (textIndex + 1) + " " + playerArr.get(textIndex).toString(gameLevel));
        text2.setStr("No." + (textIndex + 2) + " " + playerArr.get(textIndex + 1).toString(gameLevel));
        text3.setStr("No." + (textIndex + 3) + " " + playerArr.get(textIndex + 2).toString(gameLevel));
        text4.setStr("No." + (textIndex + 4) + " " + playerArr.get(textIndex + 3).toString(gameLevel));
    }
}
