/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import gameobject.player.Player;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Wan Zhen Lin
 */
public class PlayerFileDealer {

    private ArrayList<Player> dataList;

    public PlayerFileDealer() {
        dataList = new ArrayList<>();
    }

    public ArrayList<Player> readFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/resources/file/" + "playerFile" + ".csv"));
            ArrayList<Player> newData = new ArrayList<>();
            String[] str;
            while (br.ready()) {
                str = br.readLine().split(",");
                newData.add(new Player(str[0], Integer.valueOf(str[1]), Integer.valueOf(str[2]), Integer.valueOf(str[3])));
            }
            br.close();
            return newData;
        } catch (ArrayIndexOutOfBoundsException ex) {
            return null;
        } catch (FileNotFoundException ex) {
            System.out.println("找不到file");
        } catch (IOException ex) {
        }
        return null;
    }

    public void writeFile(ArrayList<Player> dataList) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("src/resources/file/" + "playerFile" + ".csv"));
            String str;
            Player player;
            for (int i = 0; i < dataList.size(); i++) {
                player = dataList.get(i);
                str = player.getName() + "," + player.getEasyModeScore() + "," + player.getMiddleModeScore() + "," + player.getHardModeScore();
                bw.write(str);
                bw.newLine();
                bw.flush();
            }
            bw.close();
            System.out.println("成功寫入");
        } catch (FileNotFoundException ex) {
            System.out.println("找不到file");
        } catch (IOException ex) {
            System.out.println("沒寫入檔案QQ");
        }
    }

    // 遊戲完存檔
    public void saveGameResult(Player player) {
        dataList = readFile();
        for (int i = 0; i < dataList.size(); i++) {
            if (dataList.get(i).getName().equals(player.getName())) {
                dataList.set(i, player);
                writeFile(dataList);
                return;
            }
        }
    }
}
