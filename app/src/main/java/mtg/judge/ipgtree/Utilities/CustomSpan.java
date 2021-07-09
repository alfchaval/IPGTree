package mtg.judge.ipgtree.Utilities;

import android.net.Uri;
import android.text.TextPaint;
import android.text.style.ClickableSpan;

public abstract class CustomSpan extends ClickableSpan {

    private boolean isPressed;
    private int color;
    private Uri uri;
    private String rule;

    public CustomSpan(int color) {
        this.color = color;
    }

    public CustomSpan(int color, Uri uri) {
        this.color = color;
        this.uri = uri;
    }

    public CustomSpan(int color, String rule) {
        this.color = color;
        this.rule = rule;
    }

    public void setPressed(boolean isSelected) {
        isPressed = isSelected;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setColor(color);
        ds.setUnderlineText(false);
    }

    public Uri GetUri() {
        return uri;
    }

    public String GetRule() {
        return rule;
    }
}
