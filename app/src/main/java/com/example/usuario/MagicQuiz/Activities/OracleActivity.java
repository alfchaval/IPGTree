package com.example.usuario.MagicQuiz.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.example.usuario.MagicQuiz.R;
import com.example.usuario.MagicQuiz.Repository;

public class OracleActivity extends AppCompatActivity {

    AutoCompleteTextView actv_search, actv_number;
    TextView txv_oracle;
    Button btn_change_mode;

    String[] cardnames;
    String[] setnames;
    String[] numbers;

    private final int nameMode = 0;
    private final int setMode = 1;

    ArrayAdapter<String> nameAdapter;
    ArrayAdapter<String> setAdapter;
    ArrayAdapter<String> numberAdapter;

    Repository repository;

    String selectedSet;
    int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oracle);

        linkViews();
        setListeners();

        repository = Repository.getInstance();
        cardnames = repository.cards.keySet().toArray(new String[repository.cards.size()]);
        setnames = repository.sets.keySet().toArray(new String[repository.sets.size()]);
        nameAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, cardnames);
        setAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, setnames);
        changeMode(nameMode);

        //TODO Find a way to set the adapter after selecting a set.
        numbers = new String[9999];
        for(int j = 1; j < 10000; j++) {
            String s = "";
            if(j < 10) {
                s += "000";
            }
            else if (j < 100) {
                s += "00";
            }
            else if (j < 1000) {
                s += "0";
            }
            numbers[j-1] = s + j;
        }
        numberAdapter = new ArrayAdapter<String>(OracleActivity.this, android.R.layout.simple_list_item_1, numbers);
        actv_number.setAdapter(numberAdapter);
        //
    }

    public void linkViews() {
        actv_search = findViewById(R.id.actv_search);
        actv_number = findViewById(R.id.actv_number);
        txv_oracle = findViewById(R.id.txv_oracle);
        btn_change_mode = findViewById(R.id.btn_change_mode);
    }

    public void setListeners() {
        actv_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                InputMethodManager imm = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(actv_search.getWindowToken(), 0);
                switch (mode) {
                    case nameMode:
                        if(repository.cards.containsKey(actv_search.getText().toString())){
                            showCard(actv_search.getText().toString());
                        }
                        else {
                            txv_oracle.setText("No se encontraron resultados");
                        }
                        break;
                    case setMode:
                        if(repository.sets.containsKey(actv_search.getText().toString())) {
                            selectedSet = actv_search.getText().toString();
                            txv_oracle.setText("Set seleccionado:\n" + selectedSet);
                            /*
                            numbers = new String[repository.sets.get(selectedSet).cards];
                            for(int j = 1; j < repository.sets.get(selectedSet).cards; j++) {
                                String s = "";
                                if(j < 10) {
                                    s += "00";
                                }
                                else if (j < 100) {
                                    s += "0";
                                }
                                numbers[j-1] = s + j;
                            }
                            numberAdapter = new ArrayAdapter<String>(OracleActivity.this, android.R.layout.simple_list_item_1, numbers);
                            actv_number.setAdapter(numberAdapter);
                            */
                            actv_number.setVisibility(View.VISIBLE);
                        }
                        break;
                }
            }
        });
        actv_number.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                InputMethodManager imm = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(actv_search.getWindowToken(), 0);
                try {
                    int number = Integer.valueOf(actv_number.getText().toString());
                    //remove first check (unnecesary) after solve problem with number of cards per set
                    if(number < repository.setsWithCards.get(repository.sets.get(selectedSet).code).length && repository.setsWithCards.get(repository.sets.get(selectedSet).code)[number-1] != null) {
                        showCard(repository.setsWithCards.get(repository.sets.get(selectedSet).code)[number-1]);
                    }
                    else {
                        txv_oracle.setText("No se encontraron resultados");
                    }
                } catch (Exception e) {
                    txv_oracle.setText("ERROR");
                }
            }
        });
        btn_change_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mode) {
                    case nameMode:
                        changeMode(setMode);
                        break;
                    case setMode:
                        changeMode(nameMode);
                        break;
                }
            }
        });
    }

    public void showCard(String name) {
        txv_oracle.setText(repository.cards.get(name).name + " ");
        if(repository.cards.get(name).manaCost != null) {
            txv_oracle.setText(txv_oracle.getText() + repository.cards.get(name).manaCost + "\n\n");
        }
        if(repository.cards.get(name).type != null) {
            txv_oracle.setText(txv_oracle.getText() + repository.cards.get(name).type + "\n\n");
        }
        if(repository.cards.get(name).text != null) {
            txv_oracle.setText(txv_oracle.getText() + repository.cards.get(name).text + "\n\n");
        }
        if(repository.cards.get(name).power != null) {
            txv_oracle.setText(txv_oracle.getText() + repository.cards.get(name).power + "/");
        }
        if(repository.cards.get(name).toughness != null) {
            txv_oracle.setText(txv_oracle.getText() + repository.cards.get(name).toughness);
        }
        if(repository.cards.get(name).loyalty != null) {
            txv_oracle.setText(txv_oracle.getText() + repository.cards.get(name).loyalty);
        }
    }

    public void changeMode(int newMode) {
        mode = newMode;
        switch (mode) {
            case nameMode:
                actv_search.setAdapter(nameAdapter);
                actv_search.setHint("Introduce una carta en inglés");
                actv_search.setText("");
                actv_number.setVisibility(View.GONE);
                btn_change_mode.setText("Cambiar a búsqueda por edición y número");
                break;
            case setMode:
                actv_search.setAdapter(setAdapter);
                actv_search.setHint("Introduce una edición en inglés");
                actv_search.setText("");
                actv_number.setText("");
                btn_change_mode.setText("Cambiar a búsqueda por nombre");
                break;
        }
    }
}