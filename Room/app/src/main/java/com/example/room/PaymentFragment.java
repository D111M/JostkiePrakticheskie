package com.example.room;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.List;

public class PaymentFragment extends Fragment {
    TextView infoBal;
    AppDatabase db;
    int sumA, sumB, sumC, sumD;
    Button btMoney, getMoney;
    PieChart pieChart;
    TextView A, B, C, D;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_payment, container, false);
        db = AppDatabase.getObInstance(getContext());
        pieChart = root.findViewById(R.id.piechart);
        infoBal = root.findViewById(R.id.infoBal);
        btMoney = root.findViewById(R.id.popolneniee);
        getMoney = root.findViewById(R.id.spisanie);
        A = root.findViewById(R.id.A);
        B = root.findViewById(R.id.B);
        C = root.findViewById(R.id.C);
        D = root.findViewById(R.id.D);
        pieChart = root.findViewById(R.id.piechart);
        loadGetMoneyText();
        loadDiagram();
        btMoney.setOnClickListener(view -> {
            setMoneyText();
            loadMoneyText();
            loadDiagram();
            infoBal.setText(String.valueOf(sumA + sumB + sumC + sumD));
        });
        getMoney.setOnClickListener(view -> {
            getMoneyText();
            loadGetMoneyText();
            loadDiagram();
            infoBal.setText(String.valueOf(sumA + sumB + sumC + sumD));
        });
        infoBal.setText(String.valueOf(sumA + sumB + sumC + sumD));
        return root;
    }
    public void loadGetMoneyText(){
        List<History> textHistor = db.historyDao().getHistory(MainActivity.ID);
        for(int i = 0; i < textHistor.size(); i++) {
            if(!textHistor.get(i).isCheck()) {
                switch (textHistor.get(i).getCategory()){
                    case "11":
                        sumA += textHistor.get(i).getCount();
                        break;
                    case "22":
                        sumB += textHistor.get(i).getCount();;
                        break;
                    case "33":
                        sumC += textHistor.get(i).getCount();
                        break;
                    case "44":
                        sumD += textHistor.get(i).getCount();
                        break;
                }
            }
        }
    }
    public void loadMoneyText(){
        List<History> textHistor = db.historyDao().getHistory(MainActivity.ID);
        for(int i = 0; i < textHistor.size(); i++) {
            if(textHistor.get(i).isCheck()) {
                switch (textHistor.get(i).getCategory()){
                    case "11":
                        sumA += textHistor.get(i).getCount();
                        break;
                    case "22":
                        sumB += textHistor.get(i).getCount();
                        break;
                    case "33":
                        sumC += textHistor.get(i).getCount();
                        break;
                    case "44":
                        sumD += textHistor.get(i).getCount();
                        break;
                }
            }
        }
    }
    public void loadDiagram(){
        pieChart.clearChart();
        pieChart.addPieSlice(
                new PieModel(
                        "1",
                        sumA,
                        Color.parseColor("#0033ED")));
        pieChart.addPieSlice(
                new PieModel(
                        "2",
                        sumB,
                        Color.parseColor("#000000")));
        pieChart.addPieSlice(
                new PieModel(
                        "3",
                        sumC,
                        Color.parseColor("#EF0000")));
        pieChart.addPieSlice(
                new PieModel(
                        "4",
                        sumD,
                        Color.parseColor("#C600F3")));
    }
    public void getMoneyText(){
        setDefaultText();
        A.setText("1");
        B.setText("2");
        C.setText("3");
        D.setText("4");
    }
    public void setMoneyText(){
        setDefaultText();
        A.setText("11");
        B.setText("22");
        C.setText("33");
        D.setText("44");
    }
    public void setDefaultText(){
        sumA = 0;
        sumB = 0;
        sumC = 0;
        sumD = 0;
    }
}