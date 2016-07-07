package com.thinkman.thinkutilssample;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.thinkman.thinkutils.view.CommonHorizontalAttachBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomBarActivity extends AppCompatActivity {

    @BindView(R.id.chab_attachment)
    CommonHorizontalAttachBar m_chabAttachment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_bar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        init();
    }

    private void init() {
        m_chabAttachment.addItem("https://www.baidu.com/img/bd_logo1.png", "百度0");
        m_chabAttachment.addItem("https://www.baidu.com/img/bd_logo1.png", "百度1");
        m_chabAttachment.addItem("https://www.baidu.com/img/bd_logo1.png", "百度2");
        m_chabAttachment.addItem("https://www.baidu.com/img/bd_logo1.png", "百度3");
        m_chabAttachment.addItem("https://www.baidu.com/img/bd_logo1.png", "百度4");

        m_chabAttachment.addItem("https://www.baidu.com/img/bd_logo1.png", "百度5");
        m_chabAttachment.addItem("https://www.baidu.com/img/bd_logo1.png", "百度6");
        m_chabAttachment.addItem("https://www.baidu.com/img/bd_logo1.png", "百度7");
        m_chabAttachment.addItem("https://www.baidu.com/img/bd_logo1.png", "百度8");
        m_chabAttachment.addItem("https://www.baidu.com/img/bd_logo1.png", "百度9");
    }

}
