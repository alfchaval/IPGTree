package mtg.judge.ipgtree.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import mtg.judge.ipgtree.R;
import mtg.judge.ipgtree.Repository;

public class MainActivity extends AppCompatActivity {

    private Button btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9;
    private Intent intent;

    private boolean documentsMenu = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linkViews();
        loadStrings();
        setListeners();
    }

    private void linkViews() {
        btn_1 = findViewById(R.id.btn_1);
        btn_2 = findViewById(R.id.btn_2);
        btn_3 = findViewById(R.id.btn_3);
        btn_4 = findViewById(R.id.btn_4);
        btn_5 = findViewById(R.id.btn_5);
        btn_6 = findViewById(R.id.btn_6);
        btn_7 = findViewById(R.id.btn_7);
        btn_8 = findViewById(R.id.btn_8);
        btn_9 = findViewById(R.id.btn_9);
    }

    private void loadStrings() {
        if(documentsMenu) {
            btn_1.setText(getString(R.string.activity_documents_menu_cr));
            btn_2.setText(getString(R.string.activity_documents_menu_jar));
            btn_3.setText(getString(R.string.activity_documents_menu_aipg));
            btn_4.setText(getString(R.string.activity_documents_menu_amtr));
            btn_5.setText(Repository.StringMap(5));
            btn_6.setText(Repository.StringMap(4));
            btn_7.setText(Repository.StringMap(6));
            btn_8.setVisibility(View.GONE);
            btn_9.setVisibility(View.GONE);
        }
        else {
            btn_1.setText(Repository.StringMap(31));
            btn_2.setText(Repository.StringMap(36));
            btn_3.setText(Repository.StringMap(34));
            btn_4.setText(Repository.StringMap(33));
            btn_5.setText(Repository.StringMap(30));
            btn_6.setText(Repository.StringMap(32));
            btn_7.setText(Repository.StringMap(38));
            btn_8.setText(Repository.StringMap(35));
            btn_9.setText(Repository.StringMap(37));
            btn_8.setVisibility(View.VISIBLE);
            btn_9.setVisibility(View.VISIBLE);
        }
    }

    private void setListeners() {
        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                move(1);
            }
        });
        btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                move(2);
            }
        });
        btn_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                move(3);
            }
        });
        btn_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                move(4);
            }
        });
        btn_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                move(5);
            }
        });
        btn_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                move(6);
            }
        });
        btn_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                move(7);
            }
        });
        btn_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                move(8);
            }
        });
        btn_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                move(9);
            }
        });
    }

    private void move(int buttonId) {
        enableButtons(false);
        if(documentsMenu) {
            switch (buttonId) {
                case 1:
                    intent = new Intent(MainActivity.this, DocumentActivity.class);
                    intent.putExtra("document", "cr");
                    break;
                case 2:
                    intent = new Intent(MainActivity.this, DocumentActivity.class);
                    intent.putExtra("document", "jar");
                    break;
                case 3:
                    intent = new Intent(MainActivity.this, DocumentActivity.class);
                    intent.putExtra("document", "aipg");
                    break;
                case 4:
                    intent = new Intent(MainActivity.this, DocumentActivity.class);
                    intent.putExtra("document", "amtr");
                    break;
                case 5:
                    intent = new Intent(MainActivity.this, DocumentActivity.class);
                    intent.putExtra("document", "dq");
                    break;
                case 6:
                    intent = new Intent(MainActivity.this, DocumentActivity.class);
                    intent.putExtra("document", "banned");
                    break;
                case 7:
                    intent = new Intent(MainActivity.this, DocumentActivity.class);
                    intent.putExtra("document", "links");
                    break;
            }
        }
        else {
            switch (buttonId) {
                case 1:
                    documentsMenu = true;
                    loadStrings();
                    enableButtons(true);
                    break;
                case 2:
                    intent = new Intent(MainActivity.this, OracleActivity.class);
                    break;
                case 3:
                    intent = new Intent(MainActivity.this, TreeActivity.class);
                    break;
                case 4:
                    intent = new Intent(MainActivity.this, QuizActivity.class);
                    break;
                case 5:
                    intent = new Intent(MainActivity.this, DecklistActivity.class);
                    break;
                case 6:
                    intent = new Intent(MainActivity.this, DraftActivity.class);
                    break;
                case 7:
                    intent = new Intent(MainActivity.this, TimerActivity.class);
                    break;
                case 8:
                    intent = new Intent(MainActivity.this, LifeActivity.class);
                    break;
                case 9:
                    intent = new Intent(MainActivity.this, SettingsActivity.class);
                    break;
            }
        }
        if (intent != null) {
            startActivityForResult(intent,1);
        }
    }

    public void enableButtons(Boolean state) {
        btn_1.setEnabled(state);
        btn_2.setEnabled(state);
        btn_3.setEnabled(state);
        btn_4.setEnabled(state);
        btn_5.setEnabled(state);
        btn_6.setEnabled(state);
        btn_7.setEnabled(state);
        btn_8.setEnabled(state);
        btn_9.setEnabled(state);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        intent = null;
        enableButtons(true);
        if(resultCode == RESULT_OK) {
            loadStrings();
            Repository.loadDocuments(MainActivity.this);
        }
    }

    @Override
    public void onBackPressed() {
        if(documentsMenu) {
            documentsMenu = false;
            loadStrings();
        }
        else {
            super.onBackPressed();
        }
    }
}