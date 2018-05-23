package com.liwen.dor.ui.fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.google.gson.Gson;
import com.liwen.dor.R;

import com.liwen.dor.entity.Display;
import com.liwen.dor.entity.MultiScreen;
import com.liwen.dor.entity.Source;
import com.liwen.dor.entity.json.*;
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

    @BindView(R.id.lstMode)
    ListView lstMode;

    @BindView(R.id.gv_display)
    public GridLayout gv_display;
    @BindView(R.id.linearLayout_fDisplayLayout_splitScreen)
    public View mSplitScreenLl;

    //@BindView(R.id.gv_display)
    //private GridView gv_display;
//    @BindView(R.id.constraintLayout_fDisplayLayout_splitScreen)
//    ConstraintLayout mSplitScreenGrep;

    private Source mSelectSource;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private List<Source> mSources;

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

        createModeView();
        return contentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //加载信号源
        HttpClient.newInit().getAllSource().enqueue(new Callback() {
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
                    SourceResponse dr = gson.fromJson(response.body().string(), SourceResponse.class);
                    List<Source> ds = dr.getData();

                    EventBus.getDefault().post(new LoadSourceEvent(ds));


                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

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
                    List<Display> ds = dr.getData();

                    EventBus.getDefault().post(new LoadDisplayEvent(ds));


                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });


        //todo 加载多画面状态
        HttpClient.newInit().getMultiScreenState().enqueue(new Callback() {
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
                    MultiScreenResponse dr = gson.fromJson(response.body().string(), MultiScreenResponse.class);

                    MultiScreen screen = dr.getData();

                    EventBus.getDefault().post(new LoadMultiStateEvent(screen));


                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    /**
     * 前台加载信号源处理事件
     *
     * @param event
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoadEvent(LoadMultiStateEvent event) {
        if (event.screen == null)
            return;
        lstMode.setSelection(event.screen.getID() - 1);
    }

    /**
     * 前台加载信号源处理事件
     *
     * @param event
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoadEvent(LoadSourceEvent event) {

//        DisplayAdapter adapter = new DisplayAdapter(getContext(), R.layout.display_item, event._datas);
//        gv_display.setAdapter(adapter);
//        gv_display.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Button btn = (Button)view;
//                btn.setText("aaa");
//            }
//        });
        mSources = event._datas;

        ArrayAdapter<Source> adapter = new ArrayAdapter<Source>(this.getActivity(), R.layout.custom_list_item, mSources);
        mSourcesLv.setAdapter(adapter);
        mSourcesLv.setOnItemClickListener(this);
    }

    private void createModeView() {
        String[] modes = new String[]{"", "", "", "", "", "", ""};
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, modes);
        lstMode.setAdapter(new ArrayAdapter<String>(this.getActivity(), R.layout.custom_list_item, modes) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                ImageView img = new ImageView(getContext());

                Bitmap tmpBitmap;
                switch (position) {
                    case 0:
                        tmpBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.split_screen01);
                        break;
                    case 1:
                        tmpBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.split_screen02);
                        break;
                    case 2:
                        tmpBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.split_screen03);
                        break;
                    case 3:
                        tmpBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.split_screen04);
                        break;
                    case 4:
                        tmpBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.split_screen05);
                        break;
                    case 5:
                        tmpBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.split_screen06);
                        break;
                    default:
                        tmpBitmap = null;

                }


                img.setScaleType(ImageView.ScaleType.FIT_XY);
                img.setImageBitmap(tmpBitmap);
                return img;
            }
        });
        lstMode.setOnItemClickListener(new ListView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: {
                        SplitScreen1Fragment splitScreen1 = SplitScreen1Fragment.newInstance("", "");
                        switchSplitScreen(splitScreen1);
                    }
                    break;
                    case 1: {
                        SplitScreen2Fragment splitScreen2 = SplitScreen2Fragment.newInstance("", "");
                        switchSplitScreen(splitScreen2);
                    }
                    break;
                    case 2: {
                        SplitScreen3Fragment splitScreen3 = SplitScreen3Fragment.newInstance("", "");
                        switchSplitScreen(splitScreen3);
                    }
                    break;
                    case 3: {
                        SplitScreen4Fragment splitScreen4 = SplitScreen4Fragment.newInstance("", "");
                        switchSplitScreen(splitScreen4);
                    }
                    break;
                    case 4: {
                        SplitScreen5Fragment splitScreen5 = SplitScreen5Fragment.newInstance("", "");
                        switchSplitScreen(splitScreen5);
                    }
                    break;
                    case 5: {
                        SplitScreen6Fragment splitScreen6 = SplitScreen6Fragment.newInstance("", "");
                        switchSplitScreen(splitScreen6);
                    }
                    break;

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

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);

        for (int i = 0; i < event._datas.size(); i++) {
            Display d = event._datas.get(i);
            View view = inflater.inflate(R.layout.display_item, null);
            TextView textName = view.findViewById(R.id.display_item_name);
            textName.setText(d.getName());

            Button btnSource = view.findViewById(R.id.display_item_source);
            btnSource.setText(d.getCurrentSignalName());
            btnSource.setTag(d.getID());

            btnSource.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button btn = (Button) v;
                    btn.setText(mSelectSource.getName());
                    try {
                        int displayId = Integer.parseInt(btn.getTag().toString());
                        HttpClient.newInit().switchDisplay(displayId, mSelectSource.getID());
                    } catch (Exception ex) {

                    }

                }
            });

            GridLayout.Spec rowSpec = GridLayout.spec(i / 2, 1f);
            GridLayout.Spec columnSpec = GridLayout.spec(i % 2, 1f);
            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(rowSpec, columnSpec);
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
            ((Button) selectView).setText(mSelectSource.getName());

            HttpClient.newInit().switchModeSource(Integer.parseInt(selectView.getTag().toString()), mSelectSource.getID());
        }
    }

    /**
     * 4个显示器最终处理
     *
     * @param selectView
     */
    private void doChangeMonitor(View selectView) {
        if (null != mSelectSource) {
            ((Button) selectView).setText(mSelectSource.getName());
            String p = selectView.getTag().toString();
        }
    }


//    @OnClick({R.id.imageButton_fDisplayLayout_screen1,
//            R.id.imageButton_fDisplayLayout_screen2,
//            R.id.imageButton_fDisplayLayout_screen3,
//            R.id.imageButton_fDisplayLayout_screen4,
//            R.id.imageButton_fDisplayLayout_screen5,
//            R.id.imageButton_fDisplayLayout_screen6,})
//    public void imageButtonOnClick(View v) {
//
//
//    }


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
        mSelectSource = mSources.get(position);
    }


    public static class LoadDisplayEvent {
        public List<Display> _datas;

        public LoadDisplayEvent(List<Display> data) {
            _datas = data;
        }

    }

    public static class DisplayLayoutEvent {
        public static final int CODE_CHANGE_MONITOR = 0x11;
        public static final int CODE_CHANGE_SCREEN = 0x12;

        public int code;
        View selectView;
        int modeId;

        public DisplayLayoutEvent(int code, View selectView, int modeId) {
            this.code = code;
            this.selectView = selectView;
            this.modeId = modeId;
        }
    }

    public static class LoadSourceEvent {
        public List<Source> _datas;

        public LoadSourceEvent(List<Source> data) {
            _datas = data;
        }
    }

    public static class LoadMultiStateEvent {
        public MultiScreen screen;

        public LoadMultiStateEvent(MultiScreen screen) {
            this.screen = screen;
        }
    }
}
