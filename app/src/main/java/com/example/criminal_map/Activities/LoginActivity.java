package com.example.criminal_map.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.example.criminal_map.R;

import com.example.criminal_map.Fragments.LoginActivity.LoginFragment;
import com.example.criminal_map.Fragments.LoginActivity.RegisterFragment;

public class LoginActivity extends FragmentActivity {

    public Fragment pages [] = {
            new LoginFragment(),
            new RegisterFragment()
    };


    public String titles [] = {
            "Login",
            "Register Account"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_login);

        ViewPager mViewPager = findViewById(R.id.activity_login_viewpager);
        mViewPager.setAdapter(new MyPageAdapter(getSupportFragmentManager()));

        // Connect the tabs with the ViewPager (the setupWithViewPager method does this for us in
        // both directions, i.e. when a new tab is selected, the ViewPager switches to this page,
        // and when the ViewPager switches to a new page, the corresponding tab is selected)
//        TabLayout tabLayout = findViewById(R.id.tablayout);
//        tabLayout.setupWithViewPager(mViewPager);

    }

    public class MyPageAdapter extends FragmentStatePagerAdapter {
        public MyPageAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }
        @Override
        public Fragment getItem(int page) {
            Fragment mFragment = pages[page];
//            Send data to fragments
//            Bundle dataBundle = new Bundle();
//            dataBundle.putSerializable("EXTRA_sessionUser", sessionUser);
//            mFragment.setArguments(dataBundle);

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
