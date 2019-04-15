package com.example.usuario.MagicQuiz.Activities;

import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.usuario.MagicQuiz.R;

public class LifeActivity extends AppCompatActivity {

    public final static int Code0 = 0;
    public final static int Code1 = 1;
    public final static int Code2 = 2;
    public final static int Code3 = 3;
    public final static int Code4 = 4;
    public final static int Code5 = 5;
    public final static int Code6 = 6;
    public final static int Code7 = 7;
    public final static int Code8 = 8;
    public final static int Code9 = 9;
    public final static int CodeSign = -1;
    public final static int CodeDelete = -5;
    public final static int CodeCancel = -100;
    public final static int CodeOk = 100;

    Button btn_reset, btn_registry;
    TextView txv_p1minus, txv_p1life, txv_p1add, txv_p1lifetrack, txv_p1setlife;
    TextView txv_p2minus, txv_p2life, txv_p2add, txv_p2lifetrack, txv_p2setlife;
    ConstraintLayout cly_p1life, cly_p1setlife, cly_p2life, cly_p2setlife;
    ScrollView scroll_p1, scroll_p2;
    Keyboard keyboard_p1, keyboard_p2;
    KeyboardView keyboardView_p1, keyboardView_p2;
    CountDownTimer timerp1 = null;
    CountDownTimer timerp2 = null;

    int p1life = 0;
    int p2life = 0;

