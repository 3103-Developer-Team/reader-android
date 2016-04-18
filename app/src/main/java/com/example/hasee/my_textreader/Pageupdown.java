package com.example.hasee.my_textreader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by hasee on 2016/4/4.
 */
public class Pageupdown extends AppCompatActivity implements View.OnClickListener{
    private  int index = 0;
    private  int page = 0;
    private  int length = 0;
    private TextView tv ;
    private  int wordnum = 300;
    protected  void  onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pageupdown);
        tv = (TextView)findViewById(R.id.tv);
        Button pgup = (Button)findViewById(R.id.pgup);
        Button pgdn = (Button)findViewById(R.id.pgdn);

        pgup.setOnClickListener(this);
        pgdn.setOnClickListener(this);
        tv.setTextSize(20);
        tv.setText(readText(index));
    }

    private String readFileSdcardFile(){//throw IOException{
        InputStreamReader inputStreamReader = null;
        //  String res = "";

        try{
            final  InputStream in = getResources().openRawResource(R.raw.test);
            inputStreamReader = new InputStreamReader(in, "gbk");

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuffer sb = new StringBuffer("");
        String line ;

        try {
            while((line = reader.readLine()) != null ){
                sb.append(line);
                sb.append('\n');
            }
            reader.close();
            inputStreamReader.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return sb.toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pgup:
                index--;
                if(index<0){
                    index = 0;
                    return;
                }
                tv.setText(readText(index));
                break;
            case R.id.pgdn:
                index++;
                if(index>page){
                    index = page;
                    return;
                }
                tv.setText(readText(index));
                break;
        }
    }



    private String readText(int index) {
        InputStreamReader inputStreamReader = null;
        try{
            final  InputStream in = getResources().openRawResource(R.raw.test);
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
