package com.wj.bitmaploader.helper;/**
 * Created by wangjiang on 2016/5/2.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.wj.bitmaploader.listener.DisplayListener;
import com.wj.bitmaploader.loader.DisplayBitmapOptions;

/**
 * User: WangJiang(https://github.com/WJRye)
 * Date: 2016-05-02
 * Time: 19:37
 */
public class RecyclerViewHelper extends ViewHelper {

    private static final String TAG = "TAG";

    private RecyclerViewOnScrollListener mOnScrollListener;

    public RecyclerViewHelper() {
        super();
        mOnScrollListener = new RecyclerViewOnScrollListener();
    }

    @Override
    public void displayBitmap(Object viewHolder) {
        RecyclerViewViewHolder viewCache = (RecyclerViewViewHolder) viewHolder;
        ImageView imageView = viewCache.imageView;
        DisplayBitmapOptions options = viewCache.options;
        DisplayListener listener = viewCache.listener;

        loadBitmap(imageView, options, listener);
    }

    @Override
    public void reset() {
        super.reset();
        mOnScrollListener = null;
    }

    @Override
    public void setView(Object view) {
        RecyclerView recyclerView = (RecyclerView) view;
        recyclerView.removeOnScrollListener(mOnScrollListener);
        recyclerView.addOnScrollListener(mOnScrollListener);
    }

    private class RecyclerViewOnScrollListener extends RecyclerView.OnScrollListener {
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
                    cancelTasks();
                }
            }
        }
    }
}
