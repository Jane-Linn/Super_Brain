 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Wan Zhen Lin
 */
public class ElementFileDealer {

    private int gameLevel;
    private ArrayList<ArrayList<String>> data;
    private String fileName;

    public ElementFileDealer(int gameLevel) {
        this.gameLevel = gameLevel;
        data = new ArrayList<>();
        
        this.fileName = "elementsData" + gameLevel;
    }

    public ArrayList<ArrayList<String>> readFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/resources/file/" + fileName + ".csv"));
            String[] str;
            while (br.ready()) {
                str = br.readLine().split(",");
                ArrayList<String> readData = new ArrayList<>();
                for(int i = 0; i<str.length; i++){
                    readData.add(str[i]);
                }
                    data.add(readData);
            }

            br.close();
            return data;
        } catch (FileNotFoundException ex) {
            System.out.println("ElementFileDealer沒找到檔案QQ");
        } catch (IOException ex) {
        }
        return null;
    }

}
