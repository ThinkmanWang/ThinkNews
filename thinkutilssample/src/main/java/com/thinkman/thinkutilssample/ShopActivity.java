package com.thinkman.thinkutilssample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.thinkman.thinkutils.activity.BaseActivityWithActionBar;
import com.thinkman.thinkutils.view.NumberButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopActivity extends BaseActivityWithActionBar {

    @BindView(R.id.nb_number)
    NumberButton m_nbNumber = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        ButterKnife.bind(this);
        this.setActionBar(R.mipmap.ic_launcher, R.string.app_name, R.mipmap.ic_launcher);

        initView();
    }

    private void initView() {
        m_nbNumber.setBuyMax(1024)
                .setInventory(1024) //库存
                .setCurrentNumber(1)
                .setOnWarnListener(new NumberButton.OnWarnListener() {
                    @Override
                    public void onWarningForInventory(int inventory) {
                        Toast.makeText(ShopActivity.this, "当前库存:" + inventory, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onWarningForBuyMax(int buyMax) {
                        Toast.makeText(ShopActivity.this, "超过最大购买数:" + buyMax, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNumberChanged(int nNum) {
                        Toast.makeText(ShopActivity.this, "" + nNum, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
