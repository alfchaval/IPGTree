package com.example.usuario.MagicQuiz;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Read {

    public static Tree<Quiz> readXMLQuiz(File file) throws XmlPullParserException, IOException {
        Tree<Quiz> tree = new Tree<Quiz>(new Tree<Quiz>(new Quiz("")));
        boolean isQuestion = false;
        boolean isAnswer = false;

        XmlPullParser xpp = Xml.newPullParser();
        xpp.setInput(new FileReader(file));
        int eventType = xpp.getEventType();

        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if (xpp.getName().equals("question")) {
                        isQuestion = true;
                    }
                    if (xpp.getName().equals("answer")) {
                        isAnswer = true;
                    }
                    break;
                case XmlPullParser.TEXT:
                    if (isQuestion) {
                        //xrp.getText()
                    }
                    if (isAnswer) {

                    }
                    break;
                case XmlPullParser.END_TAG:
                    if (isQuestion) {

                    }
                    if (isAnswer) {

                    }
            }
            eventType = xpp.next();
        }
        return tree;
    }
    
}
