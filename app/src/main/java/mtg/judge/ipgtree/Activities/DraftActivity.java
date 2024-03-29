package mtg.judge.ipgtree.Activities;

import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import mtg.judge.ipgtree.R;
import mtg.judge.ipgtree.Utilities.Repository;
import mtg.judge.ipgtree.Utilities.Translation;

public class DraftActivity extends AppCompatActivity {

    private Button btn_mode, btn_select, btn_play, btn_reset, btn_last, btn_next;
    private TextView txv_status, txv_time;

    private int[] normal = {40, 40, 35, 30, 25, 25, 20, 20, 15, 10, 10, 5, 5, 5};
    private int[] twohead = {50, 50, 45, 45, 40, 40, 30, 30, 20, 20, 10, 10, 5, 5};
    private int[] rochester = {5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5};

    private final int NORMAL = 0;
    private final int TWOHEADED = 1;
    private final int ROCHESTER = 2;

    private int selected_mode;
    private int pick;
    private int pack;

    private boolean review = false;
    private boolean started = false;
    private boolean pause = false;
    private long last_time = 0;

    private CountDownTimer timer;

    private static final String KEY_MODE = "key_mode";
    private static final String KEY_PICK = "key_pick";
    private static final String KEY_PACK = "key_pack";
    private static final String KEY_REVIEW = "key_review";
    private static final String KEY_STARTED = "key_started";
    private static final String KEY_PAUSE = "key_pause";
    private static final String KEY_TIME = "key_time";

    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_MODE, selected_mode);
        outState.putInt(KEY_PICK, pick);
        outState.putInt(KEY_PACK, pack);
        outState.putBoolean(KEY_REVIEW, review);
        outState.putBoolean(KEY_STARTED, started);
        outState.putBoolean(KEY_PAUSE, pause);
        outState.putLong(KEY_TIME, last_time);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draft);

        if(!Repository.repositoryLoaded) {
            Repository.createRepository(getApplicationContext());
        }

        linkViews();
        loadStrings();
        setListeners();

        if (savedInstanceState != null) {
            selected_mode = savedInstanceState.getInt(KEY_MODE);
            pick = savedInstanceState.getInt(KEY_PICK);
            pack = savedInstanceState.getInt(KEY_PACK);
            review = savedInstanceState.getBoolean(KEY_REVIEW);
            started = savedInstanceState.getBoolean(KEY_STARTED);
            if(started) {
                last_time = savedInstanceState.getLong(KEY_TIME);
                txv_time.setText(last_time/1000 + ".0");
                showStatus();
                if(last_time > 0) {
                    createTimer(last_time);
                    pause = savedInstanceState.getBoolean(KEY_PAUSE);
                    if(pause) {
                        btn_play.setText(Translation.StringMap(18));
                    }
                    else {
                        btn_play.setText(Translation.StringMap(15));
                        timer.start();
                    }
                }
                else {
                    btn_play.setText("---");
                }
            }
            else {
                setTimer();
            }
        }
        else {
            changeMode(NORMAL);
        }
    }

    private void linkViews() {
        txv_status = findViewById(R.id.txv_status);
        txv_time = findViewById(R.id.txv_time);
        btn_mode = findViewById(R.id.btn_mode);
        btn_select = findViewById(R.id.btn_select);
        btn_play = findViewById(R.id.btn_play);
        btn_reset = findViewById(R.id.btn_reset);
        btn_last = findViewById(R.id.btn_last);
        btn_next = findViewById(R.id.btn_next);
    }

    private void loadStrings() {
        btn_mode.setText(Translation.StringMap(12));
        btn_select.setText(Translation.StringMap(17));
        btn_play.setText(Translation.StringMap(20));
        btn_reset.setText(Translation.StringMap(16));
        btn_last.setText(Translation.StringMap(10));
        btn_next.setText(Translation.StringMap(14));
    }

    private void setListeners() {
        btn_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeMode((selected_mode + 1)%3);
            }
        });
        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeMode(selected_mode);
            }
        });
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!started) {
                    timer.start();
                    started = true;
                    btn_play.setText(Translation.StringMap(15));
                }
                else if(last_time > 0) {
                    if(pause) {
                        createTimer(last_time);
                        timer.start();
                        btn_play.setText(Translation.StringMap(15));
                    }
                    else {
                        timer.cancel();
                        pause = true;
                        btn_play.setText(Translation.StringMap(18));
                    }
                }
            }
        });
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTimer();
            }
        });
        btn_last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(review) {
                    //Check to avoid problems with Rochester
                    if(pack > 0) {
                        review = false;
                        pick--;
                    }
                }
                if(pick == 1 && (pack > 1 || selected_mode == ROCHESTER)) {
                    review = true;
                    pack--;
                    pick = 15;
                }
                else if(pick > 1){
                    pick--;
                }
                setTimer();
                btn_play.setText(Translation.StringMap(20));
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(review) {
                    review = false;
                    pack++;
                    pick = 1;
                }
                else if(++pick > 14) {
                    review = true;
                }
                setTimer();
                btn_play.setText(Translation.StringMap(20));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void changeMode(int mode) {
        selected_mode = mode;
        pick = 1;
        pack = 1;
        switch (mode) {
            case NORMAL:
                btn_mode.setText(Translation.StringMap(12));
                review = false;
                break;
            case TWOHEADED:
                btn_mode.setText(Translation.StringMap(13));
                review = false;
                break;
            case ROCHESTER:
                btn_mode.setText(Translation.StringMap(19));
                pack = 0;
                review = true;
                break;
        }
        setTimer();
    }

    public void setTimer() {
        if(timer != null) {
            timer.cancel();
        }
        createTimer(showStatus()*1000);
        btn_play.setText(Translation.StringMap(20));
        started = false;
    }

    public void createTimer(long millis) {
        timer = new CountDownTimer(millis, 100) {

            @Override
            public void onTick(long millisUntilEnd) {
                last_time = millisUntilEnd;
                txv_time.setText(millisUntilEnd/1000 + "." + (millisUntilEnd/100)%10);
            }

            @Override
            public void onFinish() {
                last_time = 0;
                txv_time.setText("0.0");
                btn_play.setText("---");
            }
        };
        txv_time.setText(millis/1000 + ".0");
        last_time = millis;
        pause = false;
    }

    public int showStatus() {
        int seconds = 0;
        if(review) {
            switch (selected_mode) {
                case NORMAL:
                    txv_status.setText(Translation.StringMap(9));
                    seconds = 60 + (30 * (pack-1));
                    break;
                case TWOHEADED:
                    txv_status.setText(Translation.StringMap(9));
                    seconds = 60;
                    break;
                case ROCHESTER:
                    txv_status.setText(Translation.StringMap(11));
                    seconds = 20;
                    break;
            }
        }
        else {
            txv_status.setText(Translation.StringMap(7) + pack + Translation.StringMap(8) + pick);
            switch (selected_mode) {
                case NORMAL:
                    seconds = normal[pick-1];
                    break;
                case TWOHEADED:
                    seconds = twohead[pick-1];
                    break;
                case ROCHESTER:
                    seconds = rochester[pick-1];
                    break;
            }
        }
        return seconds;
    }
}