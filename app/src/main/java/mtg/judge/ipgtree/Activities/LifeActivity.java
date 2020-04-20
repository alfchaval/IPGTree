package mtg.judge.ipgtree.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Random;

import mtg.judge.ipgtree.R;
import mtg.judge.ipgtree.Utilities.Repository;

public class LifeActivity extends AppCompatActivity {

    private Button btn_reset, btn_registry, btn_lifepoison, btn_dice;
    private TextView txv_p1minus, txv_p1life, txv_p1add, txv_p1lifetrack, txv_p1setlife;
    private TextView txv_p2minus, txv_p2life, txv_p2add, txv_p2lifetrack, txv_p2setlife;
    private TextView txv_p3minus, txv_p3life, txv_p3add, txv_p3lifetrack, txv_p3setlife;
    private TextView txv_p4minus, txv_p4life, txv_p4add, txv_p4lifetrack, txv_p4setlife;
    private ConstraintLayout cly_p1life, cly_p1setlife, cly_p1block;
    private ConstraintLayout cly_p2life, cly_p2setlife, cly_p2block;
    private ConstraintLayout cly_p3life, cly_p3setlife, cly_p3block;
    private ConstraintLayout cly_p4life, cly_p4setlife, cly_p4block;
    private ScrollView scroll_p1, scroll_p2, scroll_p3, scroll_p4;
    private Keyboard keyboard_p1, keyboard_p2, keyboard_p3, keyboard_p4;
    private KeyboardView keyboardView_p1, keyboardView_p2, keyboardView_p3, keyboardView_p4;
    private CountDownTimer timerp1 = null;
    private CountDownTimer timerp2 = null;
    private CountDownTimer timerp3 = null;
    private CountDownTimer timerp4 = null;
    private Random random;
    private boolean longPressing = false;

