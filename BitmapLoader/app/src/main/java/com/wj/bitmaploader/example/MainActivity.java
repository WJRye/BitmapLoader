package com.wj.bitmaploader.example;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.wj.bitmaploader.shape.ChatShape;
import com.wj.bitmaploader.shape.CircleShape;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;


public class MainActivity extends ActionBarActivity {
    private static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.GREEN));
        ImageView iv1 = (ImageView) findViewById(R.id.iv1);
        ImageView iv2 = (ImageView) findViewById(R.id.iv2);
        String path = "/mnt/sdcard/DCIM/Album/1460534123086.jpg";

//        BitmapLoader.getInstance().displayChatRightBitmap(iv, path, 480, 540, 20);
        try {
            InputStream inputStream = new BufferedInputStream(new FileInputStream(new File(path)));
            DisplayBitmapOptions options1 = new DisplayBitmapOptions.Builder(DisplayBitmapOptions.TYPE_INPUT_STREAM).width(480).height(640).inputStream(inputStream).shape(new CircleShape(20, Color.BLUE)).build();

            BitmapLoader.getInstance().displayBitmap(iv1, options1, new DisplayListener() {

                @Override
                public void onError(ImageView iv) {
                    Log.d(TAG, "onError-->");
                }

                @Override
                public void onNull(ImageView iv) {
                    Log.d(TAG, "onNull");
                }
            });
            int width = Resources.getSystem().getDisplayMetrics().widthPixels;
            int height = Resources.getSystem().getDisplayMetrics().heightPixels;

            DisplayBitmapOptions options = new DisplayBitmapOptions.Builder(DisplayBitmapOptions.TYPE_PATH).width(240).height(480).path(path).shape(new ChatShape(ChatShape.RIGHT, 20)).build();
            BitmapLoader.getInstance().displayBitmap(iv2, path, new DisplayListener() {


                @Override
                public void onError(ImageView iv) {
                    Log.d(TAG, "onError-->");
                }

                @Override
                public void onNull(ImageView iv) {
                    Log.d(TAG, "onNull");
                }
            });
        } catch (Exception e) {
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
