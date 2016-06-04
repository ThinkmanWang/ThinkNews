package com.thinkman.thinkutils.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thinkman.thinkutils.R;

/**
 * Created by wangx on 2016/6/5.
 */
public class LoginView extends RelativeLayout {
    private Context mContext = null;
    private View contentView = null;

    private EditText m_etId = null;
    private PasswordView m_etPwd = null;
    private Button m_btnLogin = null;
    private TextView m_tvForgetPwd = null;
    private TextView m_tvRegister = null;

    public LoginView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public LoginView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public LoginView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        mContext = context;
        contentView = LayoutInflater.from(context).inflate(R.layout.layout_login, this, true);

        m_etId = (EditText) contentView.findViewById(R.id.et_id);
        m_etPwd = (PasswordView) contentView.findViewById(R.id.et_pwd);
        m_btnLogin = (Button) contentView.findViewById(R.id.btn_login);
        m_tvForgetPwd = (TextView) contentView.findViewById(R.id.tv_forget_pwd);
        m_tvRegister = (TextView) contentView.findViewById(R.id.tv_register);
    }

    public void setOnLoginClickListener(View.OnClickListener listener) {
        m_btnLogin.setOnClickListener(listener);
    }

    public void setOnForgetPwdClickListener(View.OnClickListener listener) {
        m_tvForgetPwd.setOnClickListener(listener);
    }

    public void setOnRegisterClickListener(View.OnClickListener listener) {
        m_tvRegister.setOnClickListener(listener);
    }

    public String getAccountId() {
        return m_etId.getText().toString();
    }

    public String getPassword() {
        return m_etPwd.getText().toString();
    }
}
