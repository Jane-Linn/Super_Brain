/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scene;

import controllers.ImageResourceController;
import controllers.PathBuilder;
import controllers.SceneController;
import gameobject.GameObject;
import gameobject.actor.RightBrain;
import gameobject.ball.Bar;
import gameobject.ball.BallBag;
import gameobject.actor.LeftBrain;
import gameobject.actor.WholeBrain;
import gameobject.ball.QuestionBall;
import gameobject.player.Player;
import gameobject.sceneui.BackgroundImg;
import gameobject.sceneui.Text;
import io.CommandSolver.*;
import java.awt.Color;
import static java.awt.Color.black;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import utils.Global;
import utils.MusicPlayer;
import utils.PlayerFileDealer;
import values.ImagePath;

public class GameScene extends Scene {

    // 會影響、改變關卡內容的因素:    
    public int gameLevel;
    private int projectionOrNot;// 無投影 0 有投影 1 -->預設是有
    private boolean oneBrain;// 一台(true)或兩台(false)-->主要是false(兩個玩家的時候?)-->true時不印左右腦 也不判斷左右腦碰撞!!
    private boolean onePlayer;// 一個玩家(true)還是兩個玩家(false)
    private KeyCommandListener keyCommandListener;
    private Camera camera;//不是每個場景都要有camera--> Camera寫在需要有鏡頭的場景下
    private ElementCamera elementCamera;
    // 受遊戲影響因素會改變的屬性:
    private BackgroundImg background;// 背景包(背景圖 道路圖 背景物)-->隨 關卡不同、投影有無、玩家數量、(角色數量) 改變內容
    private Player player;
    private PlayerFileDealer fileDealer;
    //球
    private BallBag elementBag;//跑道上的球(色球&彩球)
    private QuestionBall questionBall1;//第三關的右題目球    
    private QuestionBall questionBall2;//第三關的左題目球(先設定只有一顆球)    
    //腦
    private LeftBrain leftBrain;//要傳入現在遊戲是幾人遊戲-->決定走的距離
    private RightBrain rightBrain;//要傳入現在遊戲是幾人遊戲-->決定走的距離
    private WholeBrain wholeBrain;
    //血條
    private Bar bloodBar;//一個玩家時 或是玩家1

    private int updateCount;//適當讓人物腳步更快，車道後移更快
    // score
    private Text scoreHint;
    private Text scoreCounter;
    private int score;

    private MusicPlayer musicPlayer;
    private BufferedImage gameInfoImage;
    //改變腦的速度的布林
    private boolean addSpeed;

