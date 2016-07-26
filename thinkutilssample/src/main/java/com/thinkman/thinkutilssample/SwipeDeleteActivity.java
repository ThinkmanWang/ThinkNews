package com.thinkman.thinkutilssample;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.thinkman.thinkutils.activity.BaseActivityWithActionBar;
import com.thinkman.thinkutils.listener.RecyclerViewTouchListener;
import com.thinkman.thinkutils.view.ThinkRecyclerView;
import com.thinkman.thinkutils.view.decorator.ThinkBorderDividerItemDecoration;
import com.thinkman.thinkutilssample.adapter.ThinkBorderDividerItemDecorationAdapter;
import com.thinkman.thinkutilssample.adapter.ThinkBorderDividerItemDecorationAdapter1;
import com.thinkman.thinkutilssample.models.ThinkRecyclerViewData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SwipeDeleteActivity extends BaseActivityWithActionBar {

    @BindView(R.id.rv_main)
    ThinkRecyclerView mRecyclerView = null;
    private ThinkBorderDividerItemDecorationAdapter1 adapter;
    private RecyclerViewTouchListener onRecyclerViewTouchListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);
        this.setActionBar(R.mipmap.ic_launcher, R.string.title_swipe_delete, R.mipmap.ic_launcher);

        ButterKnife.bind(this);

        initView();
        initData();
    }

    private void initView() {
        this.adapter = new ThinkBorderDividerItemDecorationAdapter1();
        mRecyclerView.setAdapter(this.adapter);
        //mRecyclerView.setVerticalScrollOnly(true);
//        mRecyclerView.addItemDecoration(new ThinkBorderDividerItemDecoration(
//                this.getResources().getDimensionPixelOffset(R.dimen.border_vertical_padding),
//                this.getResources().getDimensionPixelOffset(R.dimen.border_horizontal_padding)));


        onRecyclerViewTouchListener = new RecyclerViewTouchListener(this, mRecyclerView);
        onRecyclerViewTouchListener
                .setIndependentViews(R.id.rl_main)
                .setViewsToFade(R.id.rowButton)
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
