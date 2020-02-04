/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package values;

public class ImagePath {

    // Character
    public static class Character {

        public static final String FOLDER_CHARACTER = "/Characters";

        public static class Brain {

            public static final String LEFT_BRAIN1 = FOLDER_CHARACTER + "/colorbrain1.png";
            public static final String LEFT_BRAIN2 = FOLDER_CHARACTER + "/questionLeft.png";
            public static final String RIGHT_BRAIN1 = FOLDER_CHARACTER + "/colorbrain2.png";
            public static final String RIGHT_BRAIN2 = FOLDER_CHARACTER + "/questionRight.png";            
            public static final String WHOLE_BRAIN1 = FOLDER_CHARACTER + "/wholeBrain.png";
            public static final String WHOLE_BRAIN2 = FOLDER_CHARACTER + "/questionWhole.png";
        }

        public static class ElementPic {

            public static final String E1 = FOLDER_CHARACTER + "/ball1.png";
        }
    }

    public static class Background {

        public static final String FOLDER_BACKGROUND = "/Background";

        public static class StartScene {

            public static final String SCENE1 = FOLDER_BACKGROUND + "/Scene1.jpg";// 800 * 600
            public static final String BRAINANIMATION = FOLDER_BACKGROUND + "/brainAnimation.png";// 200 * 200
            public static final String STARTSCENE = FOLDER_BACKGROUND + "/StartScene.png";
            public static final String START_ANIMATION_BRAIN = FOLDER_BACKGROUND + "/StartAnimationBrain.png";
            public static final String RETYPE_NAME = FOLDER_BACKGROUND+"/reType.png";
        }

        public static class MainScene {

            public static final String GAMELEVEL = FOLDER_BACKGROUND + "/GameLevel.png";// 190 * 195
            public static final String SELECTION = FOLDER_BACKGROUND + "/Selection.png";// 215 * 270
            public static final String MENU = FOLDER_BACKGROUND + "/Menu.png";// 345 * 410
        }

        public static class GameScene {

            public static final String GAMESCENE = FOLDER_BACKGROUND + "/GameScene.png";// 800 * 600
            public static final String GAMEINFO = FOLDER_BACKGROUND + "/gameInfo.png";
        }

        public static class RankScene {

            public static final String BUTTON = FOLDER_BACKGROUND + "/Button.png";// 100 * 100
            public static final String SELECTION2 = FOLDER_BACKGROUND + "/Selection2.png";// 160 * 90
        }

        public static class Instruction {

            public static final String SCENE2 = FOLDER_BACKGROUND + "/Scene2.jpg";// 800 * 600
        }

        public static class HowToPlayScene {

            public static final String SCENE2 = FOLDER_BACKGROUND + "/Scene2.jpg";// 800 * 600
            public static final String BUTTON = FOLDER_BACKGROUND + "/Button.png";// 100 * 100
            public static final String HOWTOPLAY = FOLDER_BACKGROUND + "/HowToPlay.png";// 683 * 318
        }

        public static class EndingScene {

            public static final String SCENE2 = FOLDER_BACKGROUND + "/Scene2.jpg";// 800 * 600
//            public static final String RETRY_ICON = FOLDER_BACKGROUND + "/retry.png";
//            public static final String QUIT_ICON = FOLDER_BACKGROUND + "/quit.png";
//            public static final String GAMEOVER_ICON = FOLDER_BACKGROUND + "/gameover.png";
        }

        public static class GameWallPaper {

            public static final String PAPER1 = FOLDER_BACKGROUND + "/background1.png";
        }

        public static class Track {

            public static final String TRACK1 = FOLDER_BACKGROUND + "/track.png";//800*600
        }

        public static class PassObject {

            public static final String PASSEDOBJECT1 = FOLDER_BACKGROUND + "/passObject1.png";
        }

    }

    public static class UI {

        public static final String FOLDER_BUTTON = "/UI";

        public static class Button {    // 216*117

            public static final String BUTTON = FOLDER_BUTTON + "/button.png";

        }
    }

}