    public GameScene(SceneController sceneController, Player player, int gameLevel) {   // MainScene 玩家選擇的 gameLevel傳進來
        super(sceneController);
        // 進來就無法變更的:
        this.gameLevel = gameLevel;
        onePlayer = true;//有幾個玩家
        this.player = player;
        ImageResourceController irc = ImageResourceController.getInstance();
        gameInfoImage = irc.tryGetImage(PathBuilder.getImg(ImagePath.Background.GameScene.GAMEINFO));

        fileDealer = new PlayerFileDealer();
        musicPlayer = MusicPlayer.getInstance();
        if (gameLevel == 0) {
            musicPlayer.loop(2);
        }
        if (gameLevel == 1) {
            musicPlayer.loop(3);
        }
        if (gameLevel == 2) {
            musicPlayer.loop(4);
        }
        // 一些一開始預設但在遊戲過程可以被更改的設定:
        projectionOrNot = 1;//(1)是有投影(0)是沒投影-->按O轉換
        oneBrain = false;//目前兩個玩家會同時改變(用同一個布林)-->預設有兩個
        background = new BackgroundImg(gameLevel, projectionOrNot, gameLevel, oneBrain);//背景包(背景圖 道路圖 背景物)-->隨關卡不同、投影有無、玩家數量、(1car or 2cars) 改變內容

        // 大腦(一開始設定為兩個腦)-->在各方法中判斷要用哪一種腦 判斷碰撞(collision()) & 移動(KeyCommander) & 印出(paint())
        leftBrain = new LeftBrain(325 - Global.Actor.ACTOR_PRINT_X_SIZE / 2, Global.Actor.ACTOR_PRINT_Y_POINT, Global.Actor.ACTOR_PRINT_X_SIZE, Global.Actor.ACTOR_PRINT_X_SIZE, 2, gameLevel, 0, 1);
        rightBrain = new RightBrain(625 - Global.Actor.ACTOR_PRINT_X_SIZE / 2, Global.Actor.ACTOR_PRINT_Y_POINT, Global.Actor.ACTOR_PRINT_X_SIZE, Global.Actor.ACTOR_PRINT_X_SIZE, 2, gameLevel, 0, 1);
        wholeBrain = new WholeBrain(325 - Global.Actor.ACTOR_PRINT_X_SIZE / 2, Global.Actor.ACTOR_PRINT_Y_POINT, Global.Actor.ACTOR_PRINT_X_SIZE, Global.Actor.ACTOR_PRINT_X_SIZE, 2, gameLevel, 0, 1);
        // 角色血條                     // 10 滴血
        bloodBar = new Bar(310, 80, 100, 30, 10, new Color(235, 103, 103));
        //這關的跑道球，從第0關開始
        elementBag = new BallBag(gameLevel);
        elementBag.fillBag(gameLevel);
        //設置答案球
        if (gameLevel == 2) {
            questionBall1 = new QuestionBall(150, 100, 100, 100, gameLevel, ((int) (Math.random() * 3 + 10)));
            leftBrain.changeColor(questionBall1.getCurrentColor());
            wholeBrain.changeColor(questionBall1.getCurrentColor());
            questionBall2 = new QuestionBall(550, 100, 100, 100, gameLevel, ((int) (Math.random() * 3 + 10)));
            rightBrain.changeColor(questionBall2.getCurrentColor());
        } else {
            questionBall1 = null;
//            questionBall2 = null;uwjgf
        }
        addSpeed = false;

        // 鍵盤的指令(會依: 1. oneCar or twoCar 改變對大腦的指令)
        keyCommandListener = new KeyCommandListener() {
            @Override
            public void keyPressed(int commandCode, long time) {//按下觸發大腦移動
                switch (commandCode) {
                    case Global.KeyNumber.LEFT:
                        if (oneBrain) {//一個大腦的情況
                            if (!wholeBrain.isMoveLeft()) {
                                wholeBrain.moveLeft();
                            }
                            break;
                        } else if (!oneBrain) {//兩個大腦的情況
                            if (!leftBrain.isMove()) {
                                leftBrain.changeLeftBrainPosition();
                            }
                            break;
                        }
                    case Global.KeyNumber.RIGHT:
                        if (oneBrain) {//一個大腦的情況
                            if (!wholeBrain.isMoveRight()) {
                                wholeBrain.moveRight();
                            }
                            break;
                        } else if (!oneBrain) {//兩個大腦的情況
                            if (!rightBrain.isMove()) {
                                rightBrain.changeRightBrainPosition();
                            }
                            break;
                        }
                }
            }

            @Override
            public void keyReleased(int CommandCode, long time) {
                switch (CommandCode) {
                    case Global.KeyNumber.LEFT://放開要把大腦的移動設定回復
                        if (oneBrain) {
                            wholeBrain.resetIsMoveLeft();
                            break;
                        } else if (!oneBrain) {
                            leftBrain.resetIsMove();
                            break;
                        }
                    case Global.KeyNumber.RIGHT:
                        if (oneBrain) {
                            wholeBrain.resetIsMoveRight();
                            break;
                        } else if (!oneBrain) {
                            rightBrain.resetIsMove();
                            break;
                        }
                    case Global.KeyNumber.O://切換投影
                        changeProjection();
                    case Global.KeyNumber.Z://切換一台or兩台車
                        changeCarNumState();
                        break;
                }
            }
        };
    }

    @Override // camera 和 score
    public void sceneBegin() {
        //一些不管是哪個場景都要有的:
        updateCount = 0;
        camera = new Camera();
        elementCamera = new ElementCamera();
        // 分數
        score = 0;
        scoreHint = new Text(310, 63, black, new Font("Calabri", Font.BOLD, 30), "Score: ");
        scoreCounter = new Text(420, 63, black, new Font(Global.TextType.TEXTSTYLE, Font.BOLD, 30), score);
    }