    static int waitUntilRegister = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life);

        linkViews();
        setListeners();
        reset();
    }

    private void linkViews() {
        btn_reset = findViewById(R.id.btn_reset);
        btn_registry = findViewById(R.id.btn_registry);

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

        cly_p1life = findViewById(R.id.cly_p1life);
        cly_p1setlife = findViewById(R.id.cly_p1setlife);
        cly_p2life = findViewById(R.id.cly_p2life);
        cly_p2setlife = findViewById(R.id.cly_p2setlife);

        scroll_p1 = findViewById(R.id.scroll_p1);
        scroll_p2 = findViewById(R.id.scroll_p2);

        keyboard_p1 = new Keyboard(LifeActivity.this, R.xml.life_keyboard);
        keyboard_p2 = new Keyboard(LifeActivity.this, R.xml.life_keyboard);
        keyboardView_p1 = findViewById(R.id.keyboardView_p1);
        keyboardView_p2 = findViewById(R.id.keyboardView_p2);
        keyboardView_p1.setKeyboard(keyboard_p1);
        keyboardView_p2.setKeyboard(keyboard_p2);
        keyboardView_p1.setPreviewEnabled(false);
        keyboardView_p2.setPreviewEnabled(false);
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
                switch (i) {
                    case Code0:
                    case Code1:
                    case Code2:
                    case Code3:
                    case Code4:
                    case Code5:
                    case Code6:
                    case Code7:
                    case Code8:
                    case Code9:
                        if(!number.equals("0")) {
                            txv_p1setlife.setText(number + i);
                        }
                        else {
                            txv_p1setlife.setText(i + "");
                        }
                        break;
                    case CodeSign:
                        if(txv_p1setlife.getText().toString().charAt(0) != '-') {
                            if(!number.equals("0")) {
                                txv_p1setlife.setText("-" + number);
                            }
                        }
                        else {
                            txv_p1setlife.setText(number.substring(1));
                        }
                        break;
                    case CodeDelete:
                        if (Math.abs(Integer.parseInt(number)) > 9) {
                            txv_p1setlife.setText(number.substring(0, number.length() - 1));
                        }
                        else {
                            txv_p1setlife.setText("0");
                        }
                        break;
                    case CodeCancel:
                        cly_p1life.setVisibility(View.VISIBLE);
                        cly_p1setlife.setVisibility(View.INVISIBLE);
                        if(scroll_p2.getVisibility() == View.VISIBLE) {
                            scroll_p1.setVisibility(View.VISIBLE);
                            scroll_p1.fullScroll(View.FOCUS_DOWN);
                        }
                        break;
                    case CodeOk:
                        if(number.length() > 9) {
                            number = number.substring(0, 9);
                        }
                        p1life = Integer.parseInt(number);
                        txv_p1life.setText(p1life + "");
                        txv_p1lifetrack.setText(txv_p1lifetrack.getText().toString() + "\n" + number);
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
                switch (i) {
                    case Code0:
                    case Code1:
                    case Code2:
                    case Code3:
                    case Code4:
                    case Code5:
                    case Code6:
                    case Code7:
                    case Code8:
                    case Code9:
                        if(!number.equals("0")) {
                            txv_p2setlife.setText(number + i);
                        }
                        else {
                            txv_p2setlife.setText(i + "");
                        }
                        break;
                    case CodeSign:
                        if(txv_p2setlife.getText().toString().charAt(0) != '-') {
                            if(!number.equals("0")) {
                                txv_p2setlife.setText("-" + number);
                            }
                        }
                        else {
                            txv_p2setlife.setText(number.substring(1));
                        }
                        break;
                    case CodeDelete:
                        if (Math.abs(Integer.parseInt(number)) > 9) {
                            txv_p2setlife.setText(number.substring(0, number.length() - 1));
                        }
                        else {
                            txv_p2setlife.setText("0");
                        }
                        break;
                    case CodeCancel:
                        cly_p2life.setVisibility(View.VISIBLE);
                        cly_p2setlife.setVisibility(View.INVISIBLE);
                        if(scroll_p1.getVisibility() == View.VISIBLE) {
                            scroll_p2.setVisibility(View.VISIBLE);
                            scroll_p2.fullScroll(View.FOCUS_DOWN);
                        }
                        break;
                    case CodeOk:
                        if(number.length() > 9) {
                            number = number.substring(0, 9);
                        }
                        p2life = Integer.parseInt(number);
                        txv_p2life.setText(p2life + "");
                        txv_p2lifetrack.setText(txv_p2lifetrack.getText().toString() + "\n" + number);
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
    }

    private void setListeners() {
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
        txv_p1life.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(timerp1 != null) {
                    timerp1.cancel();
                }
                int newlife = Integer.parseInt(txv_p1life.getText().toString());
                if(p1life != newlife) {
                    p1life = newlife;
                    txv_p1lifetrack.setText(txv_p1lifetrack.getText().toString() + "\n" + p1life);
                }
                txv_p1setlife.setText(p1life + "");
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
                int newlife = Integer.parseInt(txv_p2life.getText().toString());
                if(p2life != newlife) {
                    p2life = newlife;
                    txv_p2lifetrack.setText(txv_p2lifetrack.getText().toString() + "\n" + p2life);
                }
                txv_p2setlife.setText(p2life + "");
                cly_p2life.setVisibility(View.INVISIBLE);
                scroll_p2.setVisibility(View.GONE);
                cly_p2setlife.setVisibility(View.VISIBLE);
                return true;
            }
        });

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
            }
        });
        btn_registry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (scroll_p1.getVisibility() == View.GONE || scroll_p2.getVisibility() == View.GONE) {
                    scroll_p1.setVisibility(View.VISIBLE);
                    scroll_p1.fullScroll(View.FOCUS_DOWN);
                    scroll_p2.setVisibility(View.VISIBLE);
                    scroll_p2.fullScroll(View.FOCUS_DOWN);
                } else {
                    scroll_p1.setVisibility(View.GONE);
                    scroll_p2.setVisibility(View.GONE);
                }
            }
        });

    }

    public void reset() {
        p1life = 20;
        p2life = 20;
        txv_p1life.setText("20");
        txv_p2life.setText("20");
        txv_p1lifetrack.setText("20");
        txv_p2lifetrack.setText("20");
        cly_p1life.setVisibility(View.VISIBLE);
        cly_p2life.setVisibility(View.VISIBLE);
        scroll_p1.setVisibility(View.GONE);
        scroll_p2.setVisibility(View.GONE);
        cly_p1setlife.setVisibility(View.INVISIBLE);
        cly_p2setlife.setVisibility(View.INVISIBLE);
    }

    public void changep1life(int value) {
        if(timerp1 != null) {
            timerp1.cancel();
        }
        txv_p1life.setText(Integer.parseInt(txv_p1life.getText().toString()) + value + "");
        timerp1 = new CountDownTimer(waitUntilRegister, 100) {

            @Override
            public void onTick(long millisUntilEnd) {
            }

            @Override
            public void onFinish() {
                int newlife = Integer.parseInt(txv_p1life.getText().toString());
                if(p1life != newlife) {
                    p1life = newlife;
                    txv_p1lifetrack.setText(txv_p1lifetrack.getText().toString() + "\n" + p1life);
                    scroll_p1.fullScroll(View.FOCUS_DOWN);
                }
            }
        };
        timerp1.start();
    }

    public void changep2life(int value) {
        if(timerp2 != null) {
            timerp2.cancel();
        }
        txv_p2life.setText(Integer.parseInt(txv_p2life.getText().toString()) + value + "");
        timerp2 = new CountDownTimer(waitUntilRegister, 100) {

            @Override
            public void onTick(long millisUntilEnd) {
            }

            @Override
            public void onFinish() {
                int newlife = Integer.parseInt(txv_p2life.getText().toString());
                if(p2life != newlife) {
                    p2life = newlife;
                    txv_p2lifetrack.setText(txv_p2lifetrack.getText().toString() + "\n" + p2life);
                    scroll_p2.fullScroll(View.FOCUS_DOWN);
                    timerp2 = null;
                }
            }
        };
        timerp2.start();
    }
}