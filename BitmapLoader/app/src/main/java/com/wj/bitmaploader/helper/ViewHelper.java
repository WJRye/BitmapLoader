package com.wj.bitmaploader.helper;/**
 * Created by wangjiang on 2016/5/7.
 */

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.graphics.BitmapCompat;
import android.support.v4.util.LruCache;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wj.bitmaploader.listener.DisplayListener;
import com.wj.bitmaploader.loader.DisplayBitmapOptions;
import com.wj.bitmaploader.shape.DisplayShape;
import com.wj.bitmaploader.utils.BitmapUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;

/**
 * 该类用于帮助在RecyclerView或者AbsListView(ListView,GridView)中显示大量图片
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

    private Bitmap mLoadingBitmap = null;

    /**
     * 缓存图片
     */
    LruCache<String, Bitmap> mLruCache = null;
    /**
     * 缓存开启的任务
     */
    Set<AsyncTaskHelper> mAsyncTaskHelpers = null;

    public ViewHelper() {
        mLruCache = new LruCache<String, Bitmap>((int) Runtime.getRuntime().maxMemory() / 8) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return BitmapCompat.getAllocationByteCount(value);
            }
        };
        mAsyncTaskHelpers = new HashSet<>();
    }

    /**
     * 展示图片
     *
     * @param object
     */
    public abstract void displayBitmap(Object object);

    /**
     * 加载图片
     *
     * @param imageView
     * @param options
     * @param listener
     */
    public final void loadBitmap(ImageView imageView, DisplayBitmapOptions options, DisplayListener listener) {
        if (imageView == null || options == null) {
            throw new NullPointerException("ImageView or DisplayBitmapOptions is null!");
        }
        int width = options.getWidth();
        int height = options.getHeight();
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Width or Height is 0!");
        }

        if (imageView.getWidth() == 0 || imageView.getHeight() == 0) {
            ViewGroup.LayoutParams params = imageView.getLayoutParams();
            params.width = width;
            params.height = height;
            imageView.setLayoutParams(params);
        }

        Bitmap bitmap = mLruCache.get(options.getUniqueID());
        if (bitmap == null) {
            setImageOnLoading(imageView, options);
            if (mIsIdle) {
                //停止滑动时，开始加载图片
                AsyncTaskHelper helper = new AsyncTaskHelper(imageView, options, listener);
                helper.executeOnExecutor(Executors.newFixedThreadPool(3), mLruCache);
                mAsyncTaskHelpers.add(helper);
            }
        } else {
            imageView.setImageBitmap(bitmap);
        }
    }

    /**
     * @param imageView
     * @param options
     */
    private void setImageOnLoading(ImageView imageView, DisplayBitmapOptions options) {
        if (mLoadingBitmap == null) {
            if (options.getImageOnLoading() <= 0) {
                imageView.setImageResource(0);
                return;
            }
            Bitmap loadingBitmap = BitmapUtil.drawable2Bitmap(imageView.getContext().getResources().getDrawable(options.getImageOnLoading()), options.getWidth(), options.getHeight());
            switch (options.getShape().getShapeType()) {
                case DisplayShape.CHAT:
                    return;
                case DisplayShape.CIRCLE:
                    mLoadingBitmap = BitmapUtil.getCircleBitmap(loadingBitmap);
                    break;
                case DisplayShape.RECT:
                    mLoadingBitmap = loadingBitmap;
                    break;
                case DisplayShape.ROUND_RECT:
                    mLoadingBitmap = BitmapUtil.getRoundedBitmap(loadingBitmap, options.getShape().getRadius());
                    break;
                default:
                    break;
            }
        }
        imageView.setImageBitmap(mLoadingBitmap);
    }

    /**
     * 设置View
     *
     * @param view 必须是RecyclerView或者AbsListView
     */
    public abstract void setView(Object view);

    /**
     * 取消任务加载
     */
    public final void cancelTasks() {
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

    /**
     * 回复初始设置
     */
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
