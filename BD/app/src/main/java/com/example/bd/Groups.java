package com.example.bd;

public class Groups {

    private int Id_Game;
    private String Name_Game, Info_Game;

    public Groups(int id_Game, String name_Game, String info_Game) {
        Id_Game = id_Game;
        Name_Game = name_Game;
        Info_Game = info_Game;
    }

    public int getId_Game() {
        return Id_Game;
    }

    public void setId_Game(int id_Game) {
        Id_Game = id_Game;
    }

    public String getName_Game() {
        return Name_Game;
    }

    public void setName_Game(String name_Game) {
        Name_Game = name_Game;
    }

    public String getInfo_Game() {
        return Info_Game;
    }

    public void setInfo_Game(String info_Game) {
        Info_Game = info_Game;
    }
}
