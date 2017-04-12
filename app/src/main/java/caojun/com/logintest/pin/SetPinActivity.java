package caojun.com.logintest.pin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.github.orangegangsters.lollipin.lib.managers.AppLock;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import caojun.com.logintest.R;

/**
 * 指纹解锁加数字
 * Created by tiger on 2017/3/14.
 */

public class SetPinActivity extends AppCompatActivity {

    @BindView(R.id.btn_pin_create)
    Button mBtnPinCreate;
    @BindView(R.id.btn_pin_update)
    Button mBtnPinUpdate;
    @BindView(R.id.btn_pin_disable)
    Button mBtnPinDisable;
    @BindView(R.id.btn_pin_unlock)
    Button mBtnPinUnlock;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_setpin);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_pin_create, R.id.btn_pin_update, R.id.btn_pin_disable, R.id.btn_pin_unlock})
    public void onClick(View view) {
        Intent intent = new Intent(SetPinActivity.this, CustomPinActivity.class);

        switch (view.getId()) {
            case R.id.btn_pin_create:
                intent.putExtra(AppLock.EXTRA_TYPE, AppLock.ENABLE_PINLOCK);
                startActivityForResult(intent , CREATE_PIN);

                break;
            case R.id.btn_pin_update:

                intent.putExtra(AppLock.EXTRA_TYPE, AppLock.CHANGE_PIN);
                startActivity(intent);
                break;
            case R.id.btn_pin_disable:
                intent.putExtra(AppLock.EXTRA_TYPE, AppLock.DISABLE_PINLOCK);
                startActivity(intent);
                break;
            case R.id.btn_pin_unlock:
                intent.putExtra(AppLock.EXTRA_TYPE , AppLock.UNLOCK_PIN);
                startActivity(intent);

                break;
        }

    }

    public static final int CREATE_PIN = 11;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CREATE_PIN:
            if(resultCode == RESULT_OK){
                Snackbar.make(mBtnPinCreate , "密码设置成功" , Snackbar.LENGTH_LONG).show();
            }

            default:
                break;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }
}
