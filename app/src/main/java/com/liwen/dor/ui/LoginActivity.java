package com.liwen.dor.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.liwen.dor.R;
import com.liwen.dor.util.HttpClient;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity  {

    @BindView(R.id.userName_login)
    EditText mUserNameEt;
    @BindView(R.id.password_login)
    EditText mPasswordEt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
        init();
    }

    private void init(){

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
    }

    /**
     * 前台处理事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEvent(LoginActivityEvent event) {
        switch (event.code) {
            case LoginActivityEvent.DO_SHOW_ERROR:
                aaa(event.massage);
                break;
        }
    }
    @OnClick({R.id.button_login_sign,
    R.id.button_login_exit})
    public void buttonOnClick(View v){
        switch (v.getId()){
            case R.id.button_login_sign:{
                doMainActivity();
            }
                break;
            case R.id.button_login_exit:
                getLoginExit();
                break;
        }
    }

    private void doMainActivity(){
        startActivity(new Intent(this,MainActivity.class));
    }

    private void getLoginExit(){
        String x="xxxx";
        String y ="yyyy";
        HttpClient.newInit().getAllDisplay().enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //http访问异常
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //http访问有返回
                try {
//                    doLoginTelData(response);
                    EventBus.getDefault().post(new LoginActivityEvent(LoginActivityEvent.DO_SHOW_ERROR, String.valueOf(response.body().string())));
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
    }
    private void aaa(String message){

        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    /**
     * 事件
     */
    private static class LoginActivityEvent {
        /**
         * 参数判断完成,开始访问后台
         */
        private static final int DO_UPDATE_CONFIRM = 0X01;
        private static final int DO_UPDATE_UI = 0X02;
        private static final int DO_LOGIN_YZ = 0X03;
        private static final int DO_SHOW_ERROR = 0X04;
        final int code;
        String massage;
        String userTel;
        String userPassWord;

        LoginActivityEvent(int code) {
            this.code = code;
        }

        LoginActivityEvent(int code, String massage) {
            this.code = code;
            this.massage = massage;
        }

        LoginActivityEvent(int code, String userTel, String userPassWord) {
            this.code = code;
            this.userTel = userTel;
            this.userPassWord = userPassWord;
        }

    }
}

