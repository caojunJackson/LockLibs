package caojun.com.logintest.build;

/**
 * Created by tiger on 2017/3/16.
 */

public class Director {
    private Build  mBuild;

    public Director(Build build) {
        mBuild = build;
    }

    public Car construct(){
        mBuild.seat();
        mBuild.wheel();
        mBuild.shell();

        return mBuild.getCar();
    }
}
