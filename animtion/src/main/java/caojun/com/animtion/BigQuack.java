package caojun.com.animtion;

import android.util.Log;

/**
 * Created by tiger on 2017/4/5.
 */

public class BigQuack implements QuackBehavior {
    private static final String TAG = "BigQuack";
    @Override
    public void quack() {
        Log.e(TAG, "BigQuack ");
    }
}
