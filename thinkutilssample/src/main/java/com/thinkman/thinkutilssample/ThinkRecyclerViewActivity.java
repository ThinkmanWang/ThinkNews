package com.thinkman.thinkutilssample;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.thinkman.thinkutils.activity.ThinkBaseActivity;
import com.thinkman.thinkutils.layout.ThinkPtrClassicFrameLayout;
import com.thinkman.thinkutils.view.ThinkRecyclerView;
import com.thinkman.thinkutils.view.decorator.ThinkBorderDividerItemDecoration;
import com.thinkman.thinkutilssample.adapter.ThinkBorderDividerItemDecorationAdapter;
import com.thinkman.thinkutilssample.adapter.ThinkBorderDividerItemDecorationAdapter1;
import com.thinkman.thinkutilssample.models.ThinkRecyclerViewData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class ThinkRecyclerViewActivity extends ThinkBaseActivity {

    @BindView(R.id.ftr_layout)
    ThinkPtrClassicFrameLayout m_ptrLayout = null;

    @BindView(R.id.recycler_view)
    ThinkRecyclerView mRecyclerView = null;

    private ThinkBorderDividerItemDecorationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_think_recycler_view);

        ButterKnife.bind(this);

        this.adapter = new ThinkBorderDividerItemDecorationAdapter();
        mRecyclerView.setAdapter(this.adapter);
        mRecyclerView.setVerticalScrollOnly(true);
        mRecyclerView.addItemDecoration(new ThinkBorderDividerItemDecoration(
                this.getResources().getDimensionPixelOffset(R.dimen.border_vertical_padding),
                this.getResources().getDimensionPixelOffset(R.dimen.border_horizontal_padding)));

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

        this.initData();
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
        for (int i = 1; i <= 6; i++) {
            ThinkRecyclerViewData data = new ThinkRecyclerViewData();
            String mipmapName = "mm_" + i;
            data.imageResId = this.getMipmapId(this, mipmapName);
            data.content = "Save you from anything " + "26" + "-" + i;
            allData.add(data);
        }
        this.adapter.setList(allData);
        this.adapter.notifyDataSetChanged();
    }

    public int getMipmapId(Context context, String mipmapName) {
        return context.getResources().getIdentifier(mipmapName, "mipmap", context.getPackageName());
    }
}
