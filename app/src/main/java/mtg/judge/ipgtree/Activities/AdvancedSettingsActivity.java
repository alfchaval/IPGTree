package mtg.judge.ipgtree.Activities;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;

import java.io.File;

import mtg.judge.ipgtree.Utilities.Code;
import mtg.judge.ipgtree.R;
import mtg.judge.ipgtree.Utilities.Repository;

public class AdvancedSettingsActivity extends AppCompatActivity {

    private Button btn_save, btn_unlockftp;
    private CheckBox cb_ftp;
    private EditText edt_server, edt_user, edt_password, edt_codeftp, edt_news,
            edt_aipg_en, edt_amtr_en, edt_adipg_en, edt_admtr_en, edt_banned_en, edt_cr_en, edt_dq_en, edt_tree_en, edt_jar_en, edt_links_en, edt_quiz_en, edt_hja_en,
            edt_aipg_es, edt_amtr_es, edt_adipg_es, edt_admtr_es,edt_banned_es, edt_cr_es, edt_dq_es, edt_tree_es, edt_jar_es, edt_links_es, edt_quiz_es, edt_hja_es;
    private TextView txv_life, txv_codeftp, txv_ftptitle_one, txv_ftptitle_two, txv_show_life, txv_show_ftp, txv_show_links;
    private LinearLayout ll_scrollchild, ll_life, ll_server, ll_server_code, ll_server_settings;
    private TableLayout tl_links;
    private ScrollView scroll;
    private ImageView imv_arrow_down, imv_arrow_up;
    private RadioGroup rg_players;
    private RadioButton rb_0players, rb_2players, rb_4players, rb_6players;
    CheckBox cb_reverse_life;

