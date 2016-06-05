package com.thinkman.thinkutils.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
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

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.LoginView, 0, 0);
//        int n = a.getIndexCount();
//        for (int i = 0; i < n; i++) {
//            int attr = a.getIndex(i);
//            if (R.styleable.LoginView_loginBtnBDRes == attr) {
//
//            } else if (R.styleable.LoginView_accountLabel == attr) {
//
//            }
//        }

        try {
            Drawable bg = a.getDrawable(R.styleable.LoginView_loginBtnBDRes);
            if (null != bg) {
                setLoginBtnBG(bg);
            }

            String loginBtnLabel = a.getString(R.styleable.LoginView_loginBtnLabel);
            if (null != loginBtnLabel && false == TextUtils.isEmpty(loginBtnLabel)) {
                setLoginBtnLabel(loginBtnLabel);
            }

            String szLabelAccount = a.getString(R.styleable.LoginView_accountLabel);
            if (null != szLabelAccount && false == TextUtils.isEmpty(szLabelAccount)) {
                setAccountLabel(szLabelAccount);
            }

            String accountHint = a.getString(R.styleable.LoginView_accountHint);
            if (null != accountHint && false == TextUtils.isEmpty(accountHint)) {
                setAccountHint(accountHint);
            }

            String passwordText = a.getString(R.styleable.LoginView_passwordText);
            if (null != passwordText && false == TextUtils.isEmpty(passwordText)) {
                setPasswordLabel(passwordText);
            }

            String passwordHint = a.getString(R.styleable.LoginView_passwordHint);
            if (null != passwordHint && false == TextUtils.isEmpty(passwordHint)) {
                setPasswordHint(passwordHint);
            }

            String forgetPwdLabel = a.getString(R.styleable.LoginView_forgetPwdLabel);
            if (null != forgetPwdLabel && false == TextUtils.isEmpty(forgetPwdLabel)) {
                setForgetPwdLabel(forgetPwdLabel);
            }

            String registerLabel = a.getString(R.styleable.LoginView_registerLabel);
            if (null != registerLabel && false == TextUtils.isEmpty(registerLabel)) {
                setRegisterLabel(registerLabel);
            }
        } finally {
            a.recycle();
        }
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

    public void setLoginButtonBackgroundResource(int nResId) {
        m_btnLogin.setBackgroundResource(nResId);
    }

    public void setLoginBtnBG(Drawable drawable) {
        m_btnLogin.setBackground(drawable);
    }

    public void setLoginBtnLabel(String szLabel) {
        m_btnLogin.setText(szLabel);
    }

    public void setAccountLabel(String szLabel) {
        ((TextView)contentView.findViewById(R.id.tv_id)).setText(szLabel);
    }

    public void setAccountHint(String szHint) {
        m_etId.setHint(szHint);
    }

    public void setPasswordLabel(String szLabel) {
        ((TextView)contentView.findViewById(R.id.tv_pwd)).setText(szLabel);
    }

    public void setPasswordHint(String szHint) {
        m_etPwd.setHint(szHint);
    }

    public void setForgetPwdLabel(String szLabel) {
        m_tvForgetPwd.setText(szLabel);
    }

    public void setRegisterLabel(String szLabel) {
        m_tvRegister.setText(szLabel);
    }
}
