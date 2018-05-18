package com.liwen.dor.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liwen.dor.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chenly on 2018/5/1.
 */

public class SplitScreen4Fragment extends Fragment{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public static SplitScreen4Fragment newInstance(String param1, String param2) {
        SplitScreen4Fragment fragment = new SplitScreen4Fragment();
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
        View contentView= inflater.inflate(R.layout.fragment_split_screen4, container, false);
        ButterKnife.bind(this, contentView);

        return contentView;
    }





    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.button_fSplitScreen1_screen41,
            R.id.button_fSplitScreen1_screen42,
            R.id.button_fSplitScreen1_screen43,
            R.id.button_fSplitScreen1_screen44})
    public void buttonOnClick(View v){
        EventBus.getDefault().post(new DisplayLayoutFragment.DisplayLayoutEvent(DisplayLayoutFragment.DisplayLayoutEvent.CODE_CHANGE_SCREEN, v, 4));
    }
}
