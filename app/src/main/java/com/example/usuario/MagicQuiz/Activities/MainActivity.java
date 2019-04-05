package com.example.usuario.MagicQuiz.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.usuario.MagicQuiz.R;

public class MainActivity extends AppCompatActivity {

    Button btn_documents, btn_oracle, btn_tree, btn_quiz, btn_decklist, btn_draft, btn_life, btn_about;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_documents = findViewById(R.id.btn_docs);
        btn_documents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this, DocumentsMenuActivity.class);
                startActivity(intent);
            }
        });
        btn_oracle = findViewById(R.id.btn_oracle);
        btn_oracle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this, OracleActivity.class);
                startActivity(intent);
            }
        });
        btn_tree = findViewById(R.id.btn_ipgtree);
        btn_tree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this, TreeActivity.class);
                startActivity(intent);
            }
        });
        btn_quiz = findViewById(R.id.btn_quiz);
        btn_quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this, QuizActivity.class);
                startActivity(intent);
            }
        });
        btn_decklist = findViewById(R.id.btn_decklist);
        btn_decklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this, DecklistActivity.class);
                startActivity(intent);
            }
        });
        btn_draft = findViewById(R.id.btn_draft);
        btn_draft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this, DraftActivity.class);
                startActivity(intent);
            }
        });
        btn_life = findViewById(R.id.btn_life);
        btn_life.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this, LifeActivity.class);
                startActivity(intent);
            }
        });
        btn_about = findViewById(R.id.btn_about);
        btn_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });
    }
}