    @Override
    public void sceneUpdate() {
        // 沒血惹 => update 成績 => 進結算畫面
        if (bloodBar.getChangeX() >= bloodBar.getHP()) {
            switch (gameLevel) {
                case 0:
                    player.updateEasyModeScore(scoreCounter.getCount());
                    break;
                case 1:
                    player.updateMiddleModeScore(scoreCounter.getCount());
                    break;
                case 2:
                    player.updateHardModeScore(scoreCounter.getCount());
                    break;
            }
            fileDealer.saveGameResult(player);
            musicPlayer.play(6);
            try {
                Thread.sleep(1500);
            } catch (Exception ex) {
            }
            sceneController.changeScene(new EndingScene(sceneController, player, scoreCounter.getCount(), gameLevel));
        }

        if (scoreCounter.getCount() % 100 < 50 || scoreCounter.getCount() % 100 > 55) {
            addSpeed = false;
        }
        //改變腳步和動畫的速度start
        // 1. 每50分讓腳步和車道越動越快(有上限 只會加速3次)--分關卡
        if (gameLevel == 0) {
            if (scoreCounter.getCount() % 100 > 50 && scoreCounter.getCount() % 100 < 55 && !addSpeed) {

                //(1)改移動距離 沒超過上限 變大
                if (leftBrain.getDeltaY() < Global.Actor.LIMIT_DELTA_Y) {
                    changeDeltaY(1);
                }
                //(2)改動畫速度 沒低於下限 變少
                if (leftBrain.getDelay() >= Global.Actor.LIMIT_DELAY) {
                    changeDelay(-2);
                }
                addSpeed = true;
            }
        } else if (gameLevel == 1) {
            if (scoreCounter.getCount() % 100 > 50 && scoreCounter.getCount() % 100 < 55 && !addSpeed) {
                //(1)改移動距離 沒超過上限 變大
                if (leftBrain.getDeltaY() < Global.Actor.LIMIT_DELTA_Y) {
                    changeDeltaY(2);
                }
                //(2)改動畫速度 沒低於下限 變少
                if (leftBrain.getDelay() >= Global.Actor.LIMIT_DELAY) {
                    changeDelay(-2);
                }
                addSpeed = true;
            }
        } else if (gameLevel == 2) {
            if (scoreCounter.getCount() % 100 > 50 && scoreCounter.getCount() % 500 < 55 && !addSpeed) {
                //(1)改移動距離 沒超過上限 變大
                if (leftBrain.getDeltaY() < Global.Actor.LIMIT_DELTA_Y ) {
                    changeDeltaY(1);
                }
                //(2)改動畫速度 沒低於下限 變少
                if (leftBrain.getDelay() > Global.Actor.LIMIT_DELAY ) {
                    changeDelay(-2);
                }
            }
            addSpeed = true;
        }

        //2. 一開始要讓腳色動畫跟上速度
        if (updateCount++ % 500 == 0) {
            if (leftBrain.getDelay() > Global.Actor.LIMIT_DELAY + 2) { // leftBrain delay = 8  // 這邊只會進來一次
                changeDelay(-1);
            }
        }
        //角色的移動
        leftBrain.getDisplacement();
        rightBrain.getDisplacement();
        wholeBrain.getDisplacement();
        leftBrain.brainMoveY();
        rightBrain.brainMoveY();
        wholeBrain.brainMoveY();
        //改變腳步和動畫的速度end
        if (oneBrain) {
            wholeBrain.move();
        } else {
            leftBrain.move();
            rightBrain.move();
        }

        // 鏡頭的移動(透過角色)
        camera.CameraMoveY(leftBrain);//印背景的
        elementCamera.CameraMoveY(leftBrain);//印人物和障礙物的

        // 場景的更新
        background.update();

        // 當障礙物的bottom開始>=角色的top-->判斷效果
        if (oneBrain == true) {
            oneCarCollision();
        } else if (oneBrain == false) {
            twoCarCollision();
        }

        //在這裡設置球的顏色隨機????????????????
        for (int i = 0; i < elementBag.getBag().size(); i++) {
            elementBag.getElement(i).update();
        }
        leftBrain.update();
        rightBrain.update();
        wholeBrain.update();
    }

    public void changeDelay(int i) {//動畫
        //i 是改變的值
        leftBrain.changeDelay(i);
        rightBrain.changeDelay(i);
        wholeBrain.changeDelay(i);
        background.changeDelay(i);
    }

    public void changeDeltaY(int i) {//實際移動的距離(越大速度越快)
        //i 是改變的值
        leftBrain.changedeltaY(i);
        rightBrain.changedeltaY(i);
        wholeBrain.changedeltaY(i);
    }

