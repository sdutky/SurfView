package com.dutky.surfview;

/**
 * Created by dutky on 7/25/15.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

public class MySurfaceView extends SurfaceView {

    private SurfaceHolder surfaceHolder;
    private Bitmap bmpIcon;
    private MyThread myThread;
    int xPos = 0;
    int yPos = 0;
    int deltaX = 5;
    int deltaY = 5;
    double angle=3600 ;
    double deltaAngle= 270;
    int iconWidth;
    int iconHeight;
    TextView tv ;
    double factor=1.0001 ;
    double displayFactor= 2 ;



    public MySurfaceView(Context context) {
        super(context);
        init();
    }

    public MySurfaceView(Context context,
                         AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MySurfaceView(Context context,
                         AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){

        myThread = new MyThread(this);

        surfaceHolder = getHolder();
        bmpIcon = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_launcher);

        iconWidth = bmpIcon.getWidth();
        iconHeight = bmpIcon.getHeight();

        surfaceHolder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                myThread.setRunning(true);
                myThread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder,
                                       int format, int width, int height) {
                // TODO Auto-generated method stub

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                myThread.setRunning(false);
                while (retry) {
                    try {
                        myThread.join();
                        retry = false;
                    } catch (InterruptedException e) {
                    }
                }
            }
        });
    }

    protected void drawRay( Canvas canvas) {
        //tv =(TextView) findViewById(R.id.textView) ;
        double rAngle=angle*Math.PI/180 ;
        float[] line={getWidth()/2,getHeight()/2,0,0} ;
        float diameter = getWidth()<getHeight() ? getWidth()/2 : getHeight()/2 ;
        line[2]= (float) (diameter*Math.cos(rAngle)+getWidth()/2);
        line[3]= (float) (diameter*Math.sin(rAngle)+getHeight()/2);
        angle+=deltaAngle ;
        deltaAngle*=factor ;
        Log.d("deltaAnge",""+deltaAngle) ;

        Paint p=new Paint() ;
        p.setColor(Color.WHITE);
        p.setStrokeWidth((float) 50);

        canvas.drawColor(Color.BLACK);
        canvas.drawLines(line,p);


    }

    protected void drawSomething(Canvas canvas) {
        //canvas.drawColor(Color.BLACK);
        // canvas.drawBitmap(bmpIcon,
        //        getWidth()/2, getHeight()/2, null);

        xPos += deltaX;
        if(deltaX > 0){
            if(xPos >= getWidth() - iconWidth){
                deltaX *= -1;
            }
        }else{
            if(xPos <= 0){
                deltaX *= -1;
            }
        }

        yPos += deltaY;
        if(deltaY > 0){
            if(yPos >= getHeight() - iconHeight){
                deltaY *= -1;
            }
        }else{
            if(yPos <= 0){
                deltaY *= -1;
            }
        }

        // canvas.drawColor(Color.BLACK);
        Paint p=new Paint() ;
        p.setColor(Color.WHITE);
        p.setStrokeWidth((float) 5);
        canvas.drawPoint(xPos,yPos,p);
        //canvas.drawBitmap(bmpIcon,
        // xPos, yPos, null);

    }

}