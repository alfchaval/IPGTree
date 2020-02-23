package mtg.judge.ipgtree.Activities;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import mtg.judge.ipgtree.R;
import mtg.judge.ipgtree.Repository;
import mtg.judge.ipgtree.TimerReceiver;

public class TimerActivity extends AppCompatActivity {

    private LinearLayout ll_set_time, ll_saved_time;
    private TextView txv_starting_time_title, txv_starting_time, txv_time;
    private Button btn_edit, btn_play;
    private Keyboard keyboard;
    private KeyboardView keyboardView;
    private ConstraintLayout cl_buttons;


    private CountDownTimer timer;
    private AlarmManager alarmManager;

    private LinearLayout.LayoutParams layoutParams;
    private final int TEXT_SIZE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        if(!Repository.loaded) {
            Repository.createRepository(getApplicationContext());
        }

        linkViews();
        loadStrings();
        setListeners();

        alarmManager = (AlarmManager) getApplicationContext().getSystemService(ALARM_SERVICE);

        layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,  LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 4, 0, 4);

        txv_starting_time.setText(Repository.milliSeconds/60000 + "");
        if(Repository.startedCountDown) {
            btn_play.setText(Repository.StringMap(1));
            createTimer();
            timer.start();
            for(int i = 0; i < Repository.savedTimes.length(); i++) {
                try {
                    addSavedTime(Repository.savedTimes.getInt(i));
                } catch (JSONException e) {
                }
            }
        }
        else {
            btn_play.setText(Repository.StringMap(20));
            setStartingTime();
            createTimer();
        }
    }

    private void linkViews() {
        ll_set_time = findViewById(R.id.ll_set_time);
        txv_starting_time_title = findViewById(R.id.txv_starting_time_title);
        txv_starting_time = findViewById(R.id.txv_starting_time);
        txv_time = findViewById(R.id.txv_time);
        btn_edit = findViewById(R.id.btn_edit);
        btn_play = findViewById(R.id.btn_play);
        keyboard = new Keyboard(TimerActivity.this, R.xml.life_keyboard);
        keyboardView = findViewById(R.id.keyboard);
        keyboardView.setKeyboard(keyboard);
        keyboardView.setPreviewEnabled(false);
        ll_saved_time = findViewById(R.id.ll_saved_time);
        cl_buttons = findViewById(R.id.cl_buttons);
    }

    private void loadStrings() {
        txv_starting_time_title.setText(Repository.StringMap(22));
        btn_edit.setText(Repository.StringMap(21));
    }

    private void setListeners() {
        txv_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Repository.startedCountDown) {
                    saveTime();
                }
            }
        });
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_set_time.setVisibility(View.VISIBLE);
                cl_buttons.setVisibility(View.GONE);
            }
        });
        btn_play.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if(Repository.startedCountDown) {
                    new AlertDialog.Builder(TimerActivity.this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle(Repository.StringMap(84))
                            .setMessage(Repository.StringMap(83))
                            .setPositiveButton(Repository.StringMap(28), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    stopTimer();
                                }
                            })
                            .setNegativeButton(Repository.StringMap(23), null)
                            .show();
                }
                else {
                    startTimer();
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
                    case Repository.CodeOk:
                        Repository.milliSeconds = Integer.parseInt(txv_starting_time.getText().toString())*60000;
                        SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
                        editor.putInt(Repository.KEY_MILLISECONDS, Repository.milliSeconds);
                        editor.apply();
                        setStartingTime();
                        if(Repository.startedCountDown) {
                            Intent intent = new Intent(TimerActivity.this, TimerReceiver.class);
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(TimerActivity.this, 1 , intent, PendingIntent.FLAG_UPDATE_CURRENT);
                            alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + Repository.milliSeconds, pendingIntent);
                        }
                    case Repository.CodeCancel:
                        ll_set_time.setVisibility(View.GONE);
                        cl_buttons.setVisibility(View.VISIBLE);
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

        timer = new CountDownTimer(10000000, 100) {

            @Override
            public void onTick(long millisUntilEnd) {
                int remaining = Repository.milliSeconds - (int)(System.currentTimeMillis() - Repository.startingTime);
                if(remaining < 1) {
                    txv_time.setTextColor(getResources().getColor(R.color.colorLightRed));
                }
                txv_time.setText(millisecondsToHoursFormat(remaining));
            }

            @Override
            public void onFinish() {
                timer.start();
            }
        };
    }

    public void setStartingTime() {
        if(Repository.milliSeconds > 0) {
            txv_time.setTextColor(getResources().getColor(R.color.colorLightGreen));
        }
        else {
            txv_time.setTextColor(getResources().getColor(R.color.colorLightRed));
        }
        txv_time.setText(millisecondsToHoursFormat(Repository.milliSeconds));
    }

    public String millisecondsToHoursFormat(int milliseconds) {
        int auxSeconds = milliseconds/1000;
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

    public void saveTime() {
        int time = (int)System.currentTimeMillis();
        Repository.savedTimes.put(time);
        SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
        editor.putString(Repository.KEY_SAVEDTIMES, Repository.savedTimes.toString());
        editor.apply();
        addSavedTime(time);
    }


    public void addSavedTime(int time) {
        TextView textView = new TextView(this);
        textView.setLayoutParams(layoutParams);
        textView.setTextSize(TEXT_SIZE);
        textView.setPadding(8, 8, 8, 8);
        String text = millisecondsToHoursFormat(Repository.milliSeconds - (time - (int)Repository.startingTime));
        if(ll_saved_time.getChildCount() > 0) {
            try {
                int lastTime = Repository.savedTimes.getInt(ll_saved_time.getChildCount()-1);
                text += " - " + millisecondsToHoursFormat(time - lastTime);
            } catch (JSONException e) {
            }
        }
        else {
            text += " - " + millisecondsToHoursFormat(time - (int)Repository.startingTime);
        }
        textView.setText(text);
        ll_saved_time.addView(textView);
    }

    public void startTimer() {
        if(timer != null) {
            Intent intent = new Intent(TimerActivity.this, TimerReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(TimerActivity.this, 1 , intent, PendingIntent.FLAG_UPDATE_CURRENT);
            SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();

            Repository.startingTime = System.currentTimeMillis();
            editor.putLong(Repository.KEY_STARTINGTIME, Repository.startingTime);
            Repository.startedCountDown = true;
            timer.start();
            btn_play.setText(Repository.StringMap(1));
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + Repository.milliSeconds, pendingIntent);

            editor.putBoolean(Repository.KEY_STARTEDCOUNTDOWN, Repository.startedCountDown);
            editor.apply();
        }
    }

    public void stopTimer() {
        Intent intent = new Intent(TimerActivity.this, TimerReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(TimerActivity.this, 1 , intent, PendingIntent.FLAG_UPDATE_CURRENT);
        SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();

        if(timer != null) {
            timer.cancel();
        }
        Repository.startedCountDown = false;
        setStartingTime();
        createTimer();
        btn_play.setText(Repository.StringMap(20));
        alarmManager.cancel(pendingIntent);
        Repository.savedTimes = new JSONArray();
        editor.putString(Repository.KEY_SAVEDTIMES, Repository.savedTimes.toString());
        ll_saved_time.removeAllViews();

        editor.putBoolean(Repository.KEY_STARTEDCOUNTDOWN, Repository.startedCountDown);
        editor.apply();
    }
}
