package com.example.criminal_maps.Fragments.LoginActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.criminal_maps.Activities.LoginActivity;
import com.example.criminal_maps.Activities.MapsActivity;
import com.example.criminal_maps.Classes.ApiCredentials;
import com.example.criminal_maps.Classes.DBHandler;
import com.example.criminal_maps.Classes.StringSerialize;
import com.example.criminal_maps.NetworkComms.API;
import com.example.criminal_maps.R;

import org.json.JSONException;
import java.io.IOException;

import static androidx.core.content.ContextCompat.getSystemService;

public class LoginFragment extends Fragment {
    String TAG = "LoginFragment";
    View m_view = null;

    public LoginFragment() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View this_view = inflater.inflate(R.layout.fragment_login_login, container, false);
        m_view = this_view;

        DBHandler dbHandler = new DBHandler(getContext(), null, null, 1);
        String creds = dbHandler.checkCredentials();
        API api = new API();
        if ( creds != null) {
            ApiCredentials api_creds = null;
            try {
                api_creds = (ApiCredentials) StringSerialize.fromString(creds);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Log.i(TAG, "Got creds: " + api_creds.getCredentials());
            api.setApiCredentials(api_creds);
            jump_to_activity(api);
        }

        Button registerButton = (Button) this_view.findViewById(R.id.button_register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: keep registration hidden and enable only on button click
                ViewPager vp = (ViewPager) getActivity().findViewById(R.id.activity_login_viewpager);
                vp.setCurrentItem(1, true);
            }
        });


        Button btn_login = (Button) this_view.findViewById(R.id.button_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText usernameEditText = m_view.findViewById(R.id.plaintext_username);
                String username = usernameEditText.getText().toString();
                EditText passwordEditTest = m_view.findViewById(R.id.plaintext_password);
                String password = passwordEditTest.getText().toString();

                try {
                    do_login_and_jump_activity(username, password);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });

        return this_view;


    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }


    private void do_login_and_jump_activity(String username, String password) throws IOException, ClassNotFoundException {
        DBHandler dbHandler = new DBHandler(getContext(), null, null, 1);
        // TODO: check if creds exist
        API api = new API();
        if (API.isNetworkConnected(getActivity())) {
            try {
                boolean response = api.login(username, password);
                if (response) {
                    String creds = StringSerialize.toString(api.getApiCredentials());
                    Log.i(TAG, "Added creds: " + creds);
                    dbHandler.addCredentials(creds);
                    jump_to_activity(api);
                } else {
                    Log.e(TAG, api.getError());
                    Toast.makeText((LoginActivity) getActivity(), getResources().getString(R.string.login_failed), Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            Toast.makeText((LoginActivity) getActivity(), getResources().getString(R.string.no_internet_connection), Toast.LENGTH_LONG).show();
        }

    }


    private void jump_to_activity(API api) {
        Intent intent = new Intent((LoginActivity) getActivity(), MapsActivity.class);
        intent.putExtra("API", api);

        startActivity(intent);
        ((LoginActivity) getActivity()).finish();
    }



}


