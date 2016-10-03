package com.thinkman.thinkutilssample.models;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.thinkman.thinkutils.activity.ThinkBaseActivity;
import com.thinkman.thinkutils.dialog.CommonDialogUtils;
import com.thinkman.thinkutils.layout.FlowLayout;
import com.thinkman.thinkutils.view.TagSelector;
import com.thinkman.thinkutilssample.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FlowLayoutActivity extends ThinkBaseActivity {

    @BindView(R.id.btn_add)
    Button m_btnAdd = null;

    @BindView(R.id.ts_tags)
    TagSelector m_tsTags = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        m_tsTags.setOnTagSelecteListener(new TagSelector.OnTagListener() {
            @Override
            public void onTagClicked(String szTag) {

            }

            @Override
            public void onTagAdded(String szTag) {

            }

            @Override
            public void onTagRemoved(String szTag) {

            }
        });
    }

    @OnClick(R.id.btn_add)
    public void onAddClick() {
        com.thinkman.thinkutils.dialog.CommonDialogUtils.showInputDialog(this, "标签", "输入新的标签", "标签", new CommonDialogUtils.OnInputDialogResult() {
            @Override
            public void onOk(String szText) {
                m_tsTags.addTag(szText, true);
            }
        });
    }
}
