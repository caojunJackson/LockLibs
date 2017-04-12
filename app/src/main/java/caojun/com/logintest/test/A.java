package caojun.com.logintest.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by tiger on 2017/3/22.
 */

public class A extends AppCompatActivity {


    private static final String TAG = "A";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate ");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy ");
    }

    protected void afinish(){
        super.finish();
        Log.e(TAG, "afinish ---"+this.getClass().getSimpleName());
    }
}
