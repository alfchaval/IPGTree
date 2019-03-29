package com.example.usuario.MagicQuiz.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.usuario.MagicQuiz.R;

public class LifeActivity extends AppCompatActivity {

    Button btn_p1plus10, btn_p1plus1, btn_p1minus1, btn_p1minus10, btn_reset, btn_p2minus10, btn_p2minus1, btn_p2plus1, btn_p2plus10;
    TextView txv_p1life, txv_p2life;

    int p1life = 0;
    int p2life = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life);

        txv_p1life = findViewById(R.id.txv_p1life);
        txv_p2life = findViewById(R.id.txv_p2life);

        btn_p1plus10 = findViewById(R.id.btn_p1plus10);
        btn_p1plus10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changep1life(10);
            }
        });
        btn_p1plus1 = findViewById(R.id.btn_p1plus1);
        btn_p1plus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changep1life(1);
            }
        });
        btn_p1minus1 = findViewById(R.id.btn_p1minus1);
        btn_p1minus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changep1life(-1);
            }
        });
        btn_p1minus10 = findViewById(R.id.btn_p1minus10);
        btn_p1minus10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changep1life(-10);
            }
        });
        btn_reset = findViewById(R.id.btn_reset);
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
            }
        });
        btn_p2minus10 = findViewById(R.id.btn_p2minus10);
        btn_p2minus10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changep2life(-10);
            }
        });
        btn_p2minus1 = findViewById(R.id.btn_p2minus1);
        btn_p2minus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changep2life(-1);
            }
        });
        btn_p2plus1 = findViewById(R.id.btn_p2plus1);
        btn_p2plus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changep2life(1);
            }
        });
        btn_p2plus10 = findViewById(R.id.btn_p2plus10);
        btn_p2plus10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changep2life(10);
            }
        });

        reset();
    }

    public void reset() {
        p1life = 20;
        p2life = 20;
        txv_p1life.setText("20");
        txv_p2life.setText("20");
    }

    public void changep1life(int value) {
        p1life += value;
        txv_p1life.setText(p1life + "");
    }

    public void changep2life(int value) {
        p2life += value;
        txv_p2life.setText(p2life + "");
    }
}