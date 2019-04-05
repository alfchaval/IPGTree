package com.example.usuario.MagicQuiz;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.os.Environment;
import android.util.Pair;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

//This class transform the XML in a Tree
public class Read {


    public static Tree<TypedText> readXMLDocument(XmlResourceParser resource) {
        Tree<TypedText> tree = null;
        XmlPullParser xpp = resource;
        try {
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
                                case "example":
                                    tree.getData().setType(TypedText.EXAMPLE);
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
            e.printStackTrace();
        }
        return tree;
    }

    public static Tree<Quiz> readXMLTree(XmlResourceParser resource) {
        Tree<Quiz> tree = new Tree<Quiz>(new Quiz(null,new ArrayList<String>()));
        XmlPullParser xpp = resource;
        try {
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
            e.printStackTrace();
        }
        return tree;
    }

    public static ArrayList<Quiz> readXMLQuiz (XmlResourceParser resource) {
        ArrayList<Quiz> array = new ArrayList<Quiz>();
        Quiz quiz;
        String question = null;
        ArrayList<String> answers = null;
        XmlPullParser xpp = resource;
        try {
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
            e.printStackTrace();
        }
        return array;
    }

    public static HashMap<String, Card> loadDatabase(Context context, TextView textView) {
        HashMap<String, Card> cards = new HashMap<>();
        Gson gson = new GsonBuilder().create();
        Card card;

        try {
            InputStream inputStream = context.getResources().openRawResource(R.raw.oracle);
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
            textView.setText("Error cargando la base de datos");
        }

        //textView.setText("Esta función aún está en desarrollo, pero ya se puede usar parcialmente");
        return cards;
    }

    //create a Json file with the information I want from AllCards.json and AllSets.json, so I have a smaller database (https://mtgjson.com/downloads/compiled/)
    public static void createDatabase(String fileName, Context context, int allCards, int allSets) {
        HashMap<String, Card> cards = new HashMap<>();
        Gson gson = new GsonBuilder().create();
        Card card;
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
                        case "printings":
                            reader.beginArray();
                            while(reader.hasNext()) {
                                card.printings.add(new Pair<String, Integer>(reader.nextString(), 0));
                            }
                            reader.endArray();
                            break;
                        case "colors":
                            reader.beginArray();
                            while(reader.hasNext()) {
                                card.colors.add(reader.nextString());
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

        ArrayList<Pair<String, Integer>> numbers = new ArrayList<>();
        String name = "";
        int number = 0;
        String code = "";
        boolean add = true;

        try {
            InputStream inputStream = context.getResources().openRawResource(allSets);
            JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));

            reader.beginObject();

            int aux = 0;
            while (reader.hasNext()) {
                reader.nextName();
                reader.beginObject();
                while (reader.hasNext()) {
                    switch (reader.nextName()) {
                        case "code":
                            code = reader.nextString();
                            break;
                        case "allcards":
                            numbers = new ArrayList<>();
                            reader.beginArray();
                            while(reader.hasNext()) {
                                reader.beginObject();
                                while(reader.hasNext()) {
                                    switch (reader.nextName()) {
                                        case "name":
                                            name = reader.nextString();
                                            break;
                                        case "number":
                                            try{
                                                //some allcards have special numbers... I also don't want these allcards
                                                number = Integer.parseInt(reader.nextString());
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
                                    boolean alreadyIn = false;
                                    for(int i = 0; i < numbers.size(); i++) {
                                        if(numbers.get(i).first == name && numbers.get(i).second == number) {
                                            alreadyIn = true;
                                        }
                                    }
                                    if(!alreadyIn) {
                                        numbers.add(new Pair<String, Integer>(name, number));
                                    }
                                }
                            }
                            reader.endArray();
                            break;
                        default:
                            reader.skipValue();
                            break;
                    }
                    for (Pair<String, Integer> p: numbers) {
                        if(cards.containsKey(p.first)) {
                            cards.get(p.first).printings.add(new Pair<String, Integer>(code, p.second));
                        }
                    }
                }
                reader.endObject();
            }
            reader.close();
        } catch (Exception e) {
        }

        String filename = "oracle.json";

        try{
            File gpxfile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), filename);
            FileWriter writer = new FileWriter(gpxfile);
            ArrayList<Card> list = new ArrayList<Card>(cards.values());
            String s;
            writer.append("{");
            for (int i = 0; i < list.size() - 1; i++){
                //fuck you Pang Tong, "Young Phoenix"
                writer.append("\"" + list.get(i).name.replace("\"", "") + "\":");
                s = gson.toJson(list.get(i));
                writer.append(s+",");
            }
            writer.append("\"" + list.get(list.size()-1).name.replace("\"", "") + "\":");
            s = gson.toJson(list.get(list.size()-1));
            writer.append(s);
            writer.append("}");
            writer.flush();
            writer.close();

        }catch (Exception e){
        }
    }
}