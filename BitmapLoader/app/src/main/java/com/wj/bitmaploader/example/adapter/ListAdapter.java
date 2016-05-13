package com.wj.bitmaploader.example.adapter;/**
 * Created by wangjiang on 2016/4/7.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wj.bitmaploader.R;
import com.wj.bitmaploader.helper.RecyclerViewViewHolder;
import com.wj.bitmaploader.loader.BitmapLoader;
import com.wj.bitmaploader.loader.DisplayBitmapOptions;
import com.wj.bitmaploader.shape.CircleShape;

import java.util.ArrayList;
import java.util.List;

/**
 * User: WangJiang(wangjiang7747@gmail.com)
 * Date: 2016-04-07
 * Time: 19:45
 */
public class ListAdapter extends RecyclerView.Adapter {
    private int mWidth;
    private int mHeight;
    private List<String> mUris = new ArrayList<>();
    private BitmapLoader mLoader = BitmapLoader.getInstance();

    public ListAdapter(List<String> uris, RecyclerView recyclerView) {
        mUris.addAll(uris);
        mLoader.setView(recyclerView);
        Context context = recyclerView.getContext();
        mWidth = context.getResources().getDimensionPixelSize(R.dimen.size_60dp);
        mHeight = context.getResources().getDimensionPixelSize(R.dimen.size_60dp);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, null);
        view.setLayoutParams(params);
        return new ViewCache(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ViewCache viewCache = (ViewCache) viewHolder;
        DisplayBitmapOptions dbo = new DisplayBitmapOptions.Builder().
                path(mUris.get(i)).width(mWidth).height(mHeight).shape(new CircleShape()).build();
        viewCache.options = dbo;
        mLoader.displayBitmap(viewCache);
    }

    @Override
    public int getItemCount() {
        return mUris.size();
    }

    public static class ViewCache extends RecyclerViewViewHolder {

        public ViewCache(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.item_imageview);
        }
    }
}
