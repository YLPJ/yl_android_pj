package com.liwen.dor.entity;

import com.google.gson.Gson;
import com.liwen.dor.entity.json.BaseRequest;
import com.liwen.dor.entity.json.BaseResponse;
import com.liwen.dor.entity.json.CommonResponse;
import com.liwen.dor.ui.LoginActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

public class DorCallBack<T extends BaseResponse> implements Callback {

    private BaseEvent event;
    @Override
    public void onFailure(Call call, IOException e) {
        //EventBus.getDefault().post(E.(-1, "服务器连接失败"));
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {

    }
}
