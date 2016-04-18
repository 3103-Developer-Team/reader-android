package com.example.hasee.my_textreader;

import android.app.Activity;
import android.app.TabActivity;
import android.gesture.GestureOverlayView;
import android.os.Bundle;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by hasee on 2016/4/6.
 */
public class SlideView extends Activity {
    final int RIGHT = 0;
    final int LEFT = 1;
    private  int index = 0;
    private  int page = 0;
    private  int length = 0;
    private TextView tv ;
    private  int wordnum = 300;
//    ViewFlipper flipper;
    private GestureDetector gestureDetector;
    private GestureDetector gestureDetector2;
//    Animation[] animations = new Animation[4];
    final  int FLIP_DISTANCE = 0;
         public int SCREEN_Width ;
@Override
    public void onCreate(Bundle savedInstanceState){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slideview);
        gestureDetector = new GestureDetector(this, this.onGestureListener);
    //    gestureDetector2 = new GestureDetector(this, this.onGestureListener2);
        tv = (TextView) findViewById(R.id.tv);
        tv.setTextSize(20);
        tv.setText(readText(index));
        Display display = getWindowManager().getDefaultDisplay();
        SCREEN_Width = display.getWidth();
//3
//        flipper = (ViewFlipper)this.findViewById(R.id.);
//        flipper.addView(addIm);
    }
//1
public GestureDetector.OnGestureListener onGestureListener =
            new GestureDetector.SimpleOnGestureListener(){
                @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2 , float velocityX,
                                   float velocityY){
                    float x = e2.getX() - e1.getX();
                    float y = e2.getY() - e1.getX();


                //    float ty = e1.getY();

                    if (x >FLIP_DISTANCE ){
                        index--;
                        if(index<0){
                            index = 0;
                            return false;
                        }
                        tv.setText(readText(index));
//                        doResult(RIGHT);
                    }else if (x <FLIP_DISTANCE ) {
                        index++;
                        if(index>page){
                            index = page;
                            return false;
                        }
                        tv.setText(readText(index));
//                        doResult(LEFT);
                    }
                    return true;
                }

                public boolean onDown(MotionEvent arg0) {

//        Toast.makeText(this, "onDown", Toast.LENGTH_LONG).show();
                    return true;
                }

                public boolean onSingleTapUp(MotionEvent e){
                    float x = e.getX();
                    if (x<SCREEN_Width / 2) {
                        index--;
                        if (index < 0) {
                            index = 0;
                            return false;
                        }
                        tv.setText(readText(index));
//                        doResult(RIGHT);
                    } else if (x > SCREEN_Width / 2) {
                        index++;
                        if (index > page) {
                            index = page;
                            return false;
                        }
                        tv.setText(readText(index));
//                        doResult(LEFT);
                    }
//     Toast.makeText(this, "onSingleTapUp",
//             Toast.LENGTH_LONG).show();
                    return false;
                }
            };
    public boolean onTouchEvent(MotionEvent event){
        return gestureDetector.onTouchEvent(event);
    }
//1
//    public void doResult(int action){
//        switch (action){
//            case RIGHT:
//
//                break;
//            case LEFT:
//
//                break;
//        }
//    }
//2



//
//    public boolean onFling(MotionEvent e1, MotionEvent e2,
//                           float velocityX, float velocityY ){
////        Toast.makeText(this, "onFling",
////                Toast.LENGTH_LONG).show();
//        return false;
//    }

    public void onLongPress(MotionEvent e){
//        Toast.makeText(this, "onLongPress",
//                Toast.LENGTH_LONG).show();
    }

    public boolean onScroll(MotionEvent e1, MotionEvent e2,
                            float distanceX, float distanceY){
//        Toast.makeText(this, "onScroll",
//                Toast.LENGTH_LONG).show();
        return false;
    }

    public void onShowPress(MotionEvent e){
//        Toast.makeText(this, "onShowPress",
//                Toast.LENGTH_LONG).show();
    }



    private String readText(int index) {
        InputStreamReader inputStreamReader = null;
        try{
            final InputStream in = getResources().openRawResource(R.raw.test);
            inputStreamReader = new InputStreamReader(in, "gbk");

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        BufferedReader reader = new BufferedReader(inputStreamReader);
        char[] buffer = new char[wordnum];
        try {
            reader.skip(wordnum * index);
            reader.read(buffer);
            if(page == 0){
                while (reader.read()!=-1){
                    length++;
                }
                if(length%wordnum == 0){
                    page = length/wordnum;
                }
                else {
                    page = length/wordnum+1;
                }
            }
            reader.close();
            inputStreamReader.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return String.valueOf(buffer);
    }

}