    private ViewTreeObserver viewTreeObserver;
    private ShowHint showHint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced);

        if(!Repository.repositoryLoaded) {
            Repository.createRepository(getApplicationContext());
        }

        linkViews();
        loadStrings();
        loadRepositoryData();
        setListeners();
        if(Repository.ftpCode == null) {
            Repository.ftpCode = Code.generateCode();
        }
        txv_codeftp.setText(Repository.ftpCode);

        viewTreeObserver = scroll.getViewTreeObserver();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //Arrows disapear when you full scroll to let you read the first/last question
            scroll.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View view, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    showOrHideArrows();
                }
            });
            //Also you don't want to see them if there aren't answers outside the screen
            viewTreeObserver.addOnGlobalLayoutListener (new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    showOrHideArrows();
                }
            });
        }
        else {
            //Older versions can't use OnScrollChangeListener, so instead of disapear, the arrows are semitransparent
            imv_arrow_down.setAlpha(0.4f);
            imv_arrow_up.setAlpha(0.4f);
            //They still disapear if there aren't answers outside the screen
            viewTreeObserver.addOnGlobalLayoutListener (new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    showOrHideArrowsOldDevices();
                }
            });
        }
    }

    private void linkViews() {
        rg_players = findViewById(R.id.rg_players);
        rb_0players = findViewById(R.id.rb_0players);
        rb_2players = findViewById(R.id.rb_2players);
        rb_4players = findViewById(R.id.rb_4players);
        rb_6players = findViewById(R.id.rb_6players);

        btn_save = findViewById(R.id.btn_save);
        btn_unlockftp = findViewById(R.id.btn_unlockftp);
        cb_ftp = findViewById(R.id.cb_ftp);
        edt_server = findViewById(R.id.edt_server);
        edt_user = findViewById(R.id.edt_user);
        edt_password = findViewById(R.id.edt_password);
        edt_codeftp = findViewById(R.id.edt_codeftp);
        txv_life = findViewById(R.id.txv_life);
        txv_codeftp = findViewById(R.id.txv_codeftp);
        txv_ftptitle_one = findViewById(R.id.txv_ftptitle_one);
        txv_ftptitle_two = findViewById(R.id.txv_ftptitle_two);
        txv_show_life = findViewById(R.id.txv_show_life);
        txv_show_ftp = findViewById(R.id.txv_show_ftp);
        txv_show_links = findViewById(R.id.txv_show_links);
        ll_scrollchild = findViewById(R.id.ll_scrollchild);
        ll_life = findViewById(R.id.ll_life);
        ll_life.setVisibility(View.GONE);
        ll_server = findViewById(R.id.ll_server);
        ll_server.setVisibility(View.GONE);
        ll_server_code = findViewById(R.id.ll_server_code);
        ll_server_settings = findViewById(R.id.ll_server_settings);
        tl_links = findViewById(R.id.tl_links);
        tl_links.setVisibility(View.GONE);
        cb_reverse_life = findViewById(R.id.cb_reverse_life);

        edt_news = findViewById(R.id.edt_news);

        edt_aipg_en = findViewById(R.id.edt_aipg_en);
        edt_amtr_en = findViewById(R.id.edt_amtr_en);
        edt_adipg_en = findViewById(R.id.edt_adipg_en);
        edt_admtr_en = findViewById(R.id.edt_admtr_en);
        edt_banned_en = findViewById(R.id.edt_banned_en);
        edt_cr_en = findViewById(R.id.edt_cr_en);
        edt_dq_en = findViewById(R.id.edt_dq_en);
        edt_tree_en = findViewById(R.id.edt_tree_en);
        edt_jar_en = findViewById(R.id.edt_jar_en);
        edt_links_en = findViewById(R.id.edt_links_en);
        edt_quiz_en = findViewById(R.id.edt_quiz_en);
        edt_hja_en = findViewById(R.id.edt_hja_en);

        edt_aipg_es = findViewById(R.id.edt_aipg_es);
        edt_amtr_es = findViewById(R.id.edt_amtr_es);
        edt_adipg_es = findViewById(R.id.edt_adipg_es);
        edt_admtr_es = findViewById(R.id.edt_admtr_es);
        edt_banned_es = findViewById(R.id.edt_banned_es);
        edt_cr_es = findViewById(R.id.edt_cr_es);
        edt_dq_es = findViewById(R.id.edt_dq_es);
        edt_tree_es = findViewById(R.id.edt_tree_es);
        edt_jar_es = findViewById(R.id.edt_jar_es);
        edt_links_es = findViewById(R.id.edt_links_es);
        edt_quiz_es = findViewById(R.id.edt_quiz_es);
        edt_hja_es = findViewById(R.id.edt_hja_es);

        scroll = findViewById(R.id.scroll);
        imv_arrow_down = findViewById(R.id.imv_arrow_down);
        imv_arrow_up = findViewById(R.id.imv_arrow_up);
    }

    private void loadStrings() {
        txv_ftptitle_one.setText(Repository.StringMap(60));
        txv_ftptitle_two.setText(Repository.StringMap(60));
        edt_codeftp.setHint(Repository.StringMap(52));
        btn_unlockftp.setText(Repository.StringMap(51));
        btn_save.setText(Repository.StringMap(63));
        cb_ftp.setText(Repository.StringMap(64));
        edt_user.setHint(Repository.StringMap(29));
        edt_password.setHint(Repository.StringMap(62));
        txv_life.setText(Repository.StringMap(91));
        cb_reverse_life.setText(Repository.StringMap(94));

        SharedPreferences preferences = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE);

        edt_news.setText(preferences.getString(Repository.KEY_NEWS, Repository.URL_NEWS));

        edt_aipg_en.setText(preferences.getString(Repository.KEY_AIPG_EN, Repository.URL_AIPG_EN));
        edt_amtr_en.setText(preferences.getString(Repository.KEY_AMTR_EN, Repository.URL_AMTR_EN));
        edt_adipg_en.setText(preferences.getString(Repository.KEY_ADIPG_EN, Repository.URL_ADIPG_EN));
        edt_admtr_en.setText(preferences.getString(Repository.KEY_ADMTR_EN, Repository.URL_ADMTR_EN));
        edt_banned_en.setText(preferences.getString(Repository.KEY_BANNED_EN, Repository.URL_BANNED_EN));
        edt_cr_en.setText(preferences.getString(Repository.KEY_CR_EN, Repository.URL_CR_EN));
        edt_dq_en.setText(preferences.getString(Repository.KEY_DQ_EN, Repository.URL_DQ_EN));
        edt_tree_en.setText(preferences.getString(Repository.KEY_TREE_EN, Repository.URL_TREE_EN));
        edt_jar_en.setText(preferences.getString(Repository.KEY_JAR_EN, Repository.URL_JAR_EN));
        edt_links_en.setText(preferences.getString(Repository.KEY_LINKS_EN, Repository.URL_LINKS_EN));
        edt_quiz_en.setText(preferences.getString(Repository.KEY_QUIZ_EN, Repository.URL_QUIZ_EN));
        edt_hja_en.setText(preferences.getString(Repository.KEY_HJA_EN, Repository.URL_HJA_EN));

        edt_aipg_es.setText(preferences.getString(Repository.KEY_AIPG_ES, Repository.URL_AIPG_ES));
        edt_amtr_es.setText(preferences.getString(Repository.KEY_AMTR_ES, Repository.URL_AMTR_ES));
        edt_adipg_es.setText(preferences.getString(Repository.KEY_ADIPG_ES, Repository.URL_ADIPG_ES));
        edt_admtr_es.setText(preferences.getString(Repository.KEY_ADMTR_ES, Repository.URL_ADMTR_ES));
        edt_banned_es.setText(preferences.getString(Repository.KEY_BANNED_ES, Repository.URL_BANNED_ES));
        edt_cr_es.setText(preferences.getString(Repository.KEY_CR_ES, Repository.URL_CR_ES));
        edt_dq_es.setText(preferences.getString(Repository.KEY_DQ_ES, Repository.URL_DQ_ES));
        edt_tree_es.setText(preferences.getString(Repository.KEY_TREE_ES, Repository.URL_TREE_ES));
        edt_jar_es.setText(preferences.getString(Repository.KEY_JAR_ES, Repository.URL_JAR_ES));
        edt_links_es.setText(preferences.getString(Repository.KEY_LINKS_ES, Repository.URL_LINKS_ES));
        edt_quiz_es.setText(preferences.getString(Repository.KEY_QUIZ_ES, Repository.URL_QUIZ_ES));
        edt_hja_es.setText(preferences.getString(Repository.KEY_HJA_ES, Repository.URL_HJA_ES));

        edt_news.setHint(Repository.URL_NEWS);

        edt_aipg_en.setHint(Repository.URL_AIPG_EN);
        edt_amtr_en.setHint(Repository.URL_AMTR_EN);
        edt_adipg_en.setHint(Repository.URL_ADIPG_EN);
        edt_admtr_en.setHint(Repository.URL_ADMTR_EN);
        edt_banned_en.setHint(Repository.URL_BANNED_EN);
        edt_cr_en.setHint(Repository.URL_CR_EN);
        edt_dq_en.setHint(Repository.URL_DQ_EN);
        edt_tree_en.setHint(Repository.URL_TREE_EN);
        edt_jar_en.setHint(Repository.URL_JAR_EN);
        edt_links_en.setHint(Repository.URL_LINKS_EN);
        edt_quiz_en.setHint(Repository.URL_QUIZ_EN);
        edt_hja_en.setHint(Repository.URL_HJA_EN);

        edt_aipg_es.setHint(Repository.URL_AIPG_ES);
        edt_amtr_es.setHint(Repository.URL_AMTR_ES);
        edt_adipg_es.setHint(Repository.URL_ADIPG_ES);
        edt_admtr_es.setHint(Repository.URL_ADMTR_ES);
        edt_banned_es.setHint(Repository.URL_BANNED_ES);
        edt_cr_es.setHint(Repository.URL_CR_ES);
        edt_dq_es.setHint(Repository.URL_DQ_ES);
        edt_tree_es.setHint(Repository.URL_TREE_ES);
        edt_jar_es.setHint(Repository.URL_JAR_ES);
        edt_links_es.setHint(Repository.URL_LINKS_ES);
        edt_quiz_es.setHint(Repository.URL_QUIZ_ES);
        edt_hja_es.setHint(Repository.URL_HJA_ES);

        txv_show_life.setText(Repository.StringMap(93));
        txv_show_ftp.setText(Repository.StringMap(76));
        txv_show_links.setText(Repository.StringMap(77));
    }

    private void loadRepositoryData() {
        if(Repository.unlockedFTP) {
            ll_server_code.setVisibility(View.GONE);
            ll_server_settings.setVisibility(View.VISIBLE);
            cb_ftp.setChecked(Repository.allowFTP);
            if(Repository.ftpServer != null) {
                edt_server.setText(Repository.ftpServer);
            }
            if(Repository.ftpUser != null) {
                edt_user.setText(Repository.ftpUser);
            }
            if(Repository.ftpPassword != null) {
                edt_password.setText(Repository.ftpPassword);
            }
        }
        cb_reverse_life.setChecked(Repository.reverseLife);
        switch (Repository.players) {
            case 0:
                rb_0players.setChecked(true);
                break;
            case 2:
                rb_2players.setChecked(true);
                break;
            case 4:
                rb_4players.setChecked(true);;
                break;
            case 6:
                rb_6players.setChecked(true);;
                break;
        }
    }

    private void setListeners() {
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();

                editor.putString(Repository.KEY_NEWS, edt_news.getText().toString());

                editor.putString(Repository.KEY_AIPG_EN, edt_aipg_en.getText().toString());
                editor.putString(Repository.KEY_AMTR_EN, edt_amtr_en.getText().toString());
                editor.putString(Repository.KEY_ADIPG_EN, edt_adipg_en.getText().toString());
                editor.putString(Repository.KEY_ADMTR_EN, edt_admtr_en.getText().toString());
                editor.putString(Repository.KEY_BANNED_EN, edt_banned_en.getText().toString());
                editor.putString(Repository.KEY_CR_EN, edt_cr_en.getText().toString());
                editor.putString(Repository.KEY_DQ_EN, edt_dq_en.getText().toString());
                editor.putString(Repository.KEY_TREE_EN, edt_tree_en.getText().toString());
                editor.putString(Repository.KEY_JAR_EN, edt_jar_en.getText().toString());
                editor.putString(Repository.KEY_LINKS_EN, edt_links_en.getText().toString());
                editor.putString(Repository.KEY_QUIZ_EN, edt_quiz_en.getText().toString());
                editor.putString(Repository.KEY_HJA_EN, edt_hja_en.getText().toString());

                editor.putString(Repository.KEY_AIPG_ES, edt_aipg_es.getText().toString());
                editor.putString(Repository.KEY_AMTR_ES, edt_amtr_es.getText().toString());
                editor.putString(Repository.KEY_ADIPG_ES, edt_adipg_es.getText().toString());
                editor.putString(Repository.KEY_ADMTR_ES, edt_admtr_es.getText().toString());
                editor.putString(Repository.KEY_BANNED_ES, edt_banned_es.getText().toString());
                editor.putString(Repository.KEY_CR_ES, edt_cr_es.getText().toString());
                editor.putString(Repository.KEY_DQ_ES, edt_dq_es.getText().toString());
                editor.putString(Repository.KEY_TREE_ES, edt_tree_es.getText().toString());
                editor.putString(Repository.KEY_JAR_ES, edt_jar_es.getText().toString());
                editor.putString(Repository.KEY_LINKS_ES, edt_links_es.getText().toString());
                editor.putString(Repository.KEY_QUIZ_ES, edt_quiz_es.getText().toString());
                editor.putString(Repository.KEY_HJA_ES, edt_hja_es.getText().toString());

                editor.apply();
                if (ContextCompat.checkSelfPermission(AdvancedSettingsActivity.this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(AdvancedSettingsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(AdvancedSettingsActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    checkConnection();
                }
                else {
                    ActivityCompat.requestPermissions(AdvancedSettingsActivity.this, new String[]{Manifest.permission.INTERNET, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }
            }
        });
        btn_unlockftp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Code.check(Repository.ftpCode, edt_codeftp.getText().toString())) {
                    Repository.unlockedFTP = true;
                    SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
                    editor.putBoolean(Repository.KEY_UNLOCKEDFTP, Repository.unlockedFTP);
                    editor.apply();
                    ll_server_settings.setVisibility(View.VISIBLE);
                    ll_server_code.setVisibility(View.GONE);
                }
                else {
                    txv_codeftp.setText(Code.generateCode());
                }
            }
        });
        cb_ftp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Repository.allowFTP = isChecked;
                SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
                editor.putBoolean(Repository.KEY_ALLOWFTP, Repository.allowFTP);
                editor.apply();
            }
        });
        imv_arrow_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scroll.fullScroll(View.FOCUS_UP);
            }
        });
        imv_arrow_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scroll.fullScroll(View.FOCUS_DOWN);
            }
        });

        showHint = new ShowHint();
        edt_news.setOnLongClickListener(showHint);

        edt_aipg_en.setOnLongClickListener(showHint);
        edt_amtr_en.setOnLongClickListener(showHint);
        edt_adipg_en.setOnLongClickListener(showHint);
        edt_admtr_en.setOnLongClickListener(showHint);
        edt_banned_en.setOnLongClickListener(showHint);
        edt_cr_en.setOnLongClickListener(showHint);
        edt_dq_en.setOnLongClickListener(showHint);
        edt_tree_en.setOnLongClickListener(showHint);
        edt_jar_en.setOnLongClickListener(showHint);
        edt_links_en.setOnLongClickListener(showHint);
        edt_quiz_en.setOnLongClickListener(showHint);
        edt_hja_en.setOnLongClickListener(showHint);

        edt_aipg_es.setOnLongClickListener(showHint);
        edt_amtr_es.setOnLongClickListener(showHint);
        edt_adipg_es.setOnLongClickListener(showHint);
        edt_admtr_es.setOnLongClickListener(showHint);
        edt_banned_es.setOnLongClickListener(showHint);
        edt_cr_es.setOnLongClickListener(showHint);
        edt_dq_es.setOnLongClickListener(showHint);
        edt_tree_es.setOnLongClickListener(showHint);
        edt_jar_es.setOnLongClickListener(showHint);
        edt_links_es.setOnLongClickListener(showHint);
        edt_quiz_es.setOnLongClickListener(showHint);
        edt_hja_es.setOnLongClickListener(showHint);

        txv_show_life.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ll_life.getVisibility() == View.VISIBLE) {
                    ll_life.setVisibility(View.GONE);
                }
                else {
                    ll_life.setVisibility(View.VISIBLE);
                }
            }
        });
        txv_show_ftp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ll_server.getVisibility() == View.VISIBLE) {
                    ll_server.setVisibility(View.GONE);
                }
                else {
                    ll_server.setVisibility(View.VISIBLE);
                }
            }
        });
        txv_show_links.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tl_links.getVisibility() == View.VISIBLE) {
                    tl_links.setVisibility(View.GONE);
                    btn_save.setVisibility(View.GONE);
                }
                else {
                    tl_links.setVisibility(View.VISIBLE);
                    btn_save.setVisibility(View.VISIBLE);
                }
            }
        });

        rg_players.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_0players:
                        Repository.players = 0;
                        break;
                    case R.id.rb_2players:
                        Repository.players = 2;
                        break;
                    case R.id.rb_4players:
                        Repository.players = 4;
                        break;
                    case R.id.rb_6players:
                        Repository.players = 6;
                        break;
                }
                SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
                editor.putInt(Repository.KEY_PLAYERS, Repository.players);
                editor.apply();
            }
        });

        cb_reverse_life.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Repository.reverseLife = isChecked;
                SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
                editor.putBoolean(Repository.KEY_REVERSELIFE, Repository.reverseLife);
                editor.apply();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        String folder = Environment.getExternalStorageDirectory() + File.separator + Repository.FOLDERNAME;
        File directory = new File(folder);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        checkConnection();
    }

    public void checkConnection() {
        Repository.ftpServer = edt_server.getText().toString();
        Repository.ftpUser = edt_user.getText().toString();
        Repository.ftpPassword = edt_password.getText().toString();
        SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
        editor.putString(Repository.KEY_FTPSERVER, Repository.ftpServer);
        editor.putString(Repository.KEY_FTPUSER, Repository.ftpUser);
        editor.putString(Repository.KEY_FTPPASSWORD, Repository.ftpPassword);
        editor.apply();
        //TODO Añadir confimación de conexión
        /*
        AsyncTask< String, Integer, Boolean > task = new AsyncTask< String, Integer, Boolean >()
        {
            @Override
            protected Boolean doInBackground( String... params )
            {
                try {
                    FTPClient mFTP = new FTPClient();
                    mFTP.connect(Repository.ftpServer, Repository.ftpPort);
                    mFTP.login(Repository.ftpUser, Repository.ftpPassword);
                    mFTP.logout();
                    mFTP.disconnect();
                } catch (Exception e) {
                }
                return true;
            }
        };
        task.execute("");
        */
    }

    //Arrows disapear when you full scroll to let you read the first/last question
    public void showOrHideArrows() {
        if(scroll.canScrollVertically(-1)) {
            imv_arrow_up.setVisibility(View.VISIBLE);
        }
        else {
            imv_arrow_up.setVisibility(View.INVISIBLE);
        }
        if(scroll.canScrollVertically(1)) {
            imv_arrow_down.setVisibility(View.VISIBLE);
        }
        else {
            imv_arrow_down.setVisibility(View.INVISIBLE);
        }
    }

    //In older versions you can't hide an arrow after scrolling, so you always show both arrows
    public void showOrHideArrowsOldDevices() {
        //You really don't need the "canScrollVertically(-1)"
        if(scroll.canScrollVertically(1)) {
            imv_arrow_up.setVisibility(View.VISIBLE);
            imv_arrow_down.setVisibility(View.VISIBLE);
        }
        else {
            imv_arrow_up.setVisibility(View.INVISIBLE);
            imv_arrow_down.setVisibility(View.INVISIBLE);
        }
    }

    class ShowHint implements View.OnLongClickListener {
        @Override
        public boolean onLongClick(View view) {
            if(view instanceof EditText && ((EditText) view).getText().toString().equals("")) {
                ((EditText) view).setText(((EditText)view).getHint());
            }
            return false;
        }
    }
}