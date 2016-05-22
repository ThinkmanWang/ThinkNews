package com.thinkman.thinknews.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.github.captain_miao.recyclerviewutils.common.BaseLoadMoreFooterView;
import com.github.captain_miao.recyclerviewutils.listener.LinearLayoutWithRecyclerOnScrollListener;
import com.thinkman.thinkactivity.BaseActivity;
import com.thinkman.thinknews.R;
import com.thinkman.thinknews.adapter.NewsAdapter;
import com.thinkman.thinknews.models.NewsModel;
import com.thinkman.thinknews.utils.FavoritesDbUtils;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;
import in.srain.cube.views.ptr.util.PtrLocalDisplay;

public class FavoriteActivity extends BaseActivity {
    private ProgressBar progressBar;
    private RelativeLayout mMainLayout;

    private RecyclerView mRecyclerView = null;
    private NewsAdapter mAdapter = null;
    private LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
    private PtrFrameLayout ptrFrameLayout = null;

    private static final int PAGE_SIZE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);

        progressBar = (ProgressBar) findViewById(R.id.fragment_mainTab_item_progressBar);
        mMainLayout = (RelativeLayout) findViewById(R.id.main_layout);

        this.setActionBar(R.mipmap.ic_arrow_back_white, R.string.app_name);

        initView();
        initData();
    }

    private void initView() {
        mRecyclerView =  (RecyclerView) findViewById(R.id.recycler_view);
        //mLinearLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mAdapter = new NewsAdapter(this, new ArrayList<NewsModel>());
        mAdapter.setLoadMoreFooterView(new BaseLoadMoreFooterView(this) {
            @Override
            public int getLoadMoreLayoutResource() {
                return R.layout.list_load_more;
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        ptrFrameLayout = (PtrFrameLayout) findViewById(R.id.material_style_ptr_frame);
        // header
        final MaterialHeader header = new MaterialHeader(this);
        //header.setColorSchemeColors(new int[]{R.color.line_color_run_speed_13});
        int[] colors = getResources().getIntArray(R.array.google_colors);
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, PtrLocalDisplay.dp2px(15), 0, PtrLocalDisplay.dp2px(10));
        header.setPtrFrameLayout(ptrFrameLayout);


        ptrFrameLayout.setDurationToCloseHeader(1500);
        ptrFrameLayout.setHeaderView(header);
        ptrFrameLayout.addPtrUIHandler(header);
        ptrFrameLayout.setEnabledNextPtrAtOnce(false);
        ptrFrameLayout.setPtrHandler(mPtrHandler);

        mRecyclerView.addOnScrollListener(mLoadMoreListener);
        ptrFrameLayout.refreshComplete();
        mLoadMoreListener.loadComplete();
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            progressBar.setVisibility(View.GONE);
            mMainLayout.setVisibility(View.VISIBLE);
        }
    };

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<NewsModel> listNews = FavoritesDbUtils.getFavorite(FavoriteActivity.this, 0, PAGE_SIZE);
                if (null == listNews || 0 == listNews.size()) {
                    return;
                }

                FavoriteActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.clear();
                        mAdapter.addAll(listNews);

                        mAdapter.hideFooterView();
                        mAdapter.notifyDataSetChanged();
                        mRecyclerView.scrollToPosition(0);
                        mHandler.sendEmptyMessageDelayed(1, 1000);
                    }
                });
            }
        }).start();
    }

    PtrHandler mPtrHandler = new PtrHandler() {
        @Override
        public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
            return mLoadMoreListener.checkCanDoRefresh();
        }

        @Override
        public void onRefreshBegin(PtrFrameLayout frame) {
            mLoadMoreListener.setPagination(1);//恢复第一页

            //TODO refresh datas
            initData();

            ptrFrameLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ptrFrameLayout.refreshComplete();

                    mAdapter.hideFooterView();
                    mAdapter.notifyDataSetChanged();
                    mRecyclerView.scrollToPosition(0);
                }
            }, 500);
        }
    };

    private LinearLayoutWithRecyclerOnScrollListener mLoadMoreListener = new LinearLayoutWithRecyclerOnScrollListener(mLinearLayoutManager) {

        @Override
        public void onLoadMore(final int pagination, int pageSize) {

            mRecyclerView.post(new Runnable() {
                @Override
                public void run() {
                    mAdapter.showLoadMoreView();
                }
            });

            final List<NewsModel> listNews = FavoritesDbUtils.getFavorite(FavoriteActivity.this, mAdapter.getItemCount(), PAGE_SIZE);

            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (listNews != null) {
                        mAdapter.addAll(listNews);
                    }
                    mHandler.sendEmptyMessageDelayed(1, 1000);

                    mAdapter.notifyDataSetChanged();
                    mAdapter.hideFooterView();
                    loadComplete();
                    mHandler.sendEmptyMessageDelayed(1, 1000);

                }
            }, 1000);

        }
    };
}
