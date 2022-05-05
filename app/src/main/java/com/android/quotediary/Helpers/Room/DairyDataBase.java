package com.android.quotediary.Helpers.Room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {DairyEntity.class,DairyEntity.Year.class}, version = 1, exportSchema = false)
public abstract class DairyDataBase extends RoomDatabase {
    public abstract DairyDao DairyDao();
    private static volatile DairyDataBase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    static DairyDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DairyDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DairyDataBase.class, "Dairy_DB")
                            .build();
                }
            }
        }
        return INSTANCE;
    }


}
