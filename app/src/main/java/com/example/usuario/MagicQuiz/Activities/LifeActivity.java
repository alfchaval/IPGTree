package com.example.usuario.MagicQuiz.Activities;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.usuario.MagicQuiz.R;

public class LifeActivity extends AppCompatActivity {

     //Eliminar
    Button btn_p1plus10, btn_p1plus1, btn_p1minus1, btn_p1minus10, btn_p2minus10, btn_p2minus1, btn_p2plus1, btn_p2plus10;
    //
    Button btn_reset, btn_registry;
    Button btn_p1_symbol, btn_p1_m_add, btn_p1_c_add, btn_p1_d_add, btn_p1_u_add, btn_p1_m_minus, btn_p1_c_minus, btn_p1_d_minus, btn_p1_u_minus, btn_p1_ok;
    Button btn_p2_symbol, btn_p2_m_add, btn_p2_c_add, btn_p2_d_add, btn_p2_u_add, btn_p2_m_minus, btn_p2_c_minus, btn_p2_d_minus, btn_p2_u_minus, btn_p2_ok;
    TextView txv_p1life, txv_p2life, txv_p1lifetrack, txv_p2lifetrack, txv_p1_m, txv_p1_c, txv_p1_d, txv_p1_u, txv_p1_symbol, txv_p2_m, txv_p2_c, txv_p2_d, txv_p2_u, txv_p2_symbol;
    ScrollView scroll_p1, scroll_p2;
    ConstraintLayout cl_p1, cl_p2;

    CountDownTimer timerp1;
    CountDownTimer timerp2;

    int p1life = 0;
    int p2life = 0;
    
    int p1m;
    int p1c;
    int p1d;
    int p1u;
    int p2m;
    int p2c;
    int p2d;
    int p2u;

    int nextp1life;
    int nextp2life;

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
        txv_p1life = findViewById(R.id.txv_p1life);
        txv_p2life = findViewById(R.id.txv_p2life);
        txv_p1lifetrack = findViewById(R.id.txv_p1lifetrack);
        txv_p2lifetrack = findViewById(R.id.txv_p2lifetrack);

        scroll_p1 = findViewById(R.id.scroll_p1);
        scroll_p2 = findViewById(R.id.scroll_p2);

        btn_p1plus10 = findViewById(R.id.btn_p1plus10);
        btn_p1plus1 = findViewById(R.id.btn_p1plus1);
        btn_p1minus1 = findViewById(R.id.btn_p1minus1);
        btn_p1minus10 = findViewById(R.id.btn_p1minus10);
        btn_reset = findViewById(R.id.btn_reset);
        btn_registry = findViewById(R.id.btn_registry);
        btn_p2minus10 = findViewById(R.id.btn_p2minus10);
        btn_p2minus1 = findViewById(R.id.btn_p2minus1);
        btn_p2plus1 = findViewById(R.id.btn_p2plus1);
        btn_p2plus10 = findViewById(R.id.btn_p2plus10);

        btn_p1_symbol = findViewById(R.id.btn_p1_symbol);
        btn_p1_m_add = findViewById(R.id.btn_p1_m_add);
        btn_p1_c_add = findViewById(R.id.btn_p1_c_add);
        btn_p1_d_add = findViewById(R.id.btn_p1_d_add);
        btn_p1_u_add = findViewById(R.id.btn_p1_u_add);
        txv_p1_m = findViewById(R.id.txv_p1_m);
        txv_p1_c = findViewById(R.id.txv_p1_c);
        txv_p1_d = findViewById(R.id.txv_p1_d);
        txv_p1_u = findViewById(R.id.txv_p1_u);
        btn_p1_m_minus = findViewById(R.id.btn_p1_m_minus);
        btn_p1_c_minus = findViewById(R.id.btn_p1_c_minus);
        btn_p1_d_minus = findViewById(R.id.btn_p1_d_minus);
        btn_p1_u_minus = findViewById(R.id.btn_p1_u_minus);
        txv_p1_symbol = findViewById(R.id.txv_p1_symbol);
        btn_p1_ok = findViewById(R.id.btn_p1_ok);

