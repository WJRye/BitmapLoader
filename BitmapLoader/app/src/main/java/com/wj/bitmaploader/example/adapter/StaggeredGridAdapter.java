package com.wj.bitmaploader.example.adapter;/**
 * Created by wangjiang on 2016/4/9.
 */

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;

import com.wj.bitmaploader.R;
import com.wj.bitmaploader.example.util.DisplayUtil;
import com.wj.bitmaploader.helper.RecyclerViewViewHolder;
import com.wj.bitmaploader.loader.DisplayBitmapOptions;

import java.util.List;
import java.util.Random;

/**
 * User: WangJiang(wangjiang7747@gmail.com)
 * Date: 2016-04-09
 * Time: 15:15
 */
public class StaggeredGridAdapter extends BaseGridAdapter {

    //随机生成一个数
    private Random mRandom = new Random();
    //缓存Item的高度
    private SparseIntArray mHeightArray = new SparseIntArray();

    public StaggeredGridAdapter(List<String> uris, RecyclerView recyclerView, int spanCount, int orientation) {
        super(uris, recyclerView, spanCount, orientation);
    }

    @Override
    public int[] getWidthAndHeight(Context context, int spanCount) {
        int size = 0;
        switch (mOrientation) {
            case STAGGERED_GRID_HORIZONTAL:
                size = (DisplayUtil.getContentHeight((Activity) context) - context.getResources().getDimensionPixelSize(R.dimen.margin_2dp) * (spanCount * 2)) / spanCount;
                break;
            case STAGGERED_GRID_VERTICAL:
                size = (DisplayUtil.getScreenWidth(context) - context.getResources().getDimensionPixelSize(R.dimen.margin_2dp) * (spanCount * 2)) / spanCount;
                break;
            default:
                size = (DisplayUtil.getScreenWidth(context) - context.getResources().getDimensionPixelSize(R.dimen.margin_2dp) * (spanCount * 2)) / spanCount;
                break;
        }
        int[] wh = {size, size};
        return wh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        RecyclerViewViewHolder viewHelper = (RecyclerViewViewHolder) viewHolder;
        DisplayBitmapOptions dbo = null;
        if (mOrientation == STAGGERED_GRID_VERTICAL) {
            int height = mHeightArray.get(i, -1);
            if (height == -1) {
                height = mRandom.nextInt(mWH[1]) + mWH[1];
                mHeightArray.put(i, height);
            }
            //动态设置item的高度
            ViewCache viewCache = (ViewCache) viewHolder;
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) viewCache.imageView.getLayoutParams();
            params.height = height;
            viewCache.imageView.setLayoutParams(params);
            dbo = new DisplayBitmapOptions.Builder().path(mUris.get(i)).width(mWH[0]).height(height).setImageOnLoading(R.drawable.loading_image).build();
        } else {
            dbo = new DisplayBitmapOptions.Builder().path(mUris.get(i)).width(mWH[0]).height(mWH[1]).setImageOnLoading(R.drawable.loading_image).build();
        }
        viewHelper.options = dbo;
        mLoader.displayBitmap(viewHolder);
    }


}
