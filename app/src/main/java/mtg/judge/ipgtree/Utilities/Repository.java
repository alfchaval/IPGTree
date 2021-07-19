package mtg.judge.ipgtree.Utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Pair;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.util.HashMap;

import mtg.judge.ipgtree.POJO.Card;
import mtg.judge.ipgtree.POJO.Set;

import static android.content.Context.MODE_PRIVATE;

public class Repository {

    public static final int appVersion = 20210719;
    public static int lastNews;

    //URLs
    public static final String URL_CARDS = "https://mtgjson.com/api/v5/AtomicCards.json";
    public static final String URL_SETS = "https://mtgjson.com/api/v5/AllPrintings.json";

    public static final String URL_AIPG_EN = "https://raw.githubusercontent.com/alfchaval/Tree/master/documents/aipg_en.xml";
    public static final String URL_AMTR_EN = "https://raw.githubusercontent.com/alfchaval/Tree/master/documents/amtr_en.xml";
    public static final String URL_ADIPG_EN = "https://raw.githubusercontent.com/alfchaval/Tree/master/documents/adipg_en.xml";
    public static final String URL_ADMTR_EN = "https://raw.githubusercontent.com/alfchaval/Tree/master/documents/admtr_en.xml";
    public static final String URL_BANNED_EN = "https://raw.githubusercontent.com/alfchaval/Tree/master/documents/banned_en.xml";
    public static final String URL_CR_EN = "https://raw.githubusercontent.com/alfchaval/Tree/master/documents/cr_en.xml";
    public static final String URL_DQ_EN = "https://raw.githubusercontent.com/alfchaval/Tree/master/documents/dq_en.xml";
    public static final String URL_TREE_EN = "https://raw.githubusercontent.com/alfchaval/Tree/master/documents/ipg_tree_en.xml";
    public static final String URL_JAR_EN = "https://raw.githubusercontent.com/alfchaval/Tree/master/documents/jar_en.xml";
    public static final String URL_LINKS_EN = "https://raw.githubusercontent.com/alfchaval/Tree/master/documents/links_en.xml";
    public static final String URL_QUIZ_EN = "https://raw.githubusercontent.com/alfchaval/Tree/master/documents/quiz_en.xml";
    public static final String URL_HJA_EN = "https://raw.githubusercontent.com/alfchaval/Tree/master/documents/hja_en.xml";

    public static final String URL_AIPG_ES = "https://raw.githubusercontent.com/alfchaval/Tree/master/documents/aipg_es.xml";
    public static final String URL_AMTR_ES = "https://raw.githubusercontent.com/alfchaval/Tree/master/documents/amtr_es.xml";
    public static final String URL_ADIPG_ES = "https://raw.githubusercontent.com/alfchaval/Tree/master/documents/adipg_es.xml";
    public static final String URL_ADMTR_ES = "https://raw.githubusercontent.com/alfchaval/Tree/master/documents/admtr_es.xml";
    public static final String URL_BANNED_ES = "https://raw.githubusercontent.com/alfchaval/Tree/master/documents/banned_es.xml";
    public static final String URL_CR_ES = "https://raw.githubusercontent.com/alfchaval/Tree/master/documents/cr_es.xml";
    public static final String URL_DQ_ES = "https://raw.githubusercontent.com/alfchaval/Tree/master/documents/dq_es.xml";
    public static final String URL_TREE_ES = "https://raw.githubusercontent.com/alfchaval/Tree/master/documents/ipg_tree_es.xml";
    public static final String URL_JAR_ES = "https://raw.githubusercontent.com/alfchaval/Tree/master/documents/jar_es.xml";
    public static final String URL_LINKS_ES = "https://raw.githubusercontent.com/alfchaval/Tree/master/documents/links_es.xml";
    public static final String URL_QUIZ_ES = "https://raw.githubusercontent.com/alfchaval/Tree/master/documents/quiz_es.xml";
    public static final String URL_HJA_ES = "https://raw.githubusercontent.com/alfchaval/Tree/master/documents/hja_es.xml";

