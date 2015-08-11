package com.dutky.surfview;

/**
 * Created by dutky on 7/25/15.
 */
import android.graphics.Canvas;

public class MyThread extends Thread {

    MySurfaceView myView;
    private boolean running = false;

    public MyThread(MySurfaceView view) {
        myView = view;
    }

    public void setRunning(boolean run) {
        running = run;
    }

    @Override
    public void run() {
        while(running){

            Canvas canvas = myView.getHolder().lockCanvas();

            if(canvas != null){
                synchronized (myView.getHolder()) {
                    myView.drawRay(canvas);
                }
                myView.getHolder().unlockCanvasAndPost(canvas);
            }

            try {
              sleep(0);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

}