package caojun.com.animtion;

import android.util.Log;

/**
 * Created by tiger on 2017/4/5.
 */

public class SlienceDuck extends Duck {
    private static final String TAG = "SlienceDuck";
    @Override
    public void display() {
        Log.e(TAG, "SlienceDuck ");
    }

    public SlienceDuck() {
        mQuackBehavior = new MuteQuack();
    }
}
