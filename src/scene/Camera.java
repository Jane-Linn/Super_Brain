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
public class Camera {

    private int x;//Camera的絕對座標,印出的範圍
    private int y;
    private int width;
    private int height;
//    private double speed;
//    private double moveunit;
//    private double deltaY;
//    private double deltaS;

    //內部類別(物件相對座標的修改)
    public class Transform {

        //不投影的修改
        public int transformY(GameObject obj) {//得到相對的y值
            return obj.getY() - y;
        }

        //投影的修改 Strat
        //從投影中心到實際印出的距離
        public double projectDistance = Math.sqrt((Math.pow(Global.SceneValue.FRAME_Y_OFFSET, 2) + Math.pow(Global.SceneValue.TRACK_LENGTH, 2)));
        public double eyeToScreen = Math.pow(Global.SceneValue.TRACK_LENGTH, 2) / projectDistance;

        public int projectionY(GameObject obj) {
            double angle1 = Math.atan(Global.SceneValue.TRACK_LENGTH / projectDistance);
            double angle2 = Math.atan((Global.SceneValue.TRACK_LENGTH * Global.SceneValue.FRAME_Y_OFFSET) / ((Global.SceneValue.FRAME_Y_OFFSET - transformY(obj)) * projectDistance));
            return Global.SceneValue.HORIZON_CENTER_Y + (int) (Math.tan(angle2 - angle1) * eyeToScreen);
        }

        public int projectionX(GameObject obj) {//得到相對的x值(依據y改變斜率)
            if (obj.getX() < 100) {
                double slope = Global.SceneValue.TRACK_LENGTH / (obj.getX() - 300);
                return ((int) ((projectionY(obj) - Global.SceneValue.HORIZON_CENTER_Y) / slope) + 300);
            }
            if (obj.getX() > 700) {
                double slope = Global.SceneValue.TRACK_LENGTH / (obj.getX() - 500);
                return ((int) ((projectionY(obj) - Global.SceneValue.HORIZON_CENTER_Y) / slope) + 500);
            }
            return 0;
        }

        public int projectionWidth(GameObject obj) {
            //還是projection(obj.getRight) - projection(obj.getLeft)會變斜斜的???????????????
            double elementDistance = transformY(obj);
            double playerDistance = 500;

            return (int) ((elementDistance / playerDistance) * obj.getWidth());
        }

        public int projectionHeight(GameObject obj) {
            double elementDistance = transformY(obj);
            double playerDistance = 500;

            return (int) ((elementDistance / playerDistance) * obj.getHeight());
        }

        public int getProjectBottom(GameObject obj) {
            return projectionY(obj) + projectionWidth(obj);
        }
    }
    //投影的修改 end

    public Camera() {

        width = Global.SceneValue.FRAME_X_OFFSET;
        height = Global.SceneValue.FRAME_Y_OFFSET;
        x = 0;
        y = 0;
//        this.speed = speed;
//        moveunit = (Global.SceneValue.SCENE_ACCELARATION * Global.Update.MILLISEC_PER_UPDATE * Global.Update.MILLISEC_PER_UPDATE / 1000);
//        deltaY = deltaS = 0.0;
    }

    //攝影機每次移動的距離(加速度)
    //1. 求位移量//每次camera(player應該往前移的距離)
//    private Transform transform;//不能這樣只能設定一個
//
//    public Camera() {
//       Transform transform = new Transform();
//    }
    //    public void getDisplacement() {
    //        deltaY += moveunit;
    //        deltaS += deltaY;
    //        System.out.print(deltaY);
    //    }
    //2. 更新camera的絕對y座標
    public void CameraMoveY(LeftBrain animal) {
//        if(animal.getDeltaY()<0){
//            y-=1;
//        }
        y -= animal.getDeltaY();
    }

    public boolean isCollision(LeftBrain player, GameObject obj) {
        Transform transform = new Transform();

        if (player.getLeft() > transform.projectionX(obj) + transform.projectionWidth(obj)) {
            return false;
        }
        if (player.getRight() < transform.projectionX(obj)) {
            return false;
        }
        if (player.getTop() > transform.projectionY(obj) + transform.projectionHeight(obj)) {
            return false;
        }
        if (player.getBottom() < transform.projectionY(obj)) {
            return false;
        }
        return true;
    }

    public void paint(GameObject obj, Graphics g) {
        //transform obj
        Transform transform = new Transform();
        if (transform.transformY(obj) > 0 && transform.transformY(obj) < Global.SceneValue.FRAME_Y_OFFSET) {
            obj.paint(obj.getX(), transform.projectionY(obj), obj.getWidth(), obj.getHeight(), g);
        }
    }

    public void paintProjection(GameObject obj, Graphics g) {
        //Projection obj
        Transform transform = new Transform();
        if (transform.transformY(obj) > 0 && transform.transformY(obj) < Global.SceneValue.FRAME_Y_OFFSET) {
            obj.paint(transform.projectionX(obj), transform.projectionY(obj), transform.projectionWidth(obj), transform.projectionHeight(obj), g);
        }
    }

    public boolean paintObject(GameObject obj, Graphics g) {
        //transform obj
        Transform transform = new Transform();
        if (transform.projectionY(obj) > Global.SceneValue.FRAME_Y_OFFSET) {
            return false;
        }
        if (transform.transformY(obj) > 0 && transform.transformY(obj) < Global.SceneValue.FRAME_Y_OFFSET) {
            obj.paint(obj.getX(), transform.projectionY(obj), obj.getWidth(), obj.getHeight(), g);
        }
        return true;
    }

    public boolean paintProjectionObject(GameObject obj, Graphics g) {
        Transform transform = new Transform();
        if (transform.projectionY(obj) > Global.SceneValue.FRAME_Y_OFFSET) {
            return false;
        }
        if (transform.transformY(obj) > 0 && transform.transformY(obj) < Global.SceneValue.FRAME_Y_OFFSET) {
            obj.paint(transform.projectionX(obj), transform.projectionY(obj), transform.projectionWidth(obj), transform.projectionHeight(obj), g);
        }
        return true;
    }
}
