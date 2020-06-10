package mtg.judge.ipgtree.Utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;
import android.util.Pair;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.util.HashMap;

import mtg.judge.ipgtree.POJO.Card;
import mtg.judge.ipgtree.POJO.Set;
import mtg.judge.ipgtree.Utilities.Read;

import static android.content.Context.MODE_PRIVATE;

public class Repository {

    public static final int appVersion = 20200610;
    public static int lastNews;

    //URLs
    public static final String URL_CARDS = "https://mtgjson.com/json/AllCards.json";
    public static final String URL_SETS = "https://mtgjson.com/json/AllPrintings.json";

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
    public static final String P1_POISON_FILENAME = "p1_poison.txt";
    public static final String P2_POISON_FILENAME = "p2_poison.txt";
    public static final String P3_POISON_FILENAME = "p3_poison.txt";
    public static final String P4_POISON_FILENAME = "p4_poison.txt";
    public static final String P1_LOG_FILENAME = "p1_log.txt";
    public static final String P2_LOG_FILENAME = "p2_log.txt";
    public static final String P3_LOG_FILENAME = "p3_log.txt";
    public static final String P4_LOG_FILENAME = "p4_log.txt";

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
    public static final String KEY_P1POISON = "key_p1poison";
    public static final String KEY_P2POISON = "key_p2poison";
    public static final String KEY_P3POISON = "key_p3poison";
    public static final String KEY_P4POISON = "key_p4poison";
    public static final String KEY_P1LOG = "key_p1log";
    public static final String KEY_P2LOG = "key_p2log";
    public static final String KEY_P3LOG = "key_p3log";
    public static final String KEY_P4LOG = "key_p4log";

    public static final String KEY_LANGUAGE = "key_language";
    public static final String KEY_ANNOTATION = "key_annotation";
    public static final String KEY_DOWNLOADNEWS = "key_downloadnews";

    public static final String KEY_UNLOCKEDFTP = "key_unlockedftp";
    public static final String KEY_ALLOWFTP = "key_allowftp";
    public static final String KEY_FTPSERVER = "key_ftpserver";
    public static final String KEY_FTPUSER = "key_ftpuser";
    public static final String KEY_FTPPASSWORD = "key_ftppassword";

    public static final String KEY_MILLISECONDS = "key_milliseconds";
    public static final String KEY_STARTINGTIME = "key_startingtime";
    public static final String KEY_STARTEDCOUNTDOWN = "key_startedcountdown";
    public static final String KEY_SAVEDTIMES = "key_savedtimes";

    public static final String KEY_NEWS = "key_news";

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
    public static int p1poison = 0;
    public static int p2poison = 0;
    public static int p3poison = 0;
    public static int p4poison = 0;
    public static String p1log = "20";
    public static String p2log = "20";
    public static String p3log = "20";
    public static String p4log = "20";
    public static char mode = LIFE;
    public static int lifeDelay = 4000;
    public static int dice = 20;

    //Variables de configuración
    public static String language = ENGLISH;
    public static String ftpCode = null;
    public static boolean showAnnotations = true;
    public static boolean downloadNews = false;
    public static boolean unlockedFTP = false;
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
        p1poison = preferences.getInt(KEY_P1POISON, 0);
        p2poison = preferences.getInt(KEY_P2POISON, 0);
        p3poison = preferences.getInt(KEY_P3POISON, 0);
        p4poison = preferences.getInt(KEY_P4POISON, 0);
        p1log = preferences.getString(KEY_P1LOG, "20");
        p2log = preferences.getString(KEY_P2LOG, "20");
        p3log = preferences.getString(KEY_P3LOG, "20");
        p4log = preferences.getString(KEY_P4LOG, "20");
        language = preferences.getString(KEY_LANGUAGE, ENGLISH);
        showAnnotations = preferences.getBoolean(KEY_ANNOTATION, true);
        downloadNews = preferences.getBoolean(KEY_DOWNLOADNEWS, true);
        unlockedFTP = preferences.getBoolean(KEY_UNLOCKEDFTP, false);
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

