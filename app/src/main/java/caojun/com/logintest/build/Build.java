package caojun.com.logintest.build;

/**
 * Created by tiger on 2017/3/16.
 */

public abstract class Build {

    public abstract void wheel();
    public abstract void seat();
    public abstract void shell();

    protected Car car = new Car();  //!!!!protected

    public Car getCar(){        //!!!!
        return car;
    }

}
