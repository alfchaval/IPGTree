package mtg.judge.ipgtree.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.app.AppCompatActivity;
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

import mtg.judge.ipgtree.Components.OutlineTextView;
import mtg.judge.ipgtree.R;
import mtg.judge.ipgtree.Utilities.Repository;
import mtg.judge.ipgtree.Utilities.Translation;

public class Life6Activity extends AppCompatActivity {

    private Button btn_reset, btn_registry, btn_lifepoison, btn_dice;
    private OutlineTextView txv_p1minus, txv_p1life, txv_p1add, txv_p1lifetrack, txv_p1setlife;
    private OutlineTextView txv_p2minus, txv_p2life, txv_p2add, txv_p2lifetrack, txv_p2setlife;
    private OutlineTextView txv_p3minus, txv_p3life, txv_p3add, txv_p3lifetrack, txv_p3setlife;
    private OutlineTextView txv_p4minus, txv_p4life, txv_p4add, txv_p4lifetrack, txv_p4setlife;
    private OutlineTextView txv_p5minus, txv_p5life, txv_p5add, txv_p5lifetrack, txv_p5setlife;
    private OutlineTextView txv_p6minus, txv_p6life, txv_p6add, txv_p6lifetrack, txv_p6setlife;
    private ConstraintLayout cly_p1life, cly_p1setlife, cly_p1block;
    private ConstraintLayout cly_p2life, cly_p2setlife, cly_p2block;
    private ConstraintLayout cly_p3life, cly_p3setlife, cly_p3block;
    private ConstraintLayout cly_p4life, cly_p4setlife, cly_p4block;
    private ConstraintLayout cly_p5life, cly_p5setlife, cly_p5block;
    private ConstraintLayout cly_p6life, cly_p6setlife, cly_p6block;
    private ScrollView scroll_p1, scroll_p2, scroll_p3, scroll_p4, scroll_p5, scroll_p6;
    private Keyboard keyboard_p1, keyboard_p2, keyboard_p3, keyboard_p4, keyboard_p5, keyboard_p6;
    private KeyboardView keyboardView_p1, keyboardView_p2, keyboardView_p3, keyboardView_p4, keyboardView_p5, keyboardView_p6;
    private CountDownTimer timerp1 = null;
    private CountDownTimer timerp2 = null;
    private CountDownTimer timerp3 = null;
    private CountDownTimer timerp4 = null;
    private CountDownTimer timerp5 = null;
    private CountDownTimer timerp6 = null;
    private Random random;
    
