package mtg.judge.ipgtree;

import android.content.Context;
import android.util.Pair;

import mtg.judge.ipgtree.R;

import java.util.ArrayList;
import java.util.HashMap;

public class Repository {

    static Repository repository;

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

    //Others
    public static Tree<Quiz> IPGTree;
    public static ArrayList<Quiz> Quiz;

    //Load everything
    public static void createRepository(Context context) {
        repository = new Repository();
        cards = Read.loadCardDatabase(context);
        sets = Read.loadSets(context);
        setsWithCards = linkCardsToSets();
        ComprehensiveRules = Read.readXMLDocument(context.getResources().getXml(R.xml.cr));
        JudgingAtRegular = Read.readXMLDocument(context.getResources().getXml(R.xml.jar));
        AnnotatedInfractionProcedureGuide = Read.readXMLDocument(context.getResources().getXml(R.xml.aipg));
        AnnotatedMagicTournamentRules = Read.readXMLDocument(context.getResources().getXml(R.xml.amtr));
        DisqualificationProcess = Read.readXMLDocument(context.getResources().getXml(R.xml.dq));
        BannedAndRestricted = Read.readXMLDocument(context.getResources().getXml(R.xml.banned));
        IPGTree = Read.readXMLTree(context.getResources().getXml(R.xml.ipg_tree));
        Quiz = Read.readXMLQuiz(context.getResources().getXml(R.xml.quiz));
    }

    //To get all loaded information
    public static Repository getInstance() {
        return repository;
    }

    private static HashMap<String, String[]> linkCardsToSets() {
        HashMap<String, String[]> map = new HashMap<>();
        for (String setName: sets.keySet()) {
            map.put(sets.get(setName).code, new String[sets.get(setName).cards]);
            //map.put(sets.get(setName).code, new String[999]);
        }
        for (String cardName: cards.keySet()) {
            for (Pair<String,Integer> pair: cards.get(cardName).printings) {
                if(map.containsKey(pair.first)) {
                    map.get(pair.first)[pair.second-1] = cardName;
                }
            }
        }
        return map;
    }
}