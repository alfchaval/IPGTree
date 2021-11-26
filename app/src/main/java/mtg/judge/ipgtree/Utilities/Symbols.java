package mtg.judge.ipgtree.Utilities;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;

import java.util.regex.Pattern;

import mtg.judge.ipgtree.R;

public class Symbols {

    public static final Pattern SYMBOL_PATTERN = Pattern.compile("\\{(.*?)\\}");

    public static Drawable getSymbol(String s, Context context)
    {
        switch (s) {
            case "{C}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_c);
            case "{0}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_0);
            case "{1}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_1);
            case "{2}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_2);
            case "{3}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_3);
            case "{4}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_4);
            case "{5}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_5);
            case "{6}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_6);
            case "{7}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_7);
            case "{8}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_8);
            case "{9}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_9);
            case "{10}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_10);
            case "{11}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_11);
            case "{12}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_12);
            case "{13}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_13);
            case "{14}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_14);
            case "{15}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_15);
            case "{16}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_16);
            case "{17}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_17);
            case "{18}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_18);
            case "{19}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_19);
            case "{20}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_20);
            case "{W}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_w);
            case "{U}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_u);
            case "{B}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_b);
            case "{G}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_g);
            case "{R}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_r);
            case "{W/P}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_wp);
            case "{U/P}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_up);
            case "{B/P}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_bp);
            case "{R/P}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_rp);
            case "{G/P}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_gp);
            case "{2/W}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_2w);
            case "{2/U}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_2u);
            case "{2/B}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_2b);
            case "{2/R}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_2r);
            case "{2/G}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_2g);
            case "{W/U}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_wu);
            case "{W/B}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_wb);
            case "{U/B}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_ub);
            case "{U/R}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_ur);
            case "{B/R}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_br);
            case "{B/G}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_bg);
            case "{R/G}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_rg);
            case "{R/W}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_rw);
            case "{G/W}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_gw);
            case "{G/U}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_gu);
            case "{X}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_x);
            case "{Y}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_y);
            case "{Z}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_z);
            case "{S}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_s);
            case "{T}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_t);
            case "{Q}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_q);
            case "{E}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_e);
            case "{CHAOS}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_chaos);
            case "{A}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_a);
            case "{∞}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_infinite);
            case "{100}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_100);
            case "{1000000}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_1000000);
            case "{½}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_1_2);
            case "{HW}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_w_2);
            case "{HR}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_r_2);
            case "{PW}":
                return ContextCompat.getDrawable(context, R.drawable.symbol_pw);
        }
        return null;
    }
}
