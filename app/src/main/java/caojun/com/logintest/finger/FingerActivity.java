package caojun.com.logintest.finger;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.awei.android.lib.fingerprintidentify.FingerprintIdentify;
import com.awei.android.lib.fingerprintidentify.SwirlView;
import com.awei.android.lib.fingerprintidentify.base.BaseFingerprint;

import caojun.com.logintest.R;

public class FingerActivity extends AppCompatActivity {

    private TextView mTvInfo;
    private ScrollView mScrollView;
    private FingerprintIdentify mFingerprintIdentify;

    private boolean mNeedToRestartFingerprint = false;
    private SwirlView mSwirlView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger);

        mTvInfo = (TextView) findViewById(R.id.mTvInfo);
        mScrollView = (ScrollView) findViewById(R.id.mScrollView);
        mSwirlView = (SwirlView) findViewById(R.id.swirl);

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
        mSwirlView.setState(SwirlView.State.ON);
        mFingerprintIdentify.startIdentify(3, new BaseFingerprint.FingerprintIdentifyListener() {
            @Override
            public void onSucceed() {
                tag("验证成功");



                mSwirlView.setImageDrawable(getResources().getDrawable(R.drawable.swirl_success));

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                mSwirlView.setState(SwirlView.State.SUCCESS , false);
                                //                mSwirlView.setImageDrawable(getDrawable(R.drawable.ic_fingerprint_success));
                            }
                        });

                    }
                }, 1000);


            }

            @Override
            public void onNotMatch(int availableTimes) {
                tag("指纹不匹配，可用次数剩余：" + availableTimes);
                mSwirlView.setState(SwirlView.State.ERROR);
            }

            @Override
            public void onFailed() {
                tag("验证遇到错误！！！");
                mSwirlView.setState(SwirlView.State.ERROR);
            }
        });
    }



    public  Bitmap getAlphaBitmap(Bitmap mBitmap, int mColor) {
        //          BitmapDrawable mBitmapDrawable = (BitmapDrawable) mContext.getResources().getDrawable(R.drawable.enemy_infantry_ninja);
        //          Bitmap mBitmap = mBitmapDrawable.getBitmap();

        //BitmapDrawable的getIntrinsicWidth（）方法，Bitmap的getWidth（）方法
        //注意这两个方法的区别
        //Bitmap mAlphaBitmap = Bitmap.createBitmap(mBitmapDrawable.getIntrinsicWidth(), mBitmapDrawable.getIntrinsicHeight(), Config.ARGB_8888);
        Bitmap mAlphaBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap
                .Config.RGB_565);

        Canvas mCanvas = new Canvas(mAlphaBitmap);
        Paint mPaint = new Paint();

        mPaint.setColor(mColor);
        //从原位图中提取只包含alpha的位图
        Bitmap alphaBitmap = mBitmap.extractAlpha();
        //在画布上（mAlphaBitmap）绘制alpha位图
        mCanvas.drawBitmap(alphaBitmap, 0, 0, mPaint);

        return mAlphaBitmap;
    }
    public void cancel(View view) {
        tag("取消验证");
        mFingerprintIdentify.cancelIdentify();
        mSwirlView.setState(SwirlView.State.OFF );
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
}
