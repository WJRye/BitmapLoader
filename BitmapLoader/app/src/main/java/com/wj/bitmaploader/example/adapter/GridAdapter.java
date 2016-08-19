package com.wj.bitmaploader.example.adapter;/**
 * Created by wangjiang on 2016/4/7.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.wj.bitmaploader.R;
import com.wj.bitmaploader.example.util.DisplayUtil;
import com.wj.bitmaploader.helper.RecyclerViewViewHolder;
import com.wj.bitmaploader.loader.DisplayBitmapOptions;

import java.util.List;


/**
 * User: WangJiang(wangjiang7747@gmail.com)
 * Date: 2016-04-07
 * Time: 20:02
 */
public class GridAdapter extends BaseGridAdapter {


    public GridAdapter(List<String> uris, RecyclerView recyclerView, int spanCount) {
        super(uris, recyclerView, spanCount, GRID_VERTICAL);
    }


    @Override
    public int[] getWidthAndHeight(Context context, int spanCount) {
        int size = (DisplayUtil.getScreenWidth(context) - context.getResources().getDimensionPixelSize(R.dimen.margin_2dp) * (spanCount * 2)) / spanCount;
        int[] wh = {size, size};
        return wh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        RecyclerViewViewHolder viewHelper = (RecyclerViewViewHolder) viewHolder;
        DisplayBitmapOptions dbo = new DisplayBitmapOptions.Builder().path(mUris.get(i)).width(mWH[0]).height(mWH[1])
                .setImageOnLoading(R.drawable.loading_image)
                .build();
        viewHelper.options = dbo;
        mLoader.displayBitmap(viewHolder);
    }
}
