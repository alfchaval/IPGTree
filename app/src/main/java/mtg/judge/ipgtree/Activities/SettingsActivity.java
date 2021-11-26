package mtg.judge.ipgtree.Activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

import mtg.judge.ipgtree.POJO.Card;
import mtg.judge.ipgtree.POJO.Face;
import mtg.judge.ipgtree.R;
import mtg.judge.ipgtree.Utilities.Repository;
import mtg.judge.ipgtree.POJO.Set;
import mtg.judge.ipgtree.Utilities.Translation;

public class SettingsActivity extends AppCompatActivity {

    private Button btn_update_oracle, btn_update_documents, btn_en_language, btn_es_language, btn_fr_language, btn_advanced;
    private CheckBox cb_annotations, cb_update;
    private TextView txv_annotations, txv_web;
    private LinearLayout ll_en_language, ll_es_language, ll_fr_language;


    private String startingLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        if(!Repository.repositoryLoaded) {
            Repository.createRepository(getApplicationContext());
        }

        linkViews();
        loadStrings();
        loadRepositoryData();
        setListeners();
    }

    private void linkViews() {
        btn_update_oracle = findViewById(R.id.btn_update_oracle);
        btn_update_documents = findViewById(R.id.btn_update_documents);
        btn_en_language = findViewById(R.id.btn_en_language);
        btn_es_language = findViewById(R.id.btn_es_language);
        btn_fr_language = findViewById(R.id.btn_fr_language);
        btn_advanced = findViewById(R.id.btn_advanced);
        cb_annotations = findViewById(R.id.cb_annotations);
        cb_update = findViewById(R.id.cb_update);
        txv_annotations = findViewById(R.id.txv_annotations);
        txv_web = findViewById(R.id.txv_web);
        ll_en_language = findViewById(R.id.ll_en_language);
        ll_es_language = findViewById(R.id.ll_es_language);
        ll_fr_language = findViewById(R.id.ll_fr_language);
    }

    private void loadStrings() {
        btn_update_oracle.setText(Translation.StringMap(61));
        btn_update_documents.setText(Translation.StringMap(71));
        btn_advanced.setText(Translation.StringMap(72));
        cb_annotations.setText(Translation.StringMap(65));
        txv_annotations.setText(Translation.StringMap(66));
        cb_update.setText(Translation.StringMap(70));
    }

    private void loadRepositoryData() {
        switch (Repository.language) {
            case Repository.ENGLISH:
                ll_en_language.setBackground(getResources().getDrawable(R.drawable.answer_background_parent));
                break;
            case Repository.SPANISH:
                ll_es_language.setBackground(getResources().getDrawable(R.drawable.answer_background_parent));
                break;
            case Repository.FRENCH:
                ll_fr_language.setBackground(getResources().getDrawable(R.drawable.answer_background_parent));
                break;
        }
        startingLanguage = Repository.language;
        cb_annotations.setChecked(Repository.showAnnotations);
        cb_update.setChecked(Repository.downloadNews);
    }

    private void setListeners() {
        btn_update_oracle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(SettingsActivity.this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(SettingsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(SettingsActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    askDownload();
                } else {
                    enableButtons(false);
                    ActivityCompat.requestPermissions(SettingsActivity.this, new String[]{Manifest.permission.INTERNET, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }
            }
        });
        btn_update_documents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(SettingsActivity.this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(SettingsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(SettingsActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    new UpdateDocuments().execute();
                } else {
                    enableButtons(false);
                    ActivityCompat.requestPermissions(SettingsActivity.this, new String[]{Manifest.permission.INTERNET, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},2);
                }
            }
        });
        cb_annotations.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Repository.showAnnotations = isChecked;
                SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
                editor.putBoolean(Repository.KEY_ANNOTATION, Repository.showAnnotations);
                editor.apply();
            }
        });
        cb_update.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Repository.downloadNews = isChecked;
                SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
                editor.putBoolean(Repository.KEY_DOWNLOADNEWS, Repository.downloadNews);
                editor.apply();
            }
        });
        btn_en_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_en_language.setBackground(getResources().getDrawable(R.drawable.answer_background_parent));
                ll_es_language.setBackground(null);
                ll_fr_language.setBackground(null);
                Repository.language = Repository.ENGLISH;
                SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
                editor.putString(Repository.KEY_LANGUAGE, Repository.language);
                editor.apply();
                loadStrings();
            }
        });
        btn_es_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_en_language.setBackground(null);
                ll_es_language.setBackground(getResources().getDrawable(R.drawable.answer_background_parent));
                ll_fr_language.setBackground(null);
                Repository.language = Repository.SPANISH;
                SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
                editor.putString(Repository.KEY_LANGUAGE, Repository.language);
                editor.apply();
                loadStrings();
            }
        });
        btn_fr_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_en_language.setBackground(null);
                ll_es_language.setBackground(null);
                ll_fr_language.setBackground(getResources().getDrawable(R.drawable.answer_background_parent));
                Repository.language = Repository.FRENCH;
                SharedPreferences.Editor editor = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE).edit();
                editor.putString(Repository.KEY_LANGUAGE, Repository.language);
                editor.apply();
                loadStrings();
            }
        });
        btn_advanced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableButtons(false);
                Intent intent = new Intent(SettingsActivity.this, AdvancedSettingsActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        txv_web.setMovementMethod(LinkMovementMethod.getInstance());
        txv_web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse(((TextView)view).getText().toString()));
                startActivity(browserIntent);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        String folder = Environment.getExternalStorageDirectory() + File.separator + Repository.FOLDERNAME;
        File directory = new File(folder);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        switch (requestCode) {
            case 1:
                askDownload();
                break;
            case 2:
                new UpdateDocuments().execute();
                break;
            case 3:
                donate();
                break;
        }
        enableButtons(true);
    }

    public void askDownload() {
        new AlertDialog.Builder(SettingsActivity.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(Translation.StringMap(56))
                .setMessage(Translation.StringMap(54))
                .setPositiveButton(Translation.StringMap(53), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new UpdateOracle().execute();
                    }
                })
                .setNegativeButton(Translation.StringMap(59), null)
                .show();
    }

    public void donate() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
        browserIntent.setData(Uri.parse("https://www.paypal.me/alfchaval"));
        startActivity(browserIntent);
    }

    private class UpdateOracle extends AsyncTask<String, String, String> {
        private ProgressDialog progressDialog;
        private String folder;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.progressDialog = new ProgressDialog(SettingsActivity.this);
            this.progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            this.progressDialog.setCancelable(false);
            this.progressDialog.show();
        }

        @Override
        protected String doInBackground(String... urls) {
            int count;
            InputStream input;
            OutputStream output;
            URL url;
            URLConnection connection;
            String filenameCards;
            String filenameSets;
            byte[] data;
            try {
                folder = Environment.getExternalStorageDirectory() + File.separator + Repository.FOLDERNAME;
                File directory = new File(folder);

                //Descarga la base de datos de cartas
                url = new URL(Repository.URL_CARDS);
                connection = url.openConnection();
                connection.connect();
                input = new BufferedInputStream(url.openStream(), 8192);
                filenameCards = folder + File.separator + Repository.URL_CARDS.substring(Repository.URL_CARDS.lastIndexOf('/') + 1, Repository.URL_CARDS.length());
                output = new FileOutputStream(filenameCards);
                data = new byte[1024];
                while ((count = input.read(data)) != -1) {
                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();
                publishProgress("15");

                //Descarga la base de datos de ediciones
                url = new URL(Repository.URL_SETS);
                connection = url.openConnection();
                connection.connect();
                input = new BufferedInputStream(url.openStream(), 8192);
                filenameSets = folder + File.separator + Repository.URL_SETS.substring(Repository.URL_SETS.lastIndexOf('/') + 1, Repository.URL_SETS.length());
                output = new FileOutputStream(filenameSets);
                data = new byte[1024];
                while ((count = input.read(data)) != -1) {
                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();
                publishProgress("35");

                //Crea la base de datos reducida
                HashMap<String, Card> cards = new HashMap<>();
                Gson gson = new GsonBuilder().create();
                Card card;
                Face face;

                try {
                    FileInputStream inputStream = new FileInputStream(filenameCards);
                    JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));

                    reader.beginObject();
                    while (reader.hasNext()) {
                        switch (reader.nextName()) {
                            case "data":
                                reader.beginObject();
                                while (reader.hasNext()) {
                                    card = new Card();
                                    card.name = reader.nextName();
                                    reader.beginArray();
                                    while (reader.hasNext()) {
                                        face = new Face();
                                        reader.beginObject();
                                        while (reader.hasNext()) {
                                            switch (reader.nextName()) {
                                                case "faceName":
                                                    face.faceName = reader.nextString();
                                                    break;
                                                case "manaCost":
                                                    face.manaCost = reader.nextString();
                                                    break;
                                                case "colorIndicator":
                                                    reader.beginArray();
                                                    while(reader.hasNext()) {
                                                        face.colorIndicator.add(reader.nextString());
                                                    }
                                                    reader.endArray();
                                                    break;
                                                case "type":
                                                    face.type = reader.nextString();
                                                    break;
                                                case "text":
                                                    face.text = reader.nextString();
                                                    break;
                                                case "power":
                                                    face.power = reader.nextString();
                                                    break;
                                                case "toughness":
                                                    face.toughness = reader.nextString();
                                                    break;
                                                case "loyalty":
                                                    face.loyalty = reader.nextString();
                                                    break;
                                                default:
                                                    reader.skipValue();
                                                    break;
                                            }
                                        }
                                        reader.endObject();
                                        card.faces.add(face);
                                    }
                                    reader.endArray();
                                    cards.put(card.name, card);
                                }
                                reader.endObject();
                                break;
                            default:
                                reader.skipValue();
                                break;
                        }
                    }
                    reader.close();
                } catch (Exception e) {
                }
                publishProgress("50");

                ArrayList<Pair<String, Integer>> numbers = new ArrayList<>();
                ArrayList<Set> sets = new ArrayList<>();
                String cardName = "";
                int number = 0;
                String code = "";
                String setName = "";
                boolean add = true;

                try {
                    FileInputStream inputStream = new FileInputStream(filenameSets);
                    JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));

                    reader.beginObject();
                    while (reader.hasNext()) {
                        switch (reader.nextName()) {
                            case "data":
                                reader.beginObject();
                                while (reader.hasNext()) {
                                    reader.nextName();
                                    code = "";
                                    int maxNumber = 0;
                                    reader.beginObject();
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
                                    reader.endObject();
                                    if(code != "") {
                                        for (Pair<String, Integer> p: numbers) {
                                            if(cards.containsKey(p.first)) {
                                                cards.get(p.first).printings.add(new Pair<String, Integer>(code, p.second));
                                            }
                                        }
                                        Set set = new Set(code, setName);
                                        set.cards = maxNumber;
                                        sets.add(set);
                                    }
                                }
                                reader.endObject();
                                break;
                            default:
                                reader.skipValue();
                                break;
                        }
                    }
                    reader.close();
                } catch (Exception e) {
                }
                publishProgress("70");
                try{
                    String s;

                    File oracleFile = new File(folder, "oracle.json");
                    FileWriter oracleWriter = new FileWriter(oracleFile);
                    ArrayList<Card> cardlist = new ArrayList<Card>(cards.values());

                    oracleWriter.append("{");
                    for (int i = 0; i < cardlist.size() - 1; i++){
                        //fuck you Pang Tong, "Young Phoenix"
                        oracleWriter.append("\"" + cardlist.get(i).name.replace("\"", "") + "\":");
                        s = gson.toJson(cardlist.get(i));
                        oracleWriter.append(s+",");
                    }
                    oracleWriter.append("\"" + cardlist.get(cardlist.size()-1).name.replace("\"", "") + "\":");
                    s = gson.toJson(cardlist.get(cardlist.size()-1));
                    oracleWriter.append(s);
                    oracleWriter.append("}");
                    oracleWriter.flush();
                    oracleWriter.close();

                    publishProgress("80");

                    File setsFile = new File(folder, "sets.json");
                    FileWriter setsWriter = new FileWriter(setsFile);

                    setsWriter.append("{");
                    for(int i = 0; i < sets.size() - 1; i++) {
                        setsWriter.append("\"" + sets.get(i).code + "\":");
                        s = gson.toJson(sets.get(i));
                        setsWriter.append(s+",");
                    }
                    setsWriter.append("\"" + sets.get(sets.size()-1).code + "\":");
                    s = gson.toJson(sets.get(sets.size()-1));
                    setsWriter.append(s);
                    setsWriter.append("}");
                    setsWriter.flush();
                    setsWriter.close();

                    new File(filenameCards).delete();
                    new File(filenameSets).delete();

                    publishProgress("90");

                    Repository.databaseLoaded = false;
                    Repository.loadDatabase(getApplicationContext());

                } catch (Exception e){
                    Log.d("ERROR", e.getLocalizedMessage());
                }

                return Translation.StringMap(55) + folder;

            } catch (Exception e) {
            }
            return Translation.StringMap(57);
        }

        protected void onProgressUpdate(String... progress) {
            progressDialog.setProgress(Integer.parseInt(progress[0]));
        }


        @Override
        protected void onPostExecute(String message) {
            this.progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }
    }

    private class UpdateDocuments extends AsyncTask<String, String, String> {

        private ProgressDialog progressDialog;
        private String folder;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.progressDialog = new ProgressDialog(SettingsActivity.this);
            this.progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            this.progressDialog.setCancelable(false);
            this.progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            SharedPreferences preferences = getSharedPreferences(Repository.KEY_PREFERENCES, MODE_PRIVATE);
            ArrayList<String> documents = new ArrayList<String>();
            documents.add(preferences.getString(Repository.KEY_AIPG_EN, Repository.URL_AIPG_EN));
            documents.add(preferences.getString(Repository.KEY_AMTR_EN, Repository.URL_AMTR_EN));
            documents.add(preferences.getString(Repository.KEY_ADIPG_EN, Repository.URL_ADIPG_EN));
            documents.add(preferences.getString(Repository.KEY_ADMTR_EN, Repository.URL_ADMTR_EN));
            documents.add(preferences.getString(Repository.KEY_BANNED_EN, Repository.URL_BANNED_EN));
            documents.add(preferences.getString(Repository.KEY_CR_EN, Repository.URL_CR_EN));
            documents.add(preferences.getString(Repository.KEY_DQ_EN, Repository.URL_DQ_EN));
            documents.add(preferences.getString(Repository.KEY_TREE_EN, Repository.URL_TREE_EN));
            documents.add(preferences.getString(Repository.KEY_JAR_EN, Repository.URL_JAR_EN));
            documents.add(preferences.getString(Repository.KEY_LINKS_EN, Repository.URL_LINKS_EN));
            documents.add(preferences.getString(Repository.KEY_QUIZ_EN, Repository.URL_QUIZ_EN));
            documents.add(preferences.getString(Repository.KEY_HJA_EN, Repository.URL_HJA_EN));

            documents.add(preferences.getString(Repository.KEY_AIPG_ES, Repository.URL_AIPG_ES));
            documents.add(preferences.getString(Repository.KEY_AMTR_ES, Repository.URL_AMTR_ES));
            documents.add(preferences.getString(Repository.KEY_ADIPG_ES, Repository.URL_ADIPG_ES));
            documents.add(preferences.getString(Repository.KEY_ADMTR_ES, Repository.URL_ADMTR_ES));
            documents.add(preferences.getString(Repository.KEY_BANNED_ES, Repository.URL_BANNED_ES));
            documents.add(preferences.getString(Repository.KEY_CR_ES, Repository.URL_CR_ES));
            documents.add(preferences.getString(Repository.KEY_DQ_ES, Repository.URL_DQ_ES));
            documents.add(preferences.getString(Repository.KEY_TREE_ES, Repository.URL_TREE_ES));
            documents.add(preferences.getString(Repository.KEY_JAR_ES, Repository.URL_JAR_ES));
            documents.add(preferences.getString(Repository.KEY_LINKS_ES, Repository.URL_LINKS_ES));
            documents.add(preferences.getString(Repository.KEY_QUIZ_ES, Repository.URL_QUIZ_ES));
            documents.add(preferences.getString(Repository.KEY_HJA_ES, Repository.URL_HJA_ES));

            documents.add(preferences.getString(Repository.KEY_AIPG_FR, Repository.URL_AIPG_FR));
            documents.add(preferences.getString(Repository.KEY_AMTR_FR, Repository.URL_AMTR_FR));
            documents.add(preferences.getString(Repository.KEY_ADIPG_FR, Repository.URL_ADIPG_FR));
            documents.add(preferences.getString(Repository.KEY_ADMTR_FR, Repository.URL_ADMTR_FR));
            documents.add(preferences.getString(Repository.KEY_BANNED_FR, Repository.URL_BANNED_FR));
            documents.add(preferences.getString(Repository.KEY_CR_FR, Repository.URL_CR_FR));
            documents.add(preferences.getString(Repository.KEY_DQ_FR, Repository.URL_DQ_FR));
            documents.add(preferences.getString(Repository.KEY_TREE_FR, Repository.URL_TREE_FR));
            documents.add(preferences.getString(Repository.KEY_JAR_FR, Repository.URL_JAR_FR));
            documents.add(preferences.getString(Repository.KEY_LINKS_FR, Repository.URL_LINKS_FR));
            documents.add(preferences.getString(Repository.KEY_QUIZ_FR, Repository.URL_QUIZ_FR));
            documents.add(preferences.getString(Repository.KEY_HJA_FR, Repository.URL_HJA_FR));
            int count;
            InputStream input;
            OutputStream output;
            URL url;
            URLConnection connection;
            String documentURL;
            String filename;
            int percent = 0;
            byte[] data;
            try {
                folder = Environment.getExternalStorageDirectory() + File.separator + Repository.FOLDERNAME;
                File directory = new File(folder);

                for (int i = 0; i < documents.size(); i++) {
                    documentURL = documents.get(i);
                    try {
                        url = new URL(documentURL);
                        connection = url.openConnection();
                        connection.connect();
                        input = new BufferedInputStream(url.openStream(), 8192);
                        filename = folder + File.separator + documentURL.substring(documentURL.lastIndexOf('/') + 1, documentURL.length());
                        output = new FileOutputStream(filename);
                        data = new byte[1024];
                        while ((count = input.read(data)) != -1) {
                            output.write(data, 0, count);
                        }
                        output.flush();
                        output.close();
                        input.close();
                    }
                    catch (Exception e) {
                        Log.d("ERROR", "5");
                    }
                    percent += i*100/documents.size();
                    publishProgress(percent + "");
                }
                return Translation.StringMap(55) + folder;
            }
            catch (Exception e) {
                Log.d("ERROR", "6");
            }
            return Translation.StringMap(57);
        }

        protected void onProgressUpdate(String... progress) {
            progressDialog.setProgress(Integer.parseInt(progress[0]));
        }


        @Override
        protected void onPostExecute(String message) {
            this.progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        if(startingLanguage != Repository.language) {
            setResult(RESULT_OK);
        }
        super.onBackPressed();
    }

    public void enableButtons(Boolean state) {
        btn_advanced.setEnabled(state);
        btn_update_oracle.setEnabled(state);
        btn_update_documents.setEnabled(state);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        enableButtons(true);
    }
}