    private final int lifeFontSizeLarge = 60;
    private final int lifeFontSizeSmall = 40;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life6);

        if(!Repository.repositoryLoaded) {
            Repository.createRepository(getApplicationContext());
        }

        linkViews();
        loadStrings();
        random = new Random();
        setListeners();

        if(Repository.reverseLife)
        {
            cly_p2block.setRotation(180);
            cly_p3block.setRotation(180);
            cly_p4block.setRotation(180);
        }

        File bg1 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + Repository.FOLDERNAME + File.separator + Repository.p1bg);
        if(bg1.exists()) {
            try {
                Bitmap bitmap1 = BitmapFactory.decodeFile(bg1.getAbsolutePath());
                cly_p1block.setBackground(new BitmapDrawable(getResources(), bitmap1));
            } catch (Exception e) {

            }
        }

        File bg2 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + Repository.FOLDERNAME + File.separator + Repository.p2bg);
        if(bg2.exists()) {
            try {
                Bitmap bitmap2 = BitmapFactory.decodeFile(bg2.getAbsolutePath());
                cly_p2block.setBackground(new BitmapDrawable(getResources(), bitmap2));
            } catch (Exception e) {

            }
        }

        File bg3 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + Repository.FOLDERNAME + File.separator + Repository.p3bg);
        if(bg3.exists()) {
            try {
                Bitmap bitmap3 = BitmapFactory.decodeFile(bg3.getAbsolutePath());
                cly_p3block.setBackground(new BitmapDrawable(getResources(), bitmap3));
            } catch (Exception e) {

            }
        }

        File bg4 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + Repository.FOLDERNAME + File.separator + Repository.p4bg);
        if(bg4.exists()) {
            try {
                Bitmap bitmap4 = BitmapFactory.decodeFile(bg4.getAbsolutePath());
                cly_p4block.setBackground(new BitmapDrawable(getResources(), bitmap4));
            } catch (Exception e) {

            }
        }

        File bg5 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + Repository.FOLDERNAME + File.separator + Repository.p5bg);
        if(bg5.exists()) {
            try {
                Bitmap bitmap5 = BitmapFactory.decodeFile(bg5.getAbsolutePath());
                cly_p5block.setBackground(new BitmapDrawable(getResources(), bitmap5));
            } catch (Exception e) {

            }
        }

        File bg6 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + Repository.FOLDERNAME + File.separator + Repository.p6bg);
        if(bg6.exists()) {
            try {
                Bitmap bitmap6 = BitmapFactory.decodeFile(bg6.getAbsolutePath());
                cly_p6block.setBackground(new BitmapDrawable(getResources(), bitmap6));
            } catch (Exception e) {

            }
        }

        btn_dice.setText("D" + Repository.dice);
        txv_p1life.setText(Repository.p1life + "");
        txv_p1lifetrack.setText(Repository.p1log);
        txv_p1life.setTextSize(lifeFontSizeLarge);
        txv_p2life.setText(Repository.p2life + "");
        txv_p2lifetrack.setText(Repository.p2log);
        txv_p2life.setTextSize(lifeFontSizeLarge);
        txv_p3life.setText(Repository.p3life + "");
        txv_p3lifetrack.setText(Repository.p3log);
        txv_p3life.setTextSize(lifeFontSizeLarge);
        txv_p4life.setText(Repository.p4life + "");
        txv_p4lifetrack.setText(Repository.p4log);
        txv_p4life.setTextSize(lifeFontSizeLarge);
        txv_p5life.setText(Repository.p5life + "");
        txv_p5lifetrack.setText(Repository.p5log);
        txv_p5life.setTextSize(lifeFontSizeLarge);
        txv_p6life.setText(Repository.p6life + "");
        txv_p6lifetrack.setText(Repository.p6log);
        txv_p6life.setTextSize(lifeFontSizeLarge);
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

        txv_p5minus = findViewById(R.id.txv_p5minus);
        txv_p5life = findViewById(R.id.txv_p5life);
        txv_p5add = findViewById(R.id.txv_p5add);
        txv_p5lifetrack = findViewById(R.id.txv_p5lifetrack);
        txv_p5setlife = findViewById(R.id.txv_p5setlife);

        txv_p6minus = findViewById(R.id.txv_p6minus);
        txv_p6life = findViewById(R.id.txv_p6life);
        txv_p6add = findViewById(R.id.txv_p6add);
        txv_p6lifetrack = findViewById(R.id.txv_p6lifetrack);
        txv_p6setlife = findViewById(R.id.txv_p6setlife);

        cly_p1life = findViewById(R.id.cly_p1life);
        cly_p1setlife = findViewById(R.id.cly_p1setlife);
        cly_p1block = findViewById(R.id.cly_p1block);

        cly_p2life = findViewById(R.id.cly_p2life);
        cly_p2setlife = findViewById(R.id.cly_p2setlife);
        cly_p2block = findViewById(R.id.cly_p2block);

        cly_p3life = findViewById(R.id.cly_p3life);
        cly_p3setlife = findViewById(R.id.cly_p3setlife);
        cly_p3block = findViewById(R.id.cly_p3block);

        cly_p4life = findViewById(R.id.cly_p4life);
        cly_p4setlife = findViewById(R.id.cly_p4setlife);
        cly_p4block = findViewById(R.id.cly_p4block);

        cly_p5life = findViewById(R.id.cly_p5life);
        cly_p5setlife = findViewById(R.id.cly_p5setlife);
        cly_p5block = findViewById(R.id.cly_p5block);

        cly_p6life = findViewById(R.id.cly_p6life);
        cly_p6setlife = findViewById(R.id.cly_p6setlife);
        cly_p6block = findViewById(R.id.cly_p6block);

        scroll_p1 = findViewById(R.id.scroll_p1);
        scroll_p2 = findViewById(R.id.scroll_p2);
        scroll_p3 = findViewById(R.id.scroll_p3);
        scroll_p4 = findViewById(R.id.scroll_p4);
        scroll_p5 = findViewById(R.id.scroll_p5);
        scroll_p6 = findViewById(R.id.scroll_p6);

        keyboard_p1 = new Keyboard(Life6Activity.this, R.xml.life_keyboard);
        keyboardView_p1 = findViewById(R.id.keyboardView_p1);
        keyboardView_p1.setKeyboard(keyboard_p1);
        keyboardView_p1.setPreviewEnabled(false);

        keyboard_p2 = new Keyboard(Life6Activity.this, R.xml.life_keyboard);
        keyboardView_p2 = findViewById(R.id.keyboardView_p2);
        keyboardView_p2.setKeyboard(keyboard_p2);
        keyboardView_p2.setPreviewEnabled(false);

        keyboard_p3 = new Keyboard(Life6Activity.this, R.xml.life_keyboard);
        keyboardView_p3 = findViewById(R.id.keyboardView_p3);
        keyboardView_p3.setKeyboard(keyboard_p3);
        keyboardView_p3.setPreviewEnabled(false);

        keyboard_p4 = new Keyboard(Life6Activity.this, R.xml.life_keyboard);
        keyboardView_p4 = findViewById(R.id.keyboardView_p4);
        keyboardView_p4.setKeyboard(keyboard_p4);
        keyboardView_p4.setPreviewEnabled(false);

        keyboard_p5 = new Keyboard(Life6Activity.this, R.xml.life_keyboard);
        keyboardView_p5 = findViewById(R.id.keyboardView_p5);
        keyboardView_p5.setKeyboard(keyboard_p5);
        keyboardView_p5.setPreviewEnabled(false);

        keyboard_p6 = new Keyboard(Life6Activity.this, R.xml.life_keyboard);
        keyboardView_p6 = findViewById(R.id.keyboardView_p6);
        keyboardView_p6.setKeyboard(keyboard_p6);
        keyboardView_p6.setPreviewEnabled(false);
    }

    private void loadStrings() {
        btn_reset.setText(Translation.StringMap(25));
        btn_registry.setText(Translation.StringMap(24));
    }
    
    //POR AQUI

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
                        if(scroll_p2.getVisibility() == View.VISIBLE) {
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
                            editor.putInt(Repository.KEY_P2POISON, Repository.p2poison);
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
                        if(scroll_p2.getVisibility() == View.VISIBLE) {
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
                        if(scroll_p3.getVisibility() == View.VISIBLE) {
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
                            editor.putInt(Repository.KEY_P3POISON, Repository.p3poison);
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
                        if(scroll_p3.getVisibility() == View.VISIBLE) {
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
                        if(scroll_p4.getVisibility() == View.VISIBLE) {
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
                            editor.putInt(Repository.KEY_P4POISON, Repository.p4poison);
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
                        if(scroll_p4.getVisibility() == View.VISIBLE) {
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
        keyboardView_p5.setOnKeyboardActionListener(new KeyboardView.OnKeyboardActionListener() {
            @Override
            public void onPress(int i) {
            }

            @Override
            public void onRelease(int i) {
            }

            @Override
            public void onKey(int i, int[] ints) {
                String number = txv_p5setlife.getText().toString();
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
                            txv_p5setlife.setText(number + i);
                        }
                        else {
                            txv_p5setlife.setText(i + "");
                        }
                        if(Repository.mode == Repository.POISON) {
                            txv_p5setlife.setText(txv_p5setlife.getText().toString() + Repository.POISON);
                        }
                        break;
                    case Repository.CodeSign:
                        if(txv_p5setlife.getText().toString().charAt(0) != '-') {
                            if(!number.equals("0")) {
                                txv_p5setlife.setText("-" + number);
                            }
                        }
                        else {
                            txv_p5setlife.setText(number.substring(1));
                        }
                        if(Repository.mode == Repository.POISON) {
                            txv_p5setlife.setText(txv_p5setlife.getText().toString() + Repository.POISON);
                        }
                        break;
                    case Repository.CodeDelete:
                        if (Math.abs(Integer.parseInt(number)) > 9) {
                            txv_p5setlife.setText(number.substring(0, number.length() - 1));
                        }
                        else {
                            txv_p5setlife.setText("0");
                        }
                        if(Repository.mode == Repository.POISON) {
                            txv_p5setlife.setText(txv_p5setlife.getText().toString() + Repository.POISON);
                        }
                        break;
                    case Repository.CodeCancel:
                        cly_p5life.setVisibility(View.VISIBLE);
                        cly_p5setlife.setVisibility(View.INVISIBLE);
                        if(scroll_p2.getVisibility() == View.VISIBLE) {
                            scroll_p5.setVisibility(View.VISIBLE);
                            scroll_p5.fullScroll(View.FOCUS_DOWN);
                        }
                        break;
                    case Repository.CodeOk:
                        if(number.length() > 9) {
                            number = number.substring(0, 9);
                        }
                        if(Repository.mode == Repository.LIFE) {
                            Repository.p5life = Integer.parseInt(number);
                            SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
                            editor.putInt(Repository.KEY_P5LIFE, Repository.p5life);
                            txv_p5life.setText(Repository.p5life + "");
                            SendFile(Repository.P5_LIFE_FILENAME, Repository.p5life + "");
                            Repository.p5log += "\n" + number;
                            editor.putString(Repository.KEY_P5LOG, Repository.p5log);
                            editor.apply();
                            txv_p5lifetrack.setText(Repository.p5log);
                            SendFile(Repository.P5_LOG_FILENAME, txv_p5lifetrack.getText().toString());
                        }
                        else {
                            Repository.p5poison = Integer.parseInt(number);
                            SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
                            editor.putInt(Repository.KEY_P5POISON, Repository.p5poison);
                            txv_p5life.setText(Repository.p5poison + "" + Repository.POISON);
                            SendFile(Repository.P5_POISON_FILENAME, Repository.p5poison + "");
                            Repository.p5log += "\n" + number + Repository.POISON;
                            editor.putString(Repository.KEY_P5LOG, Repository.p5log);
                            editor.apply();
                            txv_p5lifetrack.setText(Repository.p5log);
                            SendFile(Repository.P5_LOG_FILENAME, txv_p5lifetrack.getText().toString());
                        }
                        cly_p5life.setVisibility(View.VISIBLE);
                        cly_p5setlife.setVisibility(View.INVISIBLE);
                        if(scroll_p2.getVisibility() == View.VISIBLE) {
                            scroll_p5.setVisibility(View.VISIBLE);
                            scroll_p5.fullScroll(View.FOCUS_DOWN);
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
        keyboardView_p6.setOnKeyboardActionListener(new KeyboardView.OnKeyboardActionListener() {
            @Override
            public void onPress(int i) {
            }

            @Override
            public void onRelease(int i) {
            }

            @Override
            public void onKey(int i, int[] ints) {
                String number = txv_p6setlife.getText().toString();
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
                            txv_p6setlife.setText(number + i);
                        }
                        else {
                            txv_p6setlife.setText(i + "");
                        }
                        if(Repository.mode == Repository.POISON) {
                            txv_p6setlife.setText(txv_p6setlife.getText().toString() + Repository.POISON);
                        }
                        break;
                    case Repository.CodeSign:
                        if(txv_p6setlife.getText().toString().charAt(0) != '-') {
                            if(!number.equals("0")) {
                                txv_p6setlife.setText("-" + number);
                            }
                        }
                        else {
                            txv_p6setlife.setText(number.substring(1));
                        }
                        if(Repository.mode == Repository.POISON) {
                            txv_p6setlife.setText(txv_p6setlife.getText().toString() + Repository.POISON);
                        }
                        break;
                    case Repository.CodeDelete:
                        if (Math.abs(Integer.parseInt(number)) > 9) {
                            txv_p6setlife.setText(number.substring(0, number.length() - 1));
                        }
                        else {
                            txv_p6setlife.setText("0");
                        }
                        if(Repository.mode == Repository.POISON) {
                            txv_p6setlife.setText(txv_p6setlife.getText().toString() + Repository.POISON);
                        }
                        break;
                    case Repository.CodeCancel:
                        cly_p6life.setVisibility(View.VISIBLE);
                        cly_p6setlife.setVisibility(View.INVISIBLE);
                        if(scroll_p2.getVisibility() == View.VISIBLE) {
                            scroll_p6.setVisibility(View.VISIBLE);
                            scroll_p6.fullScroll(View.FOCUS_DOWN);
                        }
                        break;
                    case Repository.CodeOk:
                        if(number.length() > 9) {
                            number = number.substring(0, 9);
                        }
                        if(Repository.mode == Repository.LIFE) {
                            Repository.p6life = Integer.parseInt(number);
                            SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
                            editor.putInt(Repository.KEY_P6LIFE, Repository.p6life);
                            txv_p6life.setText(Repository.p6life + "");
                            SendFile(Repository.P6_LIFE_FILENAME, Repository.p6life + "");
                            Repository.p6log += "\n" + number;
                            editor.putString(Repository.KEY_P6LOG, Repository.p6log);
                            editor.apply();
                            txv_p6lifetrack.setText(Repository.p6log);
                            SendFile(Repository.P6_LOG_FILENAME, txv_p6lifetrack.getText().toString());
                        }
                        else {
                            Repository.p6poison = Integer.parseInt(number);
                            SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
                            editor.putInt(Repository.KEY_P6POISON, Repository.p6poison);
                            txv_p6life.setText(Repository.p6poison + "" + Repository.POISON);
                            SendFile(Repository.P6_POISON_FILENAME, Repository.p6poison + "");
                            Repository.p6log += "\n" + number + Repository.POISON;
                            editor.putString(Repository.KEY_P6LOG, Repository.p6log);
                            editor.apply();
                            txv_p6lifetrack.setText(Repository.p6log);
                            SendFile(Repository.P6_LOG_FILENAME, txv_p6lifetrack.getText().toString());
                        }
                        cly_p6life.setVisibility(View.VISIBLE);
                        cly_p6setlife.setVisibility(View.INVISIBLE);
                        if(scroll_p2.getVisibility() == View.VISIBLE) {
                            scroll_p6.setVisibility(View.VISIBLE);
                            scroll_p6.fullScroll(View.FOCUS_DOWN);
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
        txv_p5minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changep5life(-1);
            }
        });
        txv_p6minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changep6life(-1);
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
        txv_p5add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changep5life(1);
            }
        });
        txv_p6add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changep6life(1);
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
                        SendFile(Repository.P2_LOG_FILENAME, Repository.p2log);
                    }
                    txv_p2setlife.setText(Repository.p2life + "");
                }
                else {
                    String s = txv_p2life.getText().toString();
                    int newlife = Integer.parseInt(s.substring(0, s.length()-1));
                    if(Repository.p2poison != newlife) {
                        Repository.p2poison = newlife;
                        SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
                        editor.putInt(Repository.KEY_P2POISON, Repository.p2poison);
                        SendFile(Repository.P2_POISON_FILENAME, Repository.p2poison + "");
                        Repository.p2log += "\n" + Repository.p2poison + Repository.POISON;
                        editor.putString(Repository.KEY_P2LOG, Repository.p2log);
                        editor.apply();
                        txv_p2lifetrack.setText(Repository.p2log);
                        SendFile(Repository.P2_LOG_FILENAME, Repository.p2log);
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
                        SendFile(Repository.P3_LOG_FILENAME, Repository.p3log);
                    }
                    txv_p3setlife.setText(Repository.p3life + "");
                }
                else {
                    String s = txv_p3life.getText().toString();
                    int newlife = Integer.parseInt(s.substring(0, s.length()-1));
                    if(Repository.p3poison != newlife) {
                        Repository.p3poison = newlife;
                        SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
                        editor.putInt(Repository.KEY_P3POISON, Repository.p3poison);
                        SendFile(Repository.P3_POISON_FILENAME, Repository.p3poison + "");
                        Repository.p3log += "\n" + Repository.p3poison + Repository.POISON;
                        editor.putString(Repository.KEY_P3LOG, Repository.p3log);
                        editor.apply();
                        txv_p3lifetrack.setText(Repository.p3log);
                        SendFile(Repository.P3_LOG_FILENAME, Repository.p3log);
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
                        SendFile(Repository.P4_LOG_FILENAME, Repository.p4log);
                    }
                    txv_p4setlife.setText(Repository.p4life + "");
                }
                else {
                    String s = txv_p4life.getText().toString();
                    int newlife = Integer.parseInt(s.substring(0, s.length()-1));
                    if(Repository.p4poison != newlife) {
                        Repository.p4poison = newlife;
                        SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
                        editor.putInt(Repository.KEY_P4POISON, Repository.p4poison);
                        SendFile(Repository.P4_POISON_FILENAME, Repository.p4poison + "");
                        Repository.p4log += "\n" + Repository.p4poison + Repository.POISON;
                        editor.putString(Repository.KEY_P4LOG, Repository.p4log);
                        editor.apply();
                        txv_p4lifetrack.setText(Repository.p4log);
                        SendFile(Repository.P4_LOG_FILENAME, Repository.p4log);
                    }
                    txv_p4setlife.setText(Repository.p4poison + "" + Repository.POISON);
                }
                cly_p4life.setVisibility(View.INVISIBLE);
                scroll_p4.setVisibility(View.GONE);
                cly_p4setlife.setVisibility(View.VISIBLE);
                return true;
            }
        });
        txv_p5life.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(timerp5 != null) {
                    timerp5.cancel();
                }
                if (Repository.mode == Repository.LIFE) {
                    int newlife = Integer.parseInt(txv_p5life.getText().toString());
                    if(Repository.p5life != newlife) {
                        Repository.p5life = newlife;
                        SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
                        editor.putInt(Repository.KEY_P5LIFE, Repository.p5life);
                        SendFile(Repository.P5_LIFE_FILENAME, Repository.p5life + "");
                        Repository.p5log += "\n" + Repository.p5life;
                        editor.putString(Repository.KEY_P5LOG, Repository.p5log);
                        editor.apply();
                        txv_p5lifetrack.setText(Repository.p5log);
                        SendFile(Repository.P5_LOG_FILENAME, Repository.p5log);
                    }
                    txv_p5setlife.setText(Repository.p5life + "");
                }
                else {
                    String s = txv_p5life.getText().toString();
                    int newlife = Integer.parseInt(s.substring(0, s.length()-1));
                    if(Repository.p5poison != newlife) {
                        Repository.p5poison = newlife;
                        SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
                        editor.putInt(Repository.KEY_P5POISON, Repository.p5poison);
                        SendFile(Repository.P5_POISON_FILENAME, Repository.p5poison + "");
                        Repository.p5log += "\n" + Repository.p5poison + Repository.POISON;
                        editor.putString(Repository.KEY_P5LOG, Repository.p5log);
                        editor.apply();
                        txv_p5lifetrack.setText(Repository.p5log);
                        SendFile(Repository.P5_LOG_FILENAME, Repository.p5log);
                    }
                    txv_p5setlife.setText(Repository.p5poison + "" + Repository.POISON);
                }
                cly_p5life.setVisibility(View.INVISIBLE);
                scroll_p5.setVisibility(View.GONE);
                cly_p5setlife.setVisibility(View.VISIBLE);
                return true;
            }
        });
        txv_p6life.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(timerp6 != null) {
                    timerp6.cancel();
                }
                if (Repository.mode == Repository.LIFE) {
                    int newlife = Integer.parseInt(txv_p6life.getText().toString());
                    if(Repository.p6life != newlife) {
                        Repository.p6life = newlife;
                        SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
                        editor.putInt(Repository.KEY_P6LIFE, Repository.p6life);
                        SendFile(Repository.P6_LIFE_FILENAME, Repository.p6life + "");
                        Repository.p6log += "\n" + Repository.p6life;
                        editor.putString(Repository.KEY_P6LOG, Repository.p6log);
                        editor.apply();
                        txv_p6lifetrack.setText(Repository.p6log);
                        SendFile(Repository.P6_LOG_FILENAME, Repository.p6log);
                    }
                    txv_p6setlife.setText(Repository.p6life + "");
                }
                else {
                    String s = txv_p6life.getText().toString();
                    int newlife = Integer.parseInt(s.substring(0, s.length()-1));
                    if(Repository.p6poison != newlife) {
                        Repository.p6poison = newlife;
                        SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
                        editor.putInt(Repository.KEY_P6POISON, Repository.p6poison);
                        SendFile(Repository.P6_POISON_FILENAME, Repository.p6poison + "");
                        Repository.p6log += "\n" + Repository.p6poison + Repository.POISON;
                        editor.putString(Repository.KEY_P6LOG, Repository.p6log);
                        editor.apply();
                        txv_p6lifetrack.setText(Repository.p6log);
                        SendFile(Repository.P6_LOG_FILENAME, Repository.p6log);
                    }
                    txv_p6setlife.setText(Repository.p6poison + "" + Repository.POISON);
                }
                cly_p6life.setVisibility(View.INVISIBLE);
                scroll_p6.setVisibility(View.GONE);
                cly_p6setlife.setVisibility(View.VISIBLE);
                return true;
            }
        });

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(Life6Activity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(Translation.StringMap(27))
                        .setMessage(Translation.StringMap(26))
                        .setPositiveButton(Translation.StringMap(28), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                reset();
                            }
                        })
                        .setNegativeButton(Translation.StringMap(23), null)
                        .show();
            }
        });
        btn_registry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (scroll_p1.getVisibility() == View.GONE || scroll_p2.getVisibility() == View.GONE || scroll_p3.getVisibility() == View.GONE || scroll_p4.getVisibility() == View.GONE || scroll_p5.getVisibility() == View.GONE || scroll_p6.getVisibility() == View.GONE) {
                    scroll_p1.setVisibility(View.VISIBLE);
                    scroll_p2.setVisibility(View.VISIBLE);
                    scroll_p3.setVisibility(View.VISIBLE);
                    scroll_p4.setVisibility(View.VISIBLE);
                    scroll_p5.setVisibility(View.VISIBLE);
                    scroll_p6.setVisibility(View.VISIBLE);
                    scroll_p1.fullScroll(View.FOCUS_DOWN);
                    scroll_p2.fullScroll(View.FOCUS_DOWN);
                    scroll_p3.fullScroll(View.FOCUS_DOWN);
                    scroll_p4.fullScroll(View.FOCUS_DOWN);
                    scroll_p5.fullScroll(View.FOCUS_DOWN);
                    scroll_p6.fullScroll(View.FOCUS_DOWN);
                    txv_p1life.setTextSize(lifeFontSizeSmall);
                    txv_p2life.setTextSize(lifeFontSizeSmall);
                    txv_p3life.setTextSize(lifeFontSizeSmall);
                    txv_p4life.setTextSize(lifeFontSizeSmall);
                    txv_p5life.setTextSize(lifeFontSizeSmall);
                    txv_p6life.setTextSize(lifeFontSizeSmall);

                } else {
                    scroll_p1.setVisibility(View.GONE);
                    scroll_p2.setVisibility(View.GONE);
                    scroll_p3.setVisibility(View.GONE);
                    scroll_p4.setVisibility(View.GONE);
                    scroll_p5.setVisibility(View.GONE);
                    scroll_p6.setVisibility(View.GONE);
                    txv_p1life.setTextSize(lifeFontSizeLarge);
                    txv_p2life.setTextSize(lifeFontSizeLarge);
                    txv_p3life.setTextSize(lifeFontSizeLarge);
                    txv_p4life.setTextSize(lifeFontSizeLarge);
                    txv_p5life.setTextSize(lifeFontSizeLarge);
                    txv_p6life.setTextSize(lifeFontSizeLarge);
                }
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
                            editor.putInt(Repository.KEY_P2POISON, Repository.p2poison);
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
                            editor.putInt(Repository.KEY_P3POISON, Repository.p3poison);
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
                            editor.putInt(Repository.KEY_P4POISON, Repository.p4poison);
                            SendFile(Repository.P4_POISON_FILENAME, Repository.p4poison + "");
                            Repository.p4log += "\n" + Repository.p4poison + Repository.POISON;
                            editor.putString(Repository.KEY_P4LOG, Repository.p4log);
                            editor.apply();
                            txv_p4lifetrack.setText(Repository.p4log);
                            SendFile(Repository.P4_LOG_FILENAME, Repository.p4log);
                        }
                    }
                }
                if(timerp5 != null) {
                    timerp5.cancel();
                    if (Repository.mode == Repository.LIFE) {
                        int newlife = Integer.parseInt(txv_p5life.getText().toString());
                        if(Repository.p5life != newlife) {
                            Repository.p5life = newlife;
                            SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
                            editor.putInt(Repository.KEY_P5LIFE, Repository.p5life);
                            SendFile(Repository.P5_LIFE_FILENAME, Repository.p5life + "");
                            Repository.p5log += "\n" + Repository.p5life;
                            editor.putString(Repository.KEY_P5LOG, Repository.p5log);
                            editor.apply();
                            txv_p5lifetrack.setText(Repository.p5log);
                            SendFile(Repository.P5_LOG_FILENAME, Repository.p5log);
                        }
                    }
                    else {
                        String s = txv_p5life.getText().toString();
                        int newlife = Integer.parseInt(s.substring(0, s.length()-1));
                        if(Repository.p5poison != newlife) {
                            Repository.p5poison = newlife;
                            SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
                            editor.putInt(Repository.KEY_P5POISON, Repository.p5poison);
                            SendFile(Repository.P5_POISON_FILENAME, Repository.p5poison + "");
                            Repository.p5log += "\n" + Repository.p5poison + Repository.POISON;
                            editor.putString(Repository.KEY_P5LOG, Repository.p5log);
                            editor.apply();
                            txv_p5lifetrack.setText(Repository.p5log);
                            SendFile(Repository.P5_LOG_FILENAME, Repository.p5log);
                        }
                    }
                }
                if(timerp6 != null) {
                    timerp6.cancel();
                    if (Repository.mode == Repository.LIFE) {
                        int newlife = Integer.parseInt(txv_p6life.getText().toString());
                        if(Repository.p6life != newlife) {
                            Repository.p6life = newlife;
                            SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
                            editor.putInt(Repository.KEY_P6LIFE, Repository.p6life);
                            SendFile(Repository.P6_LIFE_FILENAME, Repository.p6life + "");
                            Repository.p6log += "\n" + Repository.p6life;
                            editor.putString(Repository.KEY_P6LOG, Repository.p6log);
                            editor.apply();
                            txv_p6lifetrack.setText(Repository.p6log);
                            SendFile(Repository.P6_LOG_FILENAME, Repository.p6log);
                        }
                    }
                    else {
                        String s = txv_p6life.getText().toString();
                        int newlife = Integer.parseInt(s.substring(0, s.length()-1));
                        if(Repository.p6poison != newlife) {
                            Repository.p6poison = newlife;
                            SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
                            editor.putInt(Repository.KEY_P6POISON, Repository.p6poison);
                            SendFile(Repository.P6_POISON_FILENAME, Repository.p6poison + "");
                            Repository.p6log += "\n" + Repository.p6poison + Repository.POISON;
                            editor.putString(Repository.KEY_P6LOG, Repository.p6log);
                            editor.apply();
                            txv_p6lifetrack.setText(Repository.p6log);
                            SendFile(Repository.P6_LOG_FILENAME, Repository.p6log);
                        }
                    }
                }
                if(Repository.mode == Repository.LIFE) {
                    Repository.mode = Repository.POISON;
                    txv_p1life.setText(Repository.p1poison + "" + Repository.POISON);
                    txv_p2life.setText(Repository.p2poison + "" + Repository.POISON);
                    txv_p3life.setText(Repository.p3poison + "" + Repository.POISON);
                    txv_p4life.setText(Repository.p4poison + "" + Repository.POISON);
                    txv_p5life.setText(Repository.p5poison + "" + Repository.POISON);
                    txv_p6life.setText(Repository.p6poison + "" + Repository.POISON);
                    txv_p1setlife.setText(Repository.p1poison + "" + Repository.POISON);
                    txv_p2setlife.setText(Repository.p2poison + "" + Repository.POISON);
                    txv_p3setlife.setText(Repository.p3poison + "" + Repository.POISON);
                    txv_p4setlife.setText(Repository.p4poison + "" + Repository.POISON);
                    txv_p5setlife.setText(Repository.p5poison + "" + Repository.POISON);
                    txv_p6setlife.setText(Repository.p6poison + "" + Repository.POISON);
                }
                else {
                    Repository.mode = Repository.LIFE;
                    txv_p1life.setText(Repository.p1life + "");
                    txv_p2life.setText(Repository.p2life + "");
                    txv_p3life.setText(Repository.p3life + "");
                    txv_p4life.setText(Repository.p4life + "");
                    txv_p5life.setText(Repository.p5life + "");
                    txv_p6life.setText(Repository.p6life + "");
                    txv_p1setlife.setText(Repository.p1life + "");
                    txv_p2setlife.setText(Repository.p2life + "");
                    txv_p3setlife.setText(Repository.p3life + "");
                    txv_p4setlife.setText(Repository.p4life + "");
                    txv_p5setlife.setText(Repository.p5life + "");
                    txv_p6setlife.setText(Repository.p6life + "");
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
        Repository.p5life = 20;
        Repository.p6life = 20;
        Repository.p1poison = 0;
        Repository.p2poison = 0;
        Repository.p3poison = 0;
        Repository.p4poison = 0;
        Repository.p5poison = 0;
        Repository.p6poison = 0;
        Repository.p1log = "20";
        Repository.p2log = "20";
        Repository.p3log = "20";
        Repository.p4log = "20";
        Repository.p5log = "20";
        Repository.p6log = "20";
        SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
        editor.putInt(Repository.KEY_P1LIFE, Repository.p1life);
        editor.putInt(Repository.KEY_P2LIFE, Repository.p2life);
        editor.putInt(Repository.KEY_P3LIFE, Repository.p3life);
        editor.putInt(Repository.KEY_P4LIFE, Repository.p4life);
        editor.putInt(Repository.KEY_P5LIFE, Repository.p5life);
        editor.putInt(Repository.KEY_P6LIFE, Repository.p6life);
        editor.putInt(Repository.KEY_P1POISON, Repository.p1poison);
        editor.putInt(Repository.KEY_P2POISON, Repository.p2poison);
        editor.putInt(Repository.KEY_P3POISON, Repository.p3poison);
        editor.putInt(Repository.KEY_P4POISON, Repository.p4poison);
        editor.putInt(Repository.KEY_P5POISON, Repository.p5poison);
        editor.putInt(Repository.KEY_P6POISON, Repository.p6poison);
        editor.putString(Repository.KEY_P1LOG, Repository.p1log);
        editor.putString(Repository.KEY_P2LOG, Repository.p2log);
        editor.putString(Repository.KEY_P3LOG, Repository.p3log);
        editor.putString(Repository.KEY_P4LOG, Repository.p4log);
        editor.putString(Repository.KEY_P5LOG, Repository.p5log);
        editor.putString(Repository.KEY_P6LOG, Repository.p6log);
        editor.apply();
        SendFile(Repository.P1_LIFE_FILENAME, Repository.p1life + "");
        SendFile(Repository.P1_POISON_FILENAME, Repository.p1poison+ "");
        SendFile(Repository.P1_LOG_FILENAME, Repository.p1log);
        SendFile(Repository.P2_LIFE_FILENAME, Repository.p2life + "");
        SendFile(Repository.P2_POISON_FILENAME, Repository.p2poison+ "");
        SendFile(Repository.P2_LOG_FILENAME, Repository.p2log);
        SendFile(Repository.P3_LIFE_FILENAME, Repository.p3life + "");
        SendFile(Repository.P3_POISON_FILENAME, Repository.p3poison+ "");
        SendFile(Repository.P3_LOG_FILENAME, Repository.p3log);
        SendFile(Repository.P4_LIFE_FILENAME, Repository.p4life + "");
        SendFile(Repository.P4_POISON_FILENAME, Repository.p4poison+ "");
        SendFile(Repository.P4_LOG_FILENAME, Repository.p4log);
        SendFile(Repository.P5_LIFE_FILENAME, Repository.p5life + "");
        SendFile(Repository.P5_POISON_FILENAME, Repository.p5poison+ "");
        SendFile(Repository.P5_LOG_FILENAME, Repository.p5log);
        SendFile(Repository.P6_LIFE_FILENAME, Repository.p6life + "");
        SendFile(Repository.P6_POISON_FILENAME, Repository.p6poison+ "");
        SendFile(Repository.P6_LOG_FILENAME, Repository.p6log);
        Repository.mode = Repository.LIFE;
        btn_lifepoison.setText(Repository.mode + "");
        txv_p1life.setText(Repository.p1life + "");
        txv_p2life.setText(Repository.p2life + "");
        txv_p3life.setText(Repository.p3life + "");
        txv_p4life.setText(Repository.p4life + "");
        txv_p5life.setText(Repository.p5life + "");
        txv_p6life.setText(Repository.p6life + "");
        txv_p1lifetrack.setText(Repository.p1log);
        txv_p2lifetrack.setText(Repository.p2log);
        txv_p3lifetrack.setText(Repository.p3log);
        txv_p4lifetrack.setText(Repository.p4log);
        txv_p5lifetrack.setText(Repository.p5log);
        txv_p6lifetrack.setText(Repository.p6log);
        cly_p1life.setVisibility(View.VISIBLE);
        cly_p2life.setVisibility(View.VISIBLE);
        cly_p3life.setVisibility(View.VISIBLE);
        cly_p4life.setVisibility(View.VISIBLE);
        cly_p5life.setVisibility(View.VISIBLE);
        cly_p6life.setVisibility(View.VISIBLE);
        scroll_p1.setVisibility(View.GONE);
        scroll_p2.setVisibility(View.GONE);
        scroll_p3.setVisibility(View.GONE);
        scroll_p4.setVisibility(View.GONE);
        scroll_p5.setVisibility(View.GONE);
        scroll_p6.setVisibility(View.GONE);
        cly_p1setlife.setVisibility(View.INVISIBLE);
        cly_p2setlife.setVisibility(View.INVISIBLE);
        cly_p3setlife.setVisibility(View.INVISIBLE);
        cly_p4setlife.setVisibility(View.INVISIBLE);
        cly_p5setlife.setVisibility(View.INVISIBLE);
        cly_p6setlife.setVisibility(View.INVISIBLE);
        txv_p1life.setTextSize(lifeFontSizeLarge);
        txv_p2life.setTextSize(lifeFontSizeLarge);
        txv_p3life.setTextSize(lifeFontSizeLarge);
        txv_p4life.setTextSize(lifeFontSizeLarge);
        txv_p5life.setTextSize(lifeFontSizeLarge);
        txv_p6life.setTextSize(lifeFontSizeLarge);
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
                        editor.putInt(Repository.KEY_P2POISON, Repository.p2poison);
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
                        editor.putInt(Repository.KEY_P3POISON, Repository.p3poison);
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
                        editor.putInt(Repository.KEY_P4POISON, Repository.p4poison);
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

    public void changep5life(int value) {
        if(timerp5 != null) {
            timerp5.cancel();
        }
        if(Repository.mode == Repository.LIFE) {
            txv_p5life.setText(Integer.parseInt(txv_p5life.getText().toString()) + value + "");
            timerp5 = new CountDownTimer(Repository.lifeDelay, 100) {

                @Override
                public void onTick(long millisUntilEnd) {
                }

                @Override
                public void onFinish() {
                    int newlife = Integer.parseInt(txv_p5life.getText().toString());
                    if(Repository.p5life != newlife) {
                        Repository.p5life = newlife;
                        SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
                        editor.putInt(Repository.KEY_P5LIFE, Repository.p5life);
                        SendFile(Repository.P5_LIFE_FILENAME, Repository.p5life + "");
                        Repository.p5log += "\n" + Repository.p5life;
                        editor.putString(Repository.KEY_P5LOG, Repository.p5log);
                        editor.apply();
                        txv_p5lifetrack.setText(Repository.p5log);
                        SendFile(Repository.P5_LOG_FILENAME, Repository.p5log);
                        scroll_p5.fullScroll(View.FOCUS_DOWN);
                    }
                }
            };
            timerp5.start();
        }
        else {
            String s = txv_p5life.getText().toString();
            txv_p5life.setText(Integer.parseInt(s.substring(0, s.length()-1)) + value + "" + Repository.POISON);
            timerp5 = new CountDownTimer(Repository.lifeDelay, 100) {

                @Override
                public void onTick(long millisUntilEnd) {
                }

                @Override
                public void onFinish() {
                    String s = txv_p5life.getText().toString();
                    int newlife = Integer.parseInt(s.substring(0, s.length()-1));
                    if(Repository.p5poison != newlife) {
                        Repository.p5poison = newlife;
                        SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
                        editor.putInt(Repository.KEY_P5POISON, Repository.p5poison);
                        SendFile(Repository.P5_POISON_FILENAME, Repository.p5poison + "");
                        Repository.p5log += "\n" + Repository.p5poison + Repository.POISON;
                        editor.putString(Repository.KEY_P5LOG, Repository.p5log);
                        editor.apply();
                        txv_p5lifetrack.setText(Repository.p5log);
                        SendFile(Repository.P5_LOG_FILENAME, Repository.p5log);
                        scroll_p5.fullScroll(View.FOCUS_DOWN);
                    }
                }
            };
            timerp5.start();
        }
    }

    public void changep6life(int value) {
        if(timerp6 != null) {
            timerp6.cancel();
        }
        if(Repository.mode == Repository.LIFE) {
            txv_p6life.setText(Integer.parseInt(txv_p6life.getText().toString()) + value + "");
            timerp6 = new CountDownTimer(Repository.lifeDelay, 100) {

                @Override
                public void onTick(long millisUntilEnd) {
                }

                @Override
                public void onFinish() {
                    int newlife = Integer.parseInt(txv_p6life.getText().toString());
                    if(Repository.p6life != newlife) {
                        Repository.p6life = newlife;
                        SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
                        editor.putInt(Repository.KEY_P6LIFE, Repository.p6life);
                        SendFile(Repository.P6_LIFE_FILENAME, Repository.p6life + "");
                        Repository.p6log += "\n" + Repository.p6life;
                        editor.putString(Repository.KEY_P6LOG, Repository.p6log);
                        editor.apply();
                        txv_p6lifetrack.setText(Repository.p6log);
                        SendFile(Repository.P6_LOG_FILENAME, Repository.p6log);
                        scroll_p6.fullScroll(View.FOCUS_DOWN);
                    }
                }
            };
            timerp6.start();
        }
        else {
            String s = txv_p6life.getText().toString();
            txv_p6life.setText(Integer.parseInt(s.substring(0, s.length()-1)) + value + "" + Repository.POISON);
            timerp6 = new CountDownTimer(Repository.lifeDelay, 100) {

                @Override
                public void onTick(long millisUntilEnd) {
                }

                @Override
                public void onFinish() {
                    String s = txv_p6life.getText().toString();
                    int newlife = Integer.parseInt(s.substring(0, s.length()-1));
                    if(Repository.p6poison != newlife) {
                        Repository.p6poison = newlife;
                        SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
                        editor.putInt(Repository.KEY_P6POISON, Repository.p6poison);
                        SendFile(Repository.P6_POISON_FILENAME, Repository.p6poison + "");
                        Repository.p6log += "\n" + Repository.p6poison + Repository.POISON;
                        editor.putString(Repository.KEY_P6LOG, Repository.p6log);
                        editor.apply();
                        txv_p6lifetrack.setText(Repository.p6log);
                        SendFile(Repository.P6_LOG_FILENAME, Repository.p6log);
                        scroll_p6.fullScroll(View.FOCUS_DOWN);
                    }
                }
            };
            timerp6.start();
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
                        String folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + Repository.FOLDERNAME;
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
        AlertDialog alertDialog = new AlertDialog.Builder(Life6Activity.this)
                .setTitle(Translation.StringMap(79) + Repository.dice)
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
        AlertDialog alertDialog = new AlertDialog.Builder(Life6Activity.this)
                .setTitle(Translation.StringMap(80))
                .setView(input)
                .setPositiveButton(Translation.StringMap(81), new DialogInterface.OnClickListener() {
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
                .setNegativeButton(Translation.StringMap(82), null)
                .show();
    }
}