package com.wj.bitmaploader.loading;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import com.wj.bitmaploader.listener.DisplayListener;
import com.wj.bitmaploader.loader.DisplayBitmapOptions;
import com.wj.bitmaploader.shape.ChatShape;
import com.wj.bitmaploader.shape.CircleShape;
import com.wj.bitmaploader.shape.DisplayShape;
import com.wj.bitmaploader.utils.BitmapUtil;

import java.io.FileNotFoundException;


/**
 * 该类主要用作于加载图片
 */
public class AsyncTaskHelper extends AsyncTask<LruCache<Integer, Bitmap>, Void, Bitmap> {
    private int KEY = (int) System.currentTimeMillis();
    private boolean error = false;
    private int cacheKey = -1;
    private ImageView imageView;
    private DisplayBitmapOptions options;
    private DisplayListener listener;


    public AsyncTaskHelper(ImageView imageView, DisplayBitmapOptions options, DisplayListener listener, int cacheKey) {
        this.imageView = imageView;
        this.options = options;
        this.imageView.setTag(KEY, options);
        this.listener = listener;
        this.cacheKey = cacheKey;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        if (error) {
            if (listener != null) listener.onError(imageView);
        } else {
            if (result != null) {
                if (options.equals(imageView.getTag(KEY))) imageView.setImageBitmap(result);
            } else {
                if (listener != null) listener.onFail(imageView);
            }
        }
    }

    @SuppressLint("NewApi")
    @Override
    protected Bitmap doInBackground(@SuppressWarnings("unchecked") LruCache<Integer, Bitmap>... params) {
        Bitmap bitmap = null;
        try {
            switch (options.getType()) {
                case DisplayBitmapOptions.TYPE_DATA:
                    bitmap = loadBitmap(BitmapUtil.getDstBitmap(options.getData(), options.getWidth(), options.getHeight()), options.getShape());
                    break;
                case DisplayBitmapOptions.TYPE_PATH:
                    bitmap = loadBitmap(BitmapUtil.getDstBitmap(options.getPath(), options.getWidth(), options.getHeight()), options.getShape());
                    break;
                case DisplayBitmapOptions.TYPE_INPUT_STREAM:
                    bitmap = loadBitmap(BitmapUtil.getDstBitmap(options.getInputStream(), options.getWidth(), options.getHeight()), options.getShape());
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
        if (bitmap != null && cacheKey != -1) {
            params[0].put(Integer.valueOf(cacheKey), bitmap);
        }
        return bitmap;
    }

    private static Bitmap loadBitmap(Bitmap srcBitmap, DisplayShape shape) throws FileNotFoundException, OutOfMemoryError {
        switch (shape.getShape()) {
            case DisplayShape.RECT:
                break;
            case DisplayShape.ROUND_RECT:
                return BitmapUtil.getRoundedBitmap(srcBitmap, shape.getRadius());
            case DisplayShape.CIRCLE:
                CircleShape circleShape = (CircleShape) shape;
                if (circleShape.hasBorder()) {
                    return BitmapUtil.getCircleBitmapWithBorder(srcBitmap, circleShape.getBorderWidth(), circleShape.getBorderColor());
                } else {
                    return BitmapUtil.getCircleBitmap(srcBitmap);
                }
            case DisplayShape.CHAT:
                ChatShape chatShape = (ChatShape) shape;
                if (chatShape.getOrientation() == ChatShape.LEFT) {
                    return BitmapUtil.getChatLeftBitmap(srcBitmap, chatShape.getRadius());
                } else if (chatShape.getOrientation() == ChatShape.RIGHT) {
                    return BitmapUtil.getChatRightBitmap(srcBitmap, chatShape.getRadius());
                }
            default:
                break;
        }
        return srcBitmap;
    }
}
