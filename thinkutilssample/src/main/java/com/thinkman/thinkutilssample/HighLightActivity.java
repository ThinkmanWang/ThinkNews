package com.thinkman.thinkutilssample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.thinkman.thinkutils.activity.BaseActivityWithActionBar;
import com.thinkman.thinkutils.activity.ThinkBaseActivity;
import com.thinkman.thinkutils.highlight.HighlightManager;

public class HighLightActivity extends BaseActivityWithActionBar {

    private HighlightManager highlightManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_light);

        this.setActionBar(R.mipmap.ic_launcher, R.string.app_name, R.mipmap.ic_launcher);

        highlightManager = new HighlightManager(this);
        highlightManager.reshowAllHighlights();

        highlightManager.addView(R.id.btn1).setTitle(R.string.highlight1_title)
                .setDescriptionId(R.string.highlight1_descr);
        highlightManager.addView(R.id.btn2).setTitle(R.string.highlight2_title)
                .setDescriptionId(R.string.highlight2_descr);
        highlightManager.addView(R.id.btn3).setTitle(R.string.highlight3_title)
                .setDescriptionId(R.string.highlight3_descr);
    }
}
