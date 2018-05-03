package com.liwen.dor.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.liwen.dor.R;
import com.liwen.dor.ui.fragment.ControlLayoutFragment;
import com.liwen.dor.ui.fragment.DisplayLayoutFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.navigation_home)
    BottomNavigationView mNavigation;
//    @BindView(R.id.viewPager_main)
//    ViewPager mViewPager;

    @BindView(R.id.frameLayout_main)
    FrameLayout mConten;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    doDisplayLayout();
                    return true;
                case R.id.navigation_dashboard:
                    doControlLayout();
                    return true;
                case R.id.navigation_notifications:
                    doDisplayLayout3();

                    return true;
            }
            return false;
        }
    };

    private void doDisplayLayout(){
        DisplayLayoutFragment displayLayout = DisplayLayoutFragment.newInstance("","");
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager. beginTransaction();
        transaction.replace(R.id.frameLayout_main, displayLayout);
        transaction.commit();
    }
    private void doControlLayout(){
        ControlLayoutFragment controlLayout = ControlLayoutFragment.newInstance("","");
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager. beginTransaction();
        transaction.replace(R.id.frameLayout_main, controlLayout);
        transaction.commit();
    }
    private void doDisplayLayout3(){
        DisplayLayoutFragment displayLayout = DisplayLayoutFragment.newInstance("","");
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager. beginTransaction();
        transaction.replace(R.id.frameLayout_main, displayLayout);
        transaction.commit();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);//批量获取控件
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        init();
    }

    private void init(){
        doDisplayLayout();
    }



}
