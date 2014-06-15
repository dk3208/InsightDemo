package com.example.insightdemo;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.SeekBar;

class MyTimerTask extends TimerTask  {
    Timer timer;

    public MyTimerTask(Timer t) {
        this.timer = t;
    }

    @Override
    public void run() {
        // You can do anything you want with param 
    }
}

public class VerticalSeekBar extends SeekBar {

    public VerticalSeekBar(Context context) {


        super(context);

    }

    public VerticalSeekBar(Context context, AttributeSet attrs, int defStyle) {


        super(context, attrs, defStyle);


    }

    public VerticalSeekBar(Context context, AttributeSet attrs) {


        super(context, attrs);


    }
    public void AutoResetProgress()
    {
    	Timer timer = new Timer();
    	TimerTask task = new MyTimerTask(timer) {
    		@Override
        	public void run() {
                  	
                  	int p = getProgress();
                  	if(p < 100) 
                  	{
                  		p+=2;
                  		Log.d("a", Integer.toString(p));
                  	}
                  	else
                  	{
                  		p = 100;
                  		this.timer.cancel();
                  		Log.d("a", Integer.toString(p));
                  		Log.d("a", "Stop");
                  	}
                  	setProgress(p);
                };
            };
        
        timer.schedule(task, 0, 10);
    }
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {


        super.onSizeChanged(h, w, oldh, oldw);


    }

    @Override


    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {


        super.onMeasure(heightMeasureSpec, widthMeasureSpec);


        setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());


    }

    protected void onDraw(Canvas c) {


        c.rotate(-90);


        c.translate(-getHeight(), 0);

        super.onDraw(c);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        if (!isEnabled()) {


            return false;


        }

        switch (event.getAction()) {


            case MotionEvent.ACTION_DOWN:


            case MotionEvent.ACTION_MOVE:
            	
            	int progress = getMax() - (int) (getMax() * event.getY() / getHeight());
                setProgress(progress);


                onSizeChanged(getWidth(), getHeight(), 0, 0);


                break;


            case MotionEvent.ACTION_UP:
            
                //AutoResetProgress();
                //if(getProgress() < 15)
                {
                	setProgress(100);
                }
            	onSizeChanged(getWidth(), getHeight(), 0, 0);


                break;

            case MotionEvent.ACTION_CANCEL:


                break;


        }
        return true;

    }
}

