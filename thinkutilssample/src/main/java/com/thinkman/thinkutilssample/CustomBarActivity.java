package com.thinkman.thinkutilssample;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.thinkman.thinkutils.activity.ThinkBaseActivity;
import com.thinkman.thinkutils.commonutils.ToastUtils;
import com.thinkman.thinkutils.view.CircleImageText;
import com.thinkman.thinkutils.view.CommonHorizontalAttachBar;
import com.thinkman.thinkutils.view.CommonMultiLineAttachBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.finalteam.galleryfinal.model.PhotoInfo;

public class CustomBarActivity extends ThinkBaseActivity {

    @BindView(R.id.chab_attachment)
    CommonHorizontalAttachBar m_chabAttachment = null;

    @BindView(R.id.cab_photos)
    CommonMultiLineAttachBar m_cabPhotos = null;

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
        initAttachBar();
        initMultiLineAttachBar();
    }

    private void initMultiLineAttachBar() {
        m_cabPhotos.addPhoto("https://www.baidu.com/img/bd_logo1.png");
        m_cabPhotos.addPhoto("https://www.baidu.com/img/bd_logo1.png");
        m_cabPhotos.addPhoto("https://www.baidu.com/img/bd_logo1.png");
        m_cabPhotos.addPhoto("https://www.baidu.com/img/bd_logo1.png");
        m_cabPhotos.addPhoto("https://o1wh05aeh.qnssl.com/image/view/app_icons/d6e5302682efc074ee6ac2a5c21374fb/120");
        m_cabPhotos.addPhoto("https://o1wh05aeh.qnssl.com/image/view/app_icons/d6e5302682efc074ee6ac2a5c21374fb/120");
        m_cabPhotos.addPhoto("https://o1wh05aeh.qnssl.com/image/view/app_icons/d6e5302682efc074ee6ac2a5c21374fb/120");
        m_cabPhotos.addPhoto("https://o1wh05aeh.qnssl.com/image/view/app_icons/d6e5302682efc074ee6ac2a5c21374fb/120");

        m_cabPhotos.setOnItemClickListener(new CommonMultiLineAttachBar.OnItemClickListener() {
            @Override
            public void onItemClick(int nPosition, PhotoInfo info) {
                ToastUtils.showToast(CustomBarActivity.this, "" + nPosition + " clicked");
            }
        });
    }

    private void initAttachBar() {
        m_chabAttachment.addItem("https://www.baidu.com/img/bd_logo1.png", "百度0");
        m_chabAttachment.addItem("https://o1wh05aeh.qnssl.com/image/view/app_icons/d6e5302682efc074ee6ac2a5c21374fb/120", "小宝");
        m_chabAttachment.addItem("https://www.baidu.com/img/bd_logo1.png", "百度2");
        m_chabAttachment.addItem("https://www.baidu.com/img/bd_logo1.png", "百度3");
        m_chabAttachment.addItem("https://www.baidu.com/img/bd_logo1.png", "百度4");

        m_chabAttachment.addItem("https://www.baidu.com/img/bd_logo1.png", "百度5");
        m_chabAttachment.addItem("https://www.baidu.com/img/bd_logo1.png", "百度6");
        m_chabAttachment.addItem("https://www.baidu.com/img/bd_logo1.png", "百度7");
        m_chabAttachment.addItem("https://www.baidu.com/img/bd_logo1.png", "百度8");
        m_chabAttachment.addItem("https://www.baidu.com/img/bd_logo1.png", "百度9");

        m_chabAttachment.setOnItemClickListener(new CommonHorizontalAttachBar.OnItemClickedListener() {
            @Override
            public void onItemClick(int nPostion, CircleImageText view) {
                ToastUtils.showToast(CustomBarActivity.this, "" + nPostion + " " + view.getTextView().getText());
                m_chabAttachment.removeAt(nPostion);
            }
        });
    }

}
