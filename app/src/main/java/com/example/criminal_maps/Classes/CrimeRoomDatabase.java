package com.example.criminal_maps.Classes;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = { Crime.class }, version = 1, exportSchema = false)
public abstract class CrimeRoomDatabase extends RoomDatabase {

    public abstract CrimeDao crimeDao();

    private static volatile CrimeRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static CrimeRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CrimeRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), CrimeRoomDatabase.class, "crime_database").build();
                }
            }
        }
        return INSTANCE;
    }
}
