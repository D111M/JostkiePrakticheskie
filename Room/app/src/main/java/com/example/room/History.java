package com.example.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "history")
public class History {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_history")
    private int id;
    @ColumnInfo(name = "check")
    private boolean check;
    @ColumnInfo(name = "count")
    private int count;
    @ColumnInfo(name = "category")
    private String category;
    @ColumnInfo(name = "userId")
    private int userId;

    public History(boolean check, int count, @NonNull String category, int userId) {
        this.check = check;
        this.count = count;
        this.category = category;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
