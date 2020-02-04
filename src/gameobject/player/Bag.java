/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobject.player;

import gameobject.actor.LeftBrain;
import java.util.ArrayList;
import utils.Global;

/**
 *
 * @author Wan Zhen Lin
 */
public class Bag {

    private int coin;
    private ArrayList<LeftBrain> cars;

    public Bag() {
        setCoin(0);
        cars = new ArrayList<>();
    }

    // 最大coin為: 99999 (LIMIT_COIN)
    public void updateCoin(int coin) {//遊戲結束要總結(路上吃到的金幣) 買東西扣錢
        if (this.coin < Global.Player.LIMIT_COIN) {
            if (Global.Player.LIMIT_COIN - this.coin > coin) {
                this.coin += coin;
            } else {
                this.coin = Global.Player.LIMIT_COIN;
            }
        }
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public boolean addCars(LeftBrain car) {
        for (int i = 0; i < cars.size(); i++) {
            if (cars.get(i).equals(car)) {
                return false;//已經有這台車了
            }
        }
        cars.add(car);
        return true;
    }

    public int getCoin() {//買東西要判斷夠不夠錢
        return coin;
    }

    public LeftBrain getCar(int index) {
        return cars.get(index);
    }

    //選車?
    public LeftBrain getCar(LeftBrain car) {
        for (int i = 0; i < cars.size(); i++) {
            if (cars.get(i).equals(car)) {
                return cars.get(i);
            }
        }
        return null;
    }

    public ArrayList<LeftBrain> getCarArr() {
        return cars;
    }
}
