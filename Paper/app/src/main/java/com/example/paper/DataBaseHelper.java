package com.example.paper;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

import io.paperdb.Paper;

public class DataBaseHelper {
    static final String TABLE_NAME = "guns";

    public ArrayList<Model> getModel(){
        return Paper.book(TABLE_NAME).read(TABLE_NAME, new ArrayList<Model>());
    }
    public void SaveModel(ArrayList<Model> model){
        Paper.book(TABLE_NAME).write(TABLE_NAME, model);
    }

    public void AddModel(Model model, Context context){
        ArrayList<Model> models = getModel();
        for(int i = 0; i < models.size(); i++){
            if(models.get(i).getName().equals(model.getName())){
                Toast.makeText(context, "Такая запись уже есть!", Toast.LENGTH_LONG).show();
                return;
            }
        }
        models.add(model);
        SaveModel(models);
    }

    public void DeleteColumn(int id){
        ArrayList<Model> models = getModel();
        models.remove(id);
        SaveModel(models);
    }

    public void ChangeColumn(int id, String name, String description){
        ArrayList<Model> models = getModel();
        models.set(id, new Model(name, description));
        SaveModel(models);
    }

}
