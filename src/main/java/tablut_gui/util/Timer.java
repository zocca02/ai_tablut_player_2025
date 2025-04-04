package tablut_gui.util;

public class Timer extends Thread{

    private int timeoutSec;
    private boolean stopped;
    private Runnable callback;

    public Timer(int timeoutSec, Runnable callback){
        this.timeoutSec = timeoutSec;
        this.callback = callback;
        this.stopped = false;
    }

    @Override
    public void run(){
        try {
            Thread.sleep(timeoutSec*1000);
        } catch (InterruptedException e) {}



        synchronized (this){
            if(!stopped){
                callback.run();
            }
        }

    }

    public void stopTimer(){
        synchronized (this){
            stopped = true;
        }

    }

}
