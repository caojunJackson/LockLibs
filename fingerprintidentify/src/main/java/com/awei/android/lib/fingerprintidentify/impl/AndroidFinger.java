package com.awei.android.lib.fingerprintidentify.impl;

import android.app.Activity;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;

import com.awei.android.lib.fingerprintidentify.base.BaseFingerprint;

/**
 * Created by tiger on 2017/3/9.
 */

public class AndroidFinger extends BaseFingerprint {


    private  FingerprintManager mFingerprintManager;
    private CancellationSignal mCancellationSignal;

    public AndroidFinger(Activity activity, FingerprintIdentifyExceptionListener
            exceptionListener) {
        super(activity, exceptionListener);

        try {
            mFingerprintManager = (FingerprintManager) activity.getSystemService(Context.FINGERPRINT_SERVICE);


            boolean hardwareDetected = mFingerprintManager.isHardwareDetected();
            boolean hasEnrolledFingerprints = mFingerprintManager.hasEnrolledFingerprints();
            setHardwareEnable(hardwareDetected);
            setRegisteredFinger(hasEnrolledFingerprints);
        }catch(Throwable e){
            onCatchException(e);
        }

    }

    @Override
    protected void doIdentify() {

        try {

            mCancellationSignal = new CancellationSignal();
            mFingerprintManager.authenticate(null, mCancellationSignal, 0, new FingerprintManager.AuthenticationCallback() {



                @Override
                public void onAuthenticationError(int errorCode, CharSequence errString) {
                    super.onAuthenticationError(errorCode, errString);
                    onFailed();
                }


                @Override
                public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult
                                                              result) {
                    super.onAuthenticationSucceeded(result);
                    onSucceed();
                }

                @Override
                public void onAuthenticationFailed() {
                    super.onAuthenticationFailed();
                    onNotMatch();

                }
            } , null);
        }catch (Throwable e){
            onCatchException(e);
            onFailed();
        }
    }

    @Override
    protected void doCancelIdentify() {
        try {
            if(mCancellationSignal != null){
                mCancellationSignal.cancel();
            }
        }catch (Throwable e){
            onCatchException(e);
        }


    }
}
