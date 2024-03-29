package mtg.judge.ipgtree.Activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.regex.Matcher;

import mtg.judge.ipgtree.R;
import mtg.judge.ipgtree.Utilities.Repository;
import mtg.judge.ipgtree.Utilities.Symbols;
import mtg.judge.ipgtree.Utilities.Translation;

public class OracleActivity extends AppCompatActivity {

    private AutoCompleteTextView actv_search, actv_number;
    private TextView txv_oracle;
    private Button btn_change_mode;

    private String[] cardnames;
    private String[] setnames;
    private String[] numbers;

    private final int nameMode = 0;
    private final int setMode = 1;

    private ArrayAdapter<String> nameAdapter;
    private ArrayAdapter<String> setAdapter;
    private ArrayAdapter<String> numberAdapter;

    private String selectedSet;
    private int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oracle);

        if(!Repository.repositoryLoaded) {
            finish();
        }

        linkViews();
        loadStrings();
        setListeners();

        if(ContextCompat.checkSelfPermission(OracleActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            loadCards();
        }
        else {
            ActivityCompat.requestPermissions(OracleActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }
    }

    public void loadCards() {
        if(Repository.cards.size() < 1) {
            txv_oracle.setText(Translation.StringMap(44));
        }
        else {
            cardnames = Repository.cards.keySet().toArray(new String[Repository.cards.size()]);
            Arrays.sort(cardnames);
            setnames = Repository.sets.keySet().toArray(new String[Repository.sets.size()]);
            Arrays.sort(setnames);
            nameAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, cardnames);
            setAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, setnames);

            //TODO Find a way to set the adapter after selecting a set.
            numbers = new String[0];
            numberAdapter = new ArrayAdapter<String>(OracleActivity.this, android.R.layout.simple_list_item_1, numbers);
            actv_number.setAdapter(numberAdapter);
        }
        changeMode(nameMode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Repository.databaseLoaded = false;
        Repository.loadDatabase(getApplicationContext());
        this.finish();
    }

    private void linkViews() {
        actv_search = findViewById(R.id.actv_search);
        actv_number = findViewById(R.id.actv_number);
        txv_oracle = findViewById(R.id.txv_oracle);
        btn_change_mode = findViewById(R.id.btn_change_mode);
    }

    private void loadStrings() {
        actv_search.setHint(Translation.StringMap(39));
    }

    private void setListeners() {
        actv_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                InputMethodManager imm = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(actv_search.getWindowToken(), 0);
                switch (mode) {
                    case nameMode:
                        if(Repository.cards.containsKey(actv_search.getText().toString())){
                            CardSpan(Repository.cards.get(actv_search.getText().toString()).showCard());
                        }
                        else {
                            txv_oracle.setText(Translation.StringMap(43));
                        }
                        break;
                    case setMode:
                        if(Repository.sets.containsKey(actv_search.getText().toString())) {
                            selectedSet = actv_search.getText().toString();
                            txv_oracle.setText(Translation.StringMap(45) + selectedSet);
                            setNumbers();
                            actv_number.setText("");
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
                    if(number < Repository.setsWithCards.get(Repository.sets.get(selectedSet).code).length && Repository.setsWithCards.get(Repository.sets.get(selectedSet).code)[number-1] != null) {
                        CardSpan(Repository.cards.get(Repository.setsWithCards.get(Repository.sets.get(selectedSet).code)[number-1]).showCard());
                    }
                    else {
                        txv_oracle.setText(Translation.StringMap(43));
                    }
                } catch (Exception e) {
                    txv_oracle.setText(Translation.StringMap(42));
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

    public void setNumbers() {
        int aux = numbers.length;
        numbers = new String[Repository.sets.get(selectedSet).cards];
        if(aux < numbers.length) {
            for(int j = aux; j < numbers.length; j++) {
                numberAdapter.add(j + "");
            }
        }
        else {
            for(int j = numbers.length; j < aux; j++) {
                numberAdapter.remove(j + "");
            }
        }
    }

    public void changeMode(int newMode) {
        mode = newMode;
        switch (mode) {
            case nameMode:
                actv_search.setAdapter(nameAdapter);
                actv_search.setHint(Translation.StringMap(39));
                actv_search.setText("");
                actv_number.setVisibility(View.GONE);
                btn_change_mode.setText(Translation.StringMap(41));
                break;
            case setMode:
                actv_search.setAdapter(setAdapter);
                actv_search.setHint(Translation.StringMap(46));
                actv_search.setText("");
                actv_number.setText("");
                btn_change_mode.setText(Translation.StringMap(40));
                break;
        }
    }

    private void CardSpan(String s)
    {
        Spannable spannable = new SpannableString(s);

        Matcher symbolMatcher = Symbols.SYMBOL_PATTERN.matcher(s);
        while(symbolMatcher.find()) {
            Drawable symbol = Symbols.getSymbol(symbolMatcher.group(), getApplicationContext());
            if (symbol != null) {
                symbol.setBounds(0, 0, (int)(symbol.getIntrinsicWidth()/1.8), (int)(symbol.getIntrinsicHeight()/1.8));
                ImageSpan span = new ImageSpan(symbol, ImageSpan.ALIGN_BASELINE);
                spannable.setSpan(span, symbolMatcher.start(), symbolMatcher.end(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            }
        }
        txv_oracle.setText(spannable);
    }
}