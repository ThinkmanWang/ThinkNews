package com.thinkman.thinkutilssample.models;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.thinkman.thinkutils.dialog.CommonDialogUtils;
import com.thinkman.thinkutils.layout.FlowLayout;
import com.thinkman.thinkutilssample.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FlowLayoutActivity extends AppCompatActivity {

    @BindView(R.id.btn_add)
    Button m_btnAdd = null;

    @BindView(R.id.flow_layout)
    FlowLayout m_flTags = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_add)
    public void onAddClick() {
        com.thinkman.thinkutils.dialog.CommonDialogUtils.showInputDialog(this, "标签", "输入新的标签", "标签", new CommonDialogUtils.OnInputDialogResult() {
            @Override
            public void onOk(String szText) {
                TextView tvTag = new TextView(FlowLayoutActivity.this);
                tvTag.setText(szText);
                tvTag.setTextColor(FlowLayoutActivity.this.getResources().getColor(R.color.black));
                tvTag.setBackgroundColor(FlowLayoutActivity.this.getResources().getColor(R.color.bg_grey));
                tvTag.setPadding(20, 20, 20, 20);

                m_flTags.addView(tvTag);
                m_flTags.invalidate();
            }
        });
    }
}
