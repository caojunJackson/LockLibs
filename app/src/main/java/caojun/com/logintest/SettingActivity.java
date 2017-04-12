package caojun.com.logintest;

/**
 * Created by tiger on 2017/3/17.
 */

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.orangegangsters.lollipin.lib.PinCompatActivity;
import com.github.orangegangsters.lollipin.lib.managers.AppLock;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import caojun.com.logintest.face.FaceActivity;
import caojun.com.logintest.finger.FingerActivity;
import caojun.com.logintest.model.GanHuo;
import caojun.com.logintest.pin.CustomPinActivity;
import caojun.com.logintest.pin.SetPinActivity;
import caojun.com.logintest.retrofit.GankRetrofit;
import caojun.com.logintest.retrofit.GankService;
import caojun.com.logintest.signature.SignaMainActivity;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by tiger on 2017/3/14.
 */

public class SettingActivity extends PinCompatActivity {

    @BindView(R.id.main_viewpager)
    RollPagerView mMainViewpager;
    @BindView(R.id.main_toolbar)
    Toolbar mMainToolbar;
    @BindView(R.id.collapsing)
    CollapsingToolbarLayout mCollapsing;
    @BindView(R.id.appbar)
    AppBarLayout mAppbar;
    @BindView(R.id.main_face)
    TextView mMainFace;
    @BindView(R.id.main_finger)
    TextView mMainFinger;
    @BindView(R.id.main_gesture)
    TextView mMainGesture;
    @BindView(R.id.main_signature)
    TextView mMainSignature;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.nestedscrollview)
    NestedScrollView mNestedscrollview;


    private List<String> urls = new ArrayList<>();
    private int count = 6;

    private static final String TAG = "SettingActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        Log.e(TAG , "onCreate()");

        setSupportActionBar(mMainToolbar);
        mCollapsing.setTitle("main");

        init();

    }

    private void init() {
        if (!urls.isEmpty()) {
            urls.clear();
        }

        GankRetrofit.getRetrofit()
                .create(GankService.class)
                .getGanHuo("福利", count, 1)
                //RxJava函数式编程
                //访问网络切换异步线程
                .subscribeOn(Schedulers.io())////指定在非UI线程访问,执行(被观察）事件 //访问网络切换异步线程
                //响应结果处理切换成主线程
                .observeOn(AndroidSchedulers.mainThread())////响应结果处理切换成主线程
                .subscribe(new Observer<GanHuo>() {  //观察者(回调)执行
                    @Override
                    public void onCompleted() {
                        //请求结束回调
                        Log.e("666", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        //错误回调

                    }

                    @Override
                    public void onNext(GanHuo ganHuo) {
                        //成功结果返回


                        for (int i = 0; i < count; i++) {
                            String url = ganHuo.getResults().get(i).getUrl();

                            Log.e("Retrofit urls :" , url);
                            urls.add(url);
                        }

                        initViewPager();
                    }
                });

    }

    private void initViewPager() {
        mMainViewpager.setHintView(new ColorPointHintView(this , Color.RED , Color.DKGRAY));
        mMainViewpager.setPadding(0,0,0,15);
        mMainViewpager.setPlayDelay(3000);
        mMainViewpager.setAdapter(new StaticPagerAdapter() {
            @Override
            public View getView(ViewGroup container, int position) {
                ImageView iv = new ImageView(getApplicationContext());
                iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                iv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.MATCH_PARENT));

                Glide.with(getApplicationContext())
                        .load(urls.get(position))
                        .placeholder(R.drawable.wall_picture)
                        .error(R.drawable.icon)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(iv);


                return iv;
            }

            @Override
            public int getCount() {
                return urls.size();
            }
        });
    }

    @OnClick({R.id.main_face, R.id.main_finger, R.id.main_gesture, R.id.main_signature, R.id.fab})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_face:
                startActivity(new Intent(SettingActivity.this , FaceActivity.class));
                break;
            case R.id.main_finger:
                startActivity(new Intent(SettingActivity.this , FingerActivity.class));

                break;
            case R.id.main_gesture:
                startActivity(new Intent(SettingActivity.this , SetPinActivity.class));
                break;
            case R.id.main_signature:

                startActivity(new Intent(SettingActivity.this , SignaMainActivity.class));

                break;
            case R.id.fab:
                Snackbar.make(mNestedscrollview  , " 确定验证密码吗? " , Snackbar.LENGTH_LONG).setAction("YES", new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        //开启验证
                        Intent intent = new Intent(SettingActivity.this, CustomPinActivity.class);
                        intent.putExtra(AppLock.EXTRA_TYPE , AppLock.UNLOCK_PIN);
                        startActivity(intent);

                    }
                }).show();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG , "onDestroy()");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.e(TAG , "onConfigurationChanged()");

    }
}
