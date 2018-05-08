package com.liwen.dor.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.liwen.dor.R;
import com.liwen.dor.constant.SPConstant;
import com.liwen.dor.util.SpUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ldn on 2018/5/8.
 */

public class IPconfigActivity extends Activity {

    @BindView(R.id.editText_ipconfig)
    EditText editText_ipconfig;

    @BindView(R.id.button_ipconfig)
    Button button_ipconfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_netseting);
        ButterKnife.bind(this);
        init();//初始化 业务
    }

    private void init()
    {
        //todo 光人！哪用本地保存的ip变量 就抄下面这句话就行
        String ip_text = SpUtil.getSP(SPConstant.IP_TEXT, String.class);//读取 本地保存的ip地址
        if (!"null".equals(ip_text)) {
            editText_ipconfig.setText(ip_text);//赋值给页面控件
        }

    }

    @OnClick({R.id.button_ipconfig})
    public void buttonOnClick(View v) {
        switch (v.getId()) {
            //点击配置按钮后，保存本地文件；弹出一个提示
            case R.id.button_ipconfig:
                String ip_text=editText_ipconfig.getText().toString();
                SpUtil.saveSP(SPConstant.IP_TEXT,ip_text);
                Toast.makeText(this, "配置完成", Toast.LENGTH_SHORT).show();
                break;

        }
    }



}
