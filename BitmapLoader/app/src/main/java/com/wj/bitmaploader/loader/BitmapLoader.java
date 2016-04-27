package com.wj.bitmaploader.loader;/**
 * Created by wangjiang on 2016/4/25.
 */

import android.graphics.Bitmap;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wj.bitmaploader.listener.DisplayListener;
import com.wj.bitmaploader.utils.BitmapUtil;

import java.io.IOException;
import java.io.InputStream;

/**
 * User: WangJiang(https://github.com/WJRye)
 * Date: 2016-04-25
 * Time: 16:35
 */
public class BitmapLoader {
    private static final String TAG = "TAG";

    private BitmapLoader() {
    }

    private static class BitmapLoaderHolder {
        private static BitmapLoader INSTANCE = new BitmapLoader();
    }

    public static BitmapLoader getInstance() {
        return BitmapLoaderHolder.INSTANCE;
    }

    private void display(ImageView imageView, Bitmap bitmap, int width, int height) {
        ViewGroup.LayoutParams params = imageView.getLayoutParams();
        params.width = width;
        params.height = height;
        imageView.setLayoutParams(params);
        imageView.setImageBitmap(bitmap);
    }

    public void displayCircleBitmap(ImageView imageView, String path, int width, int height) {

    }

    public void displayRoundedBitmap(ImageView imageView, String path, int width, int height, int radius) {

    }


    public void displayChatLeftBitmap(ImageView imageView, String path, int width, int height, int radius) {

    }

    public void displayChatRightBitmap(ImageView imageView, String path, int width, int height, int radius) {

    }

    public void displayBitmap(ImageView imageView, DisplayBitmapOptions options, DisplayListener listener) {
        long start = System.currentTimeMillis();
        if (imageView == null || options == null) {
            throw new NullPointerException("ImageView or DisplayBitmapOptions is null!");
        }
        int width = options.getWidth() <= 0 ? imageView.getWidth() : options.getWidth();
        int height = options.getHeight() <= 0 ? imageView.getHeight() : options.getHeight();
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Width or Height is illegal!");
        }
        try {
            Bitmap bitmap = null;
            switch (options.getType()) {
                case DisplayBitmapOptions.TYPE_DATA: {
                    byte[] data = options.getData();
                    if (data == null || data.length == 0) {
                        if (listener != null)
                            listener.onError("Data is null, or it's length is 0!");
                        return;
                    }
                    bitmap = BitmapUtil.getDstBitmap(data, width, height);
                    break;
                }
                case DisplayBitmapOptions.TYPE_PATH: {
                    String path = options.getPath();
                    if (path == null || path.length() == 0) {
                        if (listener != null)
                            listener.onError("Path is null, or it's length is 0!");
                        return;
                    }
                    bitmap = BitmapUtil.getDstBitmap(path, width, height);
                    break;
                }
                case DisplayBitmapOptions.TYPE_INPUT_STREAM: {
                    InputStream inputStream = options.getInputStream();
                    if (inputStream == null) {
                        if (listener != null) listener.onError("InputStream is null!");
                        return;
                    }
                    bitmap = BitmapUtil.getDstBitmap(inputStream, width, height);
                    break;
                }
            }
            if (bitmap == null) {
                if (listener != null) listener.onNull(imageView);
                return;
            }
            Log.d(TAG, "time=" + (System.currentTimeMillis() - start));
            display(imageView, bitmap, options.getWidth(), options.getHeight());
        } catch (IOException e) {
            e.printStackTrace();
            if (listener != null) listener.onError(e.getMessage());
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            if (listener != null) listener.onError(e.getMessage());
        }
    }
}
