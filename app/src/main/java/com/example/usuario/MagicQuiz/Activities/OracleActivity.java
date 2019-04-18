package com.example.usuario.MagicQuiz.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.example.usuario.MagicQuiz.Card;
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

    int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oracle);

        linkViews();
        setListeners();

        repository = Repository.getInstance();
        cardnames = repository.cards.keySet().toArray(new String[repository.cards.size()]);
        setnames = repository.setsMap.keySet().toArray(new String[repository.sets.size()]);
        numbers = new String[999];
        for(int i = 1; i < 1000; i++) {
            String s = "";
            if(i < 10) {
                s += "00";
            }
            else if (i < 100) {
                s += "0";
            }
            numbers[i-1] = s + i;
        }
        nameAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, cardnames);
        setAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, setnames);
        numberAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, numbers);
        actv_number.setAdapter(numberAdapter);
        changeMode(nameMode);
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
                        searchSetAndNumber();
                        break;
                }
            }
        });
        actv_number.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                InputMethodManager imm = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(actv_search.getWindowToken(), 0);
                searchSetAndNumber();
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
                actv_search.setCompletionHint("Introduce una carta en inglés");
                actv_search.setText("");
                actv_number.setVisibility(View.GONE);
                btn_change_mode.setText("Cambiar a búsqueda por edición y número");
                break;
            case setMode:
                actv_search.setAdapter(setAdapter);
                actv_search.setCompletionHint("Introduce una edición en inglés");
                actv_search.setText("");
                actv_number.setText("4");
                actv_number.setVisibility(View.VISIBLE);
                btn_change_mode.setText("Cambiar a búsqueda por nombre");
                break;
        }
    }

    public void searchSetAndNumber() {
        try {
            int number = Integer.parseInt(actv_number.getText().toString());
            Pair<String,Integer> cardId = new Pair<>(actv_search.getText().toString(), number);
            boolean found = false;
            int index = 0;
            Card card = new Card();
            while(!found && index < cardnames.length) {
                card = repository.cards.get(cardnames[index]);
                int subIndex = 0;
                while(!found && subIndex < card.printings.size()) {
                    if(card.printings.get(subIndex) == cardId) {
                        found = true;
                    }
                    subIndex++;
                }
                index++;
            }
            if(found) {
                showCard(card.name);
            }
            else {
                txv_oracle.setText("No se encontraron resultados");
            }
        }
        catch (Exception e) {
            e.getLocalizedMessage();
        }
    }
}