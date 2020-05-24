package com.example.criminal_maps.Activities;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

        // Connect the tabs with the ViewPager (the setupWithViewPager method does this for us in
        // both directions, i.e. when a new tab is selected, the ViewPager switches to this page,
        // and when the ViewPager switches to a new page, the corresponding tab is selected)
//        TabLayout tabLayout = findViewById(R.id.tablayout);
//        tabLayout.setupWithViewPager(mViewPager);

    }

    public void onClickLogin(View view) {
        EditText usernameEditText = findViewById(R.id.plaintext_username);
        String username = usernameEditText.getText().toString();
        EditText passwordEditTest = findViewById(R.id.plaintext_password);
        String password = passwordEditTest.getText().toString();
        try {
            boolean response = api.login(username, password);
            if (response) {
                Intent intent = new Intent(this, MapsActivity.class);
                intent.putExtra("API", api);
                startActivity(intent);
            }
            else {
                Log.e(TAG, api.getError());
                Toast.makeText(this, getResources().getString(R.string.login_failed), Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onClickRegister(View view) {
        EditText usernameEditText = findViewById(R.id.fragment_register_editText_username);
        EditText passwordEditText = findViewById(R.id.fragment_register_editText_password);
        EditText passwordConfirmEditText = findViewById(R.id.fragment_register_editText_password_confirm);
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String passwordConfirm = passwordConfirmEditText.getText().toString();
        boolean valid = true;
        if (!password.equals(passwordConfirm)) {
//            Toast.makeText(this, getResources().getString(R.string.passwords_must_match), Toast.LENGTH_SHORT).show();
            passwordEditText.setError(getResources().getString(R.string.passwords_must_match));
            passwordConfirmEditText.setError(getResources().getString(R.string.passwords_must_match));
            valid = false;
        }
        if (username.equals("")) {
            usernameEditText.setError(getResources().getString(R.string.missing_username));
            valid = false;
        }
        if (!valid) {
            return;
        }
        try {
            boolean response = api.register(username, password);
            if (response) {
                Intent intent = new Intent(this, MapsActivity.class);
                startActivity(intent);
            }
            else {
                Log.e(TAG, api.getError());
                Toast.makeText(this, getResources().getString(R.string.registration_failed), Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
