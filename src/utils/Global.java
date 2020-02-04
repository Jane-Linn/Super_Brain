/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;
public class Global {
 public class KeyNumber {

        // 上下左右常數制定
        public static final int UP = 3;
        public static final int LEFT = 1;
        public static final int DOWN = 0;
        public static final int RIGHT = 2;
        public static final int SPACE = 4;
        public static final int ENTER = 5;
        public static final int W = 13;
        public static final int A = 11;
        public static final int D = 12;
        public static final int S = 10;
        public static final int Q = 14;
        public static final int E = 15;

        // 按 BACK_SPACE 刪掉使用者輸入的 char
        public static final int BACK_SPACE = 87;

        // 結算畫面按 ESC 退回主畫面 
        public static final int ESCAPE = 88;

        //遊戲主選單
        public static final int M = -2;
        public static final int N = -3;
        public static final int J = -4;

        //MENU選擇
        public static final int ONE = 90;//背景介紹
        public static final int TWO = 91;//怎麼玩
        public static final int THREE = 92;//排行榜
        public static final int FOUR = 93;//重新登入
        public static final int FIVE = 94;//離開遊戲

        public static final int P = 6;//加朋友
        public static final int L = 7;//離開遊戲
        public static final int O = 8;//商店
        public static final int I = 9;//排行榜
        public static final int K = 16;//回登入畫面
        public static final int U = 17;//遊戲說明

        //切換車子數
        public static final int Z = 18;
    }

    //更新相關
    public class Update {

        // 資料刷新時間
        public static final int UPDATE_TIMES_PER_SEC = 30;//每秒偵數
        public static final int MILLISEC_PER_UPDATE = 1000 / UPDATE_TIMES_PER_SEC;//一偵要花多少毫秒

        // 畫面更新時間
        public static final int FRAME_LIMIT = 120;//刷新頁面的上限(每秒最多30次，一偵一次的時候)
        public static final int LIMIT_DELTA_TIME = 1000 / FRAME_LIMIT;//刷新一次的間隔最少要等多少秒以上才可以重印

        // 物件delay計算 (先放著)
        public static final int ANIMA_DELAY = UPDATE_TIMES_PER_SEC / 15;
    }

    //場景
    public class SceneValue {

        //Frame/Camera的大小(會影響車道斜率)
        public static final double FRAME_RATE = 4.0 / 3.0;
        public static final int FRAME_X_OFFSET = 800;
        public static final int FRAME_Y_OFFSET = (int) (800.0 / FRAME_RATE);
        //實際Y軸的長度(用來設定camera重跑)
        public static final int ACTUALL_Y_LENGTH = -14200;//最上點
        //道路最遠處(和障礙物印出有關)
        public static final int HORIZON_CENTER_X = FRAME_X_OFFSET / 2;
        public static final int HORIZON_CENTER_Y = 100;

        //車道的斜率
        public static final double TRACK_WIDTH = 100;//1個車道的寬度(變兩個Panel時會變一半 會影響車道的斜率(變2倍)-->會影響障礙物的斜率)
        public static final double TRACK_LENGTH = Global.SceneValue.FRAME_Y_OFFSET - Global.SceneValue.HORIZON_CENTER_Y;
        public static final double TRACK_SLOPE = (FRAME_Y_OFFSET * 2 / 3) / (TRACK_WIDTH * 2);//車道的斜率

        //場景加速度
        public static final double SCENE_ACCELARATION = 0.01;
    }

    // 背景物
    public class Object {

        // 圖片大小制定        
        public static final int IMG_X_OFFSET = 32;
        public static final int IMG_Y_OFFSET = 32;

        // Bar
        public static final int DATA_PER_PAGE = 4;  // 排行榜每頁顯示多少筆資料
    }

    //角色
    public class Actor {

        public static final int ACTOR_X_OFFSET = 100;
        public static final int ACTOR_Y_OFFSET = 100;
        public static final int ACTOR_PRINT_X_SIZE = 100;
        public static final int ACTOR_PRINT_Y_SIZE = 100;
        public static final int ACTOR_PRINT_Y_POINT = 500;
                //actor的移動距離和delay
        public static final int NORMAL_DELTA_Y = 6;
        public static final int LIMIT_DELTA_Y = 9;
        public static final int NORMAL_DELAY = 5;
        public static final int LIMIT_DELAY =6;
    }

    //障礙物
    public class Element {

        public static final int ELEMENT_X_OFFSET = 100;
        public static final int ELEMENT_Y_OFFSET = 100;
        public static final int ELEMENT_PRINT_X_SIZE = 60;
        public static final int ELEMENT_PRINT_Y_SIZE = 60;

        //名字(防打錯)
        public static final String BALL = "check";
        public static final String CHANGE = "change";
        public static final String QUESTION = "question";
        public static final String THUNDER = "thunder";
        public static final String SLOW_DOWN = "slowdown";
        public static final String ADD_BLOOD = "addblood";
        public static final String INVINCIBLE = "invincible";
    }

    // 物件移動調整常數
    public class ObjectMoveValue {

        public static final int ANGLE_Y = 45;
        public static final int OBJECT_SPEED_REVISED_VALUE = 240 / Global.Update.UPDATE_TIMES_PER_SEC;    //物件的移動速度(不受偵數影響)

        //單人遊戲背景物的移動 Start
        public static final double TOTAL_Y = Global.SceneValue.FRAME_Y_OFFSET - Global.SceneValue.HORIZON_CENTER_Y;
        public static final int TOTAL_TIME = 10000000 * Global.Update.MILLISEC_PER_UPDATE;//想要花幾幀*一幀的毫秒數=總共花的時間    

        //控制背景物[跑]&[放大]的程度
        public static final double SLOW_DOWN = 25;

        //背景物件的加速度 (算y軸的就好，算出 [y方向的加速改變距離] / [車道的斜率變動slope] = [x方向的加速改變距離])
        public static final double ACCELERATION = ((TOTAL_Y * 2 / (TOTAL_TIME * TOTAL_TIME))) / SLOW_DOWN;//SLOW_DOWN可以讓背景物跑比較久(讓a變小)
    }

    // Text (Font 可以寫成 static)
    public class TextType {

//        public static final String TEXTSTYLE = "Tw Cen MT Condensed Extra Bold";
//        public static final String TEXTSTYLE = "Calabr";
        public static final String TEXTSTYLE = "monospaced";
        
    }

    public class Player {

        public static final int LIMIT_NAME_LENGTH = 8;  // 名字長度 // 11/7 => 6 改成 8
        public static final int LIMIT_SCORE_DIGIT = 5;    // 分數上限位數
        public static final int LIMIT_SOCRE = 99999;      // 分數上限
        public static final int LIMIT_COIN = 99999;        // 金幣上限
    }
}

