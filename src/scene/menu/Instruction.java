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
public class Instruction extends Scene {

    private CommandSolver.KeyCommandListener keyCommandListener;
    private BufferedImage scene2Image;
    private Player player;
    private static final int[] ACT = {0, 1};
    private int actIndex;
    private SceneHelper sceneHelper;
    private DelayCounter delayCounter;
    private MusicPlayer musicPlayer;
    private Text playerName;

    public Instruction(SceneController sceneController, Player player) {
        super(sceneController);
        this.player = player;
        ImageResourceController irc = ImageResourceController.getInstance();
        scene2Image = irc.tryGetImage(PathBuilder.getImg(ImagePath.Background.Instruction.SCENE2));
        sceneHelper = new SceneHelper(800, 600, 3);
        actIndex = 0;
        delayCounter = new DelayCounter(3);
        musicPlayer = MusicPlayer.getInstance();
        musicPlayer.loop(8);
        playerName = new Text(135, 198, Color.black, new Font(Global.TextType.TEXTSTYLE, Font.BOLD, 45), player.getName());

        keyCommandListener = new CommandSolver.KeyCommandListener() {
            @Override
            public void keyPressed(int commandCode, long time) {
            }

            @Override
            public void keyReleased(int CommandCode, long time) {
                switch (CommandCode) {
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
        musicPlayer.stop(8);
    }

    @Override
    public void paint(Graphics g) {
        sceneHelper.paintInstruction(g, 0, 0, 800, 600, ACT[actIndex]);
        playerName.paint(g);
    }

    public CommandSolver.KeyCommandListener getKeyCommandListener() {
        return keyCommandListener;
    }
}
