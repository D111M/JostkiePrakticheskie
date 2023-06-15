package com.example.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class, History.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract HistoryDao historyDao();
    private static AppDatabase INSTANCE;
    public static AppDatabase getObInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "dota.db")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
}
