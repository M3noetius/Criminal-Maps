package com.example.criminal_maps.Classes;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class CrimeRepository {
    private CrimeDao crimeDao;
    private LiveData<List<Crime>> allCrimes;

    CrimeRepository(Application application) {
        CrimeRoomDatabase db = CrimeRoomDatabase.getDatabase(application);
        crimeDao = db.crimeDao();
        allCrimes = crimeDao.getAllCrimes();
    }

    LiveData<List<Crime>> getAllCrimes() {
        return allCrimes;
    }

    void insert(final Crime crime) {
        CrimeRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                crimeDao.insert(crime);
            }
        });
    }
}
