package mtg.judge.ipgtree.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.View;
import android.widget.Button;

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

public class MainActivity extends AppCompatActivity {

    private Button btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9, btn_news_alert;

    private boolean documentsMenu = false;

    private Pair<Integer, String> update;

    private SharedPreferences preferences;

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

        preferences = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE);

        new checkForUpdates().execute();
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
        btn_news_alert = findViewById(R.id.btn_news_alert);
    }

    private void loadStrings() {
        if(documentsMenu) {
            btn_1.setText(getString(R.string.activity_documents_menu_cr));
            btn_2.setText(getString(R.string.activity_documents_menu_jar));
            btn_3.setText(getString(R.string.activity_documents_menu_aipg));
            btn_4.setText(getString(R.string.activity_documents_menu_amtr));
            btn_5.setText(Repository.StringMap(5));
            btn_6.setText(Repository.StringMap(4));
            btn_7.setText(Repository.StringMap(75));
            btn_8.setText(Repository.StringMap(6));
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
            btn_9.setVisibility(View.VISIBLE);
        }
        btn_news_alert.setVisibility(View.GONE);
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
        btn_news_alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(update != null && update.second != null) {
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle(Repository.StringMap(74));
                    alertDialog.setMessage(update.second);
                    alertDialog.show();
                }
            }
        });
    }

    private void move(int buttonId) {
        enableButtons(false);
        Intent intent = null;
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
                    intent.putExtra("document", "hja");
                    break;
                case 8:
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
                    intent = new Intent(MainActivity.this, LifeActivity.class);
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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

    public class checkForUpdates extends AsyncTask<String, String, String> {
        private String folder;

        @Override
        protected String doInBackground(String... strings) {
            update = Read.readNews(getApplicationContext());
            if(Repository.downloadNews) {
                int count;
                InputStream input;
                OutputStream output;
                URL url;
                URLConnection connection;
                String filename;
                byte[] data;
                try {
                    folder = Environment.getExternalStorageDirectory() + File.separator + Repository.FOLDERNAME;
                    File directory = new File(folder);
                    String string = preferences.getString(Repository.KEY_NEWS, Repository.URL_NEWS);
                    url = new URL(string);
                    connection = url.openConnection();
                    connection.connect();
                    input = new BufferedInputStream(url.openStream(), 8192);
                    filename = folder + File.separator + string.substring(string.lastIndexOf('/') + 1, string.length());
                    output = new FileOutputStream(filename);
                    data = new byte[1024];
                    while ((count = input.read(data)) != -1) {
                        output.write(data, 0, count);
                    }
                    output.flush();
                    output.close();
                    input.close();

                    update = Read.readNews(getApplicationContext());
                }
                catch (Exception e) {
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(update != null && update.first > Repository.appVersion) {
                btn_news_alert.setVisibility(View.VISIBLE);
            }
        }
    }
}