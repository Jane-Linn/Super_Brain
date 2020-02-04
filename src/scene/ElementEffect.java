/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scene;

import gameobject.GameObject;
import gameobject.ball.Bar;
import gameobject.ball.QuestionBall;
import gameobject.sceneui.Text;
import java.util.ArrayList;
import utils.MusicPlayer;

/**
 *
 * @author Wan Zhen Lin
 */
//狀態模式 [當角色的Top(y)值和element的Bottom(y)值重疊時] 什麼element要做甚麼效果
//在GameScene判斷呼叫
public interface ElementEffect {

    //enum Start
    public enum ElementTypeEnum {

        CHECK_COLOR("check"), CHANGE_COLOR("change"), CHECK_MIX("question"), SLOW_DOWN("slowdown"), ADD_BLOOD("addblood"), THUNDER("thunder"), INVINCIBLE("invincible");
        private String active;

        private ElementTypeEnum(String active) {
            this.active = active;
        }

        public String getName() {
            return this.active;
        }

        public static ElementTypeEnum getElementTypeEnum(String active) {
            for (ElementTypeEnum elementTypeEnum : ElementTypeEnum.values()) {
                if (elementTypeEnum.getName().equals(active)) {
                    return elementTypeEnum;
                }
            }
            return null;
        }
    }

    //enum End
    //判斷是否碰撞過
    public abstract void collisionHappen(GameObject actor, GameObject element, QuestionBall question, Bar bloodBar, Text scoreCounter, GameScene gameScene);

    //判斷為碰撞到第一次要做甚麼反應
    public abstract void effect(GameObject element, Bar bloodBar, Text scoreCounter);

    public abstract void bingo(GameObject element, Text scoreCounter, boolean oneBrain);

    public abstract void effect(GameObject actor);

    //要確認顏色球(有碰撞要一樣顏色)(沒有碰撞要不同顏色)
    //ok
    public class ColorBallEffect implements ElementEffect {

        MusicPlayer musicPlayer = MusicPlayer.getInstance();

        @Override
        public void collisionHappen(GameObject actor, GameObject element, QuestionBall question, Bar bloodBar, Text scoreCounter, GameScene gameScene) {
            //1. 在GameScene判斷為y軸應確認區的物件傳入這裡後
            //2. 首先要先確認顏色是否與角色一樣
            if (actor.getCurrentColor().equals(element.getCurrentColor())) {//一樣的話-->沒有碰撞到要扣分
                if (!actor.isCollision(element)) {//腦沒有撞到色球
                    if (element.getisCollisioned() == false) {//這顆色球還沒做效果
                        effect(element, bloodBar, scoreCounter);//扣分 + 顯示叉叉
                    }
                } else {
                    if (actor.isCollision(element)) {//有碰撞
                        if (element.getisCollisioned() == false) {//有扣過(做效果)了嗎
                            bingo(element, scoreCounter, gameScene.getOneBrain());//??????????未設定 
                        }
                        element.changeNeedPrint(false);//不管碰到甚麼顏色 這顆球都要消失
                    }
                }
            } else if (!actor.getCurrentColor().equals(element.getCurrentColor())) {//顏色不一樣的話-->撞到要扣分
                if (!actor.isCollision(element)) {//腦沒有撞到色球
                    if (element.getisCollisioned() == false) {//這顆色球還沒做效果
//                        bingo(element, scoreCounter,gameScene.getOneBrain());//對的 --> 效果未設定(音效? 一個+幾分的動畫?)
                    }
                } else {
                    if (actor.isCollision(element)) {//有碰撞
                        if (element.getisCollisioned() == false) {//有扣過(做效果)了嗎
                            effect(element, bloodBar, scoreCounter);//扣分 + 顯示叉叉
                        }
                        element.changeNeedPrint(false);//不管碰到甚麼顏色 這顆球都要消失
                    }
                }
            }
        }

