package com.wj.bitmaploader.helper;/**
 * Created by wangjiang on 2016/5/7.
 */

import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;

import com.wj.bitmaploader.listener.DisplayListener;
import com.wj.bitmaploader.loader.DisplayBitmapOptions;

/**
 * User: WangJiang(https://github.com/WJRye)
 * Date: 2016-05-07
 * Time: 18:48
 */
@Deprecated
public class AbsListViewHelper extends ViewHelper implements AbsListView.OnScrollListener {

    public AbsListViewHelper() {
        super();
    }

    @Override
    public void displayBitmap(Object viewHolder) {
        AbsListViewViewHolder viewCache = (AbsListViewViewHolder) viewHolder;
        ImageView imageView = viewCache.imageView;
        DisplayBitmapOptions options = viewCache.options;
        DisplayListener listener = viewCache.listener;
        loadBitmap(imageView, options, listener);
    }


    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {

        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            mIsIdle = true;
            int count = absListView.getChildCount();
            for (int i = 0; i < count; i++) {
                if (!mIsIdle) break;
                View view = absListView.getChildAt(i);
                displayBitmap(view.getTag());
            }
        } else {
            if (mIsIdle) {
                mIsIdle = false;
                cancelTasks();
            }
        }
    }


    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }


    @Override
    public void setView(Object object) {

        AbsListView view = (AbsListView) object;
        view.setOnScrollListener(null);
        view.setOnScrollListener(this);

    }
}
