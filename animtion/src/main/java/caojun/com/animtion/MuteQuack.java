package caojun.com.animtion;

import android.util.Log;

/**
 * Created by tiger on 2017/4/5.
 */

public class MuteQuack implements QuackBehavior{
    private static final String TAG = "MuteQuack";
  
    @Override
    public void quack() {
        Log.e(TAG, "MuteQuack");
    }
}
