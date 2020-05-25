package com.example.criminal_maps.Fragments.LoginActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.criminal_maps.R;

public class LoginFragment extends Fragment {

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

        Button registerButton = (Button) this_view.findViewById(R.id.button_register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO keep registration hidden and enable only on button click
                Toast.makeText(getActivity(), getResources().getString(R.string.hello_there), Toast.LENGTH_SHORT).show();
                ViewPager vp = (ViewPager) getActivity().findViewById(R.id.activity_login_viewpager);
                vp.setCurrentItem(1, true);
            }
        });

        return this_view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

//    public void onClickLogin(View view) {
//        API api = new API();
//        EditText usernameEditText = getActivity().findViewById(R.id.plaintext_username);
//        String username = usernameEditText.getText().toString();
//        EditText passwordEditTest = getActivity().findViewById(R.id.plaintext_password);
//        String password = passwordEditTest.getText().toString();
//        try {
//            boolean response = api.login(username, password);
//            if (response) {
//                Intent intent = new Intent(getActivity(), MapsActivity.class);
//                startActivity(intent);
//            }
//            else {
//                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
}
