/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scene;

import gameobject.GameObject;
import gameobject.actor.LeftBrain;
import java.awt.Graphics;
import utils.Global;

/**
 *
 * @author Wan Zhen Lin
 */
public class ElementCamera {

    private int x;//Camera的絕對座標,印出的範圍
    private int y;
    private int width;
    private int height;

    //內部類別(物件相對座標的修改)
    public class Transform {

        //不投影的修改
        public int transformY(GameObject obj) {//得到相對的y值
//        System.out.println("elementCamera的obj y"+obj.getName()+"/"+obj.getY());
            if (y > 0 && obj.getY() < Global.SceneValue.ACTUALL_Y_LENGTH + Global.SceneValue.FRAME_Y_OFFSET) {
//            System.out.println(obj.getName()+"elementCamera重新跑了"+y+"判斷物品y"+(obj.getY() - (Global.SceneValue.ACTUALL_Y_LENGTH + (y - Global.SceneValue.FRAME_Y_OFFSET))));
                return obj.getY() - (Global.SceneValue.ACTUALL_Y_LENGTH + (y - Global.SceneValue.FRAME_Y_OFFSET));//重跑但camera尾巴還沒重跑完
            }
            return obj.getY() - y;
        }
        public int transformY(int yPoint){
            if(y > 0 && yPoint < Global.SceneValue.ACTUALL_Y_LENGTH + Global.SceneValue.FRAME_Y_OFFSET) {
//            System.out.println("elementCamera重新跑了"+y+"判斷物品y"+(obj.getY() - (Global.SceneValue.ACTUALL_Y_LENGTH + (y - Global.SceneValue.FRAME_Y_OFFSET))));
                return yPoint - (Global.SceneValue.ACTUALL_Y_LENGTH + (y - Global.SceneValue.FRAME_Y_OFFSET));//重跑但camera尾巴還沒重跑完
            }
            return yPoint - y;
        }

        public int transformBottom(GameObject obj) {
            if (y > 0) {
                return obj.getBottom() - (Global.SceneValue.ACTUALL_Y_LENGTH + (y - Global.SceneValue.FRAME_Y_OFFSET));//重跑但camera尾巴還沒重跑完
            }
            return obj.getBottom() - y;
        }

        //投影的修改 Strat
        //從投影中心到實際印出的距離
        public double projectDistance = Math.sqrt((Math.pow(Global.SceneValue.FRAME_Y_OFFSET, 2) + Math.pow(Global.SceneValue.TRACK_LENGTH, 2)));
        public double eyeToScreen = Math.pow(Global.SceneValue.TRACK_LENGTH, 2) / projectDistance;

        public int projectionY(GameObject obj) {
            double angle1 = Math.atan(Global.SceneValue.TRACK_LENGTH / projectDistance);
            double angle2 = Math.atan((Global.SceneValue.TRACK_LENGTH * Global.SceneValue.FRAME_Y_OFFSET) / ((Global.SceneValue.FRAME_Y_OFFSET - transformY(obj)) * projectDistance));
//            System.out.println("projectionY"+ Global.SceneValue.HORIZON_CENTER_Y + (int) (Math.tan(angle2 - angle1) * eyeToScreen));
            return Global.SceneValue.HORIZON_CENTER_Y + (int) (Math.tan(angle2 - angle1) * eyeToScreen);//改跑跑
        }
       public int projectionY(int yPoint) {
            double angle1 = Math.atan(Global.SceneValue.TRACK_LENGTH / projectDistance);
            double angle2 = Math.atan((Global.SceneValue.TRACK_LENGTH * Global.SceneValue.FRAME_Y_OFFSET) / ((Global.SceneValue.FRAME_Y_OFFSET - yPoint) * projectDistance));

            return Global.SceneValue.HORIZON_CENTER_Y + (int) (Math.tan(angle2 - angle1) * eyeToScreen);//改跑跑
        } 

        public int projectionX(GameObject obj) {//得到相對的x值(依據y改變斜率)
            int objCenterX = obj.getX() + obj.getWidth() / 2;
            double tatolY = Global.SceneValue.TRACK_LENGTH + 5;
            if (objCenterX < 400 && objCenterX > 250) {//2
                double slope = (tatolY) / (290 - Global.SceneValue.HORIZON_CENTER_X);
                return ((int) ((projectionY(obj) - (Global.SceneValue.FRAME_Y_OFFSET - tatolY)) / slope) + Global.SceneValue.HORIZON_CENTER_X - projectionWidth(obj) / 2) - 3;
            }
            if (obj.getX() > 100 && obj.getX() < 250) {//1
                double slope = (tatolY) / (50 - Global.SceneValue.HORIZON_CENTER_X);
                return ((int) ((projectionY(obj) - (Global.SceneValue.FRAME_Y_OFFSET - tatolY)) / slope) + Global.SceneValue.HORIZON_CENTER_X - projectionWidth(obj) / 2) - 6;
            }
            if (obj.getX() > 400 && obj.getX() < 550) {//3
                double slope = (tatolY) / (510 - Global.SceneValue.HORIZON_CENTER_X);
                return ((int) ((projectionY(obj) - (Global.SceneValue.FRAME_Y_OFFSET - tatolY)) / slope) + Global.SceneValue.HORIZON_CENTER_X - projectionWidth(obj) / 2) + 3;
            }
            if (obj.getX() > 550 && obj.getX() < 700) {//4
                double slope = (tatolY) / (750 - Global.SceneValue.HORIZON_CENTER_X);
                return ((int) ((projectionY(obj) - (Global.SceneValue.FRAME_Y_OFFSET - tatolY)) / slope) + Global.SceneValue.HORIZON_CENTER_X - projectionWidth(obj) / 2) + 6;
            }
            return 0;

        }

