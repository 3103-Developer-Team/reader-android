package com.example.hasee.my_textreader;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
//import android.view.View.OnTouchListenerer;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

//import com.google.android.gms.appindexing.Action;
//import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.IOException;

/**
 * Created by hasee on 2016/4/8.
 */
public class TurnPage extends Activity {
    private PageWidget mPageWidget;
    Bitmap mCurPageBitmap, mNextPageBitmap;
    Canvas mCurPageCanvas, mNextPageCanvas;
    BookPageFactory pagefactory;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mPageWidget = new PageWidget(this);
        setContentView(mPageWidget);

        mCurPageBitmap = Bitmap.createBitmap(480, 800, Bitmap.Config.ARGB_8888);
        mNextPageBitmap = Bitmap.createBitmap(480, 800, Bitmap.Config.ARGB_8888);

        mCurPageCanvas = new Canvas(mCurPageBitmap);
        mNextPageCanvas = new Canvas(mNextPageBitmap);

        pagefactory = new BookPageFactory(480, 800);//设置分辨率为480*800
        pagefactory.setBgBitmap(BitmapFactory.decodeResource(
                this.getResources(), R.drawable.bg));//设置背景图片

        try {
            pagefactory.openbook("/sdcard/test.txt");//打开文件
            pagefactory.onDraw(mCurPageCanvas);//将文字绘于手机屏幕
        } catch (IOException e1) {
            e1.printStackTrace();
            Toast.makeText(this, "电子书不存在，请将文件放在SD卡根目录下",
                    Toast.LENGTH_SHORT).show();
        }

        mPageWidget.setBitmaps(mCurPageBitmap, mNextPageBitmap);
        mPageWidget.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent e) {
            boolean ret = false;
                if (v == mPageWidget){
                    if (e.getAction() == MotionEvent.ACTION_DOWN){
                        //停止动画，与forceFinished(boolean)相反， Scroller 滚动到最终x与y位置是终止动画
                        mPageWidget.abortAnimation();
                        //计算拖曳点对应的拖曳角
                        mPageWidget.calcCornerXY(e.getX(), e.getY());
                        //将文字绘于当前页
                        mPageWidget.Fix_onDraw(mCurPageCanvas);
                        if (mPageWidget.DragToRight()){
                            //是否从左边翻向右边
                            try{
                                //true, 显示上一页
                                pagefactory.prePage();
                            }catch (IOException e1){
                                e1.printStackTrace();
                            }
                            if (pagefactory.isfirstPage()) return false;
                            pagefactory.onDraw(mNextPageCanvas);
                        }else {
                            try{
                                //true, 显示上一页
                                pagefactory.nextPage();
                            }catch (IOException e1){
                                e1.printStackTrace();
                            }
                            if (pagefactory.islastPage()) return false;
                            pagefactory.onDraw(mNextPageCanvas);//????
                        }
                        mPageWidget.setBitmaps(mCurPageBitmap, mNextPageBitmap);
                    }
                    ret = mPageWidget.doTouchEvent(e);
                    return ret;
                }
                return false;
            }
        });

    }


}
