package mtg.judge.ipgtree.POJO;

import android.util.Pair;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;

public class Set {
    @SerializedName("code")
    public String code;
    @SerializedName("name")
    public String name;
    @SerializedName("cards")
    public int cards = 0;

    public Set (String code, String name) {
        this.code = code;
        this.name = name;
    }

    public Set (String code, String name, int cards) {
        this.code = code;
        this.name = name;
        this.cards = cards;
    }
}
