package com.example.usuario.MagicQuiz.Activities;

import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.usuario.MagicQuiz.R;
import com.example.usuario.MagicQuiz.Repository;

public class TimerActivity extends AppCompatActivity {

    TextView txv_starting_time, txv_time;
    Button btn_edit, btn_play;
    Keyboard keyboard;
    KeyboardView keyboardView;

    int seconds;
    CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        linkViews();
        setListeners();

        seconds = 60*Integer.parseInt(txv_starting_time.getText().toString());
        setStartingTime();
        createTimer();
    }

    public void linkViews() {
        txv_starting_time = findViewById(R.id.txv_starting_time);
        txv_time = findViewById(R.id.txv_time);
        btn_edit = findViewById(R.id.btn_edit);
        btn_play = findViewById(R.id.btn_play);
        keyboard = new Keyboard(TimerActivity.this, R.xml.life_keyboard);
        keyboardView = findViewById(R.id.keyboard);
        keyboardView.setKeyboard(keyboard);
        keyboardView.setPreviewEnabled(false);
    }

    public void setListeners() {
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyboardView.setVisibility(View.VISIBLE);
                btn_edit.setVisibility(View.GONE);
            }
        });
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Checking the text is not the best way, I know
                switch (btn_play.getText().toString()) {
                    case "Comenzar":
                        if(timer != null) {
                            timer.start();
                            btn_play.setText("Reiniciar");
                        }
                        break;
                    case "Reiniciar":
                        if(timer != null) {
                            timer.cancel();
                        }
                        setStartingTime();
                        createTimer();
                        btn_play.setText("Comenzar");
                        break;
                }
            }
        });
        keyboardView.setOnKeyboardActionListener(new KeyboardView.OnKeyboardActionListener() {
            @Override
            public void onPress(int i) {

            }

            @Override
            public void onRelease(int i) {

            }

            @Override
            public void onKey(int i, int[] ints) {
                String number = txv_starting_time.getText().toString();
                switch (i) {
                    case Repository.Code0:
                    case Repository.Code1:
                    case Repository.Code2:
                    case Repository.Code3:
                    case Repository.Code4:
                    case Repository.Code5:
                    case Repository.Code6:
                    case Repository.Code7:
                    case Repository.Code8:
                    case Repository.Code9:
                        if(number.length() < 6) {
                            if(!number.equals("0")) {
                                txv_starting_time.setText(number + i);
                            }
                            else {
                                txv_starting_time.setText(i + "");
                            }
                        }
                        break;
                    case Repository.CodeSign:
                        if(txv_starting_time.getText().toString().charAt(0) != '-') {
                            if(!number.equals("0")) {
                                if(number.length() == 6) {
                                    txv_starting_time.setText("-" + number.substring(0, 6));
                                }
                                else {
                                    txv_starting_time.setText("-" + number);
                                }
                            }
                        }
                        else {
                            txv_starting_time.setText(number.substring(1));
                        }
                        break;
                    case Repository.CodeDelete:
                        if (Math.abs(Integer.parseInt(number)) > 9) {
                            txv_starting_time.setText(number.substring(0, number.length() - 1));
                        }
                        else {
                            txv_starting_time.setText("0");
                        }
                        break;
                    case Repository.CodeCancel:
                        txv_starting_time.setText(seconds/60 + "");
                        keyboardView.setVisibility(View.GONE);
                        btn_edit.setVisibility(View.VISIBLE);
                        break;
                    case Repository.CodeOk:
                        setStartingTime();
                        keyboardView.setVisibility(View.GONE);
                        btn_edit.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onText(CharSequence text) {

            }

            @Override
            public void swipeLeft() {

            }

            @Override
            public void swipeRight() {

            }

            @Override
            public void swipeDown() {

            }

            @Override
            public void swipeUp() {

            }
        });
    }

    public void createTimer(){
        if(timer != null) {
            timer.cancel();
        }

        timer = new CountDownTimer(10000000, 1000) {

            @Override
            public void onTick(long millisUntilEnd) {
                seconds--;
                txv_time.setText(secondsToHoursFormat(seconds));
            }

            @Override
            public void onFinish() {
                timer.start();
            }
        };
    }

    public void setStartingTime() {
        seconds = 60*Integer.parseInt(txv_starting_time.getText().toString());
        txv_time.setText(secondsToHoursFormat(seconds));
    }

    public String secondsToHoursFormat(int seconds) {
        int auxSeconds = seconds;
        String res = "";
        if(auxSeconds < 0) {
            auxSeconds *= -1;
            res = "-";
        }
        res += auxSeconds / 3600 + ":";
        auxSeconds %= 3600;
        if(auxSeconds / 60 < 10) {
            res += "0";
        }
        res += auxSeconds / 60 + ":";
        auxSeconds %= 60;
        if(auxSeconds < 10) {
            res += "0";
        }
        res += auxSeconds;
        return res;
    }
}
