package com.liwen.dor.ui;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import com.google.gson.Gson;
import com.liwen.dor.R;
import com.liwen.dor.entity.BaseEvent;
import com.liwen.dor.entity.json.CommonResponse;
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


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

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

    private void init() {

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
        switch (event.getCode()) {
            case 0:
                //startActivity(new Intent(this, MainActivity.class));
                break;
            case LoginActivityEvent.DO_LOGIN_YZ:
                showMsg("用户名或密码错误");
                break;
            case LoginActivityEvent.DO_SHOW_ERROR:
                showMsg(event.getMassage());
                break;
        }
        startActivity(new Intent(this, MainActivity.class));
    }

    @OnClick({R.id.button_login_sign,
            R.id.button_login_exit})
    public void buttonOnClick(View v) {
        switch (v.getId()) {
            case R.id.button_login_sign: {
                doMainActivity();
            }
            break;
            case R.id.button_login_exit:
                break;
        }
    }

    private void doMainActivity() {
        HttpClient.newInit().login(mUserNameEt.getText().toString(), mPasswordEt.getText().toString()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //http访问异常
                EventBus.getDefault().post(new LoginActivityEvent(-1, "服务器连接失败"));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //http访问有返回
                try {

                    Gson gson = new Gson();
                    CommonResponse dr = gson.fromJson(response.body().string(), CommonResponse.class);
                    EventBus.getDefault().post(new LoginActivityEvent(dr.getErrorCode(), dr.getMessage()));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

    }

    private void showMsg(String message) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 事件
     */
    private static class LoginActivityEvent extends BaseEvent {
        /**
         * 参数判断完成,开始访问后台
         */
        private static final int DO_UPDATE_CONFIRM = 0X01;
        private static final int DO_UPDATE_UI = 0X02;
        private static final int DO_LOGIN_YZ = 0X03;
        private static final int DO_SHOW_ERROR = 0X04;

        LoginActivityEvent(int code) {
            setCode(code);
        }

        LoginActivityEvent(int code, String massage) {
            setCode(code);
            setMassage(massage);
        }


    }
}

