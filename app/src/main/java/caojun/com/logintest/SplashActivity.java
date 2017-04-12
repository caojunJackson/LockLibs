package caojun.com.logintest;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.isnc.facesdk.SuperID;
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import java.util.ArrayList;
import java.util.List;

import caojun.com.logintest.model.GanHuo;
import caojun.com.logintest.retrofit.GankRetrofit;
import caojun.com.logintest.retrofit.GankService;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by tiger on 2017/3/14.
 */

public class SplashActivity extends AppCompatActivity {

    private RollPagerView mRollPagerView;
    private List<String> urls = new ArrayList<>();
    private int count = 3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SuperID.initFaceSDK(this);
        SuperID.setDebugMode(true);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_face_splash);

        init();

    }

    private void initViewPager() {
        mRollPagerView = (RollPagerView) findViewById(R.id.rollviewpager);

        mRollPagerView.setHintView(new ColorPointHintView(this, Color.YELLOW, Color.DKGRAY));
        mRollPagerView.setPadding(0,0,0, 30);

        mRollPagerView.setPlayDelay(3000);
        mRollPagerView.setAdapter(new StaticPagerAdapter() {
            @Override
            public View getView(ViewGroup container, int position) {
                ImageView iv = new ImageView(getApplicationContext());
                iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                iv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.MATCH_PARENT));

                Glide.with(getApplicationContext())
                        .load(urls.get(position))
                        .error(R.drawable.wall_picture)
                        .placeholder(R.drawable.wall_picture)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .centerCrop()
                        .into(iv);



                return iv;
            }

            @Override
            public int getCount() {
                return urls.size();
            }
        });

        mRollPagerView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                if (position == urls.size() - 1) {

                    startActivity(new Intent(SplashActivity.this , SettingActivity.class));
                    overridePendingTransition(R.anim.nothing , R.anim.slide_down);
                    finish();
                }
            }
        });
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
}
