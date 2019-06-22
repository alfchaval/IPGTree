package mtg.judge.ipgtree.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import mtg.judge.ipgtree.R;

public class MainActivity extends AppCompatActivity {

    Button btn_documents, btn_oracle, btn_tree, btn_quiz, btn_decklist, btn_draft, btn_timer, btn_life, btn_about;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linkViews();
        setListeners();
    }

    public void linkViews() {
        btn_documents = findViewById(R.id.btn_docs);
        btn_oracle = findViewById(R.id.btn_oracle);
        btn_tree = findViewById(R.id.btn_ipgtree);
        btn_quiz = findViewById(R.id.btn_quiz);
        btn_decklist = findViewById(R.id.btn_decklist);
        btn_draft = findViewById(R.id.btn_draft);
        btn_timer = findViewById(R.id.btn_timer);
        btn_life = findViewById(R.id.btn_life);
        btn_about = findViewById(R.id.btn_about);
    }

    public void setListeners() {
        btn_documents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this, DocumentsMenuActivity.class);
                startActivity(intent);
            }
        });
        btn_oracle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this, OracleActivity.class);
                startActivity(intent);
            }
        });
        btn_tree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this, TreeActivity.class);
                startActivity(intent);
            }
        });
        btn_quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this, QuizActivity.class);
                startActivity(intent);
            }
        });
        btn_decklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this, DecklistActivity.class);
                startActivity(intent);
            }
        });
        btn_draft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this, DraftActivity.class);
                startActivity(intent);
            }
        });
        btn_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this, TimerActivity.class);
                startActivity(intent);
            }
        });
        btn_life.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this, LifeActivity.class);
                startActivity(intent);
            }
        });
        btn_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });
    }
}