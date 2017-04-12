package caojun.com.logintest.face;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.isnc.facesdk.SuperID;
import com.isnc.facesdk.common.Cache;
import com.isnc.facesdk.common.SDKConfig;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import caojun.com.logintest.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by tiger on 2017/3/14.
 */

public class FaceActivity extends AppCompatActivity {
    @BindView(R.id.btn_login_face)
    Button mBtnLoginFace;
    @BindView(R.id.btn_cancel_login)
    Button mBtnCancelLogin;
    @BindView(R.id.btn_check_author)
    Button mBtnCheckAuthor;
    @BindView(R.id.btn_unregister_face)
    Button mBtnUnregisterFace;
    @BindView(R.id.btn_Verify_face)
    Button mBtnVerifyFace;
    @BindView(R.id.btn_register_face)
    Button mBtnRegisterFace;
    @BindView(R.id.face_avator)
    CircleImageView mFaceAvator;
    @BindView(R.id.face_name)
    TextView mFaceName;
    @BindView(R.id.face_phone)
    TextView mFacePhone;
    @BindView(R.id.face_gender)
    TextView mFaceGender;
    @BindView(R.id.face_tags)
    TextView mFaceTags;
    @BindView(R.id.btn_update_face)
    Button mBtnUpdateFace;


    private Context context = this;
    private String mOpenid;
    private String mUserInfo;


