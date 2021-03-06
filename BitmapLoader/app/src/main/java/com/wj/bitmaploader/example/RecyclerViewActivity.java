package com.wj.bitmaploader.example;/**
 * Created by wangjiang on 2016/4/7.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.wj.bitmaploader.R;
import com.wj.bitmaploader.example.adapter.BaseGridAdapter;
import com.wj.bitmaploader.example.adapter.ChatAdapter;
import com.wj.bitmaploader.example.adapter.GridAdapter;
import com.wj.bitmaploader.example.adapter.ListAdapter;
import com.wj.bitmaploader.example.adapter.StaggeredGridAdapter;
import com.wj.bitmaploader.example.decorator.DividerItemDecoration;


/**
 * User: WangJiang(wangjiang7747@gmail.com)
 * Date: 2016-04-07
 * Time: 19:29
 */
public class RecyclerViewActivity extends BaseActivity {
    public static final int TYPE_LIST_CHAT = 1;
    public static final int TYPE_LIST_CIRCLE = 2;
    public static final int TYPE_GRID = 3;
    public static final int TYPE_STAGGERED_GRID_HORIZONTAL = 4;
    public static final int TYPE_STAGGERED_GRID_VERTICAL = 5;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getIntent().getStringExtra(MainActivity.TITLE));
        initViews();
    }

    private void initViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        int type = getIntent().getIntExtra(MainActivity.TYPE, 1);
        RecyclerView.LayoutManager layoutManager = null;
        DividerItemDecoration dividerItemDecoration = null;
        switch (type) {
            case TYPE_LIST_CHAT: {
                layoutManager = new LinearLayoutManager(this);
                mAdapter = new ChatAdapter(getUris(), mRecyclerView);
                break;
            }
            case TYPE_LIST_CIRCLE: {
                layoutManager = new LinearLayoutManager(this);
                dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.TYPE_LIST);
                mRecyclerView.addItemDecoration(dividerItemDecoration);
                mAdapter = new ListAdapter(getUris(), mRecyclerView);
                break;
            }
            case TYPE_GRID: {
                int spanCount = 3;
                layoutManager = new GridLayoutManager(this, spanCount);
//                dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.TYPE_GRID);
//                mRecyclerView.addItemDecoration(dividerItemDecoration);
                mAdapter = new GridAdapter(getUris(), mRecyclerView, spanCount);
                break;
            }
            case TYPE_STAGGERED_GRID_HORIZONTAL: {
                int spanCount = 4;
                layoutManager = new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.HORIZONTAL);
//                dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.TYPE_GRID);
//                mRecyclerView.addItemDecoration(dividerItemDecoration);
                mAdapter = new StaggeredGridAdapter(getUris(), mRecyclerView, spanCount, BaseGridAdapter.STAGGERED_GRID_HORIZONTAL);
                break;
            }
            case TYPE_STAGGERED_GRID_VERTICAL: {
                int spanCount = 3;
                layoutManager = new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL);
                mAdapter = new StaggeredGridAdapter(getUris(), mRecyclerView, spanCount, BaseGridAdapter.STAGGERED_GRID_VERTICAL);
                break;
            }
            default:
                break;
        }
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public View getContentView() {
        return getLayoutInflater().inflate(R.layout.recyclerview, null);
    }

}
