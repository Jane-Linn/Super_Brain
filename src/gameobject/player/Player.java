/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobject.player;

import utils.Global;

/**
 *
 * @author Wan Zhen Lin
 */
public class Player {

    private String name;
    private int easyModeScore; // 簡單模式分數
    private int middleModeScore; // 中等模式分數
    private int hardModeScore; // 困難模式分數
    private Bag bag;

    public Player(String name) {
        this.name = name;
        easyModeScore = 0;
        middleModeScore = 0;
        hardModeScore = 0;
        bag = new Bag();
    }

    // for playerFile 的建構子
    public Player(String name, int easyModeScore, int middleModeScore, int hardModeScore) {
        this.name = name;
        this.easyModeScore = easyModeScore;
        this.middleModeScore = middleModeScore;
        this.hardModeScore = hardModeScore;
        this.bag = new Bag();
    }

    // 最大score為: 99999 (LIMIT_SOCRE)
    // 獲得分數 > 之前紀錄的分數才 update
    public void updateEasyModeScore(int easyModeScore) {
        if (easyModeScore > this.easyModeScore && easyModeScore <= Global.Player.LIMIT_SOCRE) {
            this.easyModeScore = easyModeScore;
        }
    }

    public void updateMiddleModeScore(int middleModeScore) {
        if (middleModeScore > this.middleModeScore && middleModeScore <= Global.Player.LIMIT_SOCRE) {
            this.middleModeScore = middleModeScore;
        }
    }

    public void updateHardModeScore(int hardModeScore) {
        if (hardModeScore > this.hardModeScore && hardModeScore <= Global.Player.LIMIT_SOCRE) {
            this.hardModeScore = hardModeScore;
        }
    }

    public int getEasyModeScore() {
        return easyModeScore;
    }

    public int getMiddleModeScore() {
        return middleModeScore;
    }

    public int getHardModeScore() {
        return hardModeScore;
    }

    public Bag getBag() {
        return bag;
    }

    public String getName() {
        return name;
    }

    public String toString(int gameLevel) {
        String str = "ID:" + name;
        for (int i = name.length(); i < Global.Player.LIMIT_NAME_LENGTH; i++) {// 名字長度~最大限制名字長度 (補空白)
            str += " ";
        }
        switch (gameLevel) {
            case 0:
                str += "  Score:" + getEasyModeScore();
                for (int i = String.valueOf(getEasyModeScore()).length(); i < Global.Player.LIMIT_SCORE_DIGIT; i++) {
                    str += " ";
                }
                break;
            case 1:
                str += "  Score:" + getMiddleModeScore();
                for (int i = String.valueOf(getMiddleModeScore()).length(); i < Global.Player.LIMIT_SCORE_DIGIT; i++) {
                    str += " ";
                }
                break;
            case 2:
                str += "  Score:" + getHardModeScore();
                for (int i = String.valueOf(getHardModeScore()).length(); i < Global.Player.LIMIT_SCORE_DIGIT; i++) {
                    str += " ";
                }
                break;
        }
//        str += " 分數: " + getEasyModeScore();
//        for (int i = String.valueOf(getEasyModeScore()).length(); i < Global.Player.LIMIT_SCORE_DIGIT; i++) {
//            str += " ";
//        }
        return str;
    }
}

