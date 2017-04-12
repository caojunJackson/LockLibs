package caojun.com.animtion;

/**
 * Created by tiger on 2017/4/5.
 */

public abstract class Duck {

   protected QuackBehavior mQuackBehavior;

    public abstract void display();

    public void performQuack(){
        mQuackBehavior.quack();
    }

    public void setQuack(QuackBehavior quack){
        this.mQuackBehavior = quack;
    }

}
