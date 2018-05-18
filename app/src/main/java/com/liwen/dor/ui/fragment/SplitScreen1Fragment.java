package com.liwen.dor.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.liwen.dor.R;

import org.greenrobot.eventbus.EventBus;

import javax.security.auth.Destroyable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chenly on 2018/5/1.
 */

public class SplitScreen1Fragment extends Fragment{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    @BindView(R.id.button_fSplitScreen1_screen11)
    Button mscreen11;
    @BindView(R.id.button_fSplitScreen1_screen12)
    Button mscreen12;
    public static SplitScreen1Fragment newInstance(String param1, String param2) {
        SplitScreen1Fragment fragment = new SplitScreen1Fragment();
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
        View contentView= inflater.inflate(R.layout.fragment_split_screen1, container, false);
        ButterKnife.bind(this, contentView);
        mscreen11.setTag(mParam1);
        mscreen11.setTag(mParam2);
        return contentView;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.button_fSplitScreen1_screen11,
            R.id.button_fSplitScreen1_screen12})
    public void buttonOnClick(View v){
        EventBus.getDefault().post(new DisplayLayoutFragment.DisplayLayoutEvent(DisplayLayoutFragment.DisplayLayoutEvent.CODE_CHANGE_SCREEN, v, 1));
    }
}