    public static final String URL_NEWS = "https://raw.githubusercontent.com/alfchaval/Tree/master/documents/news.txt";

    //File names
    public static final String P1_LIFE_FILENAME = "p1_life.txt";
    public static final String P2_LIFE_FILENAME = "p2_life.txt";
    public static final String P3_LIFE_FILENAME = "p3_life.txt";
    public static final String P4_LIFE_FILENAME = "p4_life.txt";
    public static final String P5_LIFE_FILENAME = "p5_life.txt";
    public static final String P6_LIFE_FILENAME = "p6_life.txt";
    public static final String P1_POISON_FILENAME = "p1_poison.txt";
    public static final String P2_POISON_FILENAME = "p2_poison.txt";
    public static final String P3_POISON_FILENAME = "p3_poison.txt";
    public static final String P4_POISON_FILENAME = "p4_poison.txt";
    public static final String P5_POISON_FILENAME = "p5_poison.txt";
    public static final String P6_POISON_FILENAME = "p6_poison.txt";
    public static final String P1_LOG_FILENAME = "p1_log.txt";
    public static final String P2_LOG_FILENAME = "p2_log.txt";
    public static final String P3_LOG_FILENAME = "p3_log.txt";
    public static final String P4_LOG_FILENAME = "p4_log.txt";
    public static final String P5_LOG_FILENAME = "p5_log.txt";
    public static final String P6_LOG_FILENAME = "p6_log.txt";

    //Keyboard codes
    public static final int Code0 = 0;
    public static final int Code1 = 1;
    public static final int Code2 = 2;
    public static final int Code3 = 3;
    public static final int Code4 = 4;
    public static final int Code5 = 5;
    public static final int Code6 = 6;
    public static final int Code7 = 7;
    public static final int Code8 = 8;
    public static final int Code9 = 9;
    public static final int CodeSign = -1;
    public static final int CodeDelete = -5;
    public static final int CodeCancel = -100;
    public static final int CodeOk = 100;

    //Values
    public final static char LIFE = '♥';
    public final static char POISON = 'ϕ';
    public final static String ENGLISH = "en";
    public final static String SPANISH = "es";
    public final static String FOLDERNAME = "Ipgtree";

    //Preferences
    public static final String KEY_PREFERENCES = "key_preferences";

    public static final String KEY_P1LIFE = "key_p1life";
    public static final String KEY_P2LIFE = "key_p2life";
    public static final String KEY_P3LIFE = "key_p3life";
    public static final String KEY_P4LIFE = "key_p4life";
    public static final String KEY_P5LIFE = "key_p5life";
    public static final String KEY_P6LIFE = "key_p6life";
    public static final String KEY_P1POISON = "key_p1poison";
    public static final String KEY_P2POISON = "key_p2poison";
    public static final String KEY_P3POISON = "key_p3poison";
    public static final String KEY_P4POISON = "key_p4poison";
    public static final String KEY_P5POISON = "key_5poison";
    public static final String KEY_P6POISON = "key_p6poison";
    public static final String KEY_P1LOG = "key_p1log";
    public static final String KEY_P2LOG = "key_p2log";
    public static final String KEY_P3LOG = "key_p3log";
    public static final String KEY_P4LOG = "key_p4log";
    public static final String KEY_P5LOG = "key_p5log";
    public static final String KEY_P6LOG = "key_p6log";
    public static final String KEY_P1BG = "key_p1bg";
    public static final String KEY_P2BG = "key_p2bg";
    public static final String KEY_P3BG = "key_p3bg";
    public static final String KEY_P4BG = "key_p4bg";
    public static final String KEY_P5BG = "key_p5bg";
    public static final String KEY_P6BG = "key_p6bg";

    public static final String KEY_LANGUAGE = "key_language";
    public static final String KEY_ANNOTATION = "key_annotation";
    public static final String KEY_DOWNLOADNEWS = "key_downloadnews";