    private final int lifeFontSize2pLarge = 100;
    private final int lifeFontSize2pSmall = 70;
    private final int lifeFontSize4pLarge = 60;
    private final int lifeFontSize4pSmall = 40;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life);

        if(!Repository.repositoryLoaded) {
            Repository.createRepository(getApplicationContext());
        }

        linkViews();
        loadStrings();
        random = new Random();
        setListeners();

        btn_dice.setText("D" + Repository.dice);
        txv_p1life.setText(Repository.p1life + "");
        txv_p1lifetrack.setText(Repository.p1log);
        txv_p2life.setText(Repository.p2life + "");
        txv_p2lifetrack.setText(Repository.p2log);
        txv_p3life.setText(Repository.p3life + "");
        txv_p3lifetrack.setText(Repository.p3log);
        txv_p4life.setText(Repository.p4life + "");
        txv_p4lifetrack.setText(Repository.p4log);
        txv_p1life.setTextSize(lifeFontSize2pLarge);
        txv_p2life.setTextSize(lifeFontSize2pLarge);
        txv_p3life.setTextSize(lifeFontSize4pLarge);
        txv_p4life.setTextSize(lifeFontSize4pLarge);
    }

    private void linkViews() {
        btn_reset = findViewById(R.id.btn_reset);
        btn_registry = findViewById(R.id.btn_registry);
        btn_lifepoison = findViewById(R.id.btn_lifepoison);
        btn_dice = findViewById(R.id.btn_dice);

        txv_p1minus = findViewById(R.id.txv_p1minus);
        txv_p1life = findViewById(R.id.txv_p1life);
        txv_p1add = findViewById(R.id.txv_p1add);
        txv_p1lifetrack = findViewById(R.id.txv_p1lifetrack);
        txv_p1setlife = findViewById(R.id.txv_p1setlife);

        txv_p2minus = findViewById(R.id.txv_p2minus);
        txv_p2life = findViewById(R.id.txv_p2life);
        txv_p2add = findViewById(R.id.txv_p2add);
        txv_p2lifetrack = findViewById(R.id.txv_p2lifetrack);
        txv_p2setlife = findViewById(R.id.txv_p2setlife);

        txv_p3minus = findViewById(R.id.txv_p3minus);
        txv_p3life = findViewById(R.id.txv_p3life);
        txv_p3add = findViewById(R.id.txv_p3add);
        txv_p3lifetrack = findViewById(R.id.txv_p3lifetrack);
        txv_p3setlife = findViewById(R.id.txv_p3setlife);

        txv_p4minus = findViewById(R.id.txv_p4minus);
        txv_p4life = findViewById(R.id.txv_p4life);
        txv_p4add = findViewById(R.id.txv_p4add);
        txv_p4lifetrack = findViewById(R.id.txv_p4lifetrack);
        txv_p4setlife = findViewById(R.id.txv_p4setlife);

        cly_p1block = findViewById(R.id.cly_p1block);
        cly_p1life = findViewById(R.id.cly_p1life);
        cly_p1setlife = findViewById(R.id.cly_p1setlife);

        cly_p2block = findViewById(R.id.cly_p2block);
        cly_p2life = findViewById(R.id.cly_p2life);
        cly_p2setlife = findViewById(R.id.cly_p2setlife);

        cly_p3block = findViewById(R.id.cly_p3block);
        cly_p3life = findViewById(R.id.cly_p3life);
        cly_p3setlife = findViewById(R.id.cly_p3setlife);

        cly_p4block = findViewById(R.id.cly_p4block);
        cly_p4life = findViewById(R.id.cly_p4life);
        cly_p4setlife = findViewById(R.id.cly_p4setlife);

        scroll_p1 = findViewById(R.id.scroll_p1);
        scroll_p2 = findViewById(R.id.scroll_p2);
        scroll_p3 = findViewById(R.id.scroll_p3);
        scroll_p4 = findViewById(R.id.scroll_p4);

        keyboard_p1 = new Keyboard(LifeActivity.this, R.xml.life_keyboard);
        keyboard_p2 = new Keyboard(LifeActivity.this, R.xml.life_keyboard);
        keyboard_p3 = new Keyboard(LifeActivity.this, R.xml.life_keyboard);
        keyboard_p4 = new Keyboard(LifeActivity.this, R.xml.life_keyboard);
        keyboardView_p1 = findViewById(R.id.keyboardView_p1);
        keyboardView_p2 = findViewById(R.id.keyboardView_p2);
        keyboardView_p3 = findViewById(R.id.keyboardView_p3);
        keyboardView_p4 = findViewById(R.id.keyboardView_p4);
        keyboardView_p1.setKeyboard(keyboard_p1);
        keyboardView_p2.setKeyboard(keyboard_p2);
        keyboardView_p3.setKeyboard(keyboard_p3);
        keyboardView_p4.setKeyboard(keyboard_p4);
        keyboardView_p1.setPreviewEnabled(false);
        keyboardView_p2.setPreviewEnabled(false);
        keyboardView_p3.setPreviewEnabled(false);
        keyboardView_p4.setPreviewEnabled(false);
    }

    private void loadStrings() {
        btn_reset.setText(Repository.StringMap(25));
        btn_registry.setText(Repository.StringMap(24));
    }

    private void setListeners() {
        keyboardView_p1.setOnKeyboardActionListener(new KeyboardView.OnKeyboardActionListener() {
            @Override
            public void onPress(int i) {
            }

            @Override
            public void onRelease(int i) {
            }

            @Override
            public void onKey(int i, int[] ints) {
                String number = txv_p1setlife.getText().toString();
                if(Repository.mode == Repository.POISON) {
                    number = number.substring(0, number.length()-1);
                }
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
                        if(!number.equals("0")) {
                            txv_p1setlife.setText(number + i);
                        }
                        else {
                            txv_p1setlife.setText(i + "");
                        }
                        if(Repository.mode == Repository.POISON) {
                            txv_p1setlife.setText(txv_p1setlife.getText().toString() + Repository.POISON);
                        }
                        break;
                    case Repository.CodeSign:
                        if(txv_p1setlife.getText().toString().charAt(0) != '-') {
                            if(!number.equals("0")) {
                                txv_p1setlife.setText("-" + number);
                            }
                        }
                        else {
                            txv_p1setlife.setText(number.substring(1));
                        }
                        if(Repository.mode == Repository.POISON) {
                            txv_p1setlife.setText(txv_p1setlife.getText().toString() + Repository.POISON);
                        }
                        break;
                    case Repository.CodeDelete:
                        if (Math.abs(Integer.parseInt(number)) > 9) {
                            txv_p1setlife.setText(number.substring(0, number.length() - 1));
                        }
                        else {
                            txv_p1setlife.setText("0");
                        }
                        if(Repository.mode == Repository.POISON) {
                            txv_p1setlife.setText(txv_p1setlife.getText().toString() + Repository.POISON);
                        }
                        break;
                    case Repository.CodeCancel:
                        cly_p1life.setVisibility(View.VISIBLE);
                        cly_p1setlife.setVisibility(View.INVISIBLE);
                        if(scroll_p2.getVisibility() == View.VISIBLE) {
                            scroll_p1.setVisibility(View.VISIBLE);
                            scroll_p1.fullScroll(View.FOCUS_DOWN);
                        }
                        break;
                    case Repository.CodeOk:
                        if(number.length() > 9) {
                            number = number.substring(0, 9);
                        }
                        if(Repository.mode == Repository.LIFE) {
                            Repository.p1life = Integer.parseInt(number);
                            SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
                            editor.putInt(Repository.KEY_P1LIFE, Repository.p1life);
                            txv_p1life.setText(Repository.p1life + "");
                            SendFile(Repository.P1_LIFE_FILENAME, Repository.p1life + "");
                            Repository.p1log += "\n" + number;
                            editor.putString(Repository.KEY_P1LOG, Repository.p1log);
                            editor.apply();
                            txv_p1lifetrack.setText(Repository.p1log);
                            SendFile(Repository.P1_LOG_FILENAME, txv_p1lifetrack.getText().toString());
                        }
                        else {
                            Repository.p1poison = Integer.parseInt(number);
                            SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
                            editor.putInt(Repository.KEY_P1POISON, Repository.p1poison);
                            txv_p1life.setText(Repository.p1poison + "" + Repository.POISON);
                            SendFile(Repository.P1_POISON_FILENAME, Repository.p1poison + "");
                            Repository.p1log += "\n" + number + Repository.POISON;
                            editor.putString(Repository.KEY_P1LOG, Repository.p1log);
                            editor.apply();
                            txv_p1lifetrack.setText(Repository.p1log);
                            SendFile(Repository.P1_LOG_FILENAME, txv_p1lifetrack.getText().toString());
                        }
                        cly_p1life.setVisibility(View.VISIBLE);
                        cly_p1setlife.setVisibility(View.INVISIBLE);
                        if(scroll_p2.getVisibility() == View.VISIBLE) {
                            scroll_p1.setVisibility(View.VISIBLE);
                            scroll_p1.fullScroll(View.FOCUS_DOWN);
                        }
                        break;
                }
            }

            @Override
            public void onText(CharSequence charSequence) {
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
        keyboardView_p2.setOnKeyboardActionListener(new KeyboardView.OnKeyboardActionListener() {
            @Override
            public void onPress(int i) {
            }

            @Override
            public void onRelease(int i) {
            }

            @Override
            public void onKey(int i, int[] ints) {
                String number = txv_p2setlife.getText().toString();
                if(Repository.mode == Repository.POISON) {
                    number = number.substring(0, number.length()-1);
                }
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
                        if(!number.equals("0")) {
                            txv_p2setlife.setText(number + i);
                        }
                        else {
                            txv_p2setlife.setText(i + "");
                        }
                        if(Repository.mode == Repository.POISON) {
                            txv_p2setlife.setText(txv_p2setlife.getText().toString() + Repository.POISON);
                        }
                        break;
                    case Repository.CodeSign:
                        if(txv_p2setlife.getText().toString().charAt(0) != '-') {
                            if(!number.equals("0")) {
                                txv_p2setlife.setText("-" + number);
                            }
                        }
                        else {
                            txv_p2setlife.setText(number.substring(1));
                        }
                        if(Repository.mode == Repository.POISON) {
                            txv_p2setlife.setText(txv_p2setlife.getText().toString() + Repository.POISON);
                        }
                        break;
                    case Repository.CodeDelete:
                        if (Math.abs(Integer.parseInt(number)) > 9) {
                            txv_p2setlife.setText(number.substring(0, number.length() - 1));
                        }
                        else {
                            txv_p2setlife.setText("0");
                        }
                        if(Repository.mode == Repository.POISON) {
                            txv_p2setlife.setText(txv_p2setlife.getText().toString() + Repository.POISON);
                        }
                        break;
                    case Repository.CodeCancel:
                        cly_p2life.setVisibility(View.VISIBLE);
                        cly_p2setlife.setVisibility(View.INVISIBLE);
                        if(scroll_p1.getVisibility() == View.VISIBLE) {
                            scroll_p2.setVisibility(View.VISIBLE);
                            scroll_p2.fullScroll(View.FOCUS_DOWN);
                        }
                        break;
                    case Repository.CodeOk:
                        if(number.length() > 9) {
                            number = number.substring(0, 9);
                        }
                        if(Repository.mode == Repository.LIFE) {
                            Repository.p2life = Integer.parseInt(number);
                            SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
                            editor.putInt(Repository.KEY_P2LIFE, Repository.p2life);
                            txv_p2life.setText(Repository.p2life + "");
                            SendFile(Repository.P2_LIFE_FILENAME, Repository.p2life + "");
                            Repository.p2log += "\n" + number;
                            editor.putString(Repository.KEY_P2LOG, Repository.p2log);
                            editor.apply();
                            txv_p2lifetrack.setText(Repository.p2log);
                            SendFile(Repository.P2_LOG_FILENAME, txv_p2lifetrack.getText().toString());
                        }
                        else {
                            Repository.p2poison = Integer.parseInt(number);
                            SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
                            editor.putInt(Repository.KEY_P1POISON, Repository.p1poison);
                            txv_p2life.setText(Repository.p2poison + "" + Repository.POISON);
                            SendFile(Repository.P2_LIFE_FILENAME, Repository.p2poison + "");
                            Repository.p2log += "\n" + number + Repository.POISON;
                            editor.putString(Repository.KEY_P2LOG, Repository.p2log);
                            editor.apply();
                            txv_p2lifetrack.setText(Repository.p2log);
                            SendFile(Repository.P2_LOG_FILENAME, txv_p2lifetrack.getText().toString());
                        }
                        cly_p2life.setVisibility(View.VISIBLE);
                        cly_p2setlife.setVisibility(View.INVISIBLE);
                        if(scroll_p1.getVisibility() == View.VISIBLE) {
                            scroll_p2.setVisibility(View.VISIBLE);
                            scroll_p2.fullScroll(View.FOCUS_DOWN);
                        }
                        break;
                }
            }

            @Override
            public void onText(CharSequence charSequence) {
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
        keyboardView_p3.setOnKeyboardActionListener(new KeyboardView.OnKeyboardActionListener() {
            @Override
            public void onPress(int i) {
            }

            @Override
            public void onRelease(int i) {
            }

            @Override
            public void onKey(int i, int[] ints) {
                String number = txv_p3setlife.getText().toString();
                if(Repository.mode == Repository.POISON) {
                    number = number.substring(0, number.length()-1);
                }
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
                        if(!number.equals("0")) {
                            txv_p3setlife.setText(number + i);
                        }
                        else {
                            txv_p3setlife.setText(i + "");
                        }
                        if(Repository.mode == Repository.POISON) {
                            txv_p3setlife.setText(txv_p3setlife.getText().toString() + Repository.POISON);
                        }
                        break;
                    case Repository.CodeSign:
                        if(txv_p3setlife.getText().toString().charAt(0) != '-') {
                            if(!number.equals("0")) {
                                txv_p3setlife.setText("-" + number);
                            }
                        }
                        else {
                            txv_p3setlife.setText(number.substring(1));
                        }
                        if(Repository.mode == Repository.POISON) {
                            txv_p3setlife.setText(txv_p3setlife.getText().toString() + Repository.POISON);
                        }
                        break;
                    case Repository.CodeDelete:
                        if (Math.abs(Integer.parseInt(number)) > 9) {
                            txv_p3setlife.setText(number.substring(0, number.length() - 1));
                        }
                        else {
                            txv_p3setlife.setText("0");
                        }
                        if(Repository.mode == Repository.POISON) {
                            txv_p3setlife.setText(txv_p3setlife.getText().toString() + Repository.POISON);
                        }
                        break;
                    case Repository.CodeCancel:
                        cly_p3life.setVisibility(View.VISIBLE);
                        cly_p3setlife.setVisibility(View.INVISIBLE);
                        if(scroll_p1.getVisibility() == View.VISIBLE) {
                            scroll_p3.setVisibility(View.VISIBLE);
                            scroll_p3.fullScroll(View.FOCUS_DOWN);
                        }
                        break;
                    case Repository.CodeOk:
                        if(number.length() > 9) {
                            number = number.substring(0, 9);
                        }
                        if(Repository.mode == Repository.LIFE) {
                            Repository.p3life = Integer.parseInt(number);
                            SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
                            editor.putInt(Repository.KEY_P3LIFE, Repository.p3life);
                            txv_p3life.setText(Repository.p3life + "");
                            SendFile(Repository.P3_LIFE_FILENAME, Repository.p3life + "");
                            Repository.p3log += "\n" + number;
                            editor.putString(Repository.KEY_P3LOG, Repository.p3log);
                            editor.apply();
                            txv_p3lifetrack.setText(Repository.p3log);
                            SendFile(Repository.P3_LOG_FILENAME, txv_p3lifetrack.getText().toString());
                        }
                        else {
                            Repository.p3poison = Integer.parseInt(number);
                            SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
                            editor.putInt(Repository.KEY_P1POISON, Repository.p1poison);
                            txv_p3life.setText(Repository.p3poison + "" + Repository.POISON);
                            SendFile(Repository.P3_LIFE_FILENAME, Repository.p3poison + "");
                            Repository.p3log += "\n" + number + Repository.POISON;
                            editor.putString(Repository.KEY_P3LOG, Repository.p3log);
                            editor.apply();
                            txv_p3lifetrack.setText(Repository.p3log);
                            SendFile(Repository.P3_LOG_FILENAME, txv_p3lifetrack.getText().toString());
                        }
                        cly_p3life.setVisibility(View.VISIBLE);
                        cly_p3setlife.setVisibility(View.INVISIBLE);
                        if(scroll_p1.getVisibility() == View.VISIBLE) {
                            scroll_p3.setVisibility(View.VISIBLE);
                            scroll_p3.fullScroll(View.FOCUS_DOWN);
                        }
                        break;
                }
            }

            @Override
            public void onText(CharSequence charSequence) {
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
        keyboardView_p4.setOnKeyboardActionListener(new KeyboardView.OnKeyboardActionListener() {
            @Override
            public void onPress(int i) {
            }

            @Override
            public void onRelease(int i) {
            }

            @Override
            public void onKey(int i, int[] ints) {
                String number = txv_p4setlife.getText().toString();
                if(Repository.mode == Repository.POISON) {
                    number = number.substring(0, number.length()-1);
                }
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
                        if(!number.equals("0")) {
                            txv_p4setlife.setText(number + i);
                        }
                        else {
                            txv_p4setlife.setText(i + "");
                        }
                        if(Repository.mode == Repository.POISON) {
                            txv_p4setlife.setText(txv_p4setlife.getText().toString() + Repository.POISON);
                        }
                        break;
                    case Repository.CodeSign:
                        if(txv_p4setlife.getText().toString().charAt(0) != '-') {
                            if(!number.equals("0")) {
                                txv_p4setlife.setText("-" + number);
                            }
                        }
                        else {
                            txv_p4setlife.setText(number.substring(1));
                        }
                        if(Repository.mode == Repository.POISON) {
                            txv_p4setlife.setText(txv_p4setlife.getText().toString() + Repository.POISON);
                        }
                        break;
                    case Repository.CodeDelete:
                        if (Math.abs(Integer.parseInt(number)) > 9) {
                            txv_p4setlife.setText(number.substring(0, number.length() - 1));
                        }
                        else {
                            txv_p4setlife.setText("0");
                        }
                        if(Repository.mode == Repository.POISON) {
                            txv_p4setlife.setText(txv_p4setlife.getText().toString() + Repository.POISON);
                        }
                        break;
                    case Repository.CodeCancel:
                        cly_p4life.setVisibility(View.VISIBLE);
                        cly_p4setlife.setVisibility(View.INVISIBLE);
                        if(scroll_p1.getVisibility() == View.VISIBLE) {
                            scroll_p4.setVisibility(View.VISIBLE);
                            scroll_p4.fullScroll(View.FOCUS_DOWN);
                        }
                        break;
                    case Repository.CodeOk:
                        if(number.length() > 9) {
                            number = number.substring(0, 9);
                        }
                        if(Repository.mode == Repository.LIFE) {
                            Repository.p4life = Integer.parseInt(number);
                            SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
                            editor.putInt(Repository.KEY_P4LIFE, Repository.p4life);
                            txv_p4life.setText(Repository.p4life + "");
                            SendFile(Repository.P4_LIFE_FILENAME, Repository.p4life + "");
                            Repository.p4log += "\n" + number;
                            editor.putString(Repository.KEY_P4LOG, Repository.p4log);
                            editor.apply();
                            txv_p4lifetrack.setText(Repository.p4log);
                            SendFile(Repository.P4_LOG_FILENAME, txv_p4lifetrack.getText().toString());
                        }
                        else {
                            Repository.p4poison = Integer.parseInt(number);
                            SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
                            editor.putInt(Repository.KEY_P1POISON, Repository.p1poison);
                            txv_p4life.setText(Repository.p4poison + "" + Repository.POISON);
                            SendFile(Repository.P4_LIFE_FILENAME, Repository.p4poison + "");
                            Repository.p4log += "\n" + number + Repository.POISON;
                            editor.putString(Repository.KEY_P4LOG, Repository.p4log);
                            editor.apply();
                            txv_p4lifetrack.setText(Repository.p4log);
                            SendFile(Repository.P4_LOG_FILENAME, txv_p4lifetrack.getText().toString());
                        }
                        cly_p4life.setVisibility(View.VISIBLE);
                        cly_p4setlife.setVisibility(View.INVISIBLE);
                        if(scroll_p1.getVisibility() == View.VISIBLE) {
                            scroll_p4.setVisibility(View.VISIBLE);
                            scroll_p4.fullScroll(View.FOCUS_DOWN);
                        }
                        break;
                }
            }

            @Override
            public void onText(CharSequence charSequence) {
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
        txv_p1minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changep1life(-1);
            }
        });
        txv_p2minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changep2life(-1);
            }
        });
        txv_p3minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changep3life(-1);
            }
        });
        txv_p4minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changep4life(-1);
            }
        });
        txv_p1add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changep1life(1);
            }
        });
        txv_p2add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changep2life(1);
            }
        });
        txv_p3add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changep3life(1);
            }
        });
        txv_p4add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changep4life(1);
            }
        });
        txv_p1life.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(timerp1 != null) {
                    timerp1.cancel();
                }
                if (Repository.mode == Repository.LIFE) {
                    int newlife = Integer.parseInt(txv_p1life.getText().toString());
                    if(Repository.p1life != newlife) {
                        Repository.p1life = newlife;
                        SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
                        editor.putInt(Repository.KEY_P1LIFE, Repository.p1life);
                        SendFile(Repository.P1_LIFE_FILENAME, Repository.p1life + "");
                        Repository.p1log += "\n" + Repository.p1life;
                        editor.putString(Repository.KEY_P1LOG, Repository.p1log);
                        editor.apply();
                        txv_p1lifetrack.setText(Repository.p1log);
                        SendFile(Repository.P1_LOG_FILENAME, Repository.p1log);
                    }
                    txv_p1setlife.setText(Repository.p1life + "");
                }
                else {
                    String s = txv_p1life.getText().toString();
                    int newlife = Integer.parseInt(s.substring(0, s.length()-1));
                    if(Repository.p1poison != newlife) {
                        Repository.p1poison = newlife;
                        SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
                        editor.putInt(Repository.KEY_P1POISON, Repository.p1poison);
                        SendFile(Repository.P1_POISON_FILENAME, Repository.p1poison + "");
                        Repository.p1log += "\n" + Repository.p1poison + Repository.POISON;
                        editor.putString(Repository.KEY_P1LOG, Repository.p1log);
                        editor.apply();
                        txv_p1lifetrack.setText(Repository.p1log);
                        SendFile(Repository.P1_LOG_FILENAME, Repository.p1log);
                    }
                    txv_p1setlife.setText(Repository.p1poison + "" + Repository.POISON);
                }
                cly_p1life.setVisibility(View.INVISIBLE);
                scroll_p1.setVisibility(View.GONE);
                cly_p1setlife.setVisibility(View.VISIBLE);

                return true;
            }
        });
        txv_p2life.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(timerp2 != null) {
                    timerp2.cancel();
                }
                if (Repository.mode == Repository.LIFE) {
                    int newlife = Integer.parseInt(txv_p2life.getText().toString());
                    if(Repository.p2life != newlife) {
                        Repository.p2life = newlife;
                        SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
                        editor.putInt(Repository.KEY_P2LIFE, Repository.p2life);
                        SendFile(Repository.P2_LIFE_FILENAME, Repository.p2life + "");
                        Repository.p2log += "\n" + Repository.p2life;
                        editor.putString(Repository.KEY_P2LOG, Repository.p2log);
                        editor.apply();
                        txv_p2lifetrack.setText(Repository.p2log);
                        SendFile(Repository.P1_LOG_FILENAME, Repository.p2log);
                    }
                    txv_p2setlife.setText(Repository.p2life + "");
                }
                else {
                    String s = txv_p2life.getText().toString();
                    int newlife = Integer.parseInt(s.substring(0, s.length()-1));
                    if(Repository.p2poison != newlife) {
                        Repository.p2poison = newlife;
                        SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
                        editor.putInt(Repository.KEY_P1POISON, Repository.p1poison);
                        SendFile(Repository.P2_POISON_FILENAME, Repository.p2poison + "");
                        Repository.p2log += "\n" + Repository.p2poison + Repository.POISON;
                        editor.putString(Repository.KEY_P2LOG, Repository.p2log);
                        editor.apply();
                        txv_p2lifetrack.setText(Repository.p2log);
                        SendFile(Repository.P1_LOG_FILENAME, Repository.p2log);
                    }
                    txv_p2setlife.setText(Repository.p2poison + "" + Repository.POISON);
                }
                cly_p2life.setVisibility(View.INVISIBLE);
                scroll_p2.setVisibility(View.GONE);
                cly_p2setlife.setVisibility(View.VISIBLE);
                return true;
            }
        });
        txv_p3life.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(timerp3 != null) {
                    timerp3.cancel();
                }
                if (Repository.mode == Repository.LIFE) {
                    int newlife = Integer.parseInt(txv_p3life.getText().toString());
                    if(Repository.p3life != newlife) {
                        Repository.p3life = newlife;
                        SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
                        editor.putInt(Repository.KEY_P3LIFE, Repository.p3life);
                        SendFile(Repository.P3_LIFE_FILENAME, Repository.p3life + "");
                        Repository.p3log += "\n" + Repository.p3life;
                        editor.putString(Repository.KEY_P3LOG, Repository.p3log);
                        editor.apply();
                        txv_p3lifetrack.setText(Repository.p3log);
                        SendFile(Repository.P1_LOG_FILENAME, Repository.p3log);
                    }
                    txv_p3setlife.setText(Repository.p3life + "");
                }
                else {
                    String s = txv_p3life.getText().toString();
                    int newlife = Integer.parseInt(s.substring(0, s.length()-1));
                    if(Repository.p3poison != newlife) {
                        Repository.p3poison = newlife;
                        SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
                        editor.putInt(Repository.KEY_P1POISON, Repository.p1poison);
                        SendFile(Repository.P3_POISON_FILENAME, Repository.p3poison + "");
                        Repository.p3log += "\n" + Repository.p3poison + Repository.POISON;
                        editor.putString(Repository.KEY_P3LOG, Repository.p3log);
                        editor.apply();
                        txv_p3lifetrack.setText(Repository.p3log);
                        SendFile(Repository.P1_LOG_FILENAME, Repository.p3log);
                    }
                    txv_p3setlife.setText(Repository.p3poison + "" + Repository.POISON);
                }
                cly_p3life.setVisibility(View.INVISIBLE);
                scroll_p3.setVisibility(View.GONE);
                cly_p3setlife.setVisibility(View.VISIBLE);
                return true;
            }
        });
        txv_p4life.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(timerp4 != null) {
                    timerp4.cancel();
                }
                if (Repository.mode == Repository.LIFE) {
                    int newlife = Integer.parseInt(txv_p4life.getText().toString());
                    if(Repository.p4life != newlife) {
                        Repository.p4life = newlife;
                        SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
                        editor.putInt(Repository.KEY_P4LIFE, Repository.p4life);
                        SendFile(Repository.P4_LIFE_FILENAME, Repository.p4life + "");
                        Repository.p4log += "\n" + Repository.p4life;
                        editor.putString(Repository.KEY_P4LOG, Repository.p4log);
                        editor.apply();
                        txv_p4lifetrack.setText(Repository.p4log);
                        SendFile(Repository.P1_LOG_FILENAME, Repository.p4log);
                    }
                    txv_p4setlife.setText(Repository.p4life + "");
                }
                else {
                    String s = txv_p4life.getText().toString();
                    int newlife = Integer.parseInt(s.substring(0, s.length()-1));
                    if(Repository.p4poison != newlife) {
                        Repository.p4poison = newlife;
                        SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
                        editor.putInt(Repository.KEY_P1POISON, Repository.p1poison);
                        SendFile(Repository.P4_POISON_FILENAME, Repository.p4poison + "");
                        Repository.p4log += "\n" + Repository.p4poison + Repository.POISON;
                        editor.putString(Repository.KEY_P4LOG, Repository.p4log);
                        editor.apply();
                        txv_p4lifetrack.setText(Repository.p4log);
                        SendFile(Repository.P1_LOG_FILENAME, Repository.p4log);
                    }
                    txv_p4setlife.setText(Repository.p4poison + "" + Repository.POISON);
                }
                cly_p4life.setVisibility(View.INVISIBLE);
                scroll_p4.setVisibility(View.GONE);
                cly_p4setlife.setVisibility(View.VISIBLE);
                return true;
            }
        });

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(LifeActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(Repository.StringMap(27))
                        .setMessage(Repository.StringMap(26))
                        .setPositiveButton(Repository.StringMap(28), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                reset();
                            }
                        })
                        .setNegativeButton(Repository.StringMap(23), null)
                        .show();
            }
        });
        btn_registry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(longPressing)
                {
                    longPressing = false;
                }
                else
                {
                    if (scroll_p1.getVisibility() == View.GONE || scroll_p2.getVisibility() == View.GONE || scroll_p3.getVisibility() == View.GONE || scroll_p4.getVisibility() == View.GONE) {
                        scroll_p1.setVisibility(View.VISIBLE);
                        scroll_p1.fullScroll(View.FOCUS_DOWN);
                        scroll_p2.setVisibility(View.VISIBLE);
                        scroll_p2.fullScroll(View.FOCUS_DOWN);
                        scroll_p3.setVisibility(View.VISIBLE);
                        scroll_p3.fullScroll(View.FOCUS_DOWN);
                        scroll_p4.setVisibility(View.VISIBLE);
                        scroll_p4.fullScroll(View.FOCUS_DOWN);
                        if(cly_p3block.getVisibility() == View.VISIBLE || cly_p4block.getVisibility() == View.VISIBLE)
                        {
                            txv_p1life.setTextSize(lifeFontSize4pSmall);
                            txv_p2life.setTextSize(lifeFontSize4pSmall);
                            txv_p3life.setTextSize(lifeFontSize4pSmall);
                            txv_p4life.setTextSize(lifeFontSize4pSmall);
                        }
                        else
                        {
                            txv_p1life.setTextSize(lifeFontSize2pSmall);
                            txv_p2life.setTextSize(lifeFontSize2pSmall);
                            txv_p3life.setTextSize(lifeFontSize2pSmall);
                            txv_p4life.setTextSize(lifeFontSize2pSmall);
                        }
                    } else {
                        scroll_p1.setVisibility(View.GONE);
                        scroll_p2.setVisibility(View.GONE);
                        scroll_p3.setVisibility(View.GONE);
                        scroll_p4.setVisibility(View.GONE);
                        if(cly_p3block.getVisibility() == View.VISIBLE || cly_p4block.getVisibility() == View.VISIBLE)
                        {
                            txv_p1life.setTextSize(lifeFontSize4pLarge);
                            txv_p2life.setTextSize(lifeFontSize4pLarge);
                            txv_p3life.setTextSize(lifeFontSize4pLarge);
                            txv_p4life.setTextSize(lifeFontSize4pLarge);
                        }
                        else
                        {
                            txv_p1life.setTextSize(lifeFontSize2pLarge);
                            txv_p2life.setTextSize(lifeFontSize2pLarge);
                            txv_p3life.setTextSize(lifeFontSize2pLarge);
                            txv_p4life.setTextSize(lifeFontSize2pLarge);
                        }
                    }
                }
            }
        });
        btn_registry.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                scroll_p1.setVisibility(View.GONE);
                scroll_p2.setVisibility(View.GONE);
                scroll_p3.setVisibility(View.GONE);
                scroll_p4.setVisibility(View.GONE);
                if(cly_p3block.getVisibility() == View.VISIBLE || cly_p4block.getVisibility() == View.VISIBLE)
                {
                    //cly_p1block.setRotation(180);
                    //cly_p2block.setRotation(0);
                    cly_p3block.setVisibility(View.GONE);
                    cly_p4block.setVisibility(View.GONE);
                    txv_p1life.setTextSize(lifeFontSize2pLarge);
                    txv_p2life.setTextSize(lifeFontSize2pLarge);
                }
                else
                {
                    //cly_p1block.setRotation(270);
                    //cly_p2block.setRotation(270);
                    cly_p3block.setVisibility(View.VISIBLE);
                    cly_p4block.setVisibility(View.VISIBLE);
                    txv_p1life.setTextSize(lifeFontSize4pLarge);
                    txv_p2life.setTextSize(lifeFontSize4pLarge);
                }
                longPressing = true;
                return false;
            }
        });
        btn_lifepoison.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timerp1 != null) {
                    timerp1.cancel();
                    if (Repository.mode == Repository.LIFE) {
                        int newlife = Integer.parseInt(txv_p1life.getText().toString());
                        if(Repository.p1life != newlife) {
                            Repository.p1life = newlife;
                            SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
                            editor.putInt(Repository.KEY_P1LIFE, Repository.p1life);
                            SendFile(Repository.P1_LIFE_FILENAME, Repository.p1life + "");
                            Repository.p1log += "\n" + Repository.p1life;
                            editor.putString(Repository.KEY_P1LOG, Repository.p1log);
                            editor.apply();
                            txv_p1lifetrack.setText(Repository.p1log);
                            SendFile(Repository.P1_LOG_FILENAME, Repository.p1log);
                        }
                    }
                    else {
                        String s = txv_p1life.getText().toString();
                        int newlife = Integer.parseInt(s.substring(0, s.length()-1));
                        if(Repository.p1poison != newlife) {
                            Repository.p1poison = newlife;
                            SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
                            editor.putInt(Repository.KEY_P1POISON, Repository.p1poison);
                            SendFile(Repository.P1_POISON_FILENAME, Repository.p1poison + "");
                            Repository.p1log += "\n" + Repository.p1poison + Repository.POISON;
                            editor.putString(Repository.KEY_P1LOG, Repository.p1log);
                            editor.apply();
                            txv_p1lifetrack.setText(Repository.p1log);
                            SendFile(Repository.P1_LOG_FILENAME, Repository.p1log);
                        }
                    }
                }
                if(timerp2 != null) {
                    timerp2.cancel();
                    if (Repository.mode == Repository.LIFE) {
                        int newlife = Integer.parseInt(txv_p2life.getText().toString());
                        if(Repository.p2life != newlife) {
                            Repository.p2life = newlife;
                            SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
                            editor.putInt(Repository.KEY_P2LIFE, Repository.p2life);
                            SendFile(Repository.P2_LIFE_FILENAME, Repository.p2life + "");
                            Repository.p2log += "\n" + Repository.p2life;
                            editor.putString(Repository.KEY_P2LOG, Repository.p2log);
                            editor.apply();
                            txv_p2lifetrack.setText(Repository.p2log);
                            SendFile(Repository.P2_LOG_FILENAME, Repository.p2log);
                        }
                    }
                    else {
                        String s = txv_p2life.getText().toString();
                        int newlife = Integer.parseInt(s.substring(0, s.length()-1));
                        if(Repository.p2poison != newlife) {
                            Repository.p2poison = newlife;
                            SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
                            editor.putInt(Repository.KEY_P1POISON, Repository.p1poison);
                            SendFile(Repository.P2_POISON_FILENAME, Repository.p2poison + "");
                            Repository.p2log += "\n" + Repository.p2poison + Repository.POISON;
                            editor.putString(Repository.KEY_P2LOG, Repository.p2log);
                            editor.apply();
                            txv_p2lifetrack.setText(Repository.p2log);
                            SendFile(Repository.P2_LOG_FILENAME, Repository.p2log);
                        }
                    }
                }
                if(timerp3 != null) {
                    timerp3.cancel();
                    if (Repository.mode == Repository.LIFE) {
                        int newlife = Integer.parseInt(txv_p3life.getText().toString());
                        if(Repository.p3life != newlife) {
                            Repository.p3life = newlife;
                            SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
                            editor.putInt(Repository.KEY_P3LIFE, Repository.p3life);
                            SendFile(Repository.P3_LIFE_FILENAME, Repository.p3life + "");
                            Repository.p3log += "\n" + Repository.p3life;
                            editor.putString(Repository.KEY_P3LOG, Repository.p3log);
                            editor.apply();
                            txv_p3lifetrack.setText(Repository.p3log);
                            SendFile(Repository.P3_LOG_FILENAME, Repository.p3log);
                        }
                    }
                    else {
                        String s = txv_p3life.getText().toString();
                        int newlife = Integer.parseInt(s.substring(0, s.length()-1));
                        if(Repository.p3poison != newlife) {
                            Repository.p3poison = newlife;
                            SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
                            editor.putInt(Repository.KEY_P1POISON, Repository.p1poison);
                            SendFile(Repository.P3_POISON_FILENAME, Repository.p3poison + "");
                            Repository.p3log += "\n" + Repository.p3poison + Repository.POISON;
                            editor.putString(Repository.KEY_P3LOG, Repository.p3log);
                            editor.apply();
                            txv_p3lifetrack.setText(Repository.p3log);
                            SendFile(Repository.P3_LOG_FILENAME, Repository.p3log);
                        }
                    }
                }
                if(timerp4 != null) {
                    timerp4.cancel();
                    if (Repository.mode == Repository.LIFE) {
                        int newlife = Integer.parseInt(txv_p4life.getText().toString());
                        if(Repository.p4life != newlife) {
                            Repository.p4life = newlife;
                            SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
                            editor.putInt(Repository.KEY_P4LIFE, Repository.p4life);
                            SendFile(Repository.P4_LIFE_FILENAME, Repository.p4life + "");
                            Repository.p4log += "\n" + Repository.p4life;
                            editor.putString(Repository.KEY_P4LOG, Repository.p4log);
                            editor.apply();
                            txv_p4lifetrack.setText(Repository.p4log);
                            SendFile(Repository.P4_LOG_FILENAME, Repository.p4log);
                        }
                    }
                    else {
                        String s = txv_p4life.getText().toString();
                        int newlife = Integer.parseInt(s.substring(0, s.length()-1));
                        if(Repository.p4poison != newlife) {
                            Repository.p4poison = newlife;
                            SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
                            editor.putInt(Repository.KEY_P1POISON, Repository.p1poison);
                            SendFile(Repository.P4_POISON_FILENAME, Repository.p4poison + "");
                            Repository.p4log += "\n" + Repository.p4poison + Repository.POISON;
                            editor.putString(Repository.KEY_P4LOG, Repository.p4log);
                            editor.apply();
                            txv_p4lifetrack.setText(Repository.p4log);
                            SendFile(Repository.P4_LOG_FILENAME, Repository.p4log);
                        }
                    }
                }
                if(Repository.mode == Repository.LIFE) {
                    Repository.mode = Repository.POISON;
                    txv_p1life.setText(Repository.p1poison + "" + Repository.POISON);
                    txv_p2life.setText(Repository.p2poison + "" + Repository.POISON);
                    txv_p3life.setText(Repository.p3poison + "" + Repository.POISON);
                    txv_p4life.setText(Repository.p4poison + "" + Repository.POISON);
                    txv_p1setlife.setText(Repository.p1poison + "" + Repository.POISON);
                    txv_p2setlife.setText(Repository.p2poison + "" + Repository.POISON);
                    txv_p3setlife.setText(Repository.p3poison + "" + Repository.POISON);
                    txv_p4setlife.setText(Repository.p4poison + "" + Repository.POISON);
                }
                else {
                    Repository.mode = Repository.LIFE;
                    txv_p1life.setText(Repository.p1life + "");
                    txv_p2life.setText(Repository.p2life + "");
                    txv_p3life.setText(Repository.p3life + "");
                    txv_p4life.setText(Repository.p4life + "");
                    txv_p1setlife.setText(Repository.p1life + "");
                    txv_p2setlife.setText(Repository.p2life + "");
                    txv_p3setlife.setText(Repository.p3life + "");
                    txv_p4setlife.setText(Repository.p4life + "");
                }
                btn_lifepoison.setText(Repository.mode + "");
            }
        });
        btn_dice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rollDice();
            }
        });
        btn_dice.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                selectDice();
                return false;
            }
        });
    }

    public void reset() {
        Repository.p1life = 20;
        Repository.p2life = 20;
        Repository.p3life = 20;
        Repository.p4life = 20;
        Repository.p1poison = 0;
        Repository.p2poison = 0;
        Repository.p3poison = 0;
        Repository.p4poison = 0;
        Repository.p1log = "20";
        Repository.p2log = "20";
        Repository.p3log = "20";
        Repository.p4log = "20";
        SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
        editor.putInt(Repository.KEY_P1LIFE, Repository.p1life);
        editor.putInt(Repository.KEY_P2LIFE, Repository.p2life);
        editor.putInt(Repository.KEY_P3LIFE, Repository.p3life);
        editor.putInt(Repository.KEY_P4LIFE, Repository.p4life);
        editor.putInt(Repository.KEY_P1POISON, Repository.p1poison);
        editor.putInt(Repository.KEY_P2POISON, Repository.p2poison);
        editor.putInt(Repository.KEY_P3POISON, Repository.p3poison);
        editor.putInt(Repository.KEY_P4POISON, Repository.p4poison);
        editor.putString(Repository.KEY_P1LOG, Repository.p1log);
        editor.putString(Repository.KEY_P2LOG, Repository.p2log);
        editor.putString(Repository.KEY_P3LOG, Repository.p3log);
        editor.putString(Repository.KEY_P4LOG, Repository.p4log);
        editor.apply();
        SendFile(Repository.P1_LIFE_FILENAME, Repository.p1life + "");
        SendFile(Repository.P1_POISON_FILENAME, Repository.p1poison+ "");
        SendFile(Repository.P1_LOG_FILENAME, Repository.p1log);
        SendFile(Repository.P2_LIFE_FILENAME, Repository.p1life + "");
        SendFile(Repository.P2_POISON_FILENAME, Repository.p1poison+ "");
        SendFile(Repository.P2_LOG_FILENAME, Repository.p2log);
        SendFile(Repository.P3_LIFE_FILENAME, Repository.p1life + "");
        SendFile(Repository.P3_POISON_FILENAME, Repository.p1poison+ "");
        SendFile(Repository.P3_LOG_FILENAME, Repository.p3log);
        SendFile(Repository.P4_LIFE_FILENAME, Repository.p1life + "");
        SendFile(Repository.P4_POISON_FILENAME, Repository.p1poison+ "");
        SendFile(Repository.P4_LOG_FILENAME, Repository.p4log);
        Repository.mode = Repository.LIFE;
        btn_lifepoison.setText(Repository.mode + "");
        txv_p1life.setText(Repository.p1life + "");
        txv_p2life.setText(Repository.p2life + "");
        txv_p3life.setText(Repository.p2life + "");
        txv_p4life.setText(Repository.p2life + "");
        txv_p1lifetrack.setText(Repository.p1log);
        txv_p2lifetrack.setText(Repository.p2log);
        txv_p3lifetrack.setText(Repository.p2log);
        txv_p4lifetrack.setText(Repository.p2log);
        cly_p1life.setVisibility(View.VISIBLE);
        cly_p2life.setVisibility(View.VISIBLE);
        cly_p3life.setVisibility(View.VISIBLE);
        cly_p4life.setVisibility(View.VISIBLE);
        scroll_p1.setVisibility(View.GONE);
        scroll_p2.setVisibility(View.GONE);
        scroll_p3.setVisibility(View.GONE);
        scroll_p4.setVisibility(View.GONE);
        cly_p1setlife.setVisibility(View.INVISIBLE);
        cly_p2setlife.setVisibility(View.INVISIBLE);
        cly_p3setlife.setVisibility(View.INVISIBLE);
        cly_p4setlife.setVisibility(View.INVISIBLE);
        if(cly_p3block.getVisibility() == View.VISIBLE || cly_p4block.getVisibility() == View.VISIBLE)
        {
            txv_p1life.setTextSize(lifeFontSize4pLarge);
            txv_p2life.setTextSize(lifeFontSize4pLarge);
            txv_p3life.setTextSize(lifeFontSize4pLarge);
            txv_p4life.setTextSize(lifeFontSize4pLarge);
        }
        else
        {
            txv_p1life.setTextSize(lifeFontSize2pLarge);
            txv_p2life.setTextSize(lifeFontSize2pLarge);
        }
    }

    public void changep1life(int value) {
        if(timerp1 != null) {
            timerp1.cancel();
        }
        if(Repository.mode == Repository.LIFE) {
            txv_p1life.setText(Integer.parseInt(txv_p1life.getText().toString()) + value + "");
            timerp1 = new CountDownTimer(Repository.lifeDelay, 100) {

                @Override
                public void onTick(long millisUntilEnd) {
                }

                @Override
                public void onFinish() {
                    int newlife = Integer.parseInt(txv_p1life.getText().toString());
                    if(Repository.p1life != newlife) {
                        Repository.p1life = newlife;
                        SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
                        editor.putInt(Repository.KEY_P1LIFE, Repository.p1life);
                        SendFile(Repository.P1_LIFE_FILENAME, Repository.p1life + "");
                        Repository.p1log += "\n" + Repository.p1life;
                        editor.putString(Repository.KEY_P1LOG, Repository.p1log);
                        editor.apply();
                        txv_p1lifetrack.setText(Repository.p1log);
                        SendFile(Repository.P1_LOG_FILENAME, Repository.p1log);
                        scroll_p1.fullScroll(View.FOCUS_DOWN);
                    }
                }
            };
            timerp1.start();
        }
        else {
            String s = txv_p1life.getText().toString();
            txv_p1life.setText(Integer.parseInt(s.substring(0, s.length()-1)) + value + "" + Repository.POISON);
            timerp1 = new CountDownTimer(Repository.lifeDelay, 100) {

                @Override
                public void onTick(long millisUntilEnd) {
                }

                @Override
                public void onFinish() {
                    String s = txv_p1life.getText().toString();
                    int newlife = Integer.parseInt(s.substring(0, s.length()-1));
                    if(Repository.p1poison != newlife) {
                        Repository.p1poison = newlife;
                        SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
                        editor.putInt(Repository.KEY_P1POISON, Repository.p1poison);
                        SendFile(Repository.P1_POISON_FILENAME, Repository.p1poison + "");
                        Repository.p1log += "\n" + Repository.p1poison + Repository.POISON;
                        editor.putString(Repository.KEY_P1LOG, Repository.p1log);
                        editor.apply();
                        txv_p1lifetrack.setText(Repository.p1log);
                        SendFile(Repository.P1_LOG_FILENAME, Repository.p1log);
                        scroll_p1.fullScroll(View.FOCUS_DOWN);
                    }
                }
            };
            timerp1.start();
        }
    }

    public void changep2life(int value) {
        if(timerp2 != null) {
            timerp2.cancel();
        }
        if(Repository.mode == Repository.LIFE) {
            txv_p2life.setText(Integer.parseInt(txv_p2life.getText().toString()) + value + "");
            timerp2 = new CountDownTimer(Repository.lifeDelay, 100) {

                @Override
                public void onTick(long millisUntilEnd) {
                }

                @Override
                public void onFinish() {
                    int newlife = Integer.parseInt(txv_p2life.getText().toString());
                    if(Repository.p2life != newlife) {
                        Repository.p2life = newlife;
                        SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
                        SendFile(Repository.P2_LIFE_FILENAME, Repository.p2life + "");
                        Repository.p2log += "\n" + Repository.p2life;
                        editor.putString(Repository.KEY_P2LOG, Repository.p2log);
                        editor.apply();
                        txv_p2lifetrack.setText(Repository.p2log);
                        SendFile(Repository.P2_LOG_FILENAME, Repository.p2log);
                        scroll_p2.fullScroll(View.FOCUS_DOWN);
                    }
                }
            };
            timerp2.start();
        }
        else {
            String s = txv_p2life.getText().toString();
            txv_p2life.setText(Integer.parseInt(s.substring(0, s.length()-1)) + value + "" + Repository.POISON);
            timerp2 = new CountDownTimer(Repository.lifeDelay, 100) {

                @Override
                public void onTick(long millisUntilEnd) {
                }

                @Override
                public void onFinish() {
                    String s = txv_p2life.getText().toString();
                    int newlife = Integer.parseInt(s.substring(0, s.length()-1));
                    if(Repository.p2poison != newlife) {
                        Repository.p2poison = newlife;
                        SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
                        editor.putInt(Repository.KEY_P1POISON, Repository.p1poison);
                        SendFile(Repository.P2_POISON_FILENAME, Repository.p2poison + "");
                        Repository.p2log += "\n" + Repository.p2life + Repository.POISON;
                        editor.putString(Repository.KEY_P2LOG, Repository.p2log);
                        editor.apply();
                        txv_p2lifetrack.setText(Repository.p2log);
                        SendFile(Repository.P2_LOG_FILENAME, Repository.p2log);
                        scroll_p2.fullScroll(View.FOCUS_DOWN);
                    }
                }
            };
            timerp2.start();
        }
    }

    public void changep3life(int value) {
        if(timerp3 != null) {
            timerp3.cancel();
        }
        if(Repository.mode == Repository.LIFE) {
            txv_p3life.setText(Integer.parseInt(txv_p3life.getText().toString()) + value + "");
            timerp3 = new CountDownTimer(Repository.lifeDelay, 100) {

                @Override
                public void onTick(long millisUntilEnd) {
                }

                @Override
                public void onFinish() {
                    int newlife = Integer.parseInt(txv_p3life.getText().toString());
                    if(Repository.p3life != newlife) {
                        Repository.p3life = newlife;
                        SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
                        SendFile(Repository.P3_LIFE_FILENAME, Repository.p3life + "");
                        Repository.p3log += "\n" + Repository.p3life;
                        editor.putString(Repository.KEY_P3LOG, Repository.p3log);
                        editor.apply();
                        txv_p3lifetrack.setText(Repository.p3log);
                        SendFile(Repository.P3_LOG_FILENAME, Repository.p3log);
                        scroll_p3.fullScroll(View.FOCUS_DOWN);
                    }
                }
            };
            timerp3.start();
        }
        else {
            String s = txv_p3life.getText().toString();
            txv_p3life.setText(Integer.parseInt(s.substring(0, s.length()-1)) + value + "" + Repository.POISON);
            timerp3 = new CountDownTimer(Repository.lifeDelay, 100) {

                @Override
                public void onTick(long millisUntilEnd) {
                }

                @Override
                public void onFinish() {
                    String s = txv_p3life.getText().toString();
                    int newlife = Integer.parseInt(s.substring(0, s.length()-1));
                    if(Repository.p3poison != newlife) {
                        Repository.p3poison = newlife;
                        SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
                        editor.putInt(Repository.KEY_P1POISON, Repository.p1poison);
                        SendFile(Repository.P3_POISON_FILENAME, Repository.p3poison + "");
                        Repository.p3log += "\n" + Repository.p3life + Repository.POISON;
                        editor.putString(Repository.KEY_P3LOG, Repository.p3log);
                        editor.apply();
                        txv_p3lifetrack.setText(Repository.p3log);
                        SendFile(Repository.P3_LOG_FILENAME, Repository.p3log);
                        scroll_p3.fullScroll(View.FOCUS_DOWN);
                    }
                }
            };
            timerp3.start();
        }
    }

    public void changep4life(int value) {
        if(timerp4 != null) {
            timerp4.cancel();
        }
        if(Repository.mode == Repository.LIFE) {
            txv_p4life.setText(Integer.parseInt(txv_p4life.getText().toString()) + value + "");
            timerp4 = new CountDownTimer(Repository.lifeDelay, 100) {

                @Override
                public void onTick(long millisUntilEnd) {
                }

                @Override
                public void onFinish() {
                    int newlife = Integer.parseInt(txv_p4life.getText().toString());
                    if(Repository.p4life != newlife) {
                        Repository.p4life = newlife;
                        SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
                        SendFile(Repository.P4_LIFE_FILENAME, Repository.p4life + "");
                        Repository.p4log += "\n" + Repository.p4life;
                        editor.putString(Repository.KEY_P4LOG, Repository.p4log);
                        editor.apply();
                        txv_p4lifetrack.setText(Repository.p4log);
                        SendFile(Repository.P4_LOG_FILENAME, Repository.p4log);
                        scroll_p4.fullScroll(View.FOCUS_DOWN);
                    }
                }
            };
            timerp4.start();
        }
        else {
            String s = txv_p4life.getText().toString();
            txv_p4life.setText(Integer.parseInt(s.substring(0, s.length()-1)) + value + "" + Repository.POISON);
            timerp4 = new CountDownTimer(Repository.lifeDelay, 100) {

                @Override
                public void onTick(long millisUntilEnd) {
                }

                @Override
                public void onFinish() {
                    String s = txv_p4life.getText().toString();
                    int newlife = Integer.parseInt(s.substring(0, s.length()-1));
                    if(Repository.p4poison != newlife) {
                        Repository.p4poison = newlife;
                        SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
                        editor.putInt(Repository.KEY_P1POISON, Repository.p1poison);
                        SendFile(Repository.P4_POISON_FILENAME, Repository.p4poison + "");
                        Repository.p4log += "\n" + Repository.p4life + Repository.POISON;
                        editor.putString(Repository.KEY_P4LOG, Repository.p4log);
                        editor.apply();
                        txv_p4lifetrack.setText(Repository.p4log);
                        SendFile(Repository.P4_LOG_FILENAME, Repository.p4log);
                        scroll_p4.fullScroll(View.FOCUS_DOWN);
                    }
                }
            };
            timerp4.start();
        }
    }

    public static void SendFile(String filename, String info) {
        if(Repository.allowFTP) {
            final String finalFilename = filename;
            final String finalInfo = info;
            AsyncTask< String, Integer, Boolean > task = new AsyncTask< String, Integer, Boolean >()
            {
                @Override
                protected Boolean doInBackground( String... params )
                {
                    try {
                        String folder = Environment.getExternalStorageDirectory() + File.separator + Repository.FOLDERNAME;
                        File textFile = new File(folder, finalFilename);
                        FileOutputStream fileOutputStream = new FileOutputStream(textFile);
                        fileOutputStream.write(finalInfo.getBytes());
                        fileOutputStream.close();
                        FTPClient mFTP = new FTPClient();
                        mFTP.setRemoteVerificationEnabled(false);
                        mFTP.setBufferSize(1);
                        mFTP.connect(Repository.ftpServer, Repository.ftpPort);
                        mFTP.login(Repository.ftpUser, Repository.ftpPassword);
                        mFTP.setFileType(FTP.BINARY_FILE_TYPE);
                        mFTP.enterLocalPassiveMode();
                        FileInputStream fileInputStream = new FileInputStream(textFile);
                        mFTP.storeFile(finalFilename,fileInputStream);
                        fileInputStream.close();
                        mFTP.logout();
                        mFTP.disconnect();
                    }catch(Exception e) {
                        Log.d("Error", e.getLocalizedMessage());
                    }
                    return true;
                }
            };
            task.execute("");
        }
    }

    public void rollDice() {
        int result = random.nextInt(Repository.dice) + 1;
        AlertDialog alertDialog = new AlertDialog.Builder(LifeActivity.this)
                .setTitle(Repository.StringMap(79) + Repository.dice)
                .setMessage(""+result)
                .show();
        TextView textView = (TextView) alertDialog.findViewById(android.R.id.message);
        textView.setTextSize(40);
        textView.setGravity(Gravity.CENTER);
    }

    public void selectDice() {
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setGravity(Gravity.CENTER);
        input.setMaxLines(1);
        AlertDialog alertDialog = new AlertDialog.Builder(LifeActivity.this)
                .setTitle(Repository.StringMap(80))
                .setView(input)
                .setPositiveButton(Repository.StringMap(81), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int number;
                        try {
                            number = Integer.parseInt(input.getText().toString());
                            if(number > 0) {
                                Repository.dice = number;
                                btn_dice.setText("D" + number);
                            }
                        } catch (Exception e) {

                        }
                    }
                })
                .setNegativeButton(Repository.StringMap(82), null)
                .show();
    }
}