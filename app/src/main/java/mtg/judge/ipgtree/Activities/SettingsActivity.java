package mtg.judge.ipgtree.Activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

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

import mtg.judge.ipgtree.Card;
import mtg.judge.ipgtree.Code;
import mtg.judge.ipgtree.R;
import mtg.judge.ipgtree.Repository;
import mtg.judge.ipgtree.Set;

public class SettingsActivity extends AppCompatActivity {

    private Button btn_update, btn_about, btn_saveftp, btn_unlockftp;
    private CheckBox cb_anotations, cb_ftp;
    private EditText edt_server, edt_user, edt_password, edt_codeftp;
    private TextView txv_codeftp;
    private LinearLayout ll_server_code, ll_server_settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        linkViews();
        loadRepositoryData();
        setListeners();
        txv_codeftp.setText(Code.generateCode());
    }

    public void linkViews() {
        btn_update = findViewById(R.id.btn_update);
        btn_about = findViewById(R.id.btn_about);
        btn_saveftp = findViewById(R.id.btn_saveftp);
        btn_unlockftp = findViewById(R.id.btn_unlockftp);
        cb_anotations = findViewById(R.id.cb_anotations);
        cb_ftp = findViewById(R.id.cb_ftp);
        edt_server = findViewById(R.id.edt_server);
        edt_user = findViewById(R.id.edt_user);
        edt_password = findViewById(R.id.edt_password);
        edt_codeftp = findViewById(R.id.edt_codeftp);
        txv_codeftp = findViewById(R.id.txv_codeftp);
        ll_server_code = findViewById(R.id.ll_server_code);
        ll_server_settings = findViewById(R.id.ll_server_settings);
    }

    public void loadRepositoryData() {
        cb_anotations.setChecked(Repository.showAnnotations);
        if(Repository.unlockedFTP) {
            ll_server_code.setVisibility(View.GONE);
            ll_server_settings.setVisibility(View.VISIBLE);
            cb_ftp.setChecked(Repository.allowFTP);
            if(Repository.ftpServer != null) {
                edt_server.setText(Repository.ftpServer);
            }
            if(Repository.ftpUser != null) {
                edt_user.setText(Repository.ftpUser);
            }
            if(Repository.ftpPassword != null) {
                edt_password.setText(Repository.ftpPassword);
            }
        }
    }

    public void setListeners() {
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(SettingsActivity.this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(SettingsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(SettingsActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    askDownload();
                } else {
                    ActivityCompat.requestPermissions(SettingsActivity.this, new String[]{Manifest.permission.INTERNET, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }
            }
        });
        btn_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });
        btn_saveftp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(SettingsActivity.this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(SettingsActivity.this, new String[]{Manifest.permission.INTERNET},2);
                }
                else {
                    checkConnection();
                }
            }
        });
        btn_unlockftp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Code.check(txv_codeftp.getText().toString(), edt_codeftp.getText().toString())) {
                    Repository.unlockedFTP = true;
                    //TODO Guardar en preferences
                    ll_server_settings.setVisibility(View.GONE);
                    ll_server_code.setVisibility(View.VISIBLE);
                }
                else {
                    txv_codeftp.setText(Code.generateCode());
                }
            }
        });
        cb_anotations.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Repository.showAnnotations = isChecked;
                //TODO Guardar en preferences
            }
        });
        cb_ftp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Repository.allowFTP = isChecked;
                //TODO Guardar en preferences
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                askDownload();
                break;
            case 2:
                checkConnection();
                break;
        }
    }

    public void checkConnection() {
        Repository.ftpServer = edt_server.getText().toString();
        Repository.ftpUser = edt_user.getText().toString();
        Repository.ftpPassword = edt_password.getText().toString();
        //TODO Guardar en preferences
        //TODO AsynTask crashea la app
        AsyncTask< String, Integer, Boolean > task = new AsyncTask< String, Integer, Boolean >()
        {
            @Override
            protected Boolean doInBackground( String... params )
            {
                try {
                    FTPClient mFTP = new FTPClient();
                    mFTP.connect(Repository.ftpServer, Repository.ftpPort);
                    mFTP.login(Repository.ftpUser, Repository.ftpPassword);
                    mFTP.logout();
                    mFTP.disconnect();
                    Toast.makeText(SettingsActivity.this,"Conexión exitosa", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(SettingsActivity.this,"Error de conexión", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        };
        task.execute("");
    }

    public void askDownload() {
        new AlertDialog.Builder(SettingsActivity.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Descargar Oracle")
                .setMessage("La base de datos pesa más que un Colossal Dreadmaw, recomendamos conexión wifi antes de continuar")
                .setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new Update().execute(Repository.URLCARDS, Repository.URLSETS);
                    }
                })
                .setNegativeButton("Salir", null)
                .show();
    }

    private class Update extends AsyncTask<String, String, String> {
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
                //Crea la carpeta donde se almacena la base de datos
                folder = Environment.getExternalStorageDirectory() + File.separator + "Ipgtree";
                File directory = new File(folder);
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                //Descarga la base de datos de cartas
                url = new URL(Repository.URLCARDS);
                connection = url.openConnection();
                connection.connect();
                input = new BufferedInputStream(url.openStream(), 8192);
                filenameCards = folder + File.separator + Repository.URLCARDS.substring(Repository.URLCARDS.lastIndexOf('/') + 1, Repository.URLCARDS.length());
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
                url = new URL(Repository.URLSETS);
                connection = url.openConnection();
                connection.connect();
                input = new BufferedInputStream(url.openStream(), 8192);
                filenameSets = folder + File.separator + Repository.URLSETS.substring(Repository.URLSETS.lastIndexOf('/') + 1, Repository.URLSETS.length());
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
                try {
                    FileInputStream inputStream = new FileInputStream(filenameCards);
                    JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));

                    reader.beginObject();

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
                                case "names":
                                    reader.beginArray();
                                    while(reader.hasNext()) {
                                        card.sidenames.add(reader.nextString());
                                    }
                                    reader.endArray();
                                    break;
                                default:
                                    reader.skipValue();
                                    break;
                            }
                        }
                        reader.endObject();
                        cards.put(card.name,card);
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
                                    cards.get(p.first).printings.add(new Pair<String, Integer>(code, p.second));
                                }
                            }
                            Set set = new Set(code, setName);
                            set.cards = maxNumber;
                            sets.add(set);
                        }
                        reader.endObject();
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

                    Repository.loadDatabase(getApplicationContext());

                } catch (Exception e){
                }

                return "Descargado en: " + folder;

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
            return "Error";
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

    public static boolean isSDCardPresent() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
}