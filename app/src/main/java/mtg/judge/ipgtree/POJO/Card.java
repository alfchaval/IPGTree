package mtg.judge.ipgtree.POJO;

import android.util.Pair;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Card {
    @SerializedName("name")
    public String name;
    @SerializedName("faces")
    public ArrayList<Face> faces = new ArrayList<>();
    @SerializedName("printings")
    public ArrayList<Pair<String,Integer>> printings = new ArrayList<>();

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if (obj instanceof Card) {
            Card face = (Card) obj;
            return (this.name == face.name);
        }
        return false;
    }

    public String showCard() {
        String text = "";
        text += this.name + "";
        for(int i = 0; i < faces.size(); i++)
        {
            text += faces.get(i).showFace();
        }
        return text;
    }
}
