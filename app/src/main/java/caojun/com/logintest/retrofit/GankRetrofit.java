package caojun.com.logintest.retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by gaohailong on 2016/5/17.
 */
public class GankRetrofit {

    private static final String GANHUO_API = "http://gank.io/";
    private static Retrofit retrofit;

    public static Retrofit getRetrofit(){
        if (retrofit == null){
            synchronized (GankRetrofit.class){
                if (retrofit == null){

                    OkHttpClient okHttpClient = new OkHttpClient.Builder()
                            .connectTimeout(10 , TimeUnit.SECONDS)
                            .writeTimeout(30 , TimeUnit.SECONDS)
                            .readTimeout(30 , TimeUnit.SECONDS)
                            .build();

                    retrofit = new Retrofit.Builder()
                            .baseUrl(GANHUO_API)
                            .client(okHttpClient)
                            .addConverterFactory(GsonConverterFactory.create())
                            //配置RxJavaCall Adapter Factory 是一个知道如何将 call 实例转换成其
                            // 他类型的工厂类。目前，我们只有 RxJava 的类型，也就是将 Call 类型转
                            // 换成 Observable 类型
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .build();
                }
            }
        }
        return retrofit;
    }
}
