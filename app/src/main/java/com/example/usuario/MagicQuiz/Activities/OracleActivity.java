package com.example.usuario.MagicQuiz.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.example.usuario.MagicQuiz.Card;
import com.example.usuario.MagicQuiz.R;
import com.example.usuario.MagicQuiz.Read;

import java.util.HashMap;

public class OracleActivity extends AppCompatActivity {

    EditText edt_search;
    TextView txv_oracle;

    HashMap<String, Card> cards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oracle);

        edt_search = findViewById(R.id.edt_search);
        edt_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if(cards.containsKey(edt_search.getText().toString())){
                        txv_oracle.setText(cards.get(edt_search.getText().toString()).text);
                    }
                    else {
                        txv_oracle.setText("No se encontraron resultados");
                    }
                    return true;
                }
                return false;
            }
        });
        txv_oracle = findViewById(R.id.txv_oracle);

        cards = Read.loadDatabase(getApplicationContext(), txv_oracle);
    }
}