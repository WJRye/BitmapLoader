package com.wj.bitmaploader.helper;/**
 * Created by wangjiang on 2016/5/7.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import com.wj.bitmaploader.listener.DisplayListener;
import com.wj.bitmaploader.loader.DisplayBitmapOptions;
import com.wj.bitmaploader.utils.BitmapUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;

/**
 * User: WangJiang(https://github.com/WJRye)
 * Date: 2016-05-07
 * Time: 19:27
 */
public abstract class ViewHelper {
    static final String TAG = "TAG";
    /**
     * 否停止了滑动
     */
    boolean mIsIdle = true;
    LruCache<String, Bitmap> mLruCache;
    Set<AsyncTaskHelper> mAsyncTaskHelpers;

    public ViewHelper() {
        mLruCache = new LruCache<String, Bitmap>((int) Runtime.getRuntime().maxMemory() / 8) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
        mAsyncTaskHelpers = new HashSet<>();
    }

    public abstract void displayBitmap(Object object);

    public void loadBitmap(ImageView imageView, DisplayBitmapOptions options, DisplayListener listener) {
        if (imageView == null || options == null) {
            throw new NullPointerException("ImageView or DisplayBitmapOptions is null!");
        }
        int width = options.getWidth();
        int height = options.getHeight();
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Width or Height is 0!");
        }

        Bitmap bitmap = mLruCache.get(options.getUniqueID());
        if (bitmap == null) {
            imageView.setImageBitmap(getImageOnLoading(imageView.getContext(), options));
            if (mIsIdle) {
                AsyncTaskHelper helper = new AsyncTaskHelper(imageView, options, listener);
                helper.executeOnExecutor(Executors.newCachedThreadPool(), mLruCache);
                mAsyncTaskHelpers.add(helper);
            }
        } else {
            imageView.setImageBitmap(bitmap);
        }
    }

    public Bitmap getImageOnLoading(Context context, DisplayBitmapOptions options) {
        Drawable drawable = null;
        if (options.getImageOnLoading() <= 0) {
            drawable = new ColorDrawable(context.getResources().getColor(android.R.color.darker_gray));
        } else {
            drawable = context.getResources().getDrawable(options.getImageOnLoading());
        }
        Bitmap srcBitmap = BitmapUtil.drawable2Bitmap(drawable, options.getWidth(), options.getHeight());
        return BitmapUtil.getBitmapByShape(srcBitmap, options.getShape());
    }

    public abstract void setView(Object view);

    public void cancelTasks() {
        int size = mAsyncTaskHelpers.size();
        //在滑动的时候，取消未完成的任务
        AsyncTaskHelper[] helpers = mAsyncTaskHelpers.toArray(new AsyncTaskHelper[size]);
        for (AsyncTaskHelper helper : helpers) {
            if (helper != null && helper.getStatus() != AsyncTask.Status.FINISHED) {
                helper.cancel(true);
                mAsyncTaskHelpers.remove(helper);
                helper = null;
            }
        }
    }

    public void reset() {
        mIsIdle = true;
        mLruCache.evictAll();
        AsyncTaskHelper[] helpers = mAsyncTaskHelpers.toArray(new AsyncTaskHelper[mAsyncTaskHelpers.size()]);
        for (AsyncTaskHelper helper : helpers) {
            mAsyncTaskHelpers.remove(helper);
            helper = null;
        }
        mAsyncTaskHelpers.clear();
    }
}