        btn_p2_symbol = findViewById(R.id.btn_p2_symbol);
        btn_p2_m_add = findViewById(R.id.btn_p2_m_add);
        btn_p2_c_add = findViewById(R.id.btn_p2_c_add);
        btn_p2_d_add = findViewById(R.id.btn_p2_d_add);
        btn_p2_u_add = findViewById(R.id.btn_p2_u_add);
        txv_p2_m = findViewById(R.id.txv_p2_m);
        txv_p2_c = findViewById(R.id.txv_p2_c);
        txv_p2_d = findViewById(R.id.txv_p2_d);
        txv_p2_u = findViewById(R.id.txv_p2_u);
        btn_p2_m_minus = findViewById(R.id.btn_p2_m_minus);
        btn_p2_c_minus = findViewById(R.id.btn_p2_c_minus);
        btn_p2_d_minus = findViewById(R.id.btn_p2_d_minus);
        btn_p2_u_minus = findViewById(R.id.btn_p2_u_minus);
        txv_p2_symbol = findViewById(R.id.txv_p2_symbol);
        btn_p2_ok = findViewById(R.id.btn_p2_ok);

        cl_p1 = findViewById(R.id.cl_p1);
        cl_p2 = findViewById(R.id.cl_p2);
    }

    private void setListeners() {
        txv_p1life.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(p1life > 0) {
                    txv_p1_symbol.setText("");
                }
                else {
                    txv_p1_symbol.setText("-");
                }
                p1m = p1life / 1000;
                p1c = p1life % 1000 / 100;
                p1d = p1life % 100 / 10;
                p1u = p1life % 10;
                txv_p1_m.setText(p1m + "");
                txv_p1_c.setText(p1c + "");
                txv_p1_d.setText(p1d + "");
                txv_p1_u.setText(p1u + "");
                if(p1m == 9) {
                    btn_p1_m_add.setVisibility(View.INVISIBLE);
                    btn_p1_m_minus.setVisibility(View.VISIBLE);
                }
                else if(p1m == 0) {
                    btn_p1_m_add.setVisibility(View.VISIBLE);
                    btn_p1_m_minus.setVisibility(View.INVISIBLE);
                }
                else {
                    btn_p1_m_add.setVisibility(View.VISIBLE);
                    btn_p1_m_minus.setVisibility(View.VISIBLE);
                }
                if(p1c == 9) {
                    btn_p1_c_add.setVisibility(View.INVISIBLE);
                    btn_p1_c_minus.setVisibility(View.VISIBLE);
                }
                else if(p1c == 0) {
                    btn_p1_c_add.setVisibility(View.VISIBLE);
                    btn_p1_c_minus.setVisibility(View.INVISIBLE);
                }
                else {
                    btn_p1_c_add.setVisibility(View.VISIBLE);
                    btn_p1_c_minus.setVisibility(View.VISIBLE);
                }
                if(p1d == 9) {
                    btn_p1_d_add.setVisibility(View.INVISIBLE);
                    btn_p1_d_minus.setVisibility(View.VISIBLE);
                }
                else if(p1d == 0) {
                    btn_p1_d_add.setVisibility(View.VISIBLE);
                    btn_p1_d_minus.setVisibility(View.INVISIBLE);
                }
                else {
                    btn_p1_d_add.setVisibility(View.VISIBLE);
                    btn_p1_d_minus.setVisibility(View.VISIBLE);
                }
                if(p1u == 9) {
                    btn_p1_u_add.setVisibility(View.INVISIBLE);
                    btn_p1_u_minus.setVisibility(View.VISIBLE);
                }
                else if(p1u == 0) {
                    btn_p1_u_add.setVisibility(View.VISIBLE);
                    btn_p1_u_minus.setVisibility(View.INVISIBLE);
                }
                else {
                    btn_p1_u_add.setVisibility(View.VISIBLE);
                    btn_p1_u_minus.setVisibility(View.VISIBLE);
                }
                cl_p1.setVisibility(View.VISIBLE);
                return true;
            }
        });
        txv_p2life.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(p2life > 0) {
                    txv_p2_symbol.setText("");
                }
                else {
                    txv_p2_symbol.setText("-");
                }
                p2m = p2life / 1000;
                p2c = p2life % 1000 / 100;
                p2d = p2life % 100 / 10;
                p2u = p2life % 10;
                txv_p2_m.setText(p2m + "");
                txv_p2_c.setText(p2c + "");
                txv_p2_d.setText(p2d + "");
                txv_p2_u.setText(p2u + "");
                if(p2m == 9) {
                    btn_p2_m_add.setVisibility(View.INVISIBLE);
                    btn_p2_m_minus.setVisibility(View.VISIBLE);
                }
                else if(p2m == 0) {
                    btn_p2_m_add.setVisibility(View.VISIBLE);
                    btn_p2_m_minus.setVisibility(View.INVISIBLE);
                }
                else {
                    btn_p2_m_add.setVisibility(View.VISIBLE);
                    btn_p2_m_minus.setVisibility(View.VISIBLE);
                }
                if(p2c == 9) {
                    btn_p2_c_add.setVisibility(View.INVISIBLE);
                    btn_p2_c_minus.setVisibility(View.VISIBLE);
                }
                else if(p2c == 0) {
                    btn_p2_c_add.setVisibility(View.VISIBLE);
                    btn_p2_c_minus.setVisibility(View.INVISIBLE);
                }
                else {
                    btn_p2_c_add.setVisibility(View.VISIBLE);
                    btn_p2_c_minus.setVisibility(View.VISIBLE);
                }
                if(p2d == 9) {
                    btn_p2_d_add.setVisibility(View.INVISIBLE);
                    btn_p2_d_minus.setVisibility(View.VISIBLE);
                }
                else if(p2d == 0) {
                    btn_p2_d_add.setVisibility(View.VISIBLE);
                    btn_p2_d_minus.setVisibility(View.INVISIBLE);
                }
                else {
                    btn_p2_d_add.setVisibility(View.VISIBLE);
                    btn_p2_d_minus.setVisibility(View.VISIBLE);
                }
                if(p2u == 9) {
                    btn_p2_u_add.setVisibility(View.INVISIBLE);
                    btn_p2_u_minus.setVisibility(View.VISIBLE);
                }
                else if(p2u == 0) {
                    btn_p2_u_add.setVisibility(View.VISIBLE);
                    btn_p2_u_minus.setVisibility(View.INVISIBLE);
                }
                else {
                    btn_p2_u_add.setVisibility(View.VISIBLE);
                    btn_p2_u_minus.setVisibility(View.VISIBLE);
                }
                cl_p2.setVisibility(View.VISIBLE);
                return true;
            }
        });
        btn_p1plus10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changep1life(10);
            }
        });
        btn_p1plus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changep1life(1);
            }
        });
        btn_p1minus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changep1life(-1);
            }
        });
        btn_p1minus10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changep1life(-10);
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
                if(scroll_p1.getVisibility() == View.GONE) {
                    scroll_p1.setVisibility(View.VISIBLE);
                    scroll_p2.setVisibility(View.VISIBLE);
                    cl_p1.setVisibility(View.GONE);
                    cl_p2.setVisibility(View.GONE);
                }
                else {
                    scroll_p1.setVisibility(View.GONE);
                    scroll_p1.fullScroll(View.FOCUS_DOWN);
                    scroll_p2.setVisibility(View.GONE);
                    scroll_p2.fullScroll(View.FOCUS_DOWN);
                }
            }
        });

        btn_p2minus10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changep2life(-10);
            }
        });
        btn_p2minus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changep2life(-1);
            }
        });
        btn_p2plus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changep2life(1);
            }
        });
        btn_p2plus10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changep2life(10);
            }
        });

        btn_p1_symbol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txv_p1_symbol.getText() == "") {
                    txv_p1_symbol.setText("-");
                }
                else {
                    txv_p1_symbol.setText("");
                }
            }
        });
        btn_p1_m_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(p1m < 9) {
                    txv_p1_m.setText(++p1m + "");
                    if(p1m == 9) {
                        btn_p1_m_add.setVisibility(View.INVISIBLE);
                    }
                    else {
                        btn_p1_m_add.setVisibility(View.VISIBLE);
                    }
                    btn_p1_m_minus.setVisibility(View.VISIBLE);
                }
            }
        });
        btn_p1_c_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(p1c < 9) {
                    txv_p1_c.setText(++p1c + "");
                    if(p1c == 9) {
                        btn_p1_c_add.setVisibility(View.INVISIBLE);
                    }
                    else {
                        btn_p1_c_add.setVisibility(View.VISIBLE);
                    }
                    btn_p1_c_minus.setVisibility(View.VISIBLE);
                }
            }
        });
        btn_p1_d_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(p1d < 9) {
                    txv_p1_d.setText(++p1d + "");
                    if(p1d == 9) {
                        btn_p1_d_add.setVisibility(View.INVISIBLE);
                    }
                    else {
                        btn_p1_d_add.setVisibility(View.VISIBLE);
                    }
                    btn_p1_d_minus.setVisibility(View.VISIBLE);
                }
            }
        });
        btn_p1_u_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(p1u < 9) {
                    txv_p1_u.setText(++p1u + "");
                    if(p1u == 9) {
                        btn_p1_u_add.setVisibility(View.INVISIBLE);
                    }
                    else {
                        btn_p1_u_add.setVisibility(View.VISIBLE);
                    }
                    btn_p1_u_minus.setVisibility(View.VISIBLE);
                }
            }
        });
        btn_p1_m_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(p1m > 0) {
                    txv_p1_m.setText(--p1m + "");
                    if(p1m == 0) {
                        btn_p1_m_minus.setVisibility(View.INVISIBLE);
                    }
                    else {
                        btn_p1_m_minus.setVisibility(View.VISIBLE);
                    }
                    btn_p1_m_add.setVisibility(View.VISIBLE);
                }
            }
        });
        btn_p1_c_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(p1c > 0) {
                    txv_p1_c.setText(--p1c + "");
                    if(p1c == 0) {
                        btn_p1_c_minus.setVisibility(View.INVISIBLE);
                    }
                    else {
                        btn_p1_c_minus.setVisibility(View.VISIBLE);
                    }
                    btn_p1_c_add.setVisibility(View.VISIBLE);
                }
            }
        });
        btn_p1_d_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(p1d > 0) {
                    txv_p1_d.setText(--p1d + "");
                    if(p1d == 0) {
                        btn_p1_d_minus.setVisibility(View.INVISIBLE);
                    }
                    else {
                        btn_p1_d_minus.setVisibility(View.VISIBLE);
                    }
                    btn_p1_d_add.setVisibility(View.VISIBLE);
                }
            }
        });
        btn_p1_u_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(p1u > 0) {
                    txv_p1_u.setText(--p1u + "");
                    if(p1u == 0) {
                        btn_p1_u_minus.setVisibility(View.INVISIBLE);
                    }
                    else {
                        btn_p1_u_minus.setVisibility(View.VISIBLE);
                    }
                    btn_p1_u_add.setVisibility(View.VISIBLE);
                }
            }
        });
        btn_p1_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p1life = p1m*1000 + p1c*100 + p1d*10 + p1u;
                if(txv_p1_symbol.getText() == "-") {
                    p1life *= -1;
                }
                txv_p1life.setText(p1life + "");
                txv_p1lifetrack.setText(txv_p1lifetrack.getText() + "\n" + p1life);
                cl_p1.setVisibility(View.GONE);
            }
        });

        btn_p2_symbol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txv_p2_symbol.getText() == "") {
                    txv_p2_symbol.setText("-");
                }
                else {
                    txv_p2_symbol.setText("");
                }
            }
        });
        btn_p2_m_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(p2m < 9) {
                    txv_p2_m.setText(++p2m + "");
                    if(p2m == 9) {
                        btn_p2_m_add.setVisibility(View.INVISIBLE);
                    }
                    else {
                        btn_p2_m_add.setVisibility(View.VISIBLE);
                    }
                    btn_p2_m_minus.setVisibility(View.VISIBLE);
                }
            }
        });
        btn_p2_c_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(p2c < 9) {
                    txv_p2_c.setText(++p2c + "");
                    if(p2c == 9) {
                        btn_p2_c_add.setVisibility(View.INVISIBLE);
                    }
                    else {
                        btn_p2_c_add.setVisibility(View.VISIBLE);
                    }
                    btn_p2_c_minus.setVisibility(View.VISIBLE);
                }
            }
        });
        btn_p2_d_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(p2d < 9) {
                    txv_p2_d.setText(++p2d + "");
                    if(p2d == 9) {
                        btn_p2_d_add.setVisibility(View.INVISIBLE);
                    }
                    else {
                        btn_p2_d_add.setVisibility(View.VISIBLE);
                    }
                    btn_p2_d_minus.setVisibility(View.VISIBLE);
                }
            }
        });
        btn_p2_u_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(p2u < 9) {
                    txv_p2_u.setText(++p2u + "");
                    if(p2u == 9) {
                        btn_p2_u_add.setVisibility(View.INVISIBLE);
                    }
                    else {
                        btn_p2_u_add.setVisibility(View.VISIBLE);
                    }
                    btn_p2_u_minus.setVisibility(View.VISIBLE);
                }
            }
        });
        btn_p2_m_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(p2m > 0) {
                    txv_p2_m.setText(--p2m + "");
                    if(p2m == 0) {
                        btn_p2_m_minus.setVisibility(View.INVISIBLE);
                    }
                    else {
                        btn_p2_m_minus.setVisibility(View.VISIBLE);
                    }
                    btn_p2_m_add.setVisibility(View.VISIBLE);
                }
            }
        });
        btn_p2_c_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(p2c > 0) {
                    txv_p2_c.setText(--p2c + "");
                    if(p2c == 0) {
                        btn_p2_c_minus.setVisibility(View.INVISIBLE);
                    }
                    else {
                        btn_p2_c_minus.setVisibility(View.VISIBLE);
                    }
                    btn_p2_c_add.setVisibility(View.VISIBLE);
                }
            }
        });
        btn_p2_d_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(p2d > 0) {
                    txv_p2_d.setText(--p2d + "");
                    if(p2d == 0) {
                        btn_p2_d_minus.setVisibility(View.INVISIBLE);
                    }
                    else {
                        btn_p2_d_minus.setVisibility(View.VISIBLE);
                    }
                    btn_p2_d_add.setVisibility(View.VISIBLE);
                }
            }
        });
        btn_p2_u_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(p2u > 0) {
                    txv_p2_u.setText(--p2u + "");
                    if(p2u == 0) {
                        btn_p2_u_minus.setVisibility(View.INVISIBLE);
                    }
                    else {
                        btn_p2_u_minus.setVisibility(View.VISIBLE);
                    }
                    btn_p2_u_add.setVisibility(View.VISIBLE);
                }
            }
        });
        btn_p2_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p2life = p2m*1000 + p2c*100 + p2d*10 + p2u;
                if(txv_p2_symbol.getText() == "-") {
                    p2life *= -1;
                }
                txv_p2life.setText(p2life + "");
                txv_p2lifetrack.setText(txv_p2lifetrack.getText() + "\n" + p2life);
                cl_p2.setVisibility(View.GONE);
            }
        });
    }

    public void reset() {
        p1life = 20;
        p2life = 20;
        nextp1life = p1life;
        nextp2life = p2life;
        txv_p1life.setText("20");
        txv_p2life.setText("20");
        txv_p1lifetrack.setText("20");
        txv_p2lifetrack.setText("20");
        cl_p1.setVisibility(View.GONE);
        cl_p2.setVisibility(View.GONE);
        scroll_p1.setVisibility(View.GONE);
        scroll_p2.setVisibility(View.GONE);
    }

    public void changep1life(int value) {
        if(timerp1 != null) {
            timerp1.cancel();
        }
        p1life = nextp1life;
        nextp1life = p1life + value;
        txv_p1life.setText(nextp1life + "");

        timerp1 = new CountDownTimer(waitUntilRegister, 100) {

            @Override
            public void onTick(long millisUntilEnd) {
            }

            @Override
            public void onFinish() {
                if(p1life != nextp1life) {
                    p1life = nextp1life;
                    txv_p1lifetrack.setText(txv_p1lifetrack.getText() + "\n" + p1life);
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
        p2life = nextp2life;
        nextp2life = p2life + value;
        txv_p2life.setText(nextp2life + "");

        timerp2 = new CountDownTimer(waitUntilRegister, 100) {

            @Override
            public void onTick(long millisUntilEnd) {
            }

            @Override
            public void onFinish() {
                if(p2life != nextp2life) {
                    p2life = nextp2life;
                    txv_p2lifetrack.setText(txv_p2lifetrack.getText() + "\n" + p2life);
                    scroll_p2.fullScroll(View.FOCUS_DOWN);
                }
            }
        };
        timerp2.start();
    }
}