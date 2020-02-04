/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobject.player;

import java.util.ArrayList;

/**
 *
 * @author Wan Zhen Lin
 */
public class PlayerArr {

    //要可以排序(照名字 || 成績) 
    public interface Compare {

        public boolean smaller(Player player, Player target);
    }

    private ArrayList<Player> allPlayers;
    private Compare comparator;

    public PlayerArr() {
        allPlayers = new ArrayList<>();

    }

    public void addPlayer(Player player) {
        allPlayers.add(player);
    }

    public void clearPlayerArr() {
        this.allPlayers = null;
    }

    public ArrayList<Player> getAllPlayer() {
        return allPlayers;
    }

    public Player getPlayer(int index) {
        return allPlayers.get(index);
    }

    public void sortName() {
        comparator = new Compare() {
            @Override
            public boolean smaller(Player player, Player target) {
                if (player.getName().compareTo(target.getName()) > 0) {
                    return false;
                }
                return true;
            }
        };
        for (int i = 0; i < allPlayers.size() - 1; i++) {
            if (comparator.smaller(allPlayers.get(i), allPlayers.get(i + 1))) {
                allPlayers = swap(i, i + 1);
            }
        }
    }

    public void sortScore() {
        comparator = new Compare() {
            @Override
            public boolean smaller(Player player, Player target) {
                if (player.getEasyModeScore() - target.getEasyModeScore() > 0) {
                    return false;
                }
                return true;
            }
        };
        for (int i = 0; i < allPlayers.size() - 1; i++) {
            if (comparator.smaller(allPlayers.get(i), allPlayers.get(i + 1))) {
                allPlayers = swap(i, i + 1);
            }
        }
    }

    public ArrayList<Player> swap(int i1, int i2) {
        Player tmp = allPlayers.get(i1);
        allPlayers.set(i1, allPlayers.get(i2));
        allPlayers.set(i2, tmp);
        return allPlayers;
    }

    //每個玩家分別印出(不用for迴圈印全部)
    public String printPlayer(int index) {
        String str = "";
        str += allPlayers.get(index).toString();
        return str;
    }
}
