package mtg.judge.ipgtree.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import mtg.judge.ipgtree.R;

public class MainActivity extends AppCompatActivity {

    private Button btn_documents, btn_oracle, btn_tree, btn_quiz, btn_decklist, btn_draft, btn_timer, btn_life, btn_settings;
    private Intent intent;

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
        btn_settings = findViewById(R.id.btn_settings);
    }

    public void setListeners() {
        btn_documents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enableButtons(false);
                intent = new Intent(MainActivity.this, DocumentsMenuActivity.class);
                startActivityForResult(intent,1);
            }
        });
        btn_oracle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enableButtons(false);
                intent = new Intent(MainActivity.this, OracleActivity.class);
                startActivityForResult(intent,1);
            }
        });
        btn_tree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enableButtons(false);
                intent = new Intent(MainActivity.this, TreeActivity.class);
                startActivityForResult(intent,1);
            }
        });
        btn_quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enableButtons(false);
                intent = new Intent(MainActivity.this, QuizActivity.class);
                startActivityForResult(intent,1);
            }
        });
        btn_decklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enableButtons(false);
                intent = new Intent(MainActivity.this, DecklistActivity.class);
                startActivityForResult(intent,1);
            }
        });
        btn_draft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enableButtons(false);
                intent = new Intent(MainActivity.this, DraftActivity.class);
                startActivityForResult(intent,1);
            }
        });
        btn_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enableButtons(false);
                intent = new Intent(MainActivity.this, TimerActivity.class);
                startActivityForResult(intent,1);
            }
        });
        btn_life.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enableButtons(false);
                intent = new Intent(MainActivity.this, LifeActivity.class);
                startActivityForResult(intent,1);
            }
        });
        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enableButtons(false);
                intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivityForResult(intent,1);
            }
        });
    }

    public void enableButtons(Boolean state) {
        btn_documents.setEnabled(state);
        btn_oracle.setEnabled(state);
        btn_tree.setEnabled(state);
        btn_quiz.setEnabled(state);
        btn_decklist.setEnabled(state);
        btn_draft.setEnabled(state);
        btn_timer.setEnabled(state);
        btn_life.setEnabled(state);
        btn_settings.setEnabled(state);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        enableButtons(true);
    }
}