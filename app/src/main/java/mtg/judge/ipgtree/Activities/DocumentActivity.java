package mtg.judge.ipgtree.Activities;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import mtg.judge.ipgtree.R;

import mtg.judge.ipgtree.Repository;
import mtg.judge.ipgtree.Tree;
import mtg.judge.ipgtree.TypedText;

import java.util.ArrayList;

public class DocumentActivity extends AppCompatActivity {

    Tree<TypedText> tree;

    TextView tv_title;
    LinearLayout ll_points;
    ScrollView scroll_points;
    ImageView imv_arrow_up;
    ImageView imv_arrow_down;

    ViewTreeObserver viewTreeObserver;

    ArrayList<TextView> branchs = new ArrayList<TextView>();

    LinearLayout.LayoutParams layoutParams;

    Repository repository;

    boolean showNotes = true;

    final int TEXT_SIZE = 20;

    private static final String KEY_SERIALIZED_TREE = "key_serialized_tree";

    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_SERIALIZED_TREE, tree);
    }

    //When you press back you go to the previous point (if there is previous point)
    @Override
    public void onBackPressed() {
        if(tree.isRoot()) {
            super.onBackPressed();
        }
        else {
            tree = tree.getParent();
            showList();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree);

        linkViews();
        setListeners();

        tv_title.setTextSize(TEXT_SIZE);
        viewTreeObserver = scroll_points.getViewTreeObserver();

        layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,  LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 4, 0, 4);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //Arrows disapear when you full scroll to let you read the first/last question
            scroll_points.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View view, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    showOrHideArrows();
                }
            });
            //Also you don't want to see them if there aren't answers outside the screen
            viewTreeObserver.addOnGlobalLayoutListener (new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    showOrHideArrows();
                }
            });
        }
        else {
            //Older versions can't use OnScrollChangeListener, so instead of disapear, the arrows are semitransparent
            imv_arrow_down.setAlpha(0.4f);
            imv_arrow_up.setAlpha(0.4f);
            //They still disapear if there aren't answers outside the screen
            viewTreeObserver.addOnGlobalLayoutListener (new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    showOrHideArrowsOldDevices();
                }
            });
        }

        repository = Repository.getInstance();
        loadPoints(savedInstanceState);
        showList();

    }

    public void linkViews() {
        ll_points = findViewById(R.id.ll_points);
        scroll_points = findViewById(R.id.scroll_answers);
        tv_title = findViewById(R.id.tv_question);
        imv_arrow_up = findViewById(R.id.imv_arrow_up);;
        imv_arrow_down = findViewById(R.id.imv_arrow_down);
    }

    public void setListeners() {
        tv_title.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showNotes = !showNotes;
                showList();
                return true;
            }
        });
        imv_arrow_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scroll_points.fullScroll(View.FOCUS_UP);
            }
        });
        imv_arrow_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scroll_points.fullScroll(View.FOCUS_DOWN);
            }
        });
    }

    //You only have to read the XML the first time you start the app
    private void loadPoints(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            tree = (Tree<TypedText>) savedInstanceState.getSerializable(KEY_SERIALIZED_TREE);
        }
        else {
            switch (getIntent().getStringExtra("document")) {
                case "cr":
                    tree = repository.ComprehensiveRules;
                    break;
                case "jar":
                    tree = repository.JudgingAtRegular;
                    break;
                case "aipg":
                    tree = repository.AnnotatedInfractionProcedureGuide;
                    break;
                case "amtr":
                    tree = repository.AnnotatedMagicTournamentRules;;
                    break;
                case "dq":
                    tree = repository.DisqualificationProcess;
                    break;
                case "banned":
                    tree = repository.BannedAndRestricted;
                    break;
                default:
                    tree = new Tree<TypedText>(new TypedText("ERROR"));
            }
        }
    }

    //Load the question and answers
    public void showList() {
        int index = 0;
        while (index < tree.getChildren().size()) {
            tv_title.setText(tree.getData().getText());
            //Create new TextViews when needed, you never have more TextViews that the maximum number of answers
            if (index >= branchs.size()) {
                addTextView(index);
            }
            branchs.get(index).setText(tree.getChild(index).getData().getText());
            //There is duplicate code in the switch, but it's also clearer this way
            switch (tree.getChild(index).getData().getType()) {
                case TypedText.NORMAL:
                    branchs.get(index).setTypeface(null, Typeface.NORMAL);
                    branchs.get(index).setTextColor(ContextCompat.getColor(this, R.color.colorText));
                    branchs.get(index).setPaintFlags(branchs.get(index).getPaintFlags() & (~ Paint.UNDERLINE_TEXT_FLAG));
                    branchs.get(index).setMovementMethod(null);
                    if(tree.getChild(index).isLeaf()) {
                        branchs.get(index).setBackgroundColor(getResources().getColor(R.color.colorTransparent));
                    }
                    else {
                        branchs.get(index).setBackground(getResources().getDrawable(R.drawable.answer_background_parent));
                    }
                    branchs.get(index).setVisibility(View.VISIBLE);
                    break;
                case TypedText.TITLE:
                    branchs.get(index).setTypeface(null, Typeface.NORMAL);
                    branchs.get(index).setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
                    branchs.get(index).setPaintFlags(branchs.get(index).getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                    branchs.get(index).setMovementMethod(null);
                    if(tree.getChild(index).isLeaf()) {
                        branchs.get(index).setBackgroundColor(getResources().getColor(R.color.colorTransparent));
                    }
                    else {
                        branchs.get(index).setBackground(getResources().getDrawable(R.drawable.answer_background_parent));
                    }
                    branchs.get(index).setVisibility(View.VISIBLE);
                    break;
                case TypedText.EXAMPLE:
                    branchs.get(index).setTypeface(null, Typeface.ITALIC);
                    branchs.get(index).setTextColor(ContextCompat.getColor(this, R.color.colorText));
                    branchs.get(index).setPaintFlags(branchs.get(index).getPaintFlags() & (~ Paint.UNDERLINE_TEXT_FLAG));
                    branchs.get(index).setMovementMethod(null);
                    if(tree.getChild(index).isLeaf()) {
                        branchs.get(index).setBackgroundColor(getResources().getColor(R.color.colorTransparent));
                    }
                    else {
                        branchs.get(index).setBackground(getResources().getDrawable(R.drawable.answer_background_parent));
                    }
                    branchs.get(index).setVisibility(View.VISIBLE);
                    break;
                case TypedText.ANNOTATION:
                    branchs.get(index).setTypeface(null, Typeface.NORMAL);
                    branchs.get(index).setTextColor(ContextCompat.getColor(this, R.color.colorText));
                    branchs.get(index).setPaintFlags(branchs.get(index).getPaintFlags() & (~ Paint.UNDERLINE_TEXT_FLAG));
                    branchs.get(index).setMovementMethod(null);
                    if(showNotes) {
                        branchs.get(index).setBackground(getResources().getDrawable(R.drawable.answer_background_annotation));
                        branchs.get(index).setVisibility(View.VISIBLE);
                    }
                    else {
                        branchs.get(index).setVisibility(View.GONE);
                    }
                    break;
                case TypedText.LINK:
                    branchs.get(index).setTypeface(null, Typeface.NORMAL);
                    branchs.get(index).setTextColor(ContextCompat.getColor(this, R.color.colorLink));
                    branchs.get(index).setPaintFlags(branchs.get(index).getPaintFlags() & (~ Paint.UNDERLINE_TEXT_FLAG));
                    branchs.get(index).setMovementMethod(LinkMovementMethod.getInstance());
                    if(tree.getChild(index).isLeaf()) {
                        branchs.get(index).setBackgroundColor(getResources().getColor(R.color.colorTransparent));
                    }
                    else {
                        branchs.get(index).setBackground(getResources().getDrawable(R.drawable.answer_background_parent));
                    }
                    branchs.get(index).setVisibility(View.VISIBLE);
                    break;
            }
            index++;
        }
        //Hide void TextViews
        while (index < branchs.size()) {
            branchs.get(index).setVisibility(View.GONE);
            index++;
        }
    }

    //Create a TextView
    public void addTextView(final int index) {
        TextView textView = new TextView(this);
        textView.setLayoutParams(layoutParams);
        textView.setTextSize(TEXT_SIZE);
        textView.setPadding(8, 8, 8, 8);

        branchs.add(textView);
        //Clicking an answer moves you to a web page or the next question if there is anyone
        branchs.get(index).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tree.existChild(index)) {
                    if(tree.getChild(index).getData().getType() == TypedText.LINK) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                        browserIntent.setData(Uri.parse(((TextView)view).getText().toString()));
                        startActivity(browserIntent);
                    }
                    else if (!tree.getChild(index).isLeaf()) {
                        tree = tree.getChild(index);
                        showList();
                    }
                }
            }
        });
        ll_points.addView(textView);
    }

    //Arrows disapear when you full scroll to let you read the first/last question
    public void showOrHideArrows() {
        if(scroll_points.canScrollVertically(-1)) {
            imv_arrow_up.setVisibility(View.VISIBLE);
        }
        else {
            imv_arrow_up.setVisibility(View.INVISIBLE);
        }
        if(scroll_points.canScrollVertically(1)) {
            imv_arrow_down.setVisibility(View.VISIBLE);
        }
        else {
            imv_arrow_down.setVisibility(View.INVISIBLE);
        }
    }

    //In older versions you can't hide an arrow after scrolling, so you always show both arrows
    public void showOrHideArrowsOldDevices() {
        //You really don't need the "canScrollVertically(-1)"
        if(scroll_points.canScrollVertically(1)) {
            imv_arrow_up.setVisibility(View.VISIBLE);
            imv_arrow_down.setVisibility(View.VISIBLE);
        }
        else {
            imv_arrow_up.setVisibility(View.INVISIBLE);
            imv_arrow_down.setVisibility(View.INVISIBLE);
        }
    }
}