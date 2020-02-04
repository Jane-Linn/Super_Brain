/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobject.ball;

import gameobject.GameObject;
import java.util.ArrayList;
import utils.ElementFileDealer;
import utils.Global;

/**
 *
 * @author Wan Zhen Lin
 */
public class BallBag {

    //放一關中所有的障礙物，並且用csv檔設計好每一個element的位置(x,y)
    //用狀態模式判斷你要new的是甚麼物件
    //用讀檔的方式創建每一個element(不同關讀不同的檔)
    private ArrayList<GameObject> bags;

    public BallBag(int gameLevel) {
        bags = new ArrayList<>();
        fillBag(gameLevel);
    }

    //換新關卡時 由GameScene執行此method
    //知道是哪一關後，把包包填滿
    //讀檔把每一個障礙物new好 包裝成一個elementBag
    public void fillBag(int gameLevel) {
        bags = new ArrayList<>();
        //用csv檔加!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //要避免同一個位置(x,y)印不同element && element之間y的距離要抓一下!!!!!!!!!!!!!!!!!!!
//(印出的x座標(左右輪流)，印出的y座標(從大到小)，印出的寬，印出的高，印出大圖的第幾排(gameLevel)，那一排的第幾個(選顏色ballNumber))
        ElementFileDealer fileDealer = new ElementFileDealer(gameLevel);
        ArrayList<ArrayList<String>> data = fileDealer.readFile();//data會讀一段資料(依關卡讀不同檔)
        for (int j = 0; j < data.size(); j++) {//一行一行讀  //表格: 0(物件名稱) 1(印的x座標) 2(印的y座標) 3(印出的寬) 4(印出的長) 5(關卡數) 6(球顏色)

            String name = data.get(j).get(0);
            switch (name) {
                case "ColorBall":
                    bags.add(new ColorBall(Integer.valueOf(data.get(j).get(1)) - Global.Element.ELEMENT_PRINT_X_SIZE / 2, Integer.valueOf(data.get(j).get(2)), Integer.valueOf(data.get(j).get(3)), Integer.valueOf(data.get(j).get(4)),
                            Integer.valueOf(data.get(j).get(5)), Integer.valueOf(data.get(j).get(6))));
                    break;
                case "ChangeBall":
                    bags.add(new ChangeBall(Integer.valueOf(data.get(j).get(1)) - Global.Element.ELEMENT_PRINT_X_SIZE / 2, Integer.valueOf(data.get(j).get(2)), Integer.valueOf(data.get(j).get(3)), Integer.valueOf(data.get(j).get(4)),
                            Integer.valueOf(data.get(j).get(5)), Integer.valueOf(data.get(j).get(6))));
                    break;
                case "Thunder":
                    bags.add(new Thunder(Integer.valueOf(data.get(j).get(1)) - Global.Element.ELEMENT_PRINT_X_SIZE / 2, Integer.valueOf(data.get(j).get(2)), Integer.valueOf(data.get(j).get(3)), Integer.valueOf(data.get(j).get(4)),
                            Integer.valueOf(data.get(j).get(5)), Integer.valueOf(data.get(j).get(6))));
                    break;
                case "AddBlood":
                    bags.add(new AddBlood(Integer.valueOf(data.get(j).get(1)) - Global.Element.ELEMENT_PRINT_X_SIZE / 2, Integer.valueOf(data.get(j).get(2)), Integer.valueOf(data.get(j).get(3)), Integer.valueOf(data.get(j).get(4)),
                            Integer.valueOf(data.get(j).get(5)), Integer.valueOf(data.get(j).get(6))));
                    break;
                case "Invincible":
                    bags.add(new Invincible(Integer.valueOf(data.get(j).get(1)) - Global.Element.ELEMENT_PRINT_X_SIZE / 2, Integer.valueOf(data.get(j).get(2)), Integer.valueOf(data.get(j).get(3)), Integer.valueOf(data.get(j).get(4)),
                            Integer.valueOf(data.get(j).get(5)), Integer.valueOf(data.get(j).get(6))));
                    break;
                case "SlowDown":
                    bags.add(new SlowDown(Integer.valueOf(data.get(j).get(1)) - Global.Element.ELEMENT_PRINT_X_SIZE / 2, Integer.valueOf(data.get(j).get(2)), Integer.valueOf(data.get(j).get(3)), Integer.valueOf(data.get(j).get(4)),
                            Integer.valueOf(data.get(j).get(5)), Integer.valueOf(data.get(j).get(6))));
                    break;
            }
        }
    }

    //取得放好element的包包
    //把elementBag丟出來提供使用
    public ArrayList<GameObject> getBag() {
        return bags;
    }

    public GameObject getElement(int i) {
        return bags.get(i);
    }
}
