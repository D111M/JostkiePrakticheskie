package com.example.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insertUser(User user);
    @Query("UPDATE user SET balance = :balance, user_login = :login, user_password = :pass WHERE ID = :id;")
    int updateUser(int id, String login, String pass, int balance);
    @Delete
    void deleteUser(User user);
    @Query("SELECT * FROM user WHERE user_login = :user_login")
    User getUser(String user_login);
    @Query("SELECT * FROM user WHERE ID = :id")
    User getUserById(int id);
    @Query("SELECT * FROM user")
    List<User> getAllUsers();
}
