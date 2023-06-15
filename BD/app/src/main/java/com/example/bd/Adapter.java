package com.example.bd;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private Context context;
    private ArrayList<Groups> gameList;

    public Adapter(Context context, ArrayList<Groups> gameList) {
        this.context = context;
        this.gameList = gameList;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Groups groups = gameList.get(position);
        holder.gameId.setText(String.valueOf(groups.getId_Game()));
        holder.gameName.setText(groups.getName_Game());
        holder.gameInfo.setText(groups.getInfo_Game());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MainActivity3.class);
            intent.putExtra("idGame",(String.valueOf(groups.getId_Game())));
            intent.putExtra("GameName",(String.valueOf(groups.getName_Game())));
            intent.putExtra("GameInfo",(String.valueOf(groups.getInfo_Game())));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView gameName,gameInfo, gameId;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gameName = itemView.findViewById(R.id.game_name);
            gameInfo = itemView.findViewById(R.id.game_describe);
            gameId = itemView.findViewById(R.id.id_game);
        }
    }
 }
