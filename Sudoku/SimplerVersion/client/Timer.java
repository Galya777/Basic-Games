package TheClient;

import javafx.application.Platform;
import javafx.scene.control.Label;

public class Timer {
    private Label timer;
    private boolean suspended;
    private long milliseconds;
    private Thread thread;

    public Timer(Label timer){
        if(timer != null)
            this.timer = timer;
        else
            System.out.println("Error occurred with lblTimer");

        milliseconds = 0;
        createThread();
    }

    public long getMilliseconds() {
        return milliseconds;
    }

    private void createThread() {
        thread= new Thread(() ->{
            while(true){
                try{
                    Thread.sleep(1000);
                    synchronized (this){
                        while(suspended) wait();
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                    System.out.println("Error with time thread.");
                }
                Platform.runLater(()->timer.setText(convert(milliseconds)));
                milliseconds+=1000;
            }
        });
    }

    private String convert(long milliseconds) {
        long seconds = (milliseconds / 1000) % 60;
        long minutes = (milliseconds / (1000 * 60)) % 60;
        long hours = milliseconds / (1000 * 60 * 60);

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public void start(){
        suspended = false;
        milliseconds = 0;

        synchronized (this) {
            if(thread.isAlive())
                notifyAll();
            else
                thread.start();
        }
    }
    public void stop(){
        suspended=true;
    }
}
