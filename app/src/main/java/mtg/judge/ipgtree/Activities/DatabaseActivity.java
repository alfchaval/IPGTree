package mtg.judge.ipgtree.Activities;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.widget.TextView;

import mtg.judge.ipgtree.Card;
import mtg.judge.ipgtree.R;
import mtg.judge.ipgtree.Set;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseActivity extends AppCompatActivity {

    //HIDDEN ACTIVITY WITH EXTRA CODE FOR TRANSFORMING DATABASES, TESTING, AND WORSHIPING OUR LORD AND SAVIOR, THE FLYING SPAGHETTI MONSTER

    TextView textView;
    String debug = "";
    int cardsReaded = 0;
    int cardsWritten = 0;
    int setsReaded = 0;
    int setsWritten = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        textView = findViewById(R.id.txv_about);

        //createDatabase("AllCards.json", getApplicationContext(), R.raw.allcards, R.raw.allsets);
    }

    //create two Json file with the information I want from AllCards.json and AllSets.json, so I have a smaller database (https://mtgjson.com/downloads/compiled/)
    public void createDatabase(String fileName, Context context, int allCards, int allSets) {
        HashMap<String, Card> cards = new HashMap<>();
        Gson gson = new GsonBuilder().create();
        Card card;
        debug += "Leyendo cartas" + '\n';
        textView.setText(debug);
        try {
            InputStream inputStream = context.getResources().openRawResource(allCards);
            JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));

            reader.beginObject();

            int aux = 0;
            while (reader.hasNext()) {
                reader.nextName();
                card = new Card();
                reader.beginObject();
                while (reader.hasNext()) {
                    switch (reader.nextName()) {
                        case "name":
                            card.name = reader.nextString();
                            break;
                        case "manaCost":
                            card.manaCost = reader.nextString();
                            break;
                        case "colorIndicator":
                            reader.beginArray();
                            while(reader.hasNext()) {
                                card.colorIndicator.add(reader.nextString());
                            }
                            reader.endArray();
                            break;
                        case "type":
                            card.type = reader.nextString();
                            break;
                        case "text":
                            card.text = reader.nextString();
                            break;
                        case "power":
                            card.power = reader.nextString();
                            break;
                        case "toughness":
                            card.toughness = reader.nextString();
                            break;
                        case "loyalty":
                            card.loyalty = reader.nextString();
                            break;
                        default:
                            reader.skipValue();
                            break;
                    }
                }
                reader.endObject();
                cards.put(card.name,card);
                cardsReaded++;
            }
            reader.close();
        } catch (Exception e) {
            debug += "FAIL Leyendo cartas" + e.getLocalizedMessage() + '\n';
            textView.setText(debug);
        }

        debug += "Cartas leídas: " + cardsReaded + '\n';

        ArrayList<Pair<String, Integer>> numbers = new ArrayList<>();
        ArrayList<Set> sets = new ArrayList<>();
        String cardName = "";
        int number = 0;
        String code = "";
        String setName = "";
        boolean add = true;

        debug += "Leyendo sets" + '\n';
        textView.setText(debug);
        try {
            InputStream inputStream = context.getResources().openRawResource(allSets);
            JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));

            reader.beginObject();

            int aux = 0;
            while (reader.hasNext()) {
                reader.nextName();
                reader.beginObject();
                code = "";
                int maxNumber = 0;
                while (reader.hasNext()) {
                    switch (reader.nextName()) {
                        case "code":
                            code = reader.nextString();
                            break;
                        case "name":
                            setName = reader.nextString();
                            break;
                        case "cards":
                            numbers = new ArrayList<>();
                            reader.beginArray();
                            while(reader.hasNext()) {
                                reader.beginObject();
                                while(reader.hasNext()) {
                                    switch (reader.nextName()) {
                                        case "name":
                                            cardName = reader.nextString();
                                            break;
                                        case "number":
                                            try{
                                                //some allcards have special numbers... I also don't want these allcards
                                                number = Integer.parseInt(reader.nextString());
                                                if(number > maxNumber) {
                                                    maxNumber = number;
                                                }
                                                add = true;
                                            } catch (Exception e) {
                                                add = false;
                                            }
                                            break;
                                        default:
                                            reader.skipValue();
                                            break;
                                    }
                                }
                                reader.endObject();
                                if(add) {
                                    /*
                                    //Checking if pair is already , probably unnecesary
                                    boolean alreadyIn = false;
                                    for(int i = 0; i < numbers.size(); i++) {
                                        if(numbers.get(i).first == cardName && numbers.get(i).second == number) {
                                            alreadyIn = true;
                                        }
                                    }
                                    if(!alreadyIn) {
                                        numbers.add(new Pair<String, Integer>(cardName, number));
                                    }
                                    */
                                    numbers.add(new Pair<String, Integer>(cardName, number));
                                }
                            }
                            reader.endArray();
                            break;
                        default:
                            reader.skipValue();
                            break;
                    }
                }
                if(code != "") {
                    for (Pair<String, Integer> p: numbers) {
                        if(cards.containsKey(p.first)) {
                            /*
                            //Checking if the card is already in the set, probably unnecesary
                            boolean alreadyIn = false;
                            for(int i = 0; i < cards.get(p.first).printings.size(); i++) {
                                if(cards.get(p.first).printings.get(i).first == code && cards.get(p.first).printings.get(i).second == p.second) {
                                    alreadyIn = true;
                                }
                            }
                            if(!alreadyIn) {
                                cards.get(p.first).printings.add(new Pair<String, Integer>(code, p.second));
                            }
                            //*/
                            cards.get(p.first).printings.add(new Pair<String, Integer>(code, p.second));
                        }
                    }
                    /*
                    //Checking if the set is already in the arraylist, probably unnecesary
                    boolean alreadyIn = false;
                    for(int i = 0; i < sets.size(); i++) {
                        if(sets.get(i).code == code) {
                            alreadyIn = true;
                        }
                    }
                    if(!alreadyIn) {
                        Set set = new Set(code, setName);
                        set.cards = maxNumber;
                        sets.add(set);
                        setsReaded++;
                    }
                    //*/
                    Set set = new Set(code, setName);
                    set.cards = maxNumber;
                    sets.add(set);
                    setsReaded++;
                }
                reader.endObject();
            }
            reader.close();
        } catch (Exception e) {
            debug += "FAIL + e.getLocalizedMessage() Leyendo cartas" + '\n';
            textView.setText(debug);
        }

        debug += "Sets leídos: " + setsReaded + '\n';
        debug += "Escribiendo" + '\n';
        textView.setText(debug);
        try{
            String s;

            File oracleFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "oracle.json");
            FileWriter oracleWriter = new FileWriter(oracleFile);
            ArrayList<Card> cardlist = new ArrayList<Card>(cards.values());

            oracleWriter.append("{");
            for (int i = 0; i < cardlist.size() - 1; i++){
                //fuck you Pang Tong, "Young Phoenix"
                oracleWriter.append("\"" + cardlist.get(i).name.replace("\"", "") + "\":");
                s = gson.toJson(cardlist.get(i));
                oracleWriter.append(s+",");
                cardsWritten++;

            }
            oracleWriter.append("\"" + cardlist.get(cardlist.size()-1).name.replace("\"", "") + "\":");
            s = gson.toJson(cardlist.get(cardlist.size()-1));
            oracleWriter.append(s);
            cardsWritten++;
            oracleWriter.append("}");
            oracleWriter.flush();
            oracleWriter.close();

            File setsFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "sets.json");
            FileWriter setsWriter = new FileWriter(setsFile);

            setsWriter.append("{");
            for(int i = 0; i < sets.size() - 1; i++) {
                setsWriter.append("\"" + sets.get(i).code + "\":");
                s = gson.toJson(sets.get(i));
                setsWriter.append(s+",");
                setsWritten++;
            }
            setsWriter.append("\"" + sets.get(sets.size()-1).code + "\":");
            s = gson.toJson(sets.get(sets.size()-1));
            setsWriter.append(s);
            setsWritten++;
            setsWriter.append("}");
            setsWriter.flush();
            setsWriter.close();
        } catch (Exception e){
            debug += "FAIL Escribiendo" + e.getLocalizedMessage() + '\n';
            textView.setText(debug);
        }
        debug += "Cartas escritas: " + cardsWritten + '\n';
        debug += "Sets escritos: " + setsWritten + '\n';
        debug += "Done" + '\n';
        textView.setText(debug);
    }
}