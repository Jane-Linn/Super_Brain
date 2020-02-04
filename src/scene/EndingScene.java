/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scene;

import controllers.ImageResourceController;
import controllers.PathBuilder;
import controllers.SceneController;
import gameobject.player.Player;
import gameobject.sceneui.Text;
import io.CommandSolver.KeyCommandListener;
import io.CommandSolver.MouseCommandListener;
import static java.awt.Color.black;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import utils.Global;
import utils.MusicPlayer;
import utils.PlayerFileDealer;
import values.ImagePath;

/**
 *
 * @author Nano
 */
public class EndingScene extends Scene {

    private KeyCommandListener keyCommandListener;  // 在各個場景設定此場景的鍵盤指令
    private MouseCommandListener mouseCommandListener;
    private Player player;
    private int gameScore;
    private Text gameScoreText;
    private Text bestScoreText;
    private int gameLevel;
    private PlayerFileDealer fileDealer;
    private ArrayList<Player> dataList;
    private BufferedImage retryIcon;
    private BufferedImage quitIcon;
    private BufferedImage scene2Image;
    private MusicPlayer musicPlayer;

    public EndingScene(SceneController sceneController, Player player, int gameScore, int gameLevel) {
        super(sceneController);
        this.gameScore = gameScore;
        this.player = player;
        this.gameLevel = gameLevel;
        musicPlayer = MusicPlayer.getInstance();
        musicPlayer.loop(5);
        ImageResourceController irc = ImageResourceController.getInstance();
        scene2Image = irc.tryGetImage(PathBuilder.getImg(ImagePath.Background.EndingScene.SCENE2));

        keyCommandListener = new KeyCommandListener() {
            @Override
            public void keyPressed(int commandCode, long time) {
                //System.out.println("pressed at: " + time + " -> " + commandCode);
                switch (commandCode) {
                    case Global.KeyNumber.LEFT:
                        break;
                    case Global.KeyNumber.RIGHT:
                        break;
                }
            }

            @Override
            public void keyReleased(int CommandCode, long time) {
                switch (CommandCode) {
                    case Global.KeyNumber.SPACE: //遊戲繼續玩
                        musicPlayer.play(12);
                        sceneController.changeScene(new GameScene(sceneController, player, gameLevel));
                        break;
                    case Global.KeyNumber.ESCAPE: //回遊戲主選單
                        musicPlayer.play(12);
                        sceneController.changeScene(new MainScene(sceneController, player));
                        break;
                }
            }
        };
//        mouseCommandListener = new MouseCommandListener() {
//            @Override
//            public void mouseTrig(MouseEvent e, MouseState state, long trigTime) {
//                if (state == MouseState.CLICKED) {
//                    if (button1.isCollision(e.getX(), e.getY())) {
//                        button1.click(e.getX(), e.getY());
//                    }
//                } else if (state == MouseState.MOVED) {
//                    if (button1.isCollision(e.getX(), e.getY())) {
//                        button1.hover(e.getX(), e.getY());
//                    }
//                }
//            }
//        };
    }

    @Override
    public void sceneBegin() {
        // 本場分數
        gameScoreText = new Text(350, 300, black, new Font(Global.TextType.TEXTSTYLE, Font.BOLD, 50), gameScore);
        // 最高分
        if (gameLevel == 0) {
            bestScoreText = new Text(350, 350, black, new Font(Global.TextType.TEXTSTYLE, Font.BOLD, 50), player.getEasyModeScore());
        }
        if (gameLevel == 1) {
            bestScoreText = new Text(350, 350, black, new Font(Global.TextType.TEXTSTYLE, Font.BOLD, 50), player.getMiddleModeScore());
        }
        if (gameLevel == 2) {
            bestScoreText = new Text(350, 350, black, new Font(Global.TextType.TEXTSTYLE, Font.BOLD, 50), player.getHardModeScore());
        }    
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(scene2Image, 0, 0, 800, 600,
                0, 1225, 800, 1825, null);
        gameScoreText.paint(g);
        bestScoreText.paint(g);
    }

    @Override
    public void sceneUpdate() {
    }

    @Override
    public void sceneEnd() {
        musicPlayer.stop(5);
    }

    @Override
    public KeyCommandListener getKeyCommandListener() {
        return keyCommandListener;
    }

    @Override
    public MouseCommandListener getMouseCommandListener() {
        return mouseCommandListener;
    }
}