        @Override
        public void effect(GameObject element, Bar bloodBar, Text scoreCounter) {//扣分 + 顯示叉叉
            musicPlayer.play(17);
            element.effect(bloodBar, scoreCounter);//還沒的話就做效果(扣血、印叉叉)
            element.setisCollisioned(1);//扣完標示已扣過(超出y軸判斷領域後要設回來)
        }

        public void bingo(GameObject element, Text scoreCounter, boolean oneBrain) {
            musicPlayer.play(16);
            element.bingo(scoreCounter, oneBrain);
            element.setisCollisioned(1);//扣完標示已扣過(超出y軸判斷領域後要設回來)
        }

        @Override
        public void effect(GameObject actor) {//撞到彩球
        }

    }

//要換玩家的顏色(碰撞到就actor換色)
    //ok
    public class ColorChangeEffect implements ElementEffect {

        MusicPlayer musicPlayer = MusicPlayer.getInstance();

        public void collisionHappen(GameObject actor, GameObject element, QuestionBall question, Bar bloodBar, Text scoreCounter, GameScene gameScene) {
            if (actor.isCollision(element)) {
                element.changeNeedPrint(false);//不管碰到甚麼顏色 這顆球都要消失
                if (element.getisCollisioned() == false) {
                    effect(actor);
                }
                element.setisCollisioned(1);
            }

        }

        @Override
        public void effect(GameObject element, Bar bloodBar, Text scoreCounter) {
        }

        public void bingo(GameObject element, Text scoreCounter, boolean oneBrain) {

        }

        @Override
        public void effect(GameObject actor) {
            musicPlayer.play(19);
            actor.changeColor();
        }
    }

    //ok
    public class QuestionEffect implements ElementEffect {

        MusicPlayer musicPlayer = MusicPlayer.getInstance();

        @Override
        public void collisionHappen(GameObject actor, GameObject element, QuestionBall question, Bar bloodBar, Text scoreCounter, GameScene gameScene) {
            //1. 在GameScene判斷為y軸應確認區的物件傳入這裡後
            //2. 首先要先確認顏色是否是應該要碰的顏色
            //是不是可以碰的顏色1 是的話-->3.
            //不是的話-->
            //是不是顏色2--> 是的話 -->3.
            //不是的話 -->1.完成 2.兩個顏色都不是
            //3. 在確認是不是有碰撞到(依照應不應該撞做效果)
            if (question.getBingoNum() == 2) {
                //代表這題完成-->換顏色 音效? 打勾? 

                bingo(question, scoreCounter, gameScene.getOneBrain());
                actor.changeColor(question.getCurrentColor());

            } else if (question.checkContainColor1(element.getCurrentColor())) {//一號球進入判定區
                if (actor.isCollision(element)) {//有碰撞

                    if (element.getisCollisioned() == false) {//有扣過(做效果)了嗎
                        question.setHasContainColor1(true);
                        actor.changeColor(question.getContain2Num());
                        question.setBingoNum(1);
                        musicPlayer.play(16);//撞到可以撞的
                        element.setisCollisioned(1);
                    }
                    element.changeNeedPrint(false);//不管碰到甚麼顏色 這顆球都要消失
                } else if (!actor.isCollision(element)) {//應撞而沒撞
                    if (element.getisCollisioned() == false) {//這顆色球還沒做效果
                        musicPlayer.play(17);//應撞而沒撞
                        effect(element, bloodBar, scoreCounter);//扣分 + 顯示叉叉
                    }
                }
            } else if (question.checkContainColor2(element.getCurrentColor())) {//二號球進入判定區
                if (actor.isCollision(element)) {//有碰撞

                    if (element.getisCollisioned() == false) {//有扣過(做效果)了嗎
                        question.setHasContainColor2(true);
                        actor.changeColor(question.getContain1Num());
                        question.setBingoNum(1);
                        musicPlayer.play(16);//撞到可以撞的
                        element.setisCollisioned(1);
                    }
                    element.changeNeedPrint(false);//不管碰到甚麼顏色 這顆球都要消失
                } else if (!actor.isCollision(element)) {//應撞而沒撞
                    if (element.getisCollisioned() == false) {//這顆色球還沒做效果
                        musicPlayer.play(17);//應撞而沒撞
                        effect(element, bloodBar, scoreCounter);//扣分 + 顯示叉叉
                    }
                }
            } else {
                //不是可以被撞到的顏色-->撞到就扣分
                if (actor.isCollision(element)) {//有碰撞
                    if (element.getisCollisioned() == false) {//有扣過(做效果)了嗎
                        musicPlayer.play(17);//撞到不該撞的
                        effect(element, bloodBar, scoreCounter);//扣分 + 顯示叉叉
                    }
                    element.changeNeedPrint(false);//不管碰到甚麼顏色 這顆球都要消失
                }
            }
        }

