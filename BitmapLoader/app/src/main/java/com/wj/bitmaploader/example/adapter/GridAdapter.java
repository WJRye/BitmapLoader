package com.wj.bitmaploader.example.adapter;/**
 * Created by wangjiang on 2016/4/7.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.wj.bitmaploader.R;
import com.wj.bitmaploader.helper.ViewHelper;
import com.wj.bitmaploader.loader.BitmapLoader;
import com.wj.bitmaploader.loader.DisplayBitmapOptions;
import com.wj.bitmaploader.shape.RectShape;


/**
 * User: WangJiang(wangjiang7747@gmail.com)
 * Date: 2016-04-07
 * Time: 20:02
 */
public class GridAdapter extends BaseGridAdapter {

    static final String TAG = "TAG";
    BitmapLoader loader = BitmapLoader.getInstance();
    DisplayBitmapOptions options;

    public GridAdapter(Context context, RecyclerView recyclerView, int spanCount) {
        super(context, recyclerView, spanCount, GRID_VERTICAL);
        options = new DisplayBitmapOptions.Builder().width(mWH[0]).height(mWH[1]).shape(new RectShape()).setImageOnLoading(R.drawable.loading_image).build();
        loader.setRecyclerView(recyclerView);
    }


    @Override
    public int[] getWidthAndHeight(Context context, int spanCount) {
        int size = (context.getResources().getDisplayMetrics().widthPixels - context.getResources().getDimensionPixelSize(R.dimen.margin_2dp) * (spanCount * 2)) / spanCount;
        int[] wh = {size, size};
        return wh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ViewHelper viewHelper = (ViewHelper) viewHolder;
        loader.displayBitmapInRecyclerView(viewHelper, mUris.get(i), options, null);
    }
}
