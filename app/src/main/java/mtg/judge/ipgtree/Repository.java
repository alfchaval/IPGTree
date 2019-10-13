package mtg.judge.ipgtree;

import android.content.Context;
import android.util.Pair;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

public class Repository {

    //URLs
    public static final String URLCARDS = "https://mtgjson.com/json/AllCards.json";
    public static final String URLSETS = "https://mtgjson.com/json/AllSets.json";

    //File names
    public static final String P1_LIFE_FILENAME = "p1_life.txt";
    public static final String P2_LIFE_FILENAME = "p2_life.txt";
    public static final String P1_POISON_FILENAME = "p1_poison.txt";
    public static final String P2_POISON_FILENAME = "p2_poison.txt";
    public static final String P1_LOG_FILENAME = "p1_log.txt";
    public static final String P2_LOG_FILENAME = "p2_log.txt";
    public static final String SERVER = "server.txt";

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

    //Oracle
    public static HashMap<String, Card> cards;
    public static HashMap<String, Set> sets;
    public static HashMap<String, String[]> setsWithCards;

    //Documents
    public static Tree<TypedText> ComprehensiveRules;
    public static Tree<TypedText> JudgingAtRegular;
    public static Tree<TypedText> AnnotatedInfractionProcedureGuide;
    public static Tree<TypedText> AnnotatedMagicTournamentRules;
    public static Tree<TypedText> DisqualificationProcess;
    public static Tree<TypedText> BannedAndRestricted;
    public static Tree<TypedText> Links;

    //Others
    public static Tree<Quiz> IPGTree;
    public static ArrayList<Quiz> Quiz;

    //Values
    public final static char LIFE = '♥';
    public final static char POISON = 'ϕ';

    //Variables contador
    public static int seconds = 0;
    public static long wentAway = 0;
    public static boolean startedCountDown = false;

    //Variables vida
    public static int p1life = 20;
    public static int p2life = 20;
    public static int p1poison = 0;
    public static int p2poison = 0;
    public static String p1log = "20";
    public static String p2log = "20";
    public static char mode = LIFE;
    public static int lifeDelay = 4000;

    //Variables de configuración
    public static boolean showAnnotations = true;
    public static boolean unlockedFTP = false;
    public static boolean allowFTP = false;
    public static String ftpServer = null;
    public static String ftpUser = null;
    public static String ftpPassword = null;
    public static int ftpPort = 50505;

    //Load everything
    public static void createRepository(Context context) {
        loadDatabase(context);
        ComprehensiveRules = Read.readXMLDocument(context.getResources().getXml(R.xml.cr));
        JudgingAtRegular = Read.readXMLDocument(context.getResources().getXml(R.xml.jar));
        AnnotatedInfractionProcedureGuide = Read.readXMLDocument(context.getResources().getXml(R.xml.aipg));
        AnnotatedMagicTournamentRules = Read.readXMLDocument(context.getResources().getXml(R.xml.amtr));
        DisqualificationProcess = Read.readXMLDocument(context.getResources().getXml(R.xml.dq));
        BannedAndRestricted = Read.readXMLDocument(context.getResources().getXml(R.xml.banned));
        Links = Read.readXMLDocument(context.getResources().getXml(R.xml.links));
        IPGTree = Read.readXMLTree(context.getResources().getXml(R.xml.ipg_tree));
        Quiz = Read.readXMLQuiz(context.getResources().getXml(R.xml.quiz));

        //TODO Cargar info de vidas, contador, servidor y anotaciones de preferences
    }

    public static void loadDatabase(Context context) {
        cards = Read.loadCardDatabase(context);
        sets = Read.loadSets(context);
        setsWithCards = linkCardsToSets();
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