    //Yes, I know I can change localice and have a strings.xml for each language
    public static String StringMap(int index) {
        String result = "";
        switch (language) {
            case ENGLISH:
                switch (index) {
                    case 1:
                        result = "Reset";
                        break;
                    case 2:
                        result = "Total: ";
                        break;
                    case 3:
                        result = "Undo";
                        break;
                    case 4:
                        result = "Banned lists";
                        break;
                    case 5:
                        result = "Disqualification process";
                        break;
                    case 6:
                        result = "Links";
                        break;
                    case 7:
                        result = "Booster: ";
                        break;
                    case 8:
                        result = " Card: ";
                        break;
                    case 9:
                        result = "Time to revise picks";
                        break;
                    case 10:
                        result = "Last";
                        break;
                    case 11:
                        result = "Time to look the booster";
                        break;
                    case 12:
                        result = "Mode: Normal";
                        break;
                    case 13:
                        result = "Mode: 2HG";
                        break;
                    case 14:
                        result = "Next";
                        break;
                    case 15:
                        result = "Pause";
                        break;
                    case 16:
                        result = "Reset booster";
                        break;
                    case 17:
                        result = "Reset draft";
                        break;
                    case 18:
                        result = "Resume";
                        break;
                    case 19:
                        result = "Mode: Rochester";
                        break;
                    case 20:
                        result = "Start";
                        break;
                    case 21:
                        result = "Edit";
                        break;
                    case 22:
                        result = "Minutes: ";
                        break;
                    case 23:
                        result = "No";
                        break;
                    case 24:
                        result = "REGISTRY";
                        break;
                    case 25:
                        result = "RESET";
                        break;
                    case 26:
                        result = "Do you really want to reset?";
                        break;
                    case 27:
                        result = "Reset life";
                        break;
                    case 28:
                        result = "Yes";
                        break;
                    case 29:
                        result = "user";
                        break;
                    case 30:
                        result = "Decklist counter";
                        break;
                    case 31:
                        result = "Documents";
                        break;
                    case 32:
                        result = "Draft";
                        break;
                    case 33:
                        result = "IPG Quiz";
                        break;
                    case 34:
                        result = "IPG Tree";
                        break;
                    case 35:
                        result = "Life counter";
                        break;
                    case 36:
                        result = "Oracle";
                        break;
                    case 37:
                        result = "Settings";
                        break;
                    case 38:
                        result = "Timer";
                        break;
                    case 39:
                        result = "Enter a card name";
                        break;
                    case 40:
                        result = "Change to name search";
                        break;
                    case 41:
                        result = "Change to set search";
                        break;
                    case 42:
                        result = "ERROR";
                        break;
                    case 43:
                        result = "Nothing found";
                        break;
                    case 44:
                        result = "There are no cards in your local database, you can download an updated database from Settings menu";
                        break;
                    case 45:
                        result = "Selected set: ";
                        break;
                    case 46:
                        result = "Enter a set name";
                        break;
                    case 47:
                        result = "END";
                        break;
                    case 48:
                        result = "There are no more questions, you can press end to see your result or use the arrow to check your answers";
                        break;
                    case 49:
                        result = " You can see the correct answers using the arrows";
                        break;
                    case 50:
                        result = "Your total score is: ";
                        break;
                    case 51:
                        result = "Unlock";
                        break;
                    case 52:
                        result = "code";
                        break;
                    case 53:
                        result = "Continue";
                        break;
                    case 54:
                        result = "The database weighs more than a Colossal Dreadmaw, we recommend wifi connection before download";
                        break;
                    case 55:
                        result = "Downloaded in: \"";
                        break;
                    case 56:
                        result = "Download Oracle";
                        break;
                    case 57:
                        result = "Error";
                        break;
                    case 58:
                        result = "Connection error";
                        break;
                    case 59:
                        result = "Close";
                        break;
                    case 60:
                        result = "FTP Server";
                        break;
                    case 61:
                        result = "Update oracle";
                        break;
                    case 62:
                        result = "password";
                        break;
                    case 63:
                        result = "Save";
                        break;
                    case 64:
                        result = "Allow sending data";
                        break;
                    case 65:
                        result = "Show annotations in AMTR and AIPG";
                        break;
                    case 66:
                        result = "(You can also show/hide annotations pressing and holding in the document section title)";
                        break;
                    case 67:
                        result = "Suscessful connection";
                        break;
                    case 68:
                        result = "Search";
                        break;
                    case 69:
                        result = "No results found";
                        break;
                    case 70:
                        result = "Check for updates at start";
                        break;
                    case 71:
                        result = "Update documents";
                        break;
                    case 72:
                        result = "Advanced settings";
                        break;
                    case 73:
                        result = "It looks like you don't have this document, you can download it from Settings menu";
                        break;
                    case 74:
                        result = "Available update";
                        break;
                    case 75:
                        result = "HJ Announcement";
                        break;
                    case 76:
                        result = "Show/Hide FTP Config";
                        break;
                    case 77:
                        result = "Show/Hide Document links";
                        break;
                    case 78:
                        result = "Saved to Clipboard";
                        break;
                    case 79:
                        result = "Result from D";
                        break;
                    case 80:
                        result = "Choose number of sides";
                        break;
                    case 81:
                        result = "Ok";
                        break;
                    case 82:
                        result = "Cancel";
                        break;
                    case 83:
                        result = "Do you really want to reset?";
                        break;
                    case 84:
                        result = "Reset timer";
                        break;
                }
                break;
            case SPANISH:
                switch (index) {
                    case 1:
                        result = "Reiniciar";
                        break;
                    case 2:
                        result = "Total: ";
                        break;
                    case 3:
                        result = "Deshacer";
                        break;
                    case 4:
                        result = "Lista de baneos";
                        break;
                    case 5:
                        result = "Proceso de descalificación";
                        break;
                    case 6:
                        result = "Enlaces";
                        break;
                    case 7:
                        result = "Sobre: ";
                        break;
                    case 8:
                        result = " Carta: ";
                        break;
                    case 9:
                        result = "Tiempo para revisar los picks";
                        break;
                    case 10:
                        result = "Anterior";
                        break;
                    case 11:
                        result = "Tiempo para mirar el sobre";
                        break;
                    case 12:
                        result = "Modo: Normal";
                        break;
                    case 13:
                        result = "Modo: 2HG";
                        break;
                    case 14:
                        result = "Siguiente";
                        break;
                    case 15:
                        result = "Pausar";
                        break;
                    case 16:
                        result = "Reiniciar sobre";
                        break;
                    case 17:
                        result = "Reiniciar draft";
                        break;
                    case 18:
                        result = "Reanudar";
                        break;
                    case 19:
                        result = "Modo: Rochester";
                        break;
                    case 20:
                        result = "Comenzar";
                        break;
                    case 21:
                        result = "Editar";
                        break;
                    case 22:
                        result = "Minutos: ";
                        break;
                    case 23:
                        result = "No";
                        break;
                    case 24:
                        result = "REGISTRO";
                        break;
                    case 25:
                        result = "REINICIAR";
                        break;
                    case 26:
                        result = "¿Seguro que quieres reiniciar las vidas?";
                        break;
                    case 27:
                        result = "Reiniciar vidas";
                        break;
                    case 28:
                        result = "Si";
                        break;
                    case 29:
                        result = "usuario";
                        break;
                    case 30:
                        result = "Contador de listas";
                        break;
                    case 31:
                        result = "Documentos";
                        break;
                    case 32:
                        result = "Draft";
                        break;
                    case 33:
                        result = "Quiz IPG";
                        break;
                    case 34:
                        result = "Árbol IPG";
                        break;
                    case 35:
                        result = "Contador de vida";
                        break;
                    case 36:
                        result = "Oracle";
                        break;
                    case 37:
                        result = "Ajustes";
                        break;
                    case 38:
                        result = "Temporizador";
                        break;
                    case 39:
                        result = "Introduce una carta en inglés";
                        break;
                    case 40:
                        result = "Cambiar a búsqueda por nombre";
                        break;
                    case 41:
                        result = "Cambiar a búsqueda por edición y número";
                        break;
                    case 42:
                        result = "ERROR";
                        break;
                    case 43:
                        result = "No se encontraron resultados";
                        break;
                    case 44:
                        result = "No tienes cartas en la base de datos local, puedes descargar la base de datos actualizada desde Opciones";
                        break;
                    case 45:
                        result = "Set seleccionado: ";
                        break;
                    case 46:
                        result = "Introduce una edición en inglés";
                        break;
                    case 47:
                        result = "FINALIZAR";
                        break;
                    case 48:
                        result = "No hay más preguntas, pulsa en finalizar para comprobar tus resultados... o puedes usar la flecha para volver y repasar tus respuestas";
                        break;
                    case 49:
                        result = " Puedes ver las respuestas correctas desplazándote por las preguntas con las flechas";
                        break;
                    case 50:
                        result = "Tu puntuación total es: ";
                        break;
                    case 51:
                        result = "Desbloquear";
                        break;
                    case 52:
                        result = "código";
                        break;
                    case 53:
                        result = "Continuar";
                        break;
                    case 54:
                        result = "La base de datos pesa más que un Colossal Dreadmaw, recomendamos conexión wifi antes de continuar";
                        break;
                    case 55:
                        result = "Descargado en: ";
                        break;
                    case 56:
                        result = "Descargar Oracle";
                        break;
                    case 57:
                        result = "Error";
                        break;
                    case 58:
                        result = "Error de conexión";
                        break;
                    case 59:
                        result = "Salir";
                        break;
                    case 60:
                        result = "Servidor FTP";
                        break;
                    case 61:
                        result = "Actualizar oracle";
                        break;
                    case 62:
                        result = "contraseña";
                        break;
                    case 63:
                        result = "Guardar";
                        break;
                    case 64:
                        result = "Activar envío de datos";
                        break;
                    case 65:
                        result = "Mostrar anotaciones en AMTR Y AIPG";
                        break;
                    case 66:
                        result = "(También pueden mostarse/ocultarse manteniendo pulsado en el título de la sección del documento)";
                        break;
                    case 67:
                        result = "Conexión exitosa";
                        break;
                    case 68:
                        result = "Buscar";
                        break;
                    case 69:
                        result = "No se encontraron resultados";
                        break;
                    case 70:
                        result = "Buscar actualizaciones al iniciar";
                        break;
                    case 71:
                        result = "Actualizar documentos";
                        break;
                    case 72:
                        result = "Ajustes avanzados";
                        break;
                    case 73:
                        result = "Parece que no tienes este documento, puedes descargarlo desde el menú Ajustes";
                        break;
                    case 74:
                        result = "Actualización disponible";
                        break;
                    case 75:
                        result = "Anuncio del HJ";
                        break;
                    case 76:
                        result = "Mostrar/Ocultar FTP";
                        break;
                    case 77:
                        result = "Mostrar/Ocultar Links";
                        break;
                    case 78:
                        result = "Copiado al Salvapapeles";
                        break;
                    case 79:
                        result = "Resultado de D";
                        break;
                    case 80:
                        result = "Elige número de caras";
                        break;
                    case 81:
                        result = "Aceptar";
                        break;
                    case 82:
                        result = "Cancelar";
                        break;
                    case 83:
                        result = "¿Seguro que quieres reiniciar el temporizador?";
                        break;
                    case 84:
                        result = "Reiniciar temporizador";
                        break;
                }
                break;
            default:
                if(index == 37)
                {
                    result = "Select language";
                }
                else
                {
                    result = "---";
                }
            break;
        }
        return result;
    }
}