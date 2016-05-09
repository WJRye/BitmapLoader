package com.wj.bitmaploader.example;/**
 * Created by wangjiang on 2016/4/7.
 */

import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;

/**
 * User: WangJiang(wangjiang7747@gmail.com)
 * Date: 2016-04-07
 * Time: 18:52
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(getContentView());
    }

    public abstract View getContentView();


    public ArrayList<String> getUris() {
        ArrayList<String> uris = new ArrayList<>();
        //按照添加时间倒叙排序
        Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.MediaColumns.DATA, MediaStore.MediaColumns.DATE_ADDED}, null, null, "date_added desc");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                uris.add(cursor.getString(0));
            }
            cursor.close();
            cursor = null;
        }
        return uris;
    }
}
