package com.example.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "game.db";
    private static final int SCHEMA = 1;
    static final String TABLE_NAME = "game";

    public static final String COLUMN_ID = "id_game";
    public static final String COLUMN_NAME = "game_name";
    public static final String COLUMN_INFO = "game_info";

    public DataBaseHelper (@Nullable Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME
                + " TEXT, " + COLUMN_INFO + " INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addGame(Groups groups) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, groups.getName_Game());
        contentValues.put(COLUMN_INFO, groups.getInfo_Game());

        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
    }

    public ArrayList<Groups> getGameList() {

        ArrayList<Groups> ListGame = new ArrayList<>();
        SQLiteDatabase sqliteDatabase = this.getReadableDatabase();
        Cursor result = sqliteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (result.moveToFirst()) {
            while (result.moveToNext()) {
                int id = result.getInt(0);
                String gameName = result.getString(1);
                String gameInfo = result.getString(2);
                Groups groups = new Groups(id, gameName, gameInfo);
                ListGame.add(groups);
            }
        }
        result.close();
        return ListGame;
    }

    public void updateGame(String id, String nameGame, String InfoGame) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, nameGame);
        contentValues.put(COLUMN_INFO, InfoGame);

        sqLiteDatabase.update(TABLE_NAME, contentValues, "id_game=?", new String[]{id});
    }

    public void deleteGame(String id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME, "id_game=?", new String[]{id});
    }
}
