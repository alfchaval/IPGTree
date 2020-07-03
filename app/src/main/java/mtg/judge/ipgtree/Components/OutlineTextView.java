package mtg.judge.ipgtree.Components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;

public class OutlineTextView extends android.support.v7.widget.AppCompatTextView {

    public OutlineTextView(Context context) {
        super(context);
        setTextColor(Color.WHITE);
        setShadowLayer(5f, 0, 0, Color.BLACK);
    }

    public OutlineTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTextColor(Color.WHITE);
        setShadowLayer(5f, 0, 0, Color.BLACK);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for(int i = 0; i < 5; i++)
        {
            super.onDraw(canvas);
        }
    }
}
