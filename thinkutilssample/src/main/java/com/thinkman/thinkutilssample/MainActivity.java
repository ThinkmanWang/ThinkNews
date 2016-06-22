package com.thinkman.thinkutilssample;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.ButterKnife;

import com.thinkman.thinkutils.dialog.CommonDialogUtils;
import com.thinkman.thinkutils.view.ImagePickerView;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.et_result) EditText m_etResult;
    @BindView(R.id.ipv_image_picker)
    ImagePickerView m_ipvImagePicker;

    @BindView(R.id.ftr_layout)
    PtrClassicFrameLayout m_ptrLayout = null;

    @BindView(R.id.btn_recyclerview)
    Button m_btnThinkRecyclerView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        m_ipvImagePicker.init(this);

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
    }

    protected void updateData() {

        m_ptrLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                m_ptrLayout.refreshComplete();
            }
        }, 3000);

    }

    @OnClick(R.id.btn_input_dlg)
    public void onInputDlgClick() {
        CommonDialogUtils.showInputDialog(MainActivity.this
                , "Title", "Hello World", "haha"
                , new CommonDialogUtils.OnInputDialogResult() {
            @Override
            public void onOk(String szText) {
                m_etResult.setText(szText);
            }
        });
    }

    @OnClick(R.id.btn_progress_layout)
    public void onShowProgressClick() {
        Intent intent = new Intent(MainActivity.this, ProgressActivity.class);
        this.startActivity(intent);
    }

    @OnClick(R.id.btn_login)
    public void onLoginClick() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        this.startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btn_recyclerview)
    public void onRecyclerViewTestClick() {
        Intent intent = new Intent(MainActivity.this, ThinkRecyclerViewActivity.class);
        this.startActivity(intent);
    }
}
