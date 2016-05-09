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
 * Time: 19:32
 */
public class RecyclerViewViewHolder extends RecyclerView.ViewHolder {
    public ImageView imageView;
    public DisplayBitmapOptions options;
    public DisplayListener listener;

    public RecyclerViewViewHolder(View itemView) {
        super(itemView);
    }
}
