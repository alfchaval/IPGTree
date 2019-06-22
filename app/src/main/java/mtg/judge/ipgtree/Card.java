package mtg.judge.ipgtree;

import android.util.Pair;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Card {
    @SerializedName("name")
    public String name;
    @SerializedName("manaCost")
    public String manaCost;
    @SerializedName("colorIndicator")
    public ArrayList<String> colorIndicator = new ArrayList<>();
    @SerializedName("type")
    public String type;
    @SerializedName("text")
    public String text;
    @SerializedName("power")
    public String power;
    @SerializedName("toughness")
    public String toughness;
    @SerializedName("loyalty")
    public String loyalty;
    @SerializedName("printings")
    public ArrayList<Pair<String,Integer>> printings = new ArrayList<>();

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if (obj instanceof Card) {
            Card card = (Card) obj;
            return (this.name == card.name);
        }
        return false;
    }
}
