package caojun.com.animtion;

import android.util.Log;

/**
 * Created by tiger on 2017/4/5.
 */

public class HappyDuck extends  Duck{
    private static final String TAG = "HappyDuck";
    @Override
    public void display() {
        Log.e(TAG, "HappyDuck ");
    }

    public HappyDuck(){
        mQuackBehavior = new BigQuack();
    }
}
