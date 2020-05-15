package com.example.criminal_maps.Classes;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CrimeDao {
    @Insert
    void insert(Crime crime);

    @Query("SELECT * FROM crimes")
    LiveData<List<Crime>> getAllCrimes();

}
