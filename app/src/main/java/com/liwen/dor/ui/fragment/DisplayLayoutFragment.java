package com.liwen.dor.ui.fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ToggleButton;

import com.liwen.dor.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 */
public class DisplayLayoutFragment extends Fragment implements AdapterView.OnItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @BindView(R.id.list_fDisplayLayout_sources)
    ListView mSourcesLv;
    @BindView(R.id.button_fDisplayLayout_monitor1)
    public View mMonitor1;
    @BindView(R.id.button_fDisplayLayout_monitor2)
    public View mMonitor2;
    @BindView(R.id.button_fDisplayLayout_monitor3)
    public View mMonitor3;
    @BindView(R.id.button_fDisplayLayout_monitor4)
    public View mMonitor4;

    @BindView(R.id.constraintLayout_fDisplayLayout_monitor)
    public View mMonitorCl;
    @BindView(R.id.linearLayout_fDisplayLayout_splitScreen)
    public View mSplitScreenLl;
//    @BindView(R.id.constraintLayout_fDisplayLayout_splitScreen)
//    ConstraintLayout mSplitScreenGrep;

    private String mSelectSource;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String[] sources = {"全景相机", "术野摄像机", "无影灯", "手术床", "吊塔"};


    //---构造必须-----
    public DisplayLayoutFragment() {
        // Required empty public constructor
    }

    //------------chao-----------------
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DisplayLayoutFragment.
     */

    // TODO: Rename and change types and number of parameters
    public static DisplayLayoutFragment newInstance(String param1, String param2) {
        DisplayLayoutFragment fragment = new DisplayLayoutFragment();
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
        View contentView = inflater.inflate(R.layout.fragment_display_layout, container, false);
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

    //-------------chao end----------------
    private void init() {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this.getActivity(), android.R.layout.simple_list_item_1, sources);
        mSourcesLv.setAdapter(adapter);
        mSourcesLv.setOnItemClickListener(this);
    }




    /**
     * 前台触发事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEvent(DisplayLayoutEvent event) {
        switch (event.code) {
            case DisplayLayoutEvent.CODE_CHANGE_MONITOR:
                doChangeMonitor(event.selectView);
            case DisplayLayoutEvent.CODE_CHANGE_SCREEN:
                doChangeScreen(event.selectView);
                break;


        }
    }

    @OnClick({R.id.button_fDisplayLayout_monitor1,
            R.id.button_fDisplayLayout_monitor2,
            R.id.button_fDisplayLayout_monitor3,
            R.id.button_fDisplayLayout_monitor4})
    public void buttonOnClick(View v) {
        EventBus.getDefault().post(new DisplayLayoutEvent(DisplayLayoutEvent.CODE_CHANGE_MONITOR, v));
    }

    /**
     * 分屏点击最终处理
     * @param selectView
     */
    private void doChangeScreen(View selectView) {
        if (null != mSelectSource) {
            ((Button) selectView).setText(mSelectSource);
        }
    }

    /**
     * 4个显示器最终处理
     * @param selectView
     */
    private void doChangeMonitor(View selectView) {
        if (null != mSelectSource) {
            ((Button) selectView).setText(mSelectSource);
            String p= selectView.getTag().toString();
        }
    }


    @OnClick({R.id.imageButton_fDisplayLayout_screen1,
            R.id.imageButton_fDisplayLayout_screen2,
            R.id.imageButton_fDisplayLayout_screen3,
            R.id.imageButton_fDisplayLayout_screen4,
            R.id.imageButton_fDisplayLayout_screen5,
            R.id.imageButton_fDisplayLayout_screen6,})
    public void imageButtonOnClick(View v) {
        switch (v.getId()) {
            case R.id.imageButton_fDisplayLayout_screen1: {
                SplitScreen1Fragment splitScreen1 = SplitScreen1Fragment.newInstance("", "");
                switchSplitScreen(splitScreen1);
            }
            break;
            case R.id.imageButton_fDisplayLayout_screen2: {
                SplitScreen2Fragment splitScreen2 = SplitScreen2Fragment.newInstance("", "");
                switchSplitScreen(splitScreen2);
            }
            break;
            case R.id.imageButton_fDisplayLayout_screen3: {
                SplitScreen3Fragment splitScreen3 = SplitScreen3Fragment.newInstance("", "");
                switchSplitScreen(splitScreen3);
            }
            break;
            case R.id.imageButton_fDisplayLayout_screen4: {
                SplitScreen4Fragment splitScreen4 = SplitScreen4Fragment.newInstance("", "");
                switchSplitScreen(splitScreen4);
            }
            break;
            case R.id.imageButton_fDisplayLayout_screen5: {
                SplitScreen5Fragment splitScreen5 = SplitScreen5Fragment.newInstance("", "");
                switchSplitScreen(splitScreen5);
            }
            break;
            case R.id.imageButton_fDisplayLayout_screen6: {
                SplitScreen6Fragment splitScreen6 = SplitScreen6Fragment.newInstance("", "");
                switchSplitScreen(splitScreen6);
            }
            break;

        }

    }


    @OnCheckedChanged(R.id.toggleButton_displayLayout_switchSettings)
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked){
            mMonitorCl.setVisibility(View.VISIBLE);
            mSplitScreenLl.setVisibility(View.GONE);
        }else {
            mMonitorCl.setVisibility(View.GONE);
            mSplitScreenLl.setVisibility(View.VISIBLE);
        }
    }

    private void switchSplitScreen(Fragment f) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.constraintLayout_fDisplayLayout_splitScreen, f);
        transaction.commit();
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
        mSelectSource = sources[position];
    }


    public static class DisplayLayoutEvent {
        public static final int CODE_CHANGE_MONITOR = 0x11;
        public static final int CODE_CHANGE_SCREEN = 0x12;
        public int code;
        View selectView;

        public DisplayLayoutEvent(int code, View selectView) {
            this.code = code;
            this.selectView = selectView;
        }
    }
}
