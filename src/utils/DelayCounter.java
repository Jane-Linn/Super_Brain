/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

public class DelayCounter {

    private boolean isPause;
    private int delay;// 要延遲的時間
    private int count;// 計算延遲

    public DelayCounter(int delay) {
        this.delay = delay * Global.Update.ANIMA_DELAY; // ANIMA_DELAY = 2
        count = 0;
        // isPause = true;
    }
    
    public void pause(){
        isPause = true;
    }
    
    public void stop(){
        isPause = true;
        count = 0;
    }
    
    public void start(){
        isPause = false;
    }
    
    public void reset(){
        count = 0;
    }
    
    public boolean isPause(){
        return isPause;
    }
    
    public boolean isStop(){
        return isPause && (count == 0);
    }

    public boolean update() {
        if(isPause){
            return false;
        }
        if (count++ < delay) {
            return false;
        }
        count = 0;
        return true;
    }

    public void setdelay(int i) {//傳入改變量
        delay += i;
    }
    public int getDelay(){
        return delay;
    }
}
