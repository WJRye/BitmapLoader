package com.wj.bitmaploader.example;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.wj.bitmaploader.R;
import com.wj.bitmaploader.listener.DisplayListener;
import com.wj.bitmaploader.loader.BitmapLoader;
import com.wj.bitmaploader.loader.DisplayBitmapOptions;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


public class MainActivity extends ActionBarActivity {
    private static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView iv1 = (ImageView) findViewById(R.id.iv1);
        ImageView iv2 = (ImageView) findViewById(R.id.iv2);
        String path = "/mnt/sdcard/DCIM/Album/1460534123086.jpg";

//        BitmapLoader.getInstance().displayChatRightBitmap(iv, path, 480, 540, 20);
        try {
            InputStream inputStream = new BufferedInputStream(new FileInputStream(new File(path)));
            DisplayBitmapOptions options1 = new DisplayBitmapOptions.Builder(DisplayBitmapOptions.TYPE_INPUT_STREAM).width(480).height(640).inputStream(inputStream).create();
            BitmapLoader.getInstance().displayBitmap(iv1, options1, new DisplayListener() {

                @Override
                public void onError(String msg) {
                    Log.d(TAG, "onError-->" + msg);
                }

                @Override
                public void onNull(ImageView iv) {
                    Log.d(TAG, "onNull");
                }
            });
            DisplayBitmapOptions options = new DisplayBitmapOptions.Builder(DisplayBitmapOptions.TYPE_PATH).width(480).height(640).path(path).create();
            BitmapLoader.getInstance().displayBitmap(iv2, options, new DisplayListener() {


                @Override
                public void onError(String msg) {
                    Log.d(TAG, "onError-->" + msg);
                }

                @Override
                public void onNull(ImageView iv) {
                    Log.d(TAG, "onNull");
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


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
}
