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
import com.wj.bitmaploader.shape.ChatShape;

import java.util.ArrayList;
import java.util.List;

/**
 * User: WangJiang(wangjiang7747@gmail.com)
 * Date: 2016-04-07
 * Time: 19:45
 */
public class ChatAdapter extends RecyclerView.Adapter {
    private final int VIEW_TYPE_LEFT = 1;
    private final int VIEW_TYPE_RIGHT = 2;

    private int mWidth;
    private int mHeight;
    private List<String> mUris = new ArrayList<>();
    private BitmapLoader mLoader = BitmapLoader.getInstance();

    public ChatAdapter(List<String> uris, RecyclerView recyclerView) {
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

        if (i == VIEW_TYPE_LEFT) {
            View leftView = LayoutInflater.from(context).inflate(R.layout.list_item_left, null);
            leftView.setLayoutParams(params);
            return new ViewCacheLeft(leftView);
        } else if (i == VIEW_TYPE_RIGHT) {
            View rightView = LayoutInflater.from(context).inflate(R.layout.list_item_right, null);
            rightView.setLayoutParams(params);
            return new ViewCacheRight(rightView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (position % 2 == 0) {
            ViewCacheLeft viewCacheLeft = (ViewCacheLeft) viewHolder;
            DisplayBitmapOptions dbo = new DisplayBitmapOptions.Builder().
                    path(mUris.get(position)).width(mWidth).height(mHeight).shape(new ChatShape(ChatShape.LEFT, 20)).build();
            viewCacheLeft.options = dbo;
            mLoader.displayBitmap(viewCacheLeft);
        } else {
            ViewCacheRight viewCacheRight = (ViewCacheRight) viewHolder;
            DisplayBitmapOptions dbo = new DisplayBitmapOptions.Builder().
                    path(mUris.get(position)).width(mWidth).height(mHeight).shape(new ChatShape(ChatShape.RIGHT, 20)).build();
            viewCacheRight.options = dbo;
            mLoader.displayBitmap(viewCacheRight);
        }
    }

    @Override
    public int getItemCount() {
        return mUris.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return VIEW_TYPE_LEFT;
        }
        return VIEW_TYPE_RIGHT;
    }

    public static class ViewCacheLeft extends RecyclerViewViewHolder {

        public ViewCacheLeft(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.item_imageview_left);
        }
    }

    public static class ViewCacheRight extends RecyclerViewViewHolder {

        public ViewCacheRight(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.item_imageview_right);
        }
    }
}
