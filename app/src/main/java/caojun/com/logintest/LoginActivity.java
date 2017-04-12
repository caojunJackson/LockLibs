package caojun.com.logintest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by tiger on 2017/3/17.
 */

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.usernameWrapper)
    TextInputLayout mUsernameWrapper;
    @BindView(R.id.passwordWrapper)
    TextInputLayout mPasswordWrapper;
    @BindView(R.id.btn_login_login)
    Button mBtnLoginLogin;

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private static final String PHONE_PATTERN = "^((13[0-9])|(15[^4,\\\\D])|(18[0,5-9]))\\\\d{8}$";
    private Pattern mPattern = Pattern.compile(EMAIL_PATTERN);
    private Matcher mMatcher;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mUsernameWrapper.setHint("用户名");
        mPasswordWrapper.setHint("密码");

        mUsernameWrapper.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() < 6){
                    mUsernameWrapper.setError("username的length >= 6");
                }else{
                    mUsernameWrapper.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @OnClick(R.id.btn_login_login)
    public void onClick() {
        hideKeyboard();

        String username = mUsernameWrapper.getEditText().getText().toString();
        String password = mPasswordWrapper.getEditText().getText().toString();

        if(!vailedEmail(username)){
            mUsernameWrapper.setError("请输入email");
        }else if(!vailedPassword(password)){
            mPasswordWrapper.setError("密码长读>=6");
        }else{
            mUsernameWrapper.setErrorEnabled(false);
            mPasswordWrapper.setErrorEnabled(false);

            doLogin();
        }

    }

    /**
     * 登陆具体逻辑
     */
    private void doLogin() {
        Toast.makeText(getApplicationContext(), "login success", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(LoginActivity.this , SettingActivity.class));
    }

    private void hideKeyboard(){
        View view = getCurrentFocus();
        if(view != null){
            ((InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(view.getWindowToken() ,InputMethodManager.HIDE_NOT_ALWAYS );
        }
    }

    public boolean vailedEmail(String email){
        mMatcher = mPattern.matcher(email);
        return mMatcher.matches();
    }

    public boolean vailedPhone(String phone){
        mMatcher = mPattern.matcher(phone);
        return mMatcher.matches();
    }

    public boolean vailedPassword(String password){
        return password.length()>5;
    }
}
