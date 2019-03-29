package com.example.usuario.MagicQuiz.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.usuario.MagicQuiz.R;

public class OracleActivity extends AppCompatActivity {

    TextView txv_about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        txv_about = findViewById(R.id.txv_about);
        txv_about.setText(
                "Próximamente");
    }
}