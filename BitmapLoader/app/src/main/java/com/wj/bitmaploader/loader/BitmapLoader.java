package com.wj.bitmaploader.loader;/**
 * Created by wangjiang on 2016/4/25.
 */

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.ImageView;

import com.wj.bitmaploader.helper.AbsListViewHelper;
import com.wj.bitmaploader.helper.RecyclerViewHelper;
import com.wj.bitmaploader.helper.SyncHandlerHelper;
import com.wj.bitmaploader.helper.ViewHelper;
import com.wj.bitmaploader.listener.DisplayListener;
import com.wj.bitmaploader.shape.RectShape;

/**
 * User: WangJiang(https://github.com/WJRye)
 * Date: 2016-04-25
 * Time: 16:35
 */
public final class BitmapLoader {
    private static final String TAG = "TAG";
    private ViewHelper mHelper;

    private BitmapLoader() {
    }

    private static class BitmapLoaderHolder {
        private static BitmapLoader INSTANCE = new BitmapLoader();
    }

    public static BitmapLoader getInstance() {
        return BitmapLoaderHolder.INSTANCE;
    }


    public void displayBitmap(final ImageView imageView, final String imagePath, final DisplayListener listener) {
        imageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                DisplayBitmapOptions options = new DisplayBitmapOptions.Builder().width(imageView.getWidth()).height(imageView.getHeight()).path(imagePath).shape(new RectShape()).build();
                displayBitmap(imageView, options, listener);
            }
        });
    }

    public void displayBitmap(final ImageView imageView, final byte[] data, final DisplayListener listener) {
        imageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                DisplayBitmapOptions options = new DisplayBitmapOptions.Builder().width(imageView.getWidth()).height(imageView.getHeight()).data(data).shape(new RectShape()).build();
                displayBitmap(imageView, options, listener);
            }
        });
    }

    public void displayBitmap(ImageView imageView, DisplayBitmapOptions options, DisplayListener listener) {
        if (imageView == null || options == null) {
            throw new NullPointerException("ImageView or DisplayBitmapOptions is null!");
        }
        int width = options.getWidth();
        int height = options.getHeight();
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Width or Height is illegal!");
        }

        if (imageView.getWidth() == 0 && imageView.getHeight() == 0) {
            ViewGroup.LayoutParams params = imageView.getLayoutParams();
            params.width = width;
            params.height = height;
            imageView.setLayoutParams(params);
        }

        new SyncHandlerHelper(imageView, options, listener).execute();
    }


    public void displayBitmap(Object viewHolder) {
        if (mHelper == null) {
            throw new IllegalAccessError("You must call setRecyclerView() before this!");
        }
        mHelper.displayBitmap(viewHolder);
    }


    public void setView(Object view) {
        if (view == null) throw new NullPointerException("RecyclerView is null!");

        if (mHelper != null) {
            mHelper.reset();
            mHelper = null;
        }

        if (view instanceof RecyclerView) {
            mHelper = new RecyclerViewHelper();
        } else if (view instanceof AbsListView) {
            mHelper = new AbsListViewHelper();
        }

        mHelper.setView(view);
    }


}
