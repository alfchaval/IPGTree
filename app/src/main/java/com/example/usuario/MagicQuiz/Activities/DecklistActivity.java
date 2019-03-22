package com.example.usuario.MagicQuiz.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.usuario.MagicQuiz.R;

import java.util.ArrayList;

public class DecklistActivity extends AppCompatActivity {

    private static final String KEY_NUMBERS = "key_numbers";

    ArrayList<Integer> numbers;

    Button btn_reset, btn_undo, btn_add_1, btn_add_2, btn_add_3, btn_add_4;
    TextView txv_total, txv_number_list;

    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntegerArrayList(KEY_NUMBERS, numbers);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decklist);

        btn_reset = findViewById(R.id.btn_reset);
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numbers.clear();
                WriteNumbers();
            }
        });
        btn_undo = findViewById(R.id.btn_undo);
        btn_undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numbers.remove(numbers.size() - 1);
                WriteNumbers();
            }
        });
        btn_add_1 = findViewById(R.id.btn_add_1);
        btn_add_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numbers.add(1);
                WriteNumbers();
            }
        });
        btn_add_2 = findViewById(R.id.btn_add_2);
        btn_add_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numbers.add(2);
                WriteNumbers();
            }
        });
        btn_add_3 = findViewById(R.id.btn_add_3);
        btn_add_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numbers.add(3);
                WriteNumbers();
            }
        });
        btn_add_4 = findViewById(R.id.btn_add_4);
        btn_add_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numbers.add(4);
                WriteNumbers();
            }
        });

        txv_total = findViewById(R.id.txv_total);
        txv_number_list = findViewById(R.id.txv_number_list);

        if (savedInstanceState != null) {
            numbers = savedInstanceState.getIntegerArrayList(KEY_NUMBERS);
        }
        else {
            numbers = new ArrayList<Integer>();
        }
        WriteNumbers();
    }

    public void WriteNumbers() {
        int auxTotal = 0;
        String auxList = "";
        for(int i = 0; i < numbers.size(); i++) {
            auxTotal += numbers.get(i);
            auxList += numbers.get(i) + " ";
        }
        txv_total.setText("Total: " + auxTotal);
        txv_number_list.setText(auxList);
    }
}