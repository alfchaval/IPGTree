package mtg.judge.ipgtree.Utilities;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.util.Pair;

import mtg.judge.ipgtree.POJO.Card;
import mtg.judge.ipgtree.POJO.Quiz;
import mtg.judge.ipgtree.POJO.Tree;
import mtg.judge.ipgtree.POJO.Set;
import mtg.judge.ipgtree.POJO.TypedText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

//This class transform the XML in a Tree
public class Read {

    public static Tree<TypedText> readXMLDocument(String filename) {
        Tree<TypedText> tree = null;
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + Repository.FOLDERNAME + File.separator + filename);
            FileInputStream fileInputStream = new FileInputStream(file);
            xpp.setInput(new InputStreamReader(fileInputStream));
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if(tree != null) {
                            tree.addChild(new Tree<TypedText>(new TypedText(xpp.getAttributeValue(0))));
                            tree = tree.getChild(tree.numberOfChildren() - 1);
                            switch (xpp.getName()) {
                                case "title":
                                    tree.getData().setType(TypedText.TITLE);
                                    break;
                                case "annotation":
                                    tree.getData().setType(TypedText.ANNOTATION);
                                    break;
                            }
                        }
                        else {
                            tree = new Tree<TypedText>(new TypedText(xpp.getAttributeValue(0)));
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if(!tree.isRoot()) {
                            tree = tree.getParent();
                        }
                    case XmlPullParser.TEXT:
                        //In case you want to read the comments
                        break;
                }
                eventType = xpp.next();
            }
        } catch (XmlPullParserException|IOException e) {
            return null;
        }
        return tree;
    }

    public static Tree<Quiz> readXMLTree(String filename) {
        Tree<Quiz> tree = new Tree<Quiz>(new Quiz(null,new ArrayList<String>()));
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + Repository.FOLDERNAME + File.separator + filename);
            FileInputStream fileInputStream = new FileInputStream(file);
            xpp.setInput(new InputStreamReader(fileInputStream));
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (xpp.getName().equals("question")) {
                            tree.getData().setQuestion(xpp.getAttributeValue(0));
                        }
                        else if (xpp.getName().equals("answer")) {
                            tree.getData().addAnswer(xpp.getAttributeValue(0));
                            tree.addChild(new Tree<Quiz>(new Quiz(null,new ArrayList<String>())));
                            tree = tree.getChild(tree.numberOfChildren() - 1);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (xpp.getName().equals("answer")) {
                            tree = tree.getParent();
                        }
                    case XmlPullParser.TEXT:
                        //In case you want to read the comments
                        break;
                }
                eventType = xpp.next();
            }
        } catch (XmlPullParserException|IOException e) {
            return null;
        }
        return tree;
    }

    public static ArrayList<Quiz> readXMLQuiz (String filename) {
        ArrayList<Quiz> array = new ArrayList<Quiz>();
        Quiz quiz;
        String question = null;
        ArrayList<String> answers = null;
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + Repository.FOLDERNAME + File.separator + filename);
            FileInputStream fileInputStream = new FileInputStream(file);
            xpp.setInput(new InputStreamReader(fileInputStream));
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (xpp.getName().equals("question")) {
                            question = xpp.getAttributeValue(0);
                            answers = new ArrayList<String>();
                        }
                        else if (xpp.getName().equals("answer")) {
                            answers.add(xpp.getAttributeValue(0));
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (xpp.getName().equals("question")) {
                            quiz = new Quiz(question, answers);
                            quiz.setCorrectAnswerPosition(0);
                            array.add(quiz);
                        }
                    case XmlPullParser.TEXT:
                        //In case you want to read the comments
                        break;
                }
                eventType = xpp.next();
            }
        } catch (XmlPullParserException|IOException e) {
            return null;
        }
        return array;
    }

    public static HashMap<String, Card> loadCardDatabase(Context context) {
        HashMap<String, Card> cards = new HashMap<>();
        Gson gson = new GsonBuilder().create();
        Card card;

        String filename = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + Repository.FOLDERNAME + File.separator + "oracle.json";

        if(new File(filename).exists()) {
            try {
                FileInputStream inputStream = new FileInputStream(filename);
                JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));

                reader.beginObject();
                while(reader.hasNext()) {
                    reader.nextName();
                    card = gson.fromJson(reader, Card.class);
                    cards.put(card.name, card);
                }
                reader.endObject();
                reader.close();
            } catch (Exception e) {
                Log.d("ERROR", e.getLocalizedMessage());
            }
        }

        return cards;
    }

    public static HashMap<String, Set> loadSets(Context context) {
        HashMap<String, Set> sets = new HashMap<>();
        Gson gson = new GsonBuilder().create();
        Set set;

        String filename = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + Repository.FOLDERNAME + File.separator + "sets.json";

        if(new File(filename).exists()) {
            try {
                FileInputStream inputStream = new FileInputStream(filename);
                JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));

                reader.beginObject();
                while (reader.hasNext()) {
                    reader.nextName();
                    set = gson.fromJson(reader, Set.class);
                    sets.put(set.name + " | " + set.code, set);
                }
                reader.endObject();
                reader.close();
            } catch (Exception e) {
                Log.d("ERROR", e.getLocalizedMessage());
            }
        }

        return sets;
    }
}