        public int projectionWidth(GameObject obj) {
            //還是projection(obj.getRight) - projection(obj.getLeft)會變斜斜的???????????????
            double elementDistance = transformY(obj);
            double playerDistance = Global.Actor.ACTOR_PRINT_Y_POINT;
            return (int) ((elementDistance / playerDistance) * obj.getWidth());
        }

        public int projectionHeight(GameObject obj) {
            double elementDistance = transformY(obj);
            double playerDistance = Global.Actor.ACTOR_PRINT_Y_POINT;
            return (int) ((elementDistance / playerDistance) * obj.getHeight());
//            double top = projectionY(obj.getTop());
//            double bottom = projectionY(obj.getBottom());
//            return(int)(bottom - top );
        }
        
        
        public int getProjectTop(GameObject obj){
            return projectionY(obj.getTop());
        }
        public int getProjectBottom(GameObject obj) {
            return projectionY(obj.getBottom());
        }
    }
    //投影的修改 end

    public ElementCamera() {
        width = Global.SceneValue.FRAME_X_OFFSET;
        height = Global.SceneValue.FRAME_Y_OFFSET;
        x = 0;
        y = 0;
    }

    //2. 更新camera的絕對y座標(走完全長會重新走)
    public void CameraMoveY(LeftBrain brain) {
        y -= brain.getDeltaY();//camera的y實際座標
        if (y < Global.SceneValue.ACTUALL_Y_LENGTH) {//超過預設全長
//            System.out.println("camera重跑");
            y = Global.SceneValue.FRAME_Y_OFFSET - (y % Global.SceneValue.ACTUALL_Y_LENGTH);
        }
    }

    //判斷球進入y軸判定區(分有投影無投影)
//    public boolean needToCheckCollision(int projectionOrNot, GameObject brain, GameObject ball) {
//        Transform transform = new Transform();
//        if (projectionOrNot == 0) {
//            if (transform.transformBottom(ball) >= transform.transformY(brain) && transform.transformY(ball) <= transform.transformBottom(brain)) {
//                return true;
//            }
//        } else if (projectionOrNot == 1) {
//            if (transform.getProjectBottom(ball) >= transform.getProjectBottom(brain)-(transform.projectionHeight(brain))/3 && transform.getProjectBottom(ball)<=transform.getProjectBottom(brain) && transform.getProjectTop(ball) <= transform.getProjectTop(brain)+(transform.projectionHeight(brain))/3&&transform.getProjectTop(ball)>=transform.getProjectTop(brain)  ) {//製造一個假裝的z軸碰撞(在一半時才算)
//                System.out.println("elementCamera判斷球進入判斷區" +"球: "+ ball.getY()+ " 球底: " + transform.getProjectBottom(ball) + "腦上: " + (transform.projectionY(brain) + transform.projectionHeight(brain) / 3)
//                        + "球頂: " + transform.projectionY(ball) + "腦底: " + (transform.getProjectBottom(brain) - transform.projectionHeight(brain) / 3));
//                return true;
//            }
//        }
//        return false;
//    }

    //被撞過的球過了frame後要重新設定為未撞過
    public boolean needReset(int projectionOrNot, GameObject ball) {
        Transform transform = new Transform();
        switch (projectionOrNot) {
            case 0:
                if (transform.transformY(ball) > Global.SceneValue.FRAME_Y_OFFSET) {
                    return true;
                }
            case 1:
                if (transform.projectionY(ball) > Global.SceneValue.FRAME_Y_OFFSET) {
                    return true;
                }
        }
        return false;
    }
    //印
    public void paint(GameObject obj, Graphics g) {
        //transform obj
        Transform transform = new Transform();
        if (transform.transformY(obj) > 0 && transform.transformY(obj) < Global.SceneValue.FRAME_Y_OFFSET) {
            obj.paint(obj.getX(), transform.transformY(obj), obj.getWidth(), obj.getHeight(), g);
        }
        if (transform.transformY(obj) > Global.SceneValue.FRAME_Y_OFFSET) {//物體跑出frame後都要重新設定他為還沒做效果
            obj.setisCollisioned(0);
        }

    }

    public void paintProjection(GameObject obj, Graphics g) {
        //Projection obj
        Transform transform = new Transform();
        if(obj.getName().equals("leftBrain")){
//            System.out.println("左腦相對座標"+transform.projectionY(obj));
        }
        if (transform.projectionY(obj) > Global.SceneValue.HORIZON_CENTER_Y && transform.projectionY(obj) < Global.SceneValue.FRAME_Y_OFFSET) {
            obj.paint(transform.projectionX(obj), transform.projectionY(obj), transform.projectionWidth(obj), transform.projectionHeight(obj), g);
//                System.out.println("GameScene 印了"+ obj.getName()+obj.getCurrentColor()+obj.getY());
        }
        if (transform.projectionY(obj) > Global.SceneValue.FRAME_Y_OFFSET) {//物體跑出frame後都要重新設定他為還沒做效果
            obj.setisCollisioned(0);
        }
    }
}
