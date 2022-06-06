package mtg.judge.ipgtree.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import mtg.judge.ipgtree.R;
import mtg.judge.ipgtree.Utilities.Read;
import mtg.judge.ipgtree.Utilities.Repository;
import mtg.judge.ipgtree.Utilities.Translation;

public class MainActivity extends AppCompatActivity {

    private Button btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9, btn_10;

    private boolean documentsMenu = false;

    private Intent intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!Repository.repositoryLoaded) {
            Repository.createRepository(getApplicationContext());
        }

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
        btn_10 = findViewById(R.id.btn_10);
    }

    private void loadStrings() {
        if(documentsMenu) {
            btn_1.setText(Translation.StringMap(85));
            btn_2.setText(Translation.StringMap(86));
            btn_3.setText(Translation.StringMap(87));
            btn_4.setText(Translation.StringMap(88));
            btn_5.setText(Translation.StringMap(89));
            btn_6.setText(Translation.StringMap(90));
            btn_7.setText(Translation.StringMap(5));
            btn_8.setText(Translation.StringMap(4));
            btn_9.setText(Translation.StringMap(75));
            btn_10.setText(Translation.StringMap(6));
            btn_10.setVisibility(View.VISIBLE);
        }
        else {
            btn_1.setText(Translation.StringMap(31));
            btn_2.setText(Translation.StringMap(36));
            btn_3.setText(Translation.StringMap(34));
            btn_4.setText(Translation.StringMap(33));
            btn_5.setText(Translation.StringMap(30));
            btn_6.setText(Translation.StringMap(32));
            btn_7.setText(Translation.StringMap(38));
            btn_8.setText(Translation.StringMap(35));
            btn_9.setText(Translation.StringMap(37));
            btn_10.setVisibility(View.GONE);
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
        btn_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                move(10);
            }
        });
    }

    private void move(int buttonId) {
        enableButtons(false);
        intent = null;
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
                    intent.putExtra("document", "adipg");
                    break;
                case 6:
                    intent = new Intent(MainActivity.this, DocumentActivity.class);
                    intent.putExtra("document", "admtr");
                    break;
                case 7:
                    intent = new Intent(MainActivity.this, DocumentActivity.class);
                    intent.putExtra("document", "dq");
                    break;
                case 8:
                    intent = new Intent(MainActivity.this, DocumentActivity.class);
                    intent.putExtra("document", "banned");
                    break;
                case 9:
                    intent = new Intent(MainActivity.this, DocumentActivity.class);
                    intent.putExtra("document", "hja");
                    break;
                case 10:
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
                    break;
                case 2:
                    if(Repository.databaseLoaded) {
                        intent = new Intent(MainActivity.this, OracleActivity.class);
                    }
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
                    switch (Repository.players)
                    {
                        case 0:
                            LayoutInflater layoutInflater = LayoutInflater.from(this);
                            View view = layoutInflater.inflate(R.layout.dialog_buttons, null);

                            final AlertDialog alertDialog = new AlertDialog.Builder(this).create();

                            TextView txv_title = view.findViewById(R.id.txv_title);
                            Button btn2 = view.findViewById(R.id.btn2);
                            Button btn4 = view.findViewById(R.id.btn4);
                            Button btn6 = view.findViewById(R.id.btn6);

                            txv_title.setText(Translation.StringMap(92));

                            btn2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    enableButtons(false);
                                    intent = new Intent(MainActivity.this, Life2Activity.class);
                                    startActivityForResult(intent,1);
                                    alertDialog.cancel();
                                }
                            });
                            btn4.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    enableButtons(false);
                                    intent = new Intent(MainActivity.this, Life4Activity.class);
                                    startActivityForResult(intent,1);
                                    alertDialog.cancel();
                                }
                            });
                            btn6.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    enableButtons(false);
                                    intent = new Intent(MainActivity.this, Life6Activity.class);
                                    startActivityForResult(intent,1);
                                    alertDialog.cancel();
                                }
                            });

                            alertDialog.setView(view);
                            alertDialog.show();
                            break;
                        case 2:
                            intent = new Intent(MainActivity.this, Life2Activity.class);
                            break;
                        case 4:
                            intent = new Intent(MainActivity.this, Life4Activity.class);
                            break;
                        case 6:
                            intent = new Intent(MainActivity.this, Life6Activity.class);
                            break;
                    }
                    break;
                case 9:
                    intent = new Intent(MainActivity.this, SettingsActivity.class);
                    break;
            }
        }
        if (intent == null) {
            enableButtons(true);
        }
        else {
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
        btn_10.setEnabled(state);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        enableButtons(true);
        if(resultCode == RESULT_OK) {
            loadStrings();
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