package com.thinkman.thinkutilssample;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.thinkman.thinkutils.activity.BaseActivityWithActionBar;
import com.thinkman.thinkutils.commonutils.DisplayUtil;
import com.thinkman.thinkutils.layout.ThinkPtrClassicFrameLayout;
import com.thinkman.thinkutils.listener.RecyclerViewTouchListener;
import com.thinkman.thinkutils.manager.LinearLayoutManager;
import com.thinkman.thinkutils.view.ThinkRecyclerView;
import com.thinkman.thinkutils.view.ThinkRecyclerViewScrollable;
import com.thinkman.thinkutils.view.decorator.ThinkBorderDividerItemDecoration;
import com.thinkman.thinkutilssample.adapter.ThinkBorderDividerItemDecorationAdapter;
import com.thinkman.thinkutilssample.models.ThinkRecyclerViewData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class ThinkRecyclerViewScrollingActivity extends BaseActivityWithActionBar {

    @BindView(R.id.ftr_layout)
    ThinkPtrClassicFrameLayout m_ptrLayout = null;

    @BindView(R.id.recycler_view)
    ThinkRecyclerViewScrollable mRecyclerView = null;

    private ThinkBorderDividerItemDecorationAdapter adapter;
    private RecyclerViewTouchListener onRecyclerViewTouchListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_think_recycler_view_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setActionBar(R.mipmap.ic_launcher, "HAHA");
        ButterKnife.bind(this);

        initView();
        this.initData();
    }

    private void initView() {
        this.adapter = new ThinkBorderDividerItemDecorationAdapter();
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new ThinkBorderDividerItemDecoration(
                this.getResources().getDimensionPixelOffset(R.dimen.border_vertical_padding),
                this.getResources().getDimensionPixelOffset(R.dimen.border_horizontal_padding)));

        mRecyclerView.setAdapter(this.adapter);
        mRecyclerView.setNestedScrollingEnabled(false);

        //mRecyclerView.setVerticalScrollOnly(true);

        m_ptrLayout.setLastUpdateTimeRelateObject(this);
        m_ptrLayout.setPtrHandler(new PtrDefaultHandler2() {

            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                updateData();
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                updateData();
            }

        });
        // the following are default settings
        m_ptrLayout.setResistance(1.7f); // you can also set foot and header separately
        m_ptrLayout.setRatioOfHeaderHeightToRefresh(1.2f);
        m_ptrLayout.setDurationToClose(1000);  // you can also set foot and header separately
        // default is false
        m_ptrLayout.setPullToRefresh(false);

        // default is true
        m_ptrLayout.setKeepHeaderWhenRefresh(true);
        m_ptrLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                // mPtrFrame.autoRefresh();
            }
        }, 100);

        //init swipe delete
        onRecyclerViewTouchListener = new RecyclerViewTouchListener(this, mRecyclerView);
        onRecyclerViewTouchListener
                .setIndependentViews(R.id.rl_main)
                .setViewsToFade(R.id.btn_row)
                .setSwipeOptionViews(R.id.btn_delete)
                .setSwipeable(R.id.rl_main, R.id.btn_delete, new RecyclerViewTouchListener.OnSwipeOptionsClickListener() {
                    @Override
                    public void onSwipeOptionClicked(int viewID, int position) {
                        String message = "";
                        if (viewID == R.id.btn_delete) {
                            message += "删除";
                        }
                        message += " position-> " + position;
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    }
                });
        mRecyclerView.addOnItemTouchListener(onRecyclerViewTouchListener);
    }

    protected void updateData() {

        m_ptrLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                m_ptrLayout.refreshComplete();
            }
        }, 3000);

    }

    private void initData() {

        ArrayList<ThinkRecyclerViewData> allData = new ArrayList<>();
        for (int i = 1; i <= 16; i++) {
            ThinkRecyclerViewData data = new ThinkRecyclerViewData();
            String mipmapName = "mm_" + (i%6);
            data.imageResId = this.getMipmapId(this, mipmapName);
            data.content = "Save you from anything " + "26" + "-" + i;
            allData.add(data);
        }
        this.adapter.setList(allData);
        this.adapter.notifyDataSetChanged();

        //m_ptrLayout.setMinimumHeight(mRecyclerView.getMeasuredHeight());
    }

    public int getMipmapId(Context context, String mipmapName) {
        return context.getResources().getIdentifier(mipmapName, "mipmap", context.getPackageName());
    }
}
