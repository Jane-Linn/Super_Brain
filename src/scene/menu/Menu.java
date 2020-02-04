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
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import scene.StartScene;
import utils.Global;
import utils.MusicPlayer;
import values.ImagePath;

/**
 *
 * @author Wan Zhen Lin
 */
public class Menu {

    //選單要有座標 長寬 可以放按鈕(UI) 選單被按到時才可以開放這個場景的某些key指令
//    private ArrayList<Button> menubuttons;
    private ArrayList<Text> menutexts;
    private double deltaX;
    private boolean isPressed;
    private int menuTextX;
    private int menuTextY;
    private Player player;
    private CommandSolver.KeyCommandListener keyCommandListener;
    private MusicPlayer musicPlayer;
//    private boolean musicContinue;
    private BufferedImage menuImage;
//    private //按鈕的圖(Array)

    public Menu(SceneController sceneController, Player player) {
        this.player = player;
        ImageResourceController irc = ImageResourceController.getInstance();
        menuImage = irc.tryGetImage(PathBuilder.getImg(ImagePath.Background.MainScene.MENU));
      musicPlayer = MusicPlayer.getInstance();
        isPressed = false;
        
        keyCommandListener = new CommandSolver.KeyCommandListener() {
            @Override
            public void keyReleased(int CommandCode, long time) {
                switch (CommandCode) {
                    case Global.KeyNumber.ONE://背景介紹
//                        musicContinue = true;
                        musicPlayer.play(11);
                        sceneController.changeScene(new Instruction(sceneController, player));
                        break;
                    case Global.KeyNumber.TWO://怎麼玩
//                        musicContinue = true;
                        musicPlayer.play(11);
                        sceneController.changeScene(new HowToPlayScene(sceneController, player));
                        break;
                    case Global.KeyNumber.THREE://排行榜
//                        musicContinue = true;
                        musicPlayer.play(11);
                        sceneController.changeScene(new RankScene(sceneController, player));
                        break;
                    case Global.KeyNumber.FOUR://重新登入
//                        musicContinue = false;
                        musicPlayer.play(11);
                        sceneController.changeScene(new StartScene(sceneController));
                        break;
                    case Global.KeyNumber.FIVE://離開遊戲
//                        musicContinue = false;
                        musicPlayer.play(7);
                        try {
                            Thread.sleep(3000);
                        } catch (Exception ex) {
                        }
                        System.exit(0);
                        break;
                    case Global.KeyNumber.M:
                        musicPlayer.play(12);
                        System.out.println("選單往右移");
//                        menuMoveRight();//收起Menu
                        setisPressed(false);
                        break;
                }
            }

            @Override
            public void keyPressed(int commandCode, long time) {
            }
        };
    }
//    public boolean getMusicContinue(){
//        return musicContinue;
//    }


    public void setisPressed(boolean boo) {
        isPressed = boo;
    }


    public boolean isPressed() {
        return isPressed;
        //true的時候才可以開放選單的key指令(Scene決定)
    }

    public void paint(Graphics g) {
        if (!isPressed) {
            g.drawImage(menuImage, 680, 135, 680 + 108, 135 + 72,
                    0, 0, 120, 80, null);
        } else {
            g.drawImage(menuImage, 475, 135, 475 + 311, 135 + 369,
                    0, 0, 345, 410, null);
        }
      
    }

    public CommandSolver.KeyCommandListener getKeyCommandListener() {
        return keyCommandListener;
    }
}
//        x = (int) ((Global.SceneValue.FRAME_Y_OFFSET - 20));
//        y = (int) ((Global.SceneValue.FRAME_Y_OFFSET) * 0.8);
//        width = 20 + 60;
//        height = (int) ((Global.SceneValue.FRAME_Y_OFFSET) * 0.6);
