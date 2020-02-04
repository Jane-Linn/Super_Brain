/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.File;
import java.util.ArrayList;
import javax.sound.sampled.*;

/**
 *
 * @author 奈米
 */
public class MusicPlayer {

    private static MusicPlayer musicPlayer;
    private Clip clip;
    private ArrayList<Clip> clipArray;

    private MusicPlayer() {
        clipArray = new ArrayList<>();
        setAllFile();
    }

    // singleton
    public static MusicPlayer getInstance() {
        if (musicPlayer == null) {
            musicPlayer = new MusicPlayer();
        }
        return musicPlayer;
    }

    // 整理用
    private void setAllFile() {
        //StartScene
        setFile("src/resources/audio/StartScene/StartScene.wav"); // 0 StartScene背景音
        //MainScene
        setFile("src/resources/audio/MainScene.wav");             // 1 MainScene背景音樂
        //GameScene
        setFile("src/resources/audio/GameScene/EasyLevel.wav");   // 2 EasyLevel
        setFile("src/resources/audio/GameScene/MiddleLevel.wav"); // 3 MiddleLevel
        setFile("src/resources/audio/GameScene/HardLevel.wav");   // 4 HardLevel
        //EndingScene
        setFile("src/resources/audio/ending.wav");                // 5 EndingScene背景音
        setFile("src/resources/audio/GameOver.wav");              // 6 noHP
        //Menu
        setFile("src/resources/audio/exit.wav");                  // 7 關閉遊戲
        setFile("src/resources/audio/Instruction.wav");           // 8 Instruction背景音
        setFile("src/resources/audio/HowToPlay.wav");             // 9 HowToPlay背景音
        setFile("src/resources/audio/RankScene/RankScene.wav");   // 10 RankScene背景音樂
        //通用
        setFile("src/resources/audio/click1.wav");      // 11 上下左右點擊聲
        setFile("src/resources/audio/nextscene.wav");   // 12 下一頁
        setFile("src/resources/audio/type.wav");        // 13 打字音效
        setFile("src/resources/audio/error.wav");       // 14 已有名字
        setFile("src/resources/audio/nextscene.wav");   // 15 menu open close   &選關
        //碰撞
        setFile("src/resources/audio/bingo.wav");       // 16 正確
        setFile("src/resources/audio/error.wav");       // 17 錯誤
        setFile("src/resources/audio/slowdown.wav");    // 18 變慢
        setFile("src/resources/audio/changeColor.wav"); // 19 換色 / 無敵
        setFile("src/resources/audio/thunder.wav");     // 20 打雷
        setFile("src/resources/audio/recoverHP.wav");   // 21 加血
    }

    private void setFile(String fileName) {
        try {
            File file = new File(fileName);
            AudioInputStream sound = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(sound);
            clipArray.add(clip);
        } catch (Exception ex) {
            System.out.println("音檔設定有問題!");
        }
    }

    // 播放
    public void play(int index) {
        clip = clipArray.get(index);
        clip.setFramePosition(0);
        clip.start();
    }

    // 重複播放
    public void loop(int index) {
        clip = clipArray.get(index);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    // 停止 (換場景切換音樂用)
    public void stop(int index) {
        clip = clipArray.get(index);
        clip.stop();
        //clip.close();
    }
}
