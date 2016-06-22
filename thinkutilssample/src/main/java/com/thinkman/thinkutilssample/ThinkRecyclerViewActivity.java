package com.thinkman.thinkutilssample;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.thinkman.thinkutils.view.ThinkRecyclerView;
import com.thinkman.thinkutils.view.decorator.ThinkBorderDividerItemDecoration;
import com.thinkman.thinkutilssample.adapter.ThinkBorderDividerItemDecorationAdapter;
import com.thinkman.thinkutilssample.models.ThinkRecyclerViewData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ThinkRecyclerViewActivity extends AppCompatActivity {

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
        mRecyclerView.addItemDecoration(new ThinkBorderDividerItemDecoration(
                this.getResources().getDimensionPixelOffset(R.dimen.border_vertical_padding),
                this.getResources().getDimensionPixelOffset(R.dimen.border_horizontal_padding)));

        this.initData();
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
