package com.example.usuario.MagicQuiz.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.example.usuario.MagicQuiz.Card;
import com.example.usuario.MagicQuiz.R;
import com.example.usuario.MagicQuiz.Read;
import com.example.usuario.MagicQuiz.Repository;

import java.util.ArrayList;
import java.util.HashMap;

public class OracleActivity extends AppCompatActivity {

    AutoCompleteTextView actv_search;
    TextView txv_oracle;

    HashMap<String, Card> cards;
    String[] cardnames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oracle);

        linkViews();
        setListeners();

        cards = Repository.getInstance().cards;
        cardnames = cards.keySet().toArray(new String[cards.size()]);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,cardnames);
        actv_search.setAdapter(adapter);
    }

    public void linkViews() {
        actv_search = findViewById(R.id.actv_search);
        txv_oracle = findViewById(R.id.txv_oracle);
    }

    public void setListeners() {
        actv_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                InputMethodManager imm = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(actv_search.getWindowToken(), 0);
                //You don't need to check this, but just in case...
                if(cards.containsKey(actv_search.getText().toString())){
                    showCard(actv_search.getText().toString());
                }
                else {
                    txv_oracle.setText("No se encontraron resultados");
                }
            }
        });
    }

    public void showCard(String name) {
        txv_oracle.setText(cards.get(name).name + " ");
        if(cards.get(name).manaCost != null) {
            txv_oracle.setText(txv_oracle.getText() + cards.get(name).manaCost + "\n\n");
        }
        if(cards.get(name).type != null) {
            txv_oracle.setText(txv_oracle.getText() + cards.get(name).type + "\n\n");
        }
        if(cards.get(name).text != null) {
            txv_oracle.setText(txv_oracle.getText() + cards.get(name).text + "\n\n");
        }
        if(cards.get(name).power != null) {
            txv_oracle.setText(txv_oracle.getText() + cards.get(name).power + "/");
        }
        if(cards.get(name).toughness != null) {
            txv_oracle.setText(txv_oracle.getText() + cards.get(name).toughness);
        }
        if(cards.get(name).loyalty != null) {
            txv_oracle.setText(txv_oracle.getText() + cards.get(name).loyalty);
        }
    }
}