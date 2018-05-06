package com.liwen.dor.ui.fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.google.gson.Gson;
import com.liwen.dor.R;

import com.liwen.dor.entity.BaseEvent;
import com.liwen.dor.entity.Display;
import com.liwen.dor.entity.json.CommonResponse;
import com.liwen.dor.entity.json.DisplayResponse;
import com.liwen.dor.ui.DisplayAdapter;
import com.liwen.dor.ui.LoginActivity;
import com.liwen.dor.ui.MainActivity;
import com.liwen.dor.util.HttpClient;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

import java.io.IOException;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 */
public class DisplayLayoutFragment extends Fragment implements AdapterView.OnItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @BindView(R.id.list_fDisplayLayout_sources)
    ListView mSourcesLv;

    @BindView(R.id.gv_display)
    public GridLayout gv_display;
    @BindView(R.id.linearLayout_fDisplayLayout_splitScreen)
    public View mSplitScreenLl;

    //@BindView(R.id.gv_display)
    //private GridView gv_display;
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //加载显示器
        HttpClient.newInit().getAllDisplay().enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //http访问异常
                //EventBus.getDefault().post(new LoginActivity.LoginActivityEvent(-1, "服务器连接失败"));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //http访问有返回
                try {

                    Gson gson = new Gson();
                    DisplayResponse dr = gson.fromJson(response.body().string(), DisplayResponse.class);

                    String[] from = {"Name", "CurrentSignalName"};
                    //int[] to = {R.id.display_item_name, R.id.display_item_source};
                    List<Display> ds = dr.getData();

                    EventBus.getDefault().post(new LoadDisplayEvent(ds));


                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

    }
    /**
     * 前台处理事件
     *
     * @param event
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoadEvent(LoadDisplayEvent event) {
//        DisplayAdapter adapter = new DisplayAdapter(getContext(), R.layout.display_item, event._datas);
//        gv_display.setAdapter(adapter);
//        gv_display.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Button btn = (Button)view;
//                btn.setText("aaa");
//            }
//        });
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);

        for(int i = 0;i< event._datas.size();i++){
            Display d = event._datas.get(i);
            View view = inflater.inflate(R.layout.display_item, null);
            TextView textName = view.findViewById(R.id.display_item_name);
            textName.setText(d.getName());

            Button btnSource = view.findViewById(R.id.display_item_source);
            btnSource.setText(d.getCurrentSignalName());

            gv_display.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            GridLayout.Spec rowSpec = GridLayout.spec(i / 2, 1f);
            GridLayout.Spec columnSpec = GridLayout.spec(i % 2, 1f);
            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(rowSpec,columnSpec);
            layoutParams.height = 0;
            layoutParams.width = 0;
            view.setLayoutParams(layoutParams);
            gv_display.addView(view);
        }


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

//    @OnClick({R.id.button_fDisplayLayout_monitor1,
////            R.id.button_fDisplayLayout_monitor2,
////            R.id.button_fDisplayLayout_monitor3,
////            R.id.button_fDisplayLayout_monitor4})
////    public void buttonOnClick(View v) {
////        EventBus.getDefault().post(new DisplayLayoutEvent(DisplayLayoutEvent.CODE_CHANGE_MONITOR, v));
////    }

    /**
     * 分屏点击最终处理
     *
     * @param selectView
     */
    private void doChangeScreen(View selectView) {
        if (null != mSelectSource) {
            ((Button) selectView).setText(mSelectSource);
        }
    }

    /**
     * 4个显示器最终处理
     *
     * @param selectView
     */
    private void doChangeMonitor(View selectView) {
        if (null != mSelectSource) {
            ((Button) selectView).setText(mSelectSource);
            String p = selectView.getTag().toString();
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
        if (isChecked) {
            gv_display.setVisibility(View.VISIBLE);
            mSplitScreenLl.setVisibility(View.GONE);
        } else {
            gv_display.setVisibility(View.GONE);
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


    public static class LoadDisplayEvent{
        public List<Display> _datas;
        public LoadDisplayEvent(List<Display> data)        {
            _datas = data;
        }

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
