package com.thinkman.thinkutilssample;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.ButterKnife;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.thinkman.thinkutils.activity.BaseActivityWithActionBar;
import com.thinkman.thinkutils.activity.ThinkBaseActivity;
import com.thinkman.thinkutils.commonutils.ToastUtils;
import com.thinkman.thinkutils.dialog.CommonDialogUtils;
import com.thinkman.thinkutils.view.ImagePickerView;
import com.thinkman.thinkutils.view.SearchEditText;
import com.thinkman.thinkutils.view.ThinkHorizontalScrollView;
import com.thinkman.thinkutilssample.bean.ProvinceBean;
import com.thinkman.thinkutilssample.models.FlowLayoutActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class MainActivity extends BaseActivityWithActionBar {

    @BindView(R.id.et_result) EditText m_etResult;
    @BindView(R.id.ipv_image_picker)
    ImagePickerView m_ipvImagePicker;

    @BindView(R.id.ftr_layout)
    PtrClassicFrameLayout m_ptrLayout = null;

    @BindView(R.id.btn_recyclerview)
    Button m_btnThinkRecyclerView = null;

    @BindView(R.id.btn_custombar)
    Button m_btnCustomBar = null;

    @BindView(R.id.btn_date_time)
    Button m_btnDateTime = null;

    TimePickerView pvTime = null;

    @BindView(R.id.btn_area_picker)
    Button m_btnAreaPicker = null;

    @BindView(R.id.btn_guide_page)
    Button m_btnGuidePage = null;

    @BindView(R.id.btn_fragment_test)
    Button m_btnFragmentTest = null;

    @BindView(R.id.btn_my_tags)
    Button m_btnTags = null;

    @BindView(R.id.btn_highlight)
    Button m_btnHighlight = null;

    @BindView(R.id.btn_translucent_bar)
    Button m_btnTranslucentBar = null;

    @BindView(R.id.btn_update_statusbar_color)
    Button m_btnUpdateStatusBarColor = null;

    @BindView(R.id.set_search)
    SearchEditText m_setSearch = null;

    OptionsPickerView pvOptions;
    private ArrayList<ProvinceBean> options1Items = new ArrayList<ProvinceBean>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<ArrayList<ArrayList<String>>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        this.setDoubleBackExit(true);
        this.setActionBar(R.mipmap.ic_launcher, R.string.app_name);

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

        initView();
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

    public void initView() {
        //init time picker
        pvTime = new TimePickerView(this, TimePickerView.Type.ALL);
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                m_btnDateTime.setText(getTime(date));
            }
        });

        m_setSearch.setOnLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast(MainActivity.this, "Search: " + m_setSearch.getEditText().getText().toString());
            }
        });

        m_setSearch.setOnRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_setSearch.getEditText().setText("");
            }
        });

        m_setSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH)
                {
                    ToastUtils.showToast(MainActivity.this, "Search: " + m_setSearch.getEditText().getText().toString());
                    return true;
                }
                return false;
            }
        });

        initAreaPicker();
    }

    public void initAreaPicker() {
        pvOptions = new OptionsPickerView(this);

//        options1Items.add(new String("广东"));
//        options1Items.add(new String("湖南"));
//        options1Items.add(new String("广西"));
        options1Items.add(new ProvinceBean(0,"广东","广东省，以岭南东道、广南东路得名","其他数据"));
        options1Items.add(new ProvinceBean(1,"湖南","湖南省地处中国中部、长江中游，因大部分区域处于洞庭湖以南而得名湖南","芒果TV"));
        options1Items.add(new ProvinceBean(3,"广西","嗯～～",""));

        //选项2
        ArrayList<String> options2Items_01=new ArrayList<String>();
        options2Items_01.add("广州");
        options2Items_01.add("佛山");
        options2Items_01.add("东莞");
        options2Items_01.add("阳江");
        options2Items_01.add("珠海");
        ArrayList<String> options2Items_02=new ArrayList<String>();
        options2Items_02.add("长沙");
        options2Items_02.add("岳阳");
        ArrayList<String> options2Items_03=new ArrayList<String>();
        options2Items_03.add("桂林");
        options2Items.add(options2Items_01);
        options2Items.add(options2Items_02);
        options2Items.add(options2Items_03);

        //选项3
        ArrayList<ArrayList<String>> options3Items_01 = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> options3Items_02 = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> options3Items_03 = new ArrayList<ArrayList<String>>();
        ArrayList<String> options3Items_01_01=new ArrayList<String>();
        options3Items_01_01.add("白云");
        options3Items_01_01.add("天河");
        options3Items_01_01.add("海珠");
        options3Items_01_01.add("越秀");
        options3Items_01.add(options3Items_01_01);
        ArrayList<String> options3Items_01_02=new ArrayList<String>();
        options3Items_01_02.add("南海");
        options3Items_01_02.add("高明");
        options3Items_01_02.add("顺德");
        options3Items_01_02.add("禅城");
        options3Items_01.add(options3Items_01_02);
        ArrayList<String> options3Items_01_03=new ArrayList<String>();
        options3Items_01_03.add("其他");
        options3Items_01_03.add("常平");
        options3Items_01_03.add("虎门");
        options3Items_01.add(options3Items_01_03);
        ArrayList<String> options3Items_01_04=new ArrayList<String>();
        options3Items_01_04.add("其他1");
        options3Items_01_04.add("其他2");
        options3Items_01_04.add("其他3");
        options3Items_01.add(options3Items_01_04);
        ArrayList<String> options3Items_01_05=new ArrayList<String>();
        options3Items_01_05.add("其他1");
        options3Items_01_05.add("其他2");
        options3Items_01_05.add("其他3");
        options3Items_01.add(options3Items_01_05);

        ArrayList<String> options3Items_02_01=new ArrayList<String>();
        options3Items_02_01.add("长沙长沙长沙长沙长沙长沙长沙长沙长沙1111111111");
        options3Items_02_01.add("长沙2");
        options3Items_02_01.add("长沙3");
        options3Items_02_01.add("长沙4");
        options3Items_02_01.add("长沙5");
        options3Items_02_01.add("长沙6");
        options3Items_02_01.add("长沙7");
        options3Items_02_01.add("长沙8");
        options3Items_02.add(options3Items_02_01);
        ArrayList<String> options3Items_02_02=new ArrayList<String>();
        options3Items_02_02.add("岳1");
        options3Items_02_02.add("岳2");
        options3Items_02_02.add("岳3");
        options3Items_02_02.add("岳4");
        options3Items_02_02.add("岳5");
        options3Items_02_02.add("岳6");
        options3Items_02_02.add("岳7");
        options3Items_02_02.add("岳8");
        options3Items_02_02.add("岳9");
        options3Items_02.add(options3Items_02_02);
        ArrayList<String> options3Items_03_01=new ArrayList<String>();
        options3Items_03_01.add("好山水");
        options3Items_03.add(options3Items_03_01);

        options3Items.add(options3Items_01);
        options3Items.add(options3Items_02);
        options3Items.add(options3Items_03);

        //三级联动效果
        pvOptions.setPicker(options1Items, options2Items, options3Items, true);
        //设置选择的三级单位
//        pwOptions.setLabels("省", "市", "区");
        pvOptions.setTitle("选择城市");
        pvOptions.setCyclic(false, true, true);
        pvOptions.setSelectOptions(1, 1, 1);
        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1)
                        + options2Items.get(options1).get(option2)
                        + options3Items.get(options1).get(option2).get(options3);

                m_btnAreaPicker.setText(tx);
            }
        });
    }

    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @OnClick(R.id.btn_recyclerview)
    public void onRecyclerViewTestClick() {
        Intent intent = new Intent(MainActivity.this, ThinkRecyclerViewActivity.class);
        this.startActivity(intent);
    }

    @OnClick(R.id.btn_custombar)
    public void onCustomBarClisk() {
        Intent intent = new Intent(MainActivity.this, CustomBarActivity.class);
        this.startActivity(intent);
    }

    @OnClick(R.id.btn_date_time)
    public void onDateTimeClick() {
        pvTime.show();
    }

    @OnClick(R.id.btn_area_picker)
    public void onAreaPickerClick() {
        pvOptions.show();
    }

    @OnClick(R.id.btn_guide_page)
    public void onGuidePageClick() {
        Intent intent = new Intent(MainActivity.this, GuideActivity.class);
        this.startActivity(intent);
    }

    @OnClick(R.id.btn_fragment_test)
    public void onFragmentTestClick() {
        Intent intent = new Intent(MainActivity.this, FragmentActivity.class);
        this.startActivity(intent);
    }

    @OnClick(R.id.btn_my_tags)
    public void onTagClick() {
        Intent intent = new Intent(MainActivity.this, FlowLayoutActivity.class);
        this.startActivity(intent);
    }

    @OnClick(R.id.btn_highlight)
    public void onHighlightClick() {
        Intent intent = new Intent(MainActivity.this, HighLightActivity.class);
        this.startActivity(intent);
    }

    @OnClick(R.id.btn_translucent_bar)
    public void onTranslucentBarClick() {
        Intent intent = new Intent(MainActivity.this, TranslucentActivity.class);
        this.startActivity(intent);
    }

    @OnClick(R.id.btn_update_statusbar_color)
    public void onUpdateStatusBarColorClick() {
        Intent intent = new Intent(MainActivity.this, UpdateStatusBarColorActivity.class);
        this.startActivity(intent);
    }

    @OnClick(R.id.btn_swipe_delete)
    public void onSwipeClick() {
        Intent intent = new Intent(MainActivity.this, SwipeDeleteActivity.class);
        this.startActivity(intent);
    }

    @OnClick(R.id.btn_shop)
    public void onShopClick() {
        Intent intent = new Intent(MainActivity.this, ShopActivity.class);
        this.startActivity(intent);
    }

    @OnClick(R.id.btn_scroll_activity)
    public void onScrollActivityClick() {
        Intent intent = new Intent(MainActivity.this, ScrollingActivity.class);
        this.startActivity(intent);
    }

    @OnClick(R.id.btn_group_button)
    public void onGroupButtonClick() {
        Intent intent = new Intent(MainActivity.this, GroupButtonActivity.class);
        this.startActivity(intent);
    }
}