    private ElementEffect genElementEffectState(String active) {
        ElementEffect.ElementTypeEnum elementEnum = ElementEffect.ElementTypeEnum.getElementTypeEnum(active);
        switch (elementEnum) {
            case CHECK_COLOR:
                return new ElementEffect.ColorBallEffect();
            case CHANGE_COLOR:
                return new ElementEffect.ColorChangeEffect();
            case SLOW_DOWN:
                return new ElementEffect.SlowDownEffect();
            case ADD_BLOOD:
                return new ElementEffect.AddBloodEffect();
            case THUNDER:
                return new ElementEffect.ThunderEffect();
            case INVINCIBLE:
                return new ElementEffect.Invincible();
        }
        return null;
    }

    //判斷碰撞Start-->用相對座標算
    //合腦時(分有投影無投影 0是無 1是有)
    //投影不同的碰撞邊界之分還沒
    private void oneCarCollision() {
        for (int i = 0; i < elementBag.getBag().size(); i++) {
            if (needToCheckCollision(wholeBrain, elementBag.getElement(i))) {//相對座標的y有進入判定區(裡面會依據是否有投影取值)
                //判斷是哪一種element-->用狀態模式呼叫執行效果
                if (gameLevel == 2) {//如果是第三關直接設定genElementEffectState = question
                    ElementEffect elementeffectState = new ElementEffect.QuestionEffect();
                    elementeffectState.collisionHappen(wholeBrain, elementBag.getElement(i), questionBall1, bloodBar, scoreCounter, this);//判定區的話~~

                } else {
                    ElementEffect elementeffectState = genElementEffectState(elementBag.getElement(i).getName());//用球名字判斷
                    elementeffectState.collisionHappen(wholeBrain, elementBag.getElement(i), questionBall1, bloodBar, scoreCounter, this);
                }
            }
        }
    }

    //兩台車時的碰撞判定
    private void twoCarCollision() {
        for (int i = 0; i < elementBag.getBag().size(); i++) {
            //先判斷球的相對y座標進入了需要判定區
            if (needToCheckCollision(leftBrain, elementBag.getElement(i))) {
                //左腦
                //先判斷物體是在左兩個跑道
                //判斷是哪一種ball(chack還是change)-->用狀態模式呼叫執行效果
                if (elementBag.getElement(i).getRight() < 349) {
                    if (gameLevel == 2) {//如果是第三關直接設定genElementEffectState = question
                        ElementEffect elementeffectState = new ElementEffect.QuestionEffect();
                        elementeffectState.collisionHappen(leftBrain, elementBag.getElement(i), questionBall1, bloodBar, scoreCounter, this);//判定區的話~~
                    } else {
                        ElementEffect elementeffectState = genElementEffectState(elementBag.getElement(i).getName());//一二關用球判斷
                        elementeffectState.collisionHappen(leftBrain, elementBag.getElement(i), questionBall1, bloodBar, scoreCounter, this);//要改甚麼都丟進去?
                    }
                }
                //右腦
                //先判斷物體是在右兩個跑道
                //判斷是哪一種ball(chack還是change)-->用狀態模式呼叫執行效果
                if (elementBag.getElement(i).getLeft() > 349) {
                    if (gameLevel == 2) {//如果是第三關直接設定genElementEffectState = question
                        ElementEffect elementeffectState = new ElementEffect.QuestionEffect();
                        elementeffectState.collisionHappen(rightBrain, elementBag.getElement(i), questionBall2, bloodBar, scoreCounter, this);
                    } else {
                        ElementEffect elementeffectState = genElementEffectState(elementBag.getElement(i).getName());//用球判斷
                        elementeffectState.collisionHappen(rightBrain, elementBag.getElement(i), questionBall2, bloodBar, scoreCounter, this);
                    }
                }
            }
        }
    }

    public boolean needToCheckCollision(GameObject brain, GameObject ball) {
        if (ball.getBottom() > brain.getBottom() - brain.getHeight() / 3 && ball.getBottom() < brain.getBottom()
                && ball.getTop() < brain.getTop() + brain.getHeight() / 3 && ball.getTop() > brain.getTop()) {

            return true;
        }
        return false;
    }

    //是否要投影-->影響背景(背景圖&車道的視角)
    private void changeProjection() {
        if (projectionOrNot == 0) {//0是無 1是有
            projectionOrNot = 1;
            background.changeProjrction(projectionOrNot);
        } else if (projectionOrNot == 1) {
            projectionOrNot = 0;
            background.changeProjrction(projectionOrNot);
        }
    }

