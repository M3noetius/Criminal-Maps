package com.example.criminal_maps.Activities;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;


import com.example.criminal_maps.NetworkComms.API;
import com.example.criminal_maps.R;

import com.example.criminal_maps.Fragments.LoginActivity.LoginFragment;
import com.example.criminal_maps.Fragments.LoginActivity.RegisterFragment;

import org.json.JSONException;

public class LoginActivity extends FragmentActivity {

    private API api;

    public Fragment pages[] = {
            new LoginFragment(),
            new RegisterFragment()
    };


    public String titles[] = {
            "Login",
            "Register Account"
    };

    private static final String TAG = "LoginActivity";

    MyPageAdapter pageAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_login);

        ViewPager mViewPager = findViewById(R.id.activity_login_viewpager);
        pageAdapter = new MyPageAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(pageAdapter);

        api = new API();

    }


    public class MyPageAdapter extends FragmentStatePagerAdapter {
        public MyPageAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int page) {
            Fragment mFragment = pages[page];

            return mFragment;
        }

        @Override
        public int getCount() {
            return pages.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

}
