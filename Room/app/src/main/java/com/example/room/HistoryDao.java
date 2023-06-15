package com.example.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface HistoryDao {
    @Insert
    void insertHistory(History history);
    @Query("SELECT * FROM history")
    List<History> getAllHistory();
    @Query("SELECT * FROM history WHERE userId = :user_id")
    List<History> getHistory(int user_id);
}
