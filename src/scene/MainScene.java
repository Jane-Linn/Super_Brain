/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scene;

import controllers.ImageResourceController;
import controllers.PathBuilder;
import scene.menu.Menu;
import controllers.SceneController;
import gameobject.player.Player;
import io.CommandSolver;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import utils.DelayCounter;
import utils.Global;
import utils.MusicPlayer;
import values.ImagePath;

/**
 *
 * @author Wan Zhen Lin
 */
public class MainScene extends Scene {

    private CommandSolver.KeyCommandListener keyCommandListener;
    private Menu menu;
    private Player player;  // 玩家資料
    private int gameLevel;
    private BufferedImage gameLevelImage;
    private int GAMELEVEL_X_OFFSET = 180;
    private int GAMELEVEL_Y_OFFSET = 80;
    private MusicPlayer musicPlayer;
    private SceneHelper sceneHelper;
    private static final int[] ACT = {0, 1};
    private DelayCounter delayCounter;
    private int actIndex;
    // 難度選擇圖示
    private int GAMELEVELIMAGE_Y = 340;
    private int EASYLEVEL_X = 70;
    private int MIDDLELEVEL_X = 300;
    private int HARDLEVEL_X = 530;
    private int GAMELEVELIMAGE_WIDTH = 180;
    private int GAMELEVELIMAGE_HEIGHT = 80;
    // 選擇 focus
    private BufferedImage selectionImage;

    public MainScene(SceneController sceneController, Player currentPlayer) {
        super(sceneController);
        player = currentPlayer;
        musicPlayer = MusicPlayer.getInstance();
        musicPlayer.loop(1);
        gameLevel = 0;
        ImageResourceController irc = ImageResourceController.getInstance();
        gameLevelImage = irc.tryGetImage(PathBuilder.getImg(ImagePath.Background.MainScene.GAMELEVEL));
        selectionImage = irc.tryGetImage(PathBuilder.getImg(ImagePath.Background.MainScene.SELECTION));
        sceneHelper = new SceneHelper(800, 600, 1);
        delayCounter = new DelayCounter(3);
        actIndex = 0;

        keyCommandListener = new CommandSolver.KeyCommandListener() {
            @Override
            public void keyPressed(int commandCode, long time) {
                //System.out.println("pressed at: " + time + " -> " + commandCode);
                switch (commandCode) {
                }
            }

            @Override
            public void keyReleased(int CommandCode, long time) {
                switch (CommandCode) {
                    case Global.KeyNumber.LEFT: // 左右選擇 gameLevel
                        if (gameLevel > 0) {
                            gameLevel--;
                            musicPlayer.play(11);
                        }
                        break;
                    case Global.KeyNumber.RIGHT: // 左右選擇 gameLevel
                        if (gameLevel < 2) {
                            gameLevel++;
                            musicPlayer.play(11);
                        }
                        break;
                    case Global.KeyNumber.M:    // 選擇 Menu
                        musicPlayer.play(12);
                        menu.setisPressed(true);
                        break;
                    case Global.KeyNumber.SPACE:
                        musicPlayer.play(12);
                        sceneController.changeScene(new GameScene(sceneController, player, gameLevel));
                        break;
                }
            }
        };
    }

    @Override
    public void sceneBegin() {
        menu = new Menu(sceneController, player);
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
        musicPlayer.stop(1);
    }

    @Override
    public void paint(Graphics g) {
        sceneHelper.paintMainScene(g, 0, 0, 800, 600, ACT[actIndex]);
        paintGameLevel(g); //印三種 gameLevel 圖示
        paintSelection(g);
        menu.paint(g);
    }

    public CommandSolver.KeyCommandListener getKeyCommandListener() {
        if (menu.isPressed()) {
            return menu.getKeyCommandListener();
        }
        return keyCommandListener;
    }

    private void paintSelection(Graphics g) {
        int dx = 46;
        int dy = 215;
        g.drawImage(selectionImage, dx + 230 * gameLevel, dy, dx + 230 * gameLevel + 230, dy + 230, // 原則上只有前面四個變動 => 座標 & 寬高
                0, 0, 0 + 230, 0 + 230, null);
    }

    // 印三種 gameLevel 圖示
    private void paintGameLevel(Graphics g) {
        if (gameLevel != 0) {
            g.drawImage(gameLevelImage, EASYLEVEL_X, GAMELEVELIMAGE_Y, EASYLEVEL_X + GAMELEVELIMAGE_WIDTH, GAMELEVELIMAGE_Y + GAMELEVELIMAGE_HEIGHT, // 原則上只有前面四個變動 => 座標 & 寬高
                    0, 0, 0 + GAMELEVEL_X_OFFSET, 0 + GAMELEVEL_Y_OFFSET, null);
        } else {
            g.drawImage(gameLevelImage, EASYLEVEL_X, GAMELEVELIMAGE_Y, EASYLEVEL_X + GAMELEVELIMAGE_WIDTH, GAMELEVELIMAGE_Y + GAMELEVELIMAGE_HEIGHT,
                    180, 0, 180 + GAMELEVEL_X_OFFSET, 0 + GAMELEVEL_Y_OFFSET, null);
        }
        if (gameLevel != 1) {
            g.drawImage(gameLevelImage, MIDDLELEVEL_X, GAMELEVELIMAGE_Y, MIDDLELEVEL_X + GAMELEVELIMAGE_WIDTH, GAMELEVELIMAGE_Y + GAMELEVELIMAGE_HEIGHT,
                    0, 80, 0 + GAMELEVEL_X_OFFSET, 80 + GAMELEVEL_Y_OFFSET, null);
        } else {
            g.drawImage(gameLevelImage, MIDDLELEVEL_X, GAMELEVELIMAGE_Y, MIDDLELEVEL_X + GAMELEVELIMAGE_WIDTH, GAMELEVELIMAGE_Y + GAMELEVELIMAGE_HEIGHT,
                    180, 80, 180 + GAMELEVEL_X_OFFSET, 80 + GAMELEVEL_Y_OFFSET, null);
        }
        if (gameLevel != 2) {
            g.drawImage(gameLevelImage, HARDLEVEL_X, GAMELEVELIMAGE_Y, HARDLEVEL_X + GAMELEVELIMAGE_WIDTH, GAMELEVELIMAGE_Y + GAMELEVELIMAGE_HEIGHT,
                    0, 160, 0 + GAMELEVEL_X_OFFSET, 160 + GAMELEVEL_Y_OFFSET, null);
        } else {
            g.drawImage(gameLevelImage, HARDLEVEL_X, GAMELEVELIMAGE_Y, HARDLEVEL_X + GAMELEVELIMAGE_WIDTH, GAMELEVELIMAGE_Y + GAMELEVELIMAGE_HEIGHT,
                    180, 160, 180 + GAMELEVEL_X_OFFSET, 160 + GAMELEVEL_Y_OFFSET, null);
        }
    }
}
