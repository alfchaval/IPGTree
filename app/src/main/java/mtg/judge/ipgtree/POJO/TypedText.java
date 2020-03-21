package mtg.judge.ipgtree.POJO;

import java.io.Serializable;
import java.util.ArrayList;

public class TypedText implements Serializable {

    public static final int NORMAL = 0;
    public static final int TITLE = 1;
    public static final int EXAMPLE = 2;
    public static final int ANNOTATION = 3;
    public static final int LINK = 4;

    //region variables

    private String text;
    private int type;

    //endregion

    //region constructors

    public TypedText(String text) {
        this.text = text;
        this.type = NORMAL;
    }

    public TypedText(String text, int type) {
        this.text = text;
        this.type = type;
    }

    //endregion

    //region gettersAndSetters

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    //endregion
}