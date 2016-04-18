package com.example.hasee.my_textreader;

import android.app.LauncherActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

/**
 * Created by hasee on 2016/4/6.
 */
public class ActivitiesList extends LauncherActivity{
    //定义两个Activity 的名称
    String[] names = {"滚动阅读", "翻页阅读", "滑动翻页", "特效翻页"};
    //定义两个Activity 对应的实现类
    Class<?>[] classes = {MainActivity.class,Pageupdown.class, SlideView.class, TurnPage.class};
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, names);
        //设置该窗口显示的列表所需的Adapter
        setListAdapter(adapter);
    }
    @Override public Intent intentForPosition(int position){
    return new Intent(ActivitiesList.this, classes[position]);
}

}