    public static final String KEY_ALLOWFTP = "key_allowftp";
    public static final String KEY_FTPSERVER = "key_ftpserver";
    public static final String KEY_FTPUSER = "key_ftpuser";
    public static final String KEY_FTPPASSWORD = "key_ftppassword";

    public static final String KEY_MILLISECONDS = "key_milliseconds";
    public static final String KEY_STARTINGTIME = "key_startingtime";
    public static final String KEY_STARTEDCOUNTDOWN = "key_startedcountdown";
    public static final String KEY_SAVEDTIMES = "key_savedtimes";

    public static final String KEY_NEWS = "key_news";

    public static final String KEY_PLAYERS = "key_players";
    public static final String KEY_REVERSELIFE = "key_reverselife";

    public static final String KEY_AIPG_EN = "key_aipg_en";
    public static final String KEY_AMTR_EN = "key_amtr_en";
    public static final String KEY_ADIPG_EN = "key_adipg_en";
    public static final String KEY_ADMTR_EN = "key_admtr_en";
    public static final String KEY_BANNED_EN = "key_banned_en";
    public static final String KEY_CR_EN = "key_cr_en";
    public static final String KEY_DQ_EN = "key_dq_en";
    public static final String KEY_TREE_EN = "key_tree_en";
    public static final String KEY_JAR_EN = "key_jar_en";
    public static final String KEY_LINKS_EN = "key_links_en";
    public static final String KEY_QUIZ_EN = "key_quiz_en";
    public static final String KEY_HJA_EN = "key_hja_en";

    public static final String KEY_AIPG_ES = "key_aipg_es";
    public static final String KEY_AMTR_ES = "key_amtr_es";
    public static final String KEY_ADIPG_ES = "key_adipg_es";
    public static final String KEY_ADMTR_ES = "key_admtr_es";
    public static final String KEY_BANNED_ES = "key_banned_es";
    public static final String KEY_CR_ES = "key_cr_s";
    public static final String KEY_DQ_ES = "key_dq_es";
    public static final String KEY_TREE_ES = "key_tree_es";
    public static final String KEY_JAR_ES = "key_jar_es";
    public static final String KEY_LINKS_ES = "key_links_es";
    public static final String KEY_QUIZ_ES = "key_quiz_es";
    public static final String KEY_HJA_ES = "key_hja_es";

    //Oracle
    public static HashMap<String, Card> cards;
    public static HashMap<String, Set> sets;
    public static HashMap<String, String[]> setsWithCards;

    //Variables contador
    public static int milliSeconds = 3000000;
    public static long startingTime = 0;
    public static boolean startedCountDown = false;
    public static JSONArray savedTimes = new JSONArray();

    //Variables vida
    public static int p1life = 20;
    public static int p2life = 20;
    public static int p3life = 20;
    public static int p4life = 20;
    public static int p5life = 20;
    public static int p6life = 20;
    public static int p1poison = 0;
    public static int p2poison = 0;
    public static int p3poison = 0;
    public static int p4poison = 0;
    public static int p5poison = 0;
    public static int p6poison = 0;
    public static String p1log = "20";
    public static String p2log = "20";
    public static String p3log = "20";
    public static String p4log = "20";
    public static String p5log = "20";
    public static String p6log = "20";
    public static String p1bg = null;
    public static String p2bg = null;
    public static String p3bg = null;
    public static String p4bg = null;
    public static String p5bg = null;
    public static String p6bg = null;
    public static char mode = LIFE;
    public static int lifeDelay = 4000;
    public static int dice = 20;

    //Variables de configuración
    public static String language = ENGLISH;
    public static boolean showAnnotations = true;
    public static boolean downloadNews = false;
    public static int players = 0;
    public static boolean reverseLife = true;
    public static boolean allowFTP = false;
    public static String ftpServer = null;
    public static String ftpUser = null;
    public static String ftpPassword = null;
    public static int ftpPort = 50505;

    //Varibles de carga
    public static boolean repositoryLoaded = false;
    public static boolean databaseLoaded = false;

