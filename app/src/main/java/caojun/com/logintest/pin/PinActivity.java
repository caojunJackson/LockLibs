package caojun.com.logintest.pin;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.awei.android.lib.fingerprintidentify.FingerprintIdentify;
import com.awei.android.lib.fingerprintidentify.base.BaseFingerprint;
import com.github.orangegangsters.lollipin.lib.PinCompatActivity;
import com.github.orangegangsters.lollipin.lib.managers.AppLock;

import caojun.com.logintest.R;

public class PinActivity extends PinCompatActivity {

    private TextView mTvInfo;
    private ScrollView mScrollView;
    private FingerprintIdentify mFingerprintIdentify;

    private boolean mNeedToRestartFingerprint = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);

        mTvInfo = (TextView) findViewById(R.id.mTvInfo);
        mScrollView = (ScrollView) findViewById(R.id.mScrollView);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("pin");
        setSupportActionBar(mToolbar);



        mFingerprintIdentify = new FingerprintIdentify(this, new BaseFingerprint.FingerprintIdentifyExceptionListener() {
            @Override
            public void onCatchException(Throwable exception) {

                exception.printStackTrace();
            }
        });

        tag("指纹硬件：" + mFingerprintIdentify.isHardwareEnable()); //硬件是否支持
        tag("已录指纹：" + mFingerprintIdentify.isRegisteredFinger());//是否已经录入指纹
        tag("指纹功能：" + mFingerprintIdentify.isFingerprintEnable());  //指纹设备可用,并且以录入指纹

    }

    public void start(View view) {
        mNeedToRestartFingerprint = true;
        tag("开始验证指纹，请放置你的手指到指纹传感器上");
        mFingerprintIdentify.startIdentify(3, new BaseFingerprint.FingerprintIdentifyListener() {
            @Override
            public void onSucceed() {
                tag("验证成功");
            }

            @Override
            public void onNotMatch(int availableTimes) {
                tag("指纹不匹配，可用次数剩余：" + availableTimes);
            }

            @Override
            public void onFailed() {
                tag("验证遇到错误！！！");
            }
        });
    }

    public void cancel(View view) {
        tag("取消验证");
        mFingerprintIdentify.cancelIdentify();
        mNeedToRestartFingerprint = false;
    }

    public void clear(View view) {
        mTvInfo.setText("");
    }

    private void tag(String msg) {
        mTvInfo.append(msg + "\n");
        mScrollView.fullScroll(View.FOCUS_DOWN);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mNeedToRestartFingerprint) {
            tag("onResume 恢复指纹验证流程");
            //恢复指纹识别并保证错误次数不变
            mFingerprintIdentify.resumeIdentify();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mNeedToRestartFingerprint) {
            tag("onPause 暂停指纹验证");
            mFingerprintIdentify.cancelIdentify();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mNeedToRestartFingerprint) {
            tag("onStop 暂停指纹验证");
            mFingerprintIdentify.cancelIdentify();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main , menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = new Intent(PinActivity.this , CustomPinActivity.class);
        switch (item.getItemId()) {
            case R.id.action_create_pin: //创建
                intent.putExtra(AppLock.EXTRA_TYPE , AppLock.ENABLE_PINLOCK);
                startActivityForResult(intent , CREATE_PIN);
                break;
            case R.id.action_reset_pin: //修改

                intent.putExtra(AppLock.EXTRA_TYPE , AppLock.CHANGE_PIN);
                startActivity(intent);
                break;
            case R.id.action_clear_pin: //清除

                intent.putExtra(AppLock.EXTRA_TYPE , AppLock.DISABLE_PINLOCK);
                startActivity(intent);
                break;

//            case R.id.action_disable_pin:
//
//                intent.putExtra(AppLock.EXTRA_TYPE , AppLock.DISABLE_PINLOCK );
//                startActivity(intent);
//                break;


//            case R.id.action_confirm_pin:
//                intent.putExtra(AppLock.EXTRA_TYPE , AppLock.CONFIRM_PIN );
//                startActivity(intent);
//                break;

            default:
                break;
        }
        //返回super和true效果不一样 , 返回true更好!!!!
        return true;
    }

    public static final int CREATE_PIN = 11;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CREATE_PIN:

                if(resultCode == RESULT_OK){
                    Snackbar.make(mScrollView , "密码设置成功" , Snackbar.LENGTH_LONG).show();
                }

                break;

            default:
                break;
        }


    }
}
