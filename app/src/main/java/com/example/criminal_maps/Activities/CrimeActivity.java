package com.example.criminal_maps.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.criminal_maps.Classes.Crime;
import com.example.criminal_maps.Classes.DBHandler;
import com.example.criminal_maps.NetworkComms.API;
import com.example.criminal_maps.R;

import org.json.JSONException;

import java.util.Calendar;

public class CrimeActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private static final String TAG = "CrimeActivity";
    private static final String[] crimeTypes = {"murder", "robbery", "rape", "physical violence"}; // Example values. We'll get them from the server
    private EditText nameEditText;
    private TextView dateText;
    private Spinner spinner;
    private EditText reportEditText;
    private TextView crimeTypeText;
    private API api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime);

        nameEditText = findViewById(R.id.nameEditText);
        dateText = findViewById(R.id.dateText);
        spinner = findViewById(R.id.spinner);
        reportEditText = findViewById(R.id.reportEditText);
        crimeTypeText = findViewById(R.id.crimeTypeText);

        api = (API) getIntent().getSerializableExtra("API");
    }

    public void showDatePickerDialog(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date =  dayOfMonth + "/" + month + "/" + year;
        dateText.setText(date);
        // Reset the text view to the default color (in case it was changed to red due to an error)
        dateText.setTextColor(crimeTypeText.getCurrentTextColor());
    }

    public void onSubmit(View view) {
        boolean valid = true;
        if (nameEditText.getText().toString().equals("")) {
            nameEditText.setError(getResources().getString(R.string.missing_name));
            valid = false;
        }
        if (dateText.getText().toString().equals(getResources().getString(R.string.day_month_year))) {
            dateText.setText(getResources().getString(R.string.missing_date));
            dateText.setTextColor(Color.RED);
            valid = false;
        }
        if (!valid) {
            return;
        }
        Bundle extras = getIntent().getExtras();
        // Only for debugging purposes, should never actually happen
        if (extras == null) {
            Log.d(TAG, "Extra data not passed (latitude and longitude)");
            return;
        }
        double longitude = extras.getDouble("LONGITUDE", -10000);
        double latitude = extras.getDouble("LATITUDE", -10000);
        // Only for debugging purposes, should never actually happen
        if (latitude == -10000 || longitude == -10000) {
            Log.d(TAG, "Extra data not passed (latitude and longitude)");
            return;
        }
        String crimeName = nameEditText.getText().toString();
        String date = dateText.getText().toString();
        String report = reportEditText.getText().toString();
        int type = (int) spinner.getSelectedItemId();

        Crime crime = new Crime(longitude, latitude, crimeName, date, type, report);
        try {
            if (!api.addCrime(crime)) {
                Log.e(TAG, api.getError());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Since I do not know the ID of the added crime, I do not add it to the local database now. Instead, I add it when I get the data from the server

//        Crime crime = new Crime(1, longitude, latitude, crimeName, date, type, report);
//        DBHandler dbHandler = new DBHandler(this, null, null, 1);
//        dbHandler.addCrime(crime);
        finish();
    }
}

