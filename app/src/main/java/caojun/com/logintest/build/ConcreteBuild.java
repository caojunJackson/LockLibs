package caojun.com.logintest.build;

import android.util.Log;

/**
 * Created by tiger on 2017/3/16.
 */

public class ConcreteBuild extends Build {
    private static final String TAG = "ConcreteBuild";
    
    @Override
    public void wheel() {
        Log.e(TAG , " create wheel");

    }

    @Override
    public void seat() {
        Log.e(TAG , " create seat");
    }

    @Override
    public void shell() {
        Log.e(TAG , " create seat");
    }
}
