package com.wj.bitmaploader.example.adapter;/**
 * Created by wangjiang on 2016/4/7.
 */

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wj.bitmaploader.R;
import com.wj.bitmaploader.helper.ViewHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * User: WangJiang(wangjiang7747@gmail.com)
 * Date: 2016-04-07
 * Time: 19:44
 */
public abstract class BaseGridAdapter extends RecyclerView.Adapter {

    //水平网格
    public static final int STAGGERED_GRID_HORIZONTAL = 0;
    //瀑布流
    public static final int STAGGERED_GRID_VERTICAL = 1;
    //垂直网格
    public static final int GRID_VERTICAL = 2;
    public int mOrientation = GRID_VERTICAL;
    //是否停止了滑动
    private boolean mIsIdle = true;
    //item 的宽高
    int[] mWH = null;
    List<String> mUris = new ArrayList<>();

    public BaseGridAdapter(Context context, RecyclerView recyclerView, int spanCount, int orientation) {
        mOrientation = orientation;
        mWH = getWidthAndHeight(context, spanCount);
        mUris.addAll(getUris(context));
    }

    public abstract int[] getWidthAndHeight(Context context, int spanCount);


    /**
     * 获得系统图片的路径
     *
     * @param context
     * @return
     */
    private ArrayList<String> getUris(Context context) {
        ArrayList<String> uris = new ArrayList<>();
        //按照添加时间倒叙排序
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.MediaColumns.DATA, MediaStore.MediaColumns.DATE_ADDED}, null, null, "date_added desc");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                uris.add(cursor.getString(0));
            }
            cursor.close();
            cursor = null;
        }
        return uris;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(mWH[0], mWH[1]);
        int margin = context.getResources().getDimensionPixelSize(R.dimen.margin_2dp);
        params.leftMargin = margin;
        params.topMargin = margin;
        params.rightMargin = margin;
        params.bottomMargin = margin;
        imageView.setLayoutParams(params);
        return new ViewCache(imageView);
    }

    @Override
    public abstract void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i);


    @Override
    public int getItemCount() {
        return mUris.size();
    }

    public static class ViewCache extends ViewHelper {
        public ViewCache(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView;
        }
    }
}
