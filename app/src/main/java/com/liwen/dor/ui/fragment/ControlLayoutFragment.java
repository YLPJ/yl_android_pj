package com.liwen.dor.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.liwen.dor.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ldn on 2018/5/3.
 */

public class ControlLayoutFragment extends Fragment implements AdapterView.OnItemClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    String[] sources = {"摄像头", "灯", "床", "塔"};

    @BindView(R.id.list_fContorlLayout_sources)
    ListView list_fContorlLayout_sources;

    //初始化 摄像头控制布局
    @BindView(R.id.constraintLayout_control_Layout_camera)
    public View constraintLayout_control_Layout_camera;
    //初始化 灯控制布局
    @BindView(R.id.constraintLayout_control_Layout_light)
    public View constraintLayout_control_Layout_light;
    //初始化 床控制布局
    @BindView(R.id.constraintLayout_control_Layout_bed)
    public View constraintLayout_control_Layout_bed;
    //初始化 塔控制布局
    @BindView(R.id.constraintLayout_control_Layout_tower)
    public View constraintLayout_control_Layout_tower;


    //---构造必须有---
    public ControlLayoutFragment() {
        // Required empty public constructor
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
    }

    private void init() {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this.getActivity(), android.R.layout.simple_list_item_1, sources);
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
//        mSelectSource = sources[position];
        if(position<=3) {
            switch (position) {
                case 0:
                    constraintLayout_control_Layout_camera.setVisibility(View.VISIBLE);
                    constraintLayout_control_Layout_light.setVisibility(View.GONE);
                    constraintLayout_control_Layout_bed.setVisibility(View.GONE);
                    constraintLayout_control_Layout_tower.setVisibility(View.GONE);
                    break;
                case 1:
                    constraintLayout_control_Layout_camera.setVisibility(View.GONE);
                    constraintLayout_control_Layout_light.setVisibility(View.VISIBLE);
                    constraintLayout_control_Layout_bed.setVisibility(View.GONE);
                    constraintLayout_control_Layout_tower.setVisibility(View.GONE);
                    break;
                case 2:
                    constraintLayout_control_Layout_camera.setVisibility(View.GONE);
                    constraintLayout_control_Layout_light.setVisibility(View.GONE);
                    constraintLayout_control_Layout_bed.setVisibility(View.VISIBLE);
                    constraintLayout_control_Layout_tower.setVisibility(View.GONE);
                    break;
                case 3:
                    constraintLayout_control_Layout_camera.setVisibility(View.GONE);
                    constraintLayout_control_Layout_light.setVisibility(View.GONE);
                    constraintLayout_control_Layout_bed.setVisibility(View.GONE);
                    constraintLayout_control_Layout_tower.setVisibility(View.VISIBLE);
                    break;
            }
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