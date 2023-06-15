package com.example.firebaze1920;

import java.util.ArrayList;

public class User {
    private String email;
    private String key;
    private ArrayList<String> notes;

    public User(String email, String key, ArrayList<String> notes) {
        this.email = email;
        this.key = key;
        this.notes = notes;
    }

    public ArrayList<String> getNotes() {
        return notes;
    }

    public void setNotes(ArrayList<String> notes) {
        this.notes = notes;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
