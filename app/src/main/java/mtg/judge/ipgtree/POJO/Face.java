package mtg.judge.ipgtree.POJO;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Face {
    @SerializedName("faceName")
    public String faceName;
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

    public String showFace() {
        String text = "";
        if(this.faceName != null)
        {
            text += "\n\n////////////////////\n\n" + this.faceName;
        }
        if(this.manaCost != null) {
            text += " " + this.manaCost;
        }
        text += "\n\n";
        if(colorIndicator.size() > 0) {
            text += "Color indicator: ";
            for (String s: colorIndicator) {
                text += s;
            }
            text += "\n\n";
        }
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
