package com.wj.bitmaploader.example;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.wj.bitmaploader.R;
import com.wj.bitmaploader.loader.BitmapLoader;
import com.wj.bitmaploader.loader.DisplayBitmapOptions;
import com.wj.bitmaploader.shape.ChatShape;
import com.wj.bitmaploader.shape.CircleShape;
import com.wj.bitmaploader.shape.RoundRectShape;

import java.util.List;

public class DemoActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getIntent().getStringExtra(MainActivity.TITLE));
        initViews();
    }

    private void initViews() {
        ImageView roundRectView = (ImageView) findViewById(R.id.roundRect);
        ImageView circleView = (ImageView) findViewById(R.id.circle);
        ImageView circleWithBorderView = (ImageView) findViewById(R.id.circleWithBorder);
        ImageView chatLeftView = (ImageView) findViewById(R.id.chatLeft);
        ImageView chatRightView = (ImageView) findViewById(R.id.chatRight);
        List<String> uris = getUris();
        if(!uris.isEmpty()) {
            BitmapLoader loader = BitmapLoader.getInstance();
            int width = getResources().getDimensionPixelSize(R.dimen.size_60dp);
            int height = getResources().getDimensionPixelSize(R.dimen.size_80dp);
            String imagePath = uris.get(0);
            loader.displayBitmap(roundRectView,new DisplayBitmapOptions.Builder().path(imagePath).
                    width(width)
                    .height(height)
                    .shape(new RoundRectShape(20))
                   .build(),null);

            loader.displayBitmap(circleView,new DisplayBitmapOptions.Builder().path(imagePath).
                    width(width)
                    .height(height)
                    .shape(new CircleShape())
                    .build(),null);

            loader.displayBitmap(circleWithBorderView,new DisplayBitmapOptions.Builder().path(imagePath).
                    width(width)
                    .height(height)
                    .shape(new CircleShape(15,Color.parseColor("#FFAAFF")))
                    .build(),null);

            loader.displayBitmap(chatLeftView,new DisplayBitmapOptions.Builder().path(imagePath).
                    width(width)
                    .height(height)
                    .shape(new ChatShape(ChatShape.LEFT,20))
                    .build(),null);

            loader.displayBitmap(chatRightView,new DisplayBitmapOptions.Builder().path(imagePath).
                    width(width)
                    .height(height)
                    .shape(new ChatShape(ChatShape.RIGHT,20))
                    .build(),null);
        }
    }

    @Override
    public View getContentView() {
        return getLayoutInflater().inflate(R.layout.activity_demo,null);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.demo_mune, menu);
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
