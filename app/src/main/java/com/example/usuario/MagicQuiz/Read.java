package com.example.usuario.MagicQuiz;

import android.content.res.XmlResourceParser;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

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
}