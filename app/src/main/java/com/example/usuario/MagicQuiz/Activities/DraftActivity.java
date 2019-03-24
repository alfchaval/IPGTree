package com.example.usuario.MagicQuiz.Activities;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.usuario.MagicQuiz.R;

public class DraftActivity extends AppCompatActivity {

    Button btn_mode, btn_select, btn_play, btn_reset, btn_last, btn_next;
    TextView txv_status, txv_time;

    int[] normal = {40, 40, 35, 30, 25, 25, 20, 20, 15, 10, 10, 5, 5, 5};
    int[] twohead = {50, 50, 45, 45, 40, 40, 30, 30, 20, 20, 10, 10, 5, 5};
    int[] rochester = {5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5};

    private final int NORMAL = 0;
    private final int TWOHEADED = 1;
    private final int ROCHESTER = 2;

    int selected_mode;
    int pick;
    int pack;

    boolean review;
    boolean started;
    boolean pause;
    long last_time;

    CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draft);

        txv_status = findViewById(R.id.txv_status);
        txv_time = findViewById(R.id.txv_time);

        btn_mode = findViewById(R.id.btn_mode);
        btn_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeMode((selected_mode + 1)%3);
            }
        });
        btn_select = findViewById(R.id.btn_select);
        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeMode(selected_mode);
            }
        });
        btn_play = findViewById(R.id.btn_play);
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!started) {
                    started = true;
                    timer.start();
                    btn_play.setText("Pausar");
                }
                else if(pause) {
                    createTimer(last_time);
                    timer.start();
                    btn_play.setText("Pausar");
                }
                else {
                    timer.cancel();
                    pause = true;
                    btn_play.setText("Reanudar");
                }
            }
        });
        btn_reset = findViewById(R.id.btn_reset);
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTimer();
            }
        });
        btn_last = findViewById(R.id.btn_last);
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
                btn_play.setText("Comenzar");
            }
        });
        btn_next = findViewById(R.id.btn_next);
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
                btn_play.setText("Comenzar");
            }
        });

        changeMode(NORMAL);
    }

    public void changeMode(int mode) {
        selected_mode = mode;
        pick = 1;
        pack = 1;
        switch (mode) {
            case NORMAL:
                btn_mode.setText("Modo: Normal");
                review = false;
                break;
            case TWOHEADED:
                btn_mode.setText("Modo: 2HG");
                review = false;
                break;
            case ROCHESTER:
                btn_mode.setText("Modo: Rochester");
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
        pause = false;
        int seconds = 0;
        if(review) {
            switch (selected_mode) {
                case NORMAL:
                    txv_status.setText("Tiempo para revisar los picks");
                    seconds = 60 + (30 * (pack-1));
                    break;
                case TWOHEADED:
                    txv_status.setText("Tiempo para revisar los picks");
                    seconds = 60;
                    break;
                case ROCHESTER:
                    txv_status.setText("Tiempo para mirar el sobre");
                    seconds = 20;
                    break;
            }
        }
        else {
            txv_status.setText("Sobre: " + pack + "\nCarta: " + pick);
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

        createTimer(seconds*1000);
        started = false;
        txv_time.setText(seconds + ".0");
        btn_play.setText("Comenzar");
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
            }
        };
        pause = false;
    }
}