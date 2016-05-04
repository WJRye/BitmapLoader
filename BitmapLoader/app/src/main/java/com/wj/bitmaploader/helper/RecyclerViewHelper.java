package com.wj.bitmaploader.helper;/**
 * Created by wangjiang on 2016/5/2.
 */

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.wj.bitmaploader.listener.DisplayListener;
import com.wj.bitmaploader.loader.DisplayBitmapOptions;
import com.wj.bitmaploader.loading.AsyncTaskHelper;

import java.util.HashSet;
import java.util.Set;

/**
 * User: WangJiang(https://github.com/WJRye)
 * Date: 2016-05-02
 * Time: 19:37
 */
public class RecyclerViewHelper extends RecyclerView.OnScrollListener {

    private static final String TAG = "TAG";

    /**
     * 否停止了滑动
     */
    private boolean mIsIdle = true;

    private LruCache<Integer, Bitmap> mLruCache;
    private Set<AsyncTaskHelper> mAsyncTaskHelpers;

    public RecyclerViewHelper() {
        mLruCache = new LruCache<Integer, Bitmap>((int) Runtime.getRuntime().maxMemory() / 8) {
            @Override
            protected int sizeOf(Integer key, Bitmap value) {
                return value.getByteCount();
            }
        };
        mAsyncTaskHelpers = new HashSet<>();
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            mIsIdle = true;
            int count = recyclerView.getChildCount();
            for (int i = 0; i < count; i++) {
                if (!mIsIdle) break;
                View view = recyclerView.getChildAt(i);
                displayBitmap(recyclerView.getChildViewHolder(view));
            }
        } else {
            if (mIsIdle) {
                mIsIdle = false;
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
        }
    }

    public void displayBitmap(RecyclerView.ViewHolder viewHolder, String imagePath, DisplayBitmapOptions options, DisplayListener listener) {
        ViewHelper viewCache = (ViewHelper) viewHolder;
        viewCache.options = new DisplayBitmapOptions.Builder(options).path(imagePath).build();
        viewCache.listener = listener;
        displayBitmap(viewCache);
    }

    public void displayBitmap(RecyclerView.ViewHolder viewHolder, byte[] data, DisplayBitmapOptions options, DisplayListener listener) {
        ViewHelper viewCache = (ViewHelper) viewHolder;
        viewCache.options = new DisplayBitmapOptions.Builder(options).data(data).build();
        viewCache.listener = listener;
        displayBitmap(viewCache);
    }

    /**
     * 加载图片
     *
     * @param viewHolder
     */
    public void displayBitmap(RecyclerView.ViewHolder viewHolder) {
        ViewHelper viewCache = (ViewHelper) viewHolder;
        ImageView imageView = viewCache.imageView;
        DisplayBitmapOptions options = viewCache.options;
        if (imageView == null || options == null) {
            throw new NullPointerException("ImageView or DisplayBitmapOptions is null!");
        }
        int width = options.getWidth();
        int height = options.getHeight();
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Width or Height is illegal!");
        }
        int cacheKey = viewCache.getLayoutPosition();
        Bitmap bitmap = mLruCache.get(Integer.valueOf(cacheKey));
        if (bitmap == null) {
            imageView.setImageDrawable(imageView.getContext().getResources().getDrawable(options.getImageOnLoading()));
            if (mIsIdle) {
                AsyncTaskHelper helper = new AsyncTaskHelper(viewCache.imageView, viewCache.options, viewCache.listener, cacheKey);
                helper.execute(mLruCache);
                mAsyncTaskHelpers.add(helper);
            }
        } else {
            viewCache.imageView.setImageBitmap(bitmap);
        }
    }

}
