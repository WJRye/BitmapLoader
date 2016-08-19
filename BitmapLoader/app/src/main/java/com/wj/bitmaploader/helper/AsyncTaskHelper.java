package com.wj.bitmaploader.helper;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import com.wj.bitmaploader.listener.DisplayListener;
import com.wj.bitmaploader.loader.DisplayBitmapOptions;
import com.wj.bitmaploader.utils.BitmapUtil;
import com.wj.bitmaploader.utils.DisplayShapeUtil;


/**
 * 该类主要用作于加载图片
 */
public class AsyncTaskHelper extends AsyncTask<LruCache<String, Bitmap>, Void, Bitmap> {
    private int KEY = (int) System.currentTimeMillis();
    private boolean error = false;
    private ImageView imageView;
    private DisplayBitmapOptions options;
    private DisplayListener listener;


    public AsyncTaskHelper(ImageView imageView, DisplayBitmapOptions options, DisplayListener listener) {
        this.imageView = imageView;
        this.options = options;
        this.imageView.setTag(KEY, options);
        this.listener = listener;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        if (error) {
            if (listener != null) listener.onError(imageView);
        } else {
            if (result != null) {
                if (options.equals(imageView.getTag(KEY))) {
                    imageView.setImageBitmap(result);
                }
            } else {
                if (listener != null) listener.onFail(imageView);
            }
        }
    }

    @SuppressLint("NewApi")
    @Override
    protected Bitmap doInBackground(@SuppressWarnings("unchecked") LruCache<String, Bitmap>... params) {
        Bitmap bitmap = null;
        try {
            switch (options.getType()) {
                case DisplayBitmapOptions.TYPE_DATA:
                    bitmap = DisplayShapeUtil.getBitmapByShape(BitmapUtil.getDstBitmap(options.getData(), options.getWidth(), options.getHeight()), options.getShape());
                    break;
                case DisplayBitmapOptions.TYPE_PATH:
                    bitmap = DisplayShapeUtil.getBitmapByShape(BitmapUtil.getDstBitmap(options.getPath(), options.getWidth(), options.getHeight()), options.getShape());
                    break;
                case DisplayBitmapOptions.TYPE_INPUT_STREAM:
                    bitmap = DisplayShapeUtil.getBitmapByShape(BitmapUtil.getDstBitmap(options.getInputStream(), options.getWidth(), options.getHeight()), options.getShape());
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            error = true;
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            error = true;
            e.printStackTrace();
        }
        if (bitmap != null) params[0].put(options.getUniqueID(), bitmap);
        return bitmap;
    }


}
