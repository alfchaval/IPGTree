package mtg.judge.ipgtree.POJO;

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
    @SerializedName("sidenames")
    public ArrayList<String> sidenames = new ArrayList<>();

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

    public String showCard() {
        String text = "";
        text += this.name + " ";
        if(this.manaCost != null) {
            text += this.manaCost;
        }
        text += "\n\n";
        //TODO indicador de color
        if(this.type != null) {
            text += this.type + "\n\n";
        }
        if(this.text != null) {
            text += this.text;
        }
        if(this.power != null && this.toughness != null) {
            text += "\n\n" + this.power + "/" + this.toughness;
        }
        if(this.loyalty != null) {
            text += "\n\n" + this.loyalty;
        }
        return text;
    }
}
