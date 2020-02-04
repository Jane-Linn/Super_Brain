/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scene;

import io.CommandSolver.*;
import controllers.SceneController;
import gameobject.player.Player;
import gameobject.sceneui.Text;
import io.CommandSolver;
import static java.awt.Color.*;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import utils.DelayCounter;
import utils.Global;
import utils.MusicPlayer;
import utils.PlayerFileDealer;

public class StartScene extends Scene {

    private KeyCommandListener keyCommandListener;
    private CommandSolver.TypedListener typedListener;
    private static final int[] ACT = {0, 1};
    private int actIndex;
    private DelayCounter delayCounter;
    private SceneHelper sceneHelper;
    private MusicPlayer musicPlayer;
    private StartAnimationBrain brainAnimation;
    private ArrayList<Player> playerArr; // 存使用者檔案的 list
    private PlayerFileDealer fileDealer;
    private Player currentPlayer;
    private ArrayList<String> nameArray;
    private Text userName;
    private String name;
    private Text hint;

    public StartScene(SceneController sceneController) {
        super(sceneController);
        musicPlayer = MusicPlayer.getInstance();
        musicPlayer.loop(0);
        actIndex = 0;
        delayCounter = new DelayCounter(3);
        sceneHelper = new SceneHelper(800, 600, 0);
        fileDealer = new PlayerFileDealer();
        playerArr = fileDealer.readFile();  
        nameArray = new ArrayList<>();

        keyCommandListener = new KeyCommandListener() {
            @Override
            public void keyPressed(int commandCode, long time) {
            }

            @Override
            public void keyReleased(int CommandCode, long time) {
                switch (CommandCode) {
                    case Global.KeyNumber.SPACE:   //顯示輸入名字 hint
                        if (!name.equals("")) { // 沒輸入則不存
                            if (isNameExisted()) {// true 新玩家
                                System.out.println("新玩家輸入名字: " + name);
                                currentPlayer = new Player(name, 0, 0, 0);
                                playerArr.add(currentPlayer); // 把新玩家輸入的名字存入list
                            }
//                            else {    // 輸入名字與 playerFile 相同
//                                sceneHelper.setHaveName(true);
//                                musicPlayer.play(14);
//                                break;
//                            }
                            fileDealer.writeFile(playerArr);   // 新使用者檔案存檔
                            musicPlayer.play(12);
                            sceneController.changeScene(new MainScene(sceneController, currentPlayer)); //把player傳入MainScene
                        }
                        break;
                    case Global.KeyNumber.ENTER:
                        break;
                }
            }
        };
        typedListener = new CommandSolver.TypedListener() {
            @Override
            public void keyTyped(char c, long trigTime) {
                if (c == KeyEvent.VK_BACK_SPACE) {
                    sceneHelper.setHaveName(false);
                    if (!nameArray.isEmpty()) {
                        nameArray.remove(nameArray.size() - 1);
                    }
                } else {
                    if (c >= 48 && c <= 57) {// 轉為 ASCII 限制輸入內容 & 名字長度
                        if (nameArray.size() < Global.Player.LIMIT_NAME_LENGTH) {
                            nameArray.add(String.valueOf(c));
                            musicPlayer.play(13);
                        }
                    }
                    if (c >= 65 && c <= 90) {
                        if (nameArray.size() < Global.Player.LIMIT_NAME_LENGTH) {
                            nameArray.add(String.valueOf(c));
                            musicPlayer.play(13);
                        }
                    }
                    if (c >= 97 && c <= 122) {
                        if (nameArray.size() < Global.Player.LIMIT_NAME_LENGTH) {
                            nameArray.add(String.valueOf(c));
                            musicPlayer.play(13);
                        }
                    }
                }
            }
        };
    }

    private boolean isNameExisted() {
        if (playerArr == null) {
            ArrayList<Player> newPlayerArr = new ArrayList<>();
            playerArr = newPlayerArr;
            return true;
        }
        for (int i = 0; i < playerArr.size(); i++) {
            if (name.equals(playerArr.get(i).getName())) {
                currentPlayer = playerArr.get(i);
                return false;
            }
        }
        return true;
    }

    @Override
    public void sceneBegin() {
        // 大腦動畫
        brainAnimation = new StartAnimationBrain(342, 47, 150, 150, 2, 0);

        name = "";
        userName = new Text(370, 368, black, new Font(Global.TextType.TEXTSTYLE, Font.BOLD, 35), name);    // userName的str再寫入檔案 

    }

    @Override
    public void sceneUpdate() {
        // 背景動畫刷新
        if (delayCounter.update()) {
            actIndex = ++actIndex % 2;
        }
        // 大腦動畫刷新
        brainAnimation.move();
        // 玩家輸入名字
        if (nameArray != null) {
            name = "";
            for (int i = 0; i < nameArray.size(); i++) {
                name += nameArray.get(i);
            }
        }
        userName.setStr(name);
    }

    @Override
    public void sceneEnd() {
        musicPlayer.stop(0);
    }

    @Override
    public void paint(Graphics g) {
        // 厲害ㄉ背景
        sceneHelper.paintScene(g, 0, 0, 800, 600, ACT[actIndex]);
        // 大腦動畫
        brainAnimation.paint(g);
        // 玩家輸入名字
        userName.paint(g);
    }

    public CommandSolver.KeyCommandListener getKeyCommandListener() {
        return keyCommandListener;
    }

    public CommandSolver.TypedListener getTypedListener() {
        return typedListener;
    }
}
