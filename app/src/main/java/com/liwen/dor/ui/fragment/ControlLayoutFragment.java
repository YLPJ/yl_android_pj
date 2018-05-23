package com.liwen.dor.ui.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.app.Fragment;
import com.liwen.dor.R;

import com.liwen.dor.ui.pwd.LocusPassWordView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * Created by ldn on 2018/5/3.
 */

public class ControlLayoutFragment extends Fragment implements AdapterView.OnItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static boolean isUnLock = false;

    private CameraFragment fragmentCamera;
    private LightFragment fragmentLight;
    private BedFragment fragmentBed;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    String[] sources = {"摄像头", "灯", "床"};

    @BindView(R.id.list_fContorlLayout_sources)
    ListView list_fContorlLayout_sources;

    @BindView(R.id.fragmentControlRoot)
    FrameLayout rootLayout;

    //初始化 摄像头控制布局
    @BindView(R.id.fragmentControl)
    public View frameHost;

    //---构造必须有---
    public ControlLayoutFragment() {
        // Required empty public constructor

        fragmentCamera = CameraFragment.newInstance("", "");
        fragmentLight = LightFragment.newInstance("", "");
        fragmentBed = BedFragment.newInstance("", "");
    }


    // TODO: Rename and change types and number of parameters
    public static ControlLayoutFragment newInstance(String param1, String param2) {
        ControlLayoutFragment fragment = new ControlLayoutFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View contentView = inflater.inflate(R.layout.fragment_control_layout, container, false);
        ButterKnife.bind(this, contentView);
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
        init();
        return contentView;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onStart() {
        super.onStart();
        if(isUnLock == false){
//            showPwdView();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void showPwdView(){
        final LocusPassWordView pwdView = new LocusPassWordView(getContext());
        pwdView.setBackground(new ColorDrawable(getResources().getColor(R.color.color_control_green)));
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(MATCH_PARENT,MATCH_PARENT);
        pwdView.setLayoutParams(params);
        pwdView.setOnCompleteListener(new LocusPassWordView.OnCompleteListener() {
            @Override
            public void onComplete(String password) {
                if(password.equals("12345")){
                    isUnLock = true;
                    rootLayout.removeView(pwdView);
                }
                else{
                    pwdView.markError();
                }
            }
        });
        rootLayout.addView(pwdView);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
    }

    private void init() {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this.getActivity(), R.layout.custom_list_item, sources);
        list_fContorlLayout_sources.setAdapter(adapter);
        list_fContorlLayout_sources.setOnItemClickListener(this);
    }

    /**
     * 前台触发事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEvent(ControlLayoutEvent event) {
        switch (event.code) {
            case ControlLayoutEvent.CODE_CHANGE_MONITOR:
//                doChangeMonitor(event.selectView);
            case ControlLayoutEvent.CODE_CHANGE_SCREEN:
//                doChangeScreen(event.selectView);
                break;


        }
    }

    /**
     * 左侧list点击事件
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Fragment tmpview = null;
        if (position <= 3) {
            switch (position) {
                case 0:
                    tmpview = fragmentCamera;
                    break;
                case 1:
                    tmpview = fragmentLight;
                    break;
                case 2:
                    tmpview = fragmentBed;
                    break;
            }
            if (tmpview == null)
                return;
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragmentControl, tmpview);
            transaction.commit();
        }
    }

    public static class ControlLayoutEvent {
        public static final int CODE_CHANGE_MONITOR = 0x11;
        public static final int CODE_CHANGE_SCREEN = 0x12;
        public int code;
        View selectView;

        public ControlLayoutEvent(int code, View selectView) {
            this.code = code;
            this.selectView = selectView;
        }
    }
}