    //Load everything
    public static void createRepository(final Context context) {
        SharedPreferences preferences = context.getSharedPreferences(KEY_PREFERENCES, MODE_PRIVATE);
        p1life = preferences.getInt(KEY_P1LIFE, 20);
        p2life = preferences.getInt(KEY_P2LIFE, 20);
        p3life = preferences.getInt(KEY_P3LIFE, 20);
        p4life = preferences.getInt(KEY_P4LIFE, 20);
        p5life = preferences.getInt(KEY_P5LIFE, 20);
        p6life = preferences.getInt(KEY_P6LIFE, 20);
        p1poison = preferences.getInt(KEY_P1POISON, 0);
        p2poison = preferences.getInt(KEY_P2POISON, 0);
        p3poison = preferences.getInt(KEY_P3POISON, 0);
        p4poison = preferences.getInt(KEY_P4POISON, 0);
        p5poison = preferences.getInt(KEY_P5POISON, 0);
        p6poison = preferences.getInt(KEY_P6POISON, 0);
        p1log = preferences.getString(KEY_P1LOG, "20");
        p2log = preferences.getString(KEY_P2LOG, "20");
        p3log = preferences.getString(KEY_P3LOG, "20");
        p4log = preferences.getString(KEY_P4LOG, "20");
        p5log = preferences.getString(KEY_P5LOG, "20");
        p6log = preferences.getString(KEY_P6LOG, "20");
        p1bg = preferences.getString(KEY_P1BG, null);
        p2bg = preferences.getString(KEY_P2BG, null);
        p3bg = preferences.getString(KEY_P3BG, null);
        p4bg = preferences.getString(KEY_P4BG, null);
        p5bg = preferences.getString(KEY_P5BG, null);
        p6bg = preferences.getString(KEY_P6BG, null);
        language = preferences.getString(KEY_LANGUAGE, ENGLISH);
        showAnnotations = preferences.getBoolean(KEY_ANNOTATION, true);
        downloadNews = preferences.getBoolean(KEY_DOWNLOADNEWS, true);
        players = preferences.getInt(KEY_PLAYERS, 0);
        reverseLife = preferences.getBoolean(KEY_REVERSELIFE, true);
        allowFTP = preferences.getBoolean(KEY_ALLOWFTP, false);
        ftpServer = preferences.getString(KEY_FTPSERVER, null);
        ftpUser = preferences.getString(KEY_FTPUSER, null);
        ftpPassword = preferences.getString(KEY_FTPPASSWORD, null);
        milliSeconds = preferences.getInt(KEY_MILLISECONDS, 3000000);
        startingTime = preferences.getLong(KEY_STARTINGTIME, 0);
        startedCountDown = preferences.getBoolean(KEY_STARTEDCOUNTDOWN, false);
        try {
            JSONArray savedTimes = new JSONArray(preferences.getString(KEY_SAVEDTIMES, "[]"));
        } catch (JSONException e) {
            savedTimes = new JSONArray();
        }
        String folder = Environment.getExternalStorageDirectory() + File.separator + FOLDERNAME;
        File directory = new File(folder);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        repositoryLoaded = true;
        new Thread(new Runnable() {
            public void run() {
                loadDatabase(context);
            }
        }).start();
    }

    public static synchronized void loadDatabase(Context context) {
        if (!databaseLoaded) {
            cards = Read.loadCardDatabase(context);
            sets = Read.loadSets(context);
            setsWithCards = linkCardsToSets();
            databaseLoaded = true;
        }
    }

    private static HashMap<String, String[]> linkCardsToSets() {
        HashMap<String, String[]> map = new HashMap<>();
        for (String setName: sets.keySet()) {
            map.put(sets.get(setName).code, new String[sets.get(setName).cards]);
        }
        for (String cardName: cards.keySet()) {
            for (Pair<String,Integer> pair: cards.get(cardName).printings) {
                if(map.containsKey(pair.first)) {
                    //WC02 has an advertisment card numbered 0...
                    if(pair.second > 0) {
                        map.get(pair.first)[pair.second - 1] = cardName;
                    }
                }
            }
        }
        return map;
    }
}