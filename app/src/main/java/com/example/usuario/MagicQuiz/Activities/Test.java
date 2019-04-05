package com.example.usuario.MagicQuiz.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.usuario.MagicQuiz.Card;
import com.example.usuario.MagicQuiz.R;
import com.example.usuario.MagicQuiz.Read;

import java.util.ArrayList;

public class Test extends AppCompatActivity {

    //HIDDEN ACTIVITY FOR TESTING PURPOSES

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        //Read.createDatabase("AllCards.json", getApplicationContext(), R.raw.allcards, R.raw.allsets);
    }
}