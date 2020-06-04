package com.example.criminal_maps.Fragments.LoginActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.viewpager.widget.ViewPager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.criminal_maps.Activities.LoginActivity;
import com.example.criminal_maps.Activities.MapsActivity;
import com.example.criminal_maps.NetworkComms.API;
import com.example.criminal_maps.R;

import org.json.JSONException;

public class RegisterFragment extends Fragment {
    String TAG = "RegisterFragment";
    View m_view = null;
    public RegisterFragment() {
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
        final View this_view = inflater.inflate(R.layout.fragment_login_register, container, false);
        m_view = this_view;
        Button btn_register = (Button) this_view.findViewById(R.id.fragment_register_button_submit);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText usernameEditText = m_view.findViewById(R.id.fragment_register_editText_username);
                EditText passwordEditText = m_view.findViewById(R.id.fragment_register_editText_password);
                EditText passwordConfirmEditText = m_view.findViewById(R.id.fragment_register_editText_password_confirm);
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
                if (API.isNetworkConnected(getActivity())){
                    try {
                        API api = new API();
                        boolean response = api.register(username, password);
                        if (response) {
                            ViewPager vp = (ViewPager) getActivity().findViewById(R.id.activity_login_viewpager);
                            vp.setCurrentItem(0, true);
                        }
                        else {
                            Log.e(TAG, api.getError());
                            Toast.makeText((LoginActivity) getActivity(), getResources().getString(R.string.registration_failed), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText((LoginActivity) getActivity(), getResources().getString(R.string.no_internet_connection), Toast.LENGTH_LONG).show();
                }
            }
        });


        return this_view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public void onClickRegister(View view) {

    }

}
