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
import com.example.criminal_maps.R;

import java.util.Calendar;

public class CrimeActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private static final String TAG = "CrimeActivity";
    private static final String[] crimeTypes = {"murder", "robbery", "rape", "physical violence"}; // Example values. We'll get them from the server
    private EditText nameEditText;
    private TextView dateText;
    private Spinner spinner;
    private EditText reportEditText;
    private TextView crimeTypeText;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime);

        nameEditText = findViewById(R.id.nameEditText);
        dateText = findViewById(R.id.dateText);
        spinner = findViewById(R.id.spinner);
        reportEditText = findViewById(R.id.reportEditText);
        crimeTypeText = findViewById(R.id.crimeTypeText);
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
        double longitude = extras.getDouble("LONGITUDE");
        double latitude = extras.getDouble("LATITUDE");
        String crimeName = nameEditText.getText().toString();
        String date = dateText.getText().toString();
        String report = reportEditText.getText().toString();
        String type = crimeTypes[(int) spinner.getSelectedItemId()];

        if (false) {
            // TODO: POST the crime to the server. If the POST is successful, also add it to the local DB
            Crime crime = new Crime(1, longitude, latitude, crimeName, date, type, report);
            DBHandler dbHandler = new DBHandler(this, null, null, 1);
            dbHandler.addCrime(crime);
        }
        finish();
    }
}