    private static final String TAG = "FaceActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_face_login);
        ButterKnife.bind(this);


    }


    /***
     * 验证
     */
    public void checkAuthorized() {

        SuperID.isOpenIDAuthorized(this, mOpenid, new SuperID.IntSuccessCallback() {
                    @Override
                    public void onSuccess(int result) {
                        switch (result) {
                            case SDKConfig.ISAUTHORIZED:
                                Toast.makeText(context, "已授权", Toast.LENGTH_SHORT).show();

                                break;
                            case SDKConfig.NOAUTHORIZED:
                                Toast.makeText(context, "未授权", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                }, new SuperID.IntFailCallback() {
                    @Override
                    public void onFail(int error) {
                        switch (error) {
                            case SDKConfig.APPTOKENERROR:
                                Toast.makeText(context, "app token 过期", Toast.LENGTH_SHORT).show();
                                break;
                            case SDKConfig.OTHER_ERROR:
                                Toast.makeText(context, "网络错误", Toast.LENGTH_SHORT).show();
                                break;

                            default:
                                break;
                        }
                    }
                }


        );

        }


        @Override
        protected void onActivityResult ( int requestCode, int resultCode, Intent data){
            super.onActivityResult(requestCode, resultCode, data);
            switch (resultCode) {
                case SDKConfig.AUTH_SUCCESS:
                    // openid 为开发者应用生成的openid，若调用faceLogin(Context context)进行注册授权，则系统将会自动生成一个openid
                    mOpenid = Cache.getCached(context, SDKConfig.KEY_OPENID);
                    //userInfo 为SuperID用户信息，格式为json
                    mUserInfo = Cache.getCached(context, SDKConfig.KEY_APPINFO);
                    Toast.makeText(context, "授权成功", Toast.LENGTH_SHORT).show();


                    Log.e(TAG, "mOpenid: " + mOpenid);
                    Log.e(TAG, "mUserInfo: " + mUserInfo);

                    break;
                case SDKConfig.LOGINSUCCESS:


                    // openid 为开发者应用的openid，若用户在调用faceLogin(Context context)
                    // 进行注册授权，则系统将会自动生成一个openid，重新登录成功时返回此openid
                    //一登账号的 openid , 此 id 为授权用户唯一标识。开发者如需将此参数与自身应用的用户id统一
                    mOpenid = Cache.getCached(context, SDKConfig.KEY_OPENID);
                    //userInfo 为SuperID用户信息，格式为json
                    mUserInfo = Cache.getCached(context, SDKConfig.KEY_APPINFO);
                    Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show();

                    Log.e(TAG, "mOpenid: " + mOpenid);
                    Log.e(TAG, "mUserInfo: " + mUserInfo);

                    initFace();

                    break;


                case SDKConfig.VERIFY_SUCCESS:
                    Toast.makeText(context, "验证成功", Toast.LENGTH_SHORT).show();
                    break;

                case SDKConfig.VERIFY_FAIL:
                    Toast.makeText(context, "验证失败", Toast.LENGTH_SHORT).show();
                    break;


                default:
                    Toast.makeText(context, "登录失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    private void initFace() {
        Gson gson = new Gson();
        UserInfo userInfo = gson.fromJson(mUserInfo, UserInfo.class);



        mFaceName.setText(userInfo.getName());
        mFaceGender.setText(userInfo.getPersona().getGender());
        mFacePhone.setText(userInfo.getPhone());
        mFaceTags.setText(userInfo.getPersona().getTags().toString());

        Glide.with(this)
                .load(userInfo.getAvatar())
                .error(R.drawable.wall_picture)
                .into(mFaceAvator);
    }

    /**
     * 解绑
     */
    private void cancelAuthorization() {

        SuperID.userCancelAuthorization(context, new SuperID.IntSuccessCallback() {
                    @Override
                    public void onSuccess(int result) {
                        Snackbar.make(mBtnUnregisterFace, "接触绑定success!", Snackbar.LENGTH_LONG)
                                .show();
                    }
                }
                , new SuperID.IntFailCallback() {
                    @Override
                    public void onFail(int arg0) {
                        // TODO 处理失败事件
                        //int arg0为返回错误类型，若需详细处理返回错误类型，则相应地错误码如下：
                        switch (arg0) {
                            case SDKConfig.ACCESSTOKEN_EXPIRED:
                                Toast.makeText(context, "解绑失败,access_token过期", Toast
                                        .LENGTH_SHORT).show();
                                break;
                            case SDKConfig.OTHER_ERROR:
                                Toast.makeText(context, "解绑失败,网络连接错误", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });
    }

    @OnClick({R.id.btn_login_face, R.id.btn_cancel_login, R.id.btn_check_author, R.id
            .btn_unregister_face, R.id.btn_Verify_face, R.id.btn_register_face, R.id
            .btn_update_face})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login_face:
                SuperID.faceLogin(this);
                break;
            case R.id.btn_cancel_login:
                //不会解除一登账户与三方应用的授权绑定状态，仅用于清除当前一登账户的凭证信息
                SuperID.faceLogout(context);
                break;
            case R.id.btn_check_author:
                checkAuthorized();
                break;
            case R.id.btn_unregister_face:
                cancelAuthorization();
                break;
            case R.id.btn_Verify_face:
                SuperID.faceVerify(FaceActivity.this, 3);


            case R.id.btn_register_face:
                SuperID.faceLoginWithPhoneUid(FaceActivity.this, "18621309475", mUserInfo, "", new SuperID.SoLoaderCallback() {

                    @Override
                    public void soLoader() {
                            Snackbar.make(mFaceAvator , "soLoader 快速授权" , Snackbar.LENGTH_LONG).show();
                    }
                });
                break;

            case R.id.btn_update_face:
                updateAppInfo();
                break;

        }
    }

    private void updateAppInfo() {

        SuperID.updateAppInfo(context, mUserInfo, new SuperID.IntSuccessCallback() {
            @Override
            public void onSuccess(int result) {
                Toast.makeText(context, "更新成功", Toast.LENGTH_SHORT).show();
            }
        }, new SuperID.IntFailCallback() {
            @Override
            public void onFail(int error) {
                switch (error) {
                    case SDKConfig.ACCESSTOKEN_EXPIRED:
                        Toast.makeText(context, "更新失败,授权已过期", Toast.LENGTH_SHORT).show();
                        break;
                    case SDKConfig.OTHER_ERROR:
                        Toast.makeText(context, "更新失败,网络连接错误", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });
    }


}