    //一個玩家 設定oneCar or TwoCar-->改車道 改要出線的腦的顏色
    private void changeCarNumState() {
        if (oneBrain == false) {//如果原本是分左右
            oneBrain = true;//合併
            background.changeCarNum(0);//換跑道
            //設定合腦的顏色
            if (gameLevel == 0 || gameLevel == 1) {
                wholeBrain.changeColor(1);
            }
        } else if (oneBrain == true) {
            oneBrain = false;//變為左右腦的狀態
            background.changeCarNum(1);//換跑道
            //設定左右腦的顏色
            if (gameLevel == 0 || gameLevel == 1) {
                leftBrain.changeColor(1);
                rightBrain.changeColor(1);
            }
        }
    }

    //getter
    public LeftBrain getLeftBrain() {
        return leftBrain;
    }

    public RightBrain getRightBrain() {
        return rightBrain;
    }

    public WholeBrain getWholeBrain() {
        return wholeBrain;
    }

    public boolean getOneBrain() {
        return oneBrain;
    }

    public BallBag getElementBag() {
        return elementBag;
    }

    //印東西(待處理)
    //要考慮 是否投影 玩家人數
    @Override
    public void paint(Graphics g) {
        // 1. 印背景
        background.paint(g);    // 印 wallpaper 背景圖 track 車道

        //6.如果是第三關要印題目
        if (gameLevel == 2) {
            if (oneBrain) {
                questionBall1.paint(g);
            } else if (!oneBrain) {
                questionBall1.paint(g);
                questionBall2.paint(g);
            }
        }
        // 3. 印跑道球
        if (projectionOrNot == 0) { // 無投影 0
            for (int i = 0; i < elementBag.getBag().size(); i++) {//每個物件一一列印 elementCamera會判斷你可以不可以被印出
                elementCamera.paint(elementBag.getElement(i), g);
                if (elementCamera.needReset(projectionOrNot, elementBag.getElement(i))) {//被碰撞的超過frame後要重新設定碰撞狀態
                    elementBag.getElement(i).setisCollisioned(0);
                    elementBag.getElement(i).changeNeedPrint(true);
                    if (elementBag.getElement(i).getName().equals("check")) {
                        elementBag.getElement(i).changeColor();//隨機換顏色
                    }
                }
            }
        } else if (projectionOrNot == 1) {  // 有投影 1
            for (int i = 0; i < elementBag.getBag().size(); i++) {
                elementCamera.paintProjection(elementBag.getElement(i), g);
                if (elementCamera.needReset(projectionOrNot, elementBag.getElement(i))) {//被碰撞的超過frame後要重新設定碰撞狀態
                    elementBag.getElement(i).setisCollisioned(0);
                    elementBag.getElement(i).changeNeedPrint(true);
                    if (elementBag.getElement(i).getName().equals("check")) {
                        elementBag.getElement(i).changeColor();//隨機換顏色
                    }
                }
            }
        }

        // 5. 印腦(會依 是否投影 & 是否OneCar )
        if (oneBrain == false) {//分左右腦的狀態
            if (projectionOrNot == 0) {//沒有投影
                elementCamera.paint(leftBrain, g);
                elementCamera.paint(rightBrain, g);
            } else if (projectionOrNot == 1) {//有投影
                elementCamera.paintProjection(leftBrain, g);
                elementCamera.paintProjection(rightBrain, g);
            }
        } else if (oneBrain == true) {//合腦的時候
            if (projectionOrNot == 0) {//沒有投影
                elementCamera.paint(wholeBrain, g);
            } else if (projectionOrNot == 1) {//有投影
                elementCamera.paintProjection(wholeBrain, g);
            }
        }

        // 透明
        g.drawImage(gameInfoImage, 275, 25, 250, 100, null);

        //UI
        //血條
        bloodBar.paintHP(g);
        scoreHint.paint(g);
        scoreCounter.paint(g);
    }

    @Override
    public void sceneEnd() {
        if (gameLevel == 0) {
            musicPlayer.stop(2);
        }
        if (gameLevel == 1) {
            musicPlayer.stop(3);
        }
        if (gameLevel == 2) {
            musicPlayer.stop(4);
        }
    }

    @Override
    public KeyCommandListener getKeyCommandListener() {
        return keyCommandListener;
    }
}
