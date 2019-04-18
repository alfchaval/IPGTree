package com.example.usuario.MagicQuiz;

import android.util.Pair;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;

public class Set {
    @SerializedName("code")
    public String code;
    @SerializedName("name")
    public String name;

    public Set (String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static HashMap<String, String> toHashMap(ArrayList<Set> sets) {
        HashMap<String, String>  hashMap = new HashMap();
        for (Set set: sets) {
            hashMap.put(set.name + " | " + set.code, set.code);
        }
        return hashMap;
    }
}
