package com.liwen.dor.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.liwen.dor.R;
import com.liwen.dor.ui.IPconfigActivity;
import com.liwen.dor.ui.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * to handle interaction events.
 * Use the {@link LightFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LightFragment extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //====灯A的 五个亮度控件==begin==
    @BindView(R.id.imageView1)
    public ImageView imageView1;
    @BindView(R.id.imageView2)
    public ImageView imageView2;
    @BindView(R.id.imageView3)
    public ImageView imageView3;
    @BindView(R.id.imageView4)
    public ImageView imageView4;
    @BindView(R.id.imageView5)
    public ImageView imageView5;
    //====灯A的 五个亮度控件==end==

    //控制灯A 的两个加减按钮====begin=====
    @BindView(R.id.image_button_light_a_high)
    public ImageButton image_button_light_a_high;
    @BindView(R.id.image_button_light_a_low)
    public ImageButton image_button_light_a_low;
    //控制灯A 的两个加减按钮====end=====

    //灯A的状态控制（核心值） 1-5 测试给了3
    //todo 光人初始化的时候应该读取服务下发的值，来初始化这里成 1-5，还有下面控件控制显示也是改变这个值实现的。你先看，晚上我和你说
    private int count_a=3;

    public LightFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LightFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LightFragment newInstance(String param1, String param2) {
        LightFragment fragment = new LightFragment();
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
        View contentView = inflater.inflate(R.layout.fragment_light, container, false);
        ButterKnife.bind(this, contentView);
        initTag(count_a);//初始化 灯A 相关的显示控件 的背景值，以达到UI效果
        return contentView;
    }

    @OnClick({R.id.image_button_light_a_high,
            R.id.image_button_light_a_low})
    public void buttonOnClick(View v) {
        switch (v.getId()) {
            case R.id.image_button_light_a_high: {
                //往左变化的事件（猜测实际应该让灯 变暗）
                if(0<count_a && count_a<5)
                {
                    count_a++;
                    initTag(count_a);
                }else
                {
//                    Toast.makeText(MainActivity.this, "已经是最大值了",Toast.LENGTH_SHORT).show();
                }
            }
            break;
            case R.id.image_button_light_a_low:
                //往右变化的事件（猜测实际应该让灯 变亮）
                if(1<count_a && count_a<6)
                {
                    count_a--;
                    initTag(count_a);
                }else
                {
//                    Toast.makeText(MainActivity.this, "已经是最小值了",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    public void initTag(int count)
    {
        if(1<=count && count<=5) {
            switch (count) {
                case 1:
                    //绿色停留在 最左的1号位
                    //原理就是 让第一个imageview 的背景变成绿色，其他4个变成灰色，以下同理
                    imageView1.setBackgroundColor(getResources().getColor(R.color.color_green));
                    imageView2.setBackgroundColor(getResources().getColor(R.color.color_gray));
                    imageView3.setBackgroundColor(getResources().getColor(R.color.color_gray));
                    imageView4.setBackgroundColor(getResources().getColor(R.color.color_gray));
                    imageView5.setBackgroundColor(getResources().getColor(R.color.color_gray));

                    image_button_light_a_high.setEnabled(Boolean.TRUE);
                    image_button_light_a_low.setEnabled(Boolean.FALSE);
                    break;
                case 2:
                    //绿色停留在 2号位
                    imageView1.setBackgroundColor(getResources().getColor(R.color.color_gray));
                    imageView2.setBackgroundColor(getResources().getColor(R.color.color_green));
                    imageView3.setBackgroundColor(getResources().getColor(R.color.color_gray));
                    imageView4.setBackgroundColor(getResources().getColor(R.color.color_gray));
                    imageView5.setBackgroundColor(getResources().getColor(R.color.color_gray));
                    break;
                case 3:
                    imageView1.setBackgroundColor(getResources().getColor(R.color.color_gray));
                    imageView2.setBackgroundColor(getResources().getColor(R.color.color_gray));
                    imageView3.setBackgroundColor(getResources().getColor(R.color.color_green));
                    imageView4.setBackgroundColor(getResources().getColor(R.color.color_gray));
                    imageView5.setBackgroundColor(getResources().getColor(R.color.color_gray));
                    break;
                case 4:
                    imageView1.setBackgroundColor(getResources().getColor(R.color.color_gray));
                    imageView2.setBackgroundColor(getResources().getColor(R.color.color_gray));
                    imageView3.setBackgroundColor(getResources().getColor(R.color.color_gray));
                    imageView4.setBackgroundColor(getResources().getColor(R.color.color_green));
                    imageView5.setBackgroundColor(getResources().getColor(R.color.color_gray));
                    break;
                case 5:
                    imageView1.setBackgroundColor(getResources().getColor(R.color.color_gray));
                    imageView2.setBackgroundColor(getResources().getColor(R.color.color_gray));
                    imageView3.setBackgroundColor(getResources().getColor(R.color.color_gray));
                    imageView4.setBackgroundColor(getResources().getColor(R.color.color_gray));
                    imageView5.setBackgroundColor(getResources().getColor(R.color.color_green));

                    image_button_light_a_high.setEnabled(Boolean.FALSE);
                    image_button_light_a_low.setEnabled(Boolean.TRUE);
                    break;
                default:
                    break;
            }
        }
        else
        {

        }
    }
}
