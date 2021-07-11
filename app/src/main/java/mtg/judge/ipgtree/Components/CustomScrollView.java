package mtg.judge.ipgtree.Components;

import android.content.Context;
import android.graphics.Canvas;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.util.AttributeSet;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

import mtg.judge.ipgtree.R;

public class CustomScrollView extends ScrollView {

    public ArrayList<TextView> textViews;
    public int viewIndex = -1;
    public int textLength;

    public CustomScrollView(Context context) {
        super(context);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        scrollToTargetTextView();
    }

    private void scrollToTargetTextView()
    {
        if(viewIndex >= 0 && viewIndex < textViews.size())
        {
            this.smoothScrollTo(0, textViews.get(viewIndex).getTop());
            SpannableString spannable = (SpannableString)textViews.get(viewIndex).getText();
            spannable.setSpan(new BackgroundColorSpan(getResources().getColor(R.color.colorSelected)), 0, textLength, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            viewIndex = -1;
        }
    }
}
