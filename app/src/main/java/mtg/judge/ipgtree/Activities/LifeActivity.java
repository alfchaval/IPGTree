package mtg.judge.ipgtree.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import mtg.judge.ipgtree.R;
import mtg.judge.ipgtree.Repository;

public class LifeActivity extends AppCompatActivity {

    public final static char LIFE = '♥';
    public final static char POISON = 'ϕ';

    Button btn_reset, btn_registry, btn_lifepoison;
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
    int p1poison = 0;
    int p2poison = 0;
    char mode = LIFE;

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
        btn_lifepoison = findViewById(R.id.btn_lifepoison);

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
                if(mode == POISON) {
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
                        if(mode == POISON) {
                            txv_p1setlife.setText(txv_p1setlife.getText().toString() + POISON);
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
                        if(mode == POISON) {
                            txv_p1setlife.setText(txv_p1setlife.getText().toString() + POISON);
                        }
                        break;
                    case Repository.CodeDelete:
                        if (Math.abs(Integer.parseInt(number)) > 9) {
                            txv_p1setlife.setText(number.substring(0, number.length() - 1));
                        }
                        else {
                            txv_p1setlife.setText("0");
                        }
                        if(mode == POISON) {
                            txv_p1setlife.setText(txv_p1setlife.getText().toString() + POISON);
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
                        if(mode == LIFE) {
                            p1life = Integer.parseInt(number);
                            txv_p1life.setText(p1life + "");
                            txv_p1lifetrack.setText(txv_p1lifetrack.getText().toString() + "\n" + number);
                        }
                        else {
                            p1poison = Integer.parseInt(number);
                            txv_p1life.setText(p1poison + "" + POISON);
                            txv_p1lifetrack.setText(txv_p1lifetrack.getText().toString() + "\n" + number + POISON);
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
                if(mode == POISON) {
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
                        if(mode == POISON) {
                            txv_p2setlife.setText(txv_p2setlife.getText().toString() + POISON);
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
                        if(mode == POISON) {
                            txv_p2setlife.setText(txv_p2setlife.getText().toString() + POISON);
                        }
                        break;
                    case Repository.CodeDelete:
                        if (Math.abs(Integer.parseInt(number)) > 9) {
                            txv_p2setlife.setText(number.substring(0, number.length() - 1));
                        }
                        else {
                            txv_p2setlife.setText("0");
                        }
                        if(mode == POISON) {
                            txv_p2setlife.setText(txv_p2setlife.getText().toString() + POISON);
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
                        if(mode == LIFE) {
                            p2life = Integer.parseInt(number);
                            txv_p2life.setText(p2life + "");
                            txv_p2lifetrack.setText(txv_p2lifetrack.getText().toString() + "\n" + number);
                        }
                        else {
                            p2poison = Integer.parseInt(number);
                            txv_p2life.setText(p2poison + "" + POISON);
                            txv_p2lifetrack.setText(txv_p2lifetrack.getText().toString() + "\n" + number + POISON);
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
                if (mode == LIFE) {
                    int newlife = Integer.parseInt(txv_p1life.getText().toString());
                    if(p1life != newlife) {
                        p1life = newlife;
                        txv_p1lifetrack.setText(txv_p1lifetrack.getText().toString() + "\n" + p1life);
                    }
                    txv_p1setlife.setText(p1life + "");
                }
                else {
                    String s = txv_p1life.getText().toString();
                    int newlife = Integer.parseInt(s.substring(0, s.length()-1));
                    if(p1poison != newlife) {
                        p1poison = newlife;
                        txv_p1lifetrack.setText(txv_p1lifetrack.getText().toString() + "\n" + p1poison + POISON);
                    }
                    txv_p1setlife.setText(p1poison + "" + POISON);
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
                if (mode == LIFE) {
                    int newlife = Integer.parseInt(txv_p2life.getText().toString());
                    if(p2life != newlife) {
                        p2life = newlife;
                        txv_p2lifetrack.setText(txv_p2lifetrack.getText().toString() + "\n" + p2life);
                    }
                    txv_p2setlife.setText(p2life + "");
                }
                else {
                    String s = txv_p2life.getText().toString();
                    int newlife = Integer.parseInt(s.substring(0, s.length()-1));
                    if(p2poison != newlife) {
                        p2poison = newlife;
                        txv_p2lifetrack.setText(txv_p2lifetrack.getText().toString() + "\n" + p2poison + POISON);
                    }
                    txv_p2setlife.setText(p2poison + "" + POISON);
                }
                cly_p2life.setVisibility(View.INVISIBLE);
                scroll_p2.setVisibility(View.GONE);
                cly_p2setlife.setVisibility(View.VISIBLE);
                return true;
            }
        });

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(LifeActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Reiniciar vidas")
                        .setMessage("¿Seguro que quieres reiniciar las vidas?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                reset();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
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
        btn_lifepoison.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timerp1 != null) {
                    timerp1.cancel();
                    if (mode == LIFE) {
                        int newlife = Integer.parseInt(txv_p1life.getText().toString());
                        if(p1life != newlife) {
                            p1life = newlife;
                            txv_p1lifetrack.setText(txv_p1lifetrack.getText().toString() + "\n" + p1life);
                        }
                    }
                    else {
                        String s = txv_p1life.getText().toString();
                        int newlife = Integer.parseInt(s.substring(0, s.length()-1));
                        if(p1poison != newlife) {
                            p1poison = newlife;
                            txv_p1lifetrack.setText(txv_p1lifetrack.getText().toString() + "\n" + p1poison + POISON);
                        }
                    }
                }
                if(timerp2 != null) {
                    timerp2.cancel();
                    if (mode == LIFE) {
                        int newlife = Integer.parseInt(txv_p2life.getText().toString());
                        if(p2life != newlife) {
                            p2life = newlife;
                            txv_p2lifetrack.setText(txv_p2lifetrack.getText().toString() + "\n" + p2life);
                        }
                    }
                    else {
                        String s = txv_p2life.getText().toString();
                        int newlife = Integer.parseInt(s.substring(0, s.length()-1));
                        if(p2poison != newlife) {
                            p2poison = newlife;
                            txv_p2lifetrack.setText(txv_p2lifetrack.getText().toString() + "\n" + p2poison + POISON);
                        }
                    }
                }
                if(mode == LIFE) {
                    mode = POISON;
                    txv_p1life.setText(p1poison + "" + POISON);
                    txv_p2life.setText(p2poison + "" + POISON);
                    txv_p1setlife.setText(p1poison + "" + POISON);
                    txv_p2setlife.setText(p2poison + "" + POISON);
                }
                else {
                    mode = LIFE;
                    txv_p1life.setText(p1life + "");
                    txv_p2life.setText(p2life + "");
                    txv_p1setlife.setText(p1life + "");
                    txv_p2setlife.setText(p2life + "");
                }
                btn_lifepoison.setText(mode + "");
            }
        });

    }

    public void reset() {
        p1life = 20;
        p2life = 20;
        p1poison = 0;
        p2poison = 0;
        mode = LIFE;
        btn_lifepoison.setText(mode + "");
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
        if(mode == LIFE) {
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
        else {
            String s = txv_p1life.getText().toString();
            txv_p1life.setText(Integer.parseInt(s.substring(0, s.length()-1)) + value + "" + POISON);
            timerp1 = new CountDownTimer(waitUntilRegister, 100) {

                @Override
                public void onTick(long millisUntilEnd) {
                }

                @Override
                public void onFinish() {
                    String s = txv_p1life.getText().toString();
                    int newlife = Integer.parseInt(s.substring(0, s.length()-1));
                    if(p1poison != newlife) {
                        p1poison = newlife;
                        txv_p1lifetrack.setText(txv_p1lifetrack.getText().toString() + "\n" + p1poison + POISON);
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
        if(mode == LIFE) {
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
        else {
            String s = txv_p2life.getText().toString();
            txv_p2life.setText(Integer.parseInt(s.substring(0, s.length()-1)) + value + "" + POISON);
            timerp2 = new CountDownTimer(waitUntilRegister, 100) {

                @Override
                public void onTick(long millisUntilEnd) {
                }

                @Override
                public void onFinish() {
                    String s = txv_p2life.getText().toString();
                    int newlife = Integer.parseInt(s.substring(0, s.length()-1));
                    if(p2poison != newlife) {
                        p2poison = newlife;
                        txv_p2lifetrack.setText(txv_p2lifetrack.getText().toString() + "\n" + p2poison + POISON);
                        scroll_p2.fullScroll(View.FOCUS_DOWN);
                    }
                }
            };
            timerp2.start();
        }
    }
}