        @Override
        public void effect(GameObject element, Bar bloodBar, Text scoreCounter) {
            element.effect(bloodBar, scoreCounter);//還沒的話就做效果(扣血、印叉叉)
            element.setisCollisioned(1);//扣完標示已扣過(超出y軸判斷領域後要設回來)
        }

        public void bingo(GameObject question, Text scoreCounter, boolean oneBrain) {
            question.bingo(scoreCounter, oneBrain);

        }

        @Override
        public void effect(GameObject actor) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    //ok
    public class SlowDownEffect implements ElementEffect {

        MusicPlayer musicPlayer = MusicPlayer.getInstance();

        @Override//element是烏龜
        public void collisionHappen(GameObject actor, GameObject element, QuestionBall question, Bar bloodBar, Text scoreCounter, GameScene gameScene) {
            if (actor.isCollision(element)) {
                element.changeNeedPrint(false);//不管碰到甚麼顏色 這顆球都要消失
                if (element.getisCollisioned() == false) {
                    musicPlayer.play(18);
                    gameScene.changeDelay(+1);
                    gameScene.changeDeltaY(-2);
                    System.out.println("effect減速");
                }
                element.setisCollisioned(1);
            }
        }

        @Override
        public void effect(GameObject element, Bar bloodBar, Text scoreCounter) {

        }

        @Override
        public void bingo(GameObject element, Text scoreCounter, boolean oneBrain) {
        }

        @Override
        public void effect(GameObject actor) {
        }

    }

    public class Invincible implements ElementEffect {

        MusicPlayer musicPlayer = MusicPlayer.getInstance();

        @Override
        public void collisionHappen(GameObject actor, GameObject element, QuestionBall question, Bar bloodBar, Text scoreCounter, GameScene gameScene) {
            if (actor.isCollision(element)) {//撞到燈泡
                element.changeNeedPrint(false);//不管碰到甚麼顏色 這顆球都要消失
                if (element.getisCollisioned() == false) {//燈泡效果
                    musicPlayer.play(19);
                    int yPoint = actor.getY();//當下腦的實際座標
                    String currentColorName = actor.getCurrentColor();
                    int currentColorNum = BrainColorNum(gameScene.gameLevel,currentColorName);
                    System.out.println("currentColor" + currentColorNum);
                    for (int i = 0; i < gameScene.getElementBag().getBag().size(); i++) {
                        if (gameScene.getElementBag().getElement(i).getName().equals("check")) {
                            if (gameScene.getElementBag().getElement(i).getY() < yPoint && gameScene.getElementBag().getElement(i).getY() > yPoint - 1400) {
                                gameScene.getLeftBrain().changeColor(currentColorNum);
                                gameScene.getRightBrain().changeColor(currentColorNum);
                                gameScene.getWholeBrain().changeColor(currentColorNum);
                                gameScene.getElementBag().getElement(i).changeColor(currentColorNum);
                            }
                        }
                    }

                    element.setisCollisioned(1);
                }
            }
        }

        @Override
        public void effect(GameObject element, Bar bloodBar, Text scoreCounter) {
        }

        @Override
        public void bingo(GameObject element, Text scoreCounter, boolean oneBrain) {
        }

        @Override
        public void effect(GameObject actor) {
        }

        private int BrainColorNum(int gameLevel, String colorName) {
            if (gameLevel == 0) {

                if (colorName.equals("black")) {
                    return 0;
                } else if (colorName.equals("white")) {
                    return 1;
                }
            }
            if (gameLevel == 1) {
                if (colorName.equals("red")) {
                    return 0;
                } else if (colorName.equals("yellow")) {
                    return 1;
                } else if (colorName.equals("green")) {
                    return 2;
                } else if (colorName.equals("blue")) {
                    return 3;
                }
            }
            return 1;
        }

    }

    public class ThunderEffect implements ElementEffect {

        MusicPlayer musicPlayer = MusicPlayer.getInstance();

        @Override//element是thunder
        public void collisionHappen(GameObject actor, GameObject element, QuestionBall question, Bar bloodBar, Text scoreCounter, GameScene gameScene) {
            if (actor.isCollision(element)) {
                element.changeNeedPrint(false);//不管碰到甚麼顏色 這顆球都要消失
                if (element.getisCollisioned() == false) {//打雷效果
                    ArrayList<GameObject> arrayList = new ArrayList<>();
                    if (gameScene.getOneBrain()) {
                        arrayList.add(gameScene.getWholeBrain());
                    } else if (!gameScene.getOneBrain()) {
                        arrayList.add(gameScene.getLeftBrain());
                        arrayList.add(gameScene.getRightBrain());
                    }
                    int yPoint = actor.getY();
                    for (int i = 0; i < gameScene.getElementBag().getBag().size(); i++) {
                        if (gameScene.getElementBag().getElement(i).getName().equals("check")) {//只有colorBall會變色
                            if (gameScene.getElementBag().getElement(i).getY() < yPoint && gameScene.getElementBag().getElement(i).getY() > yPoint - 300) {
                                arrayList.add(gameScene.getElementBag().getElement(i));
                            }
                        }
                    }
                    effect(arrayList.get((int) (Math.random() * arrayList.size())));
                    element.setisCollisioned(1);
                }
            }
        }

        @Override
        public void effect(GameObject element, Bar bloodBar, Text scoreCounter) {
        }

        @Override
        public void bingo(GameObject element, Text scoreCounter, boolean oneBrain) {
        }

        @Override
        public void effect(GameObject actor) {
            actor.thunderHit();
            System.out.println("ElementEffect" + "設定thunder" + actor.getCurrentColor() + actor.getY());
            musicPlayer.play(20);
            actor.changeColor();//object或element隨機一個變色
            System.out.println("ElementEffect" + "換成" + actor.getCurrentColor() + actor.getY());
        }

    }

    //ok
    public class AddBloodEffect implements ElementEffect {

        MusicPlayer musicPlayer = MusicPlayer.getInstance();

        @Override//elemet是愛心
        public void collisionHappen(GameObject actor, GameObject element, QuestionBall question, Bar bloodBar, Text scoreCounter, GameScene gameScene) {
            if (actor.isCollision(element)) {
                element.changeNeedPrint(false);//不管碰到甚麼顏色 這顆球都要消失
                if (element.getisCollisioned() == false) {
                    effect(element, bloodBar, scoreCounter);
                }
                element.setisCollisioned(1);
            }
        }

        @Override
        public void effect(GameObject element, Bar bloodBar, Text scoreCounter) {   //加血
            musicPlayer.play(21);
            element.effect(bloodBar, scoreCounter);
        }

        @Override
        public void bingo(GameObject element, Text scoreCounter, boolean oneBrain) {
        }

        @Override
        public void effect(GameObject actor) {
        }

    }
}
