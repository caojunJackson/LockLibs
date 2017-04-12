package caojun.com.logintest.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import caojun.com.logintest.R;

/**
 * Created by tiger on 2017/3/22.
 */

public class B extends A {
    private static final String TAG = "B";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate ");

        setContentView(R.layout.activity_test);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy ");


    }

    public void click(View v){
        afinish();
    }

}
