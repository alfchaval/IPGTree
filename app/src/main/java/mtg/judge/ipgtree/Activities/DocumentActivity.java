package mtg.judge.ipgtree.Activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import mtg.judge.ipgtree.R;

import mtg.judge.ipgtree.Utilities.Read;
import mtg.judge.ipgtree.Utilities.Repository;
import mtg.judge.ipgtree.POJO.Tree;
import mtg.judge.ipgtree.POJO.TypedText;

import java.util.ArrayList;

public class DocumentActivity extends AppCompatActivity {

    private Tree<TypedText> tree;

    private TextView tv_title;
    private LinearLayout ll_points;
    private ScrollView scroll_points;
    private ImageView imv_arrow_up;
    private ImageView imv_arrow_down;
    private EditText edt_search;
    private Button btn_search;

    private ViewTreeObserver viewTreeObserver;

    private ArrayList<TextView> branchs = new ArrayList<TextView>();

    private LinearLayout.LayoutParams layoutParams;
    private final int TEXT_SIZE = 20;

    private boolean showNotes = true;
    private boolean searching = false;

    private static final String KEY_SERIALIZED_TREE = "key_serialized_tree";

    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_SERIALIZED_TREE, tree);
    }

    //When you press back you go to the previous point (if there is previous point)
    @Override
    public void onBackPressed() {
        if(searching) {
            searching = false;
            edt_search.setText("");
            tv_title.setVisibility(View.VISIBLE);
            showList();
        }
        else if(tree.isRoot()) {
            super.onBackPressed();
        }
        else {
            tree = tree.getParent();
            showList();
        }
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            super.onBackPressed();
            return true;
        }
        return super.onKeyLongPress(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);

        if(!Repository.repositoryLoaded) {
            Repository.createRepository(getApplicationContext());
        }

        linkViews();
        loadStrings();
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
        showNotes = Repository.showAnnotations;
        loadPoints(savedInstanceState);
        showList();

    }

    private void linkViews() {
        ll_points = findViewById(R.id.ll_points);
        scroll_points = findViewById(R.id.scroll_answers);
        tv_title = findViewById(R.id.tv_question);
        imv_arrow_up = findViewById(R.id.imv_arrow_up);;
        imv_arrow_down = findViewById(R.id.imv_arrow_down);
        edt_search = findViewById(R.id.edt_search);
        btn_search = findViewById(R.id.btn_search);
    }

    private void loadStrings() {
        btn_search.setText(Repository.StringMap(68));
    }

    private void setListeners() {
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
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word = edt_search.getText().toString();
                if(word.length() > 2) {
                    search(word);
                }
                else {
                    searching = false;
                    tv_title.setVisibility(View.VISIBLE);
                    showList();
                }
            }
        });
    }

    private void loadPoints(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            tree = (Tree<TypedText>) savedInstanceState.getSerializable(KEY_SERIALIZED_TREE);
        }
        else {
            String document = getIntent().getStringExtra("document");
            tree = Read.readXMLDocument(document + "_" + Repository.language + ".xml");
            if(document.equals("cr")) {
                edt_search.setVisibility(View.VISIBLE);
                btn_search.setVisibility(View.VISIBLE);
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
            format(index, tree.getChild(index).getData().getType());
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
                if(!searching && tree.existChild(index)) {
                    if(tree.getChild(index).getData().getType() == TypedText.LINK) {
                        ((TextView)view).setMovementMethod(LinkMovementMethod.getInstance());
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                        browserIntent.setData(Uri.parse(((TextView)view).getText().toString()));
                        startActivity(browserIntent);
                    }
                    else if (!tree.getChild(index).isLeaf()) {
                        ((TextView)view).setMovementMethod(null);
                        tree = tree.getChild(index);
                        showList();
                    }
                }
            }
        });
        branchs.get(index).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(tree.existChild(index) && tree.getChild(index).isLeaf()) {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText(Repository.FOLDERNAME, tree.getChild(index).getData().getText());
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(DocumentActivity.this, Repository.StringMap(78), Toast.LENGTH_LONG).show();
                }
                return false;
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

    private void search(String word) {
        searching = true;
        tv_title.setVisibility(View.GONE);
        tree = tree.getRoot();
        ArrayList<TypedText> results = treeSearch(word);
        if(results.size() == 0) {
            results.add(new TypedText(Repository.StringMap(69)));
        }
        int index = 0;
        while (index < results.size()) {
            if (index >= branchs.size()) {
                addTextView(index);
            }
            branchs.get(index).setText(results.get(index).getText());
            format(index, results.get(index).getType());
            index++;
        }
        while (index < branchs.size()) {
            branchs.get(index).setVisibility(View.GONE);
            index++;
        }
    }

    private ArrayList<TypedText> treeSearch(String word) {
        return treeSearch(word.toLowerCase(), new ArrayList<TypedText>());
    }

    private ArrayList<TypedText> treeSearch(String word, ArrayList<TypedText> list) {
        ArrayList<TypedText> results = list;
        int index = 0;
        while (index < tree.getChildren().size()) {
            tree = tree.getChild(index);
            if(tree.getData().getText().toLowerCase().contains(word)) {
                results.add(tree.getData());
            }
            results = treeSearch(word, results);
            tree = tree.getParent();
            index++;

        }
        return results;
    }

    private void format(int index, int type) {
        //There is duplicate code in the switch, but it's also clearer this way
        switch (type) {
            case TypedText.NORMAL:
                branchs.get(index).setTypeface(null, Typeface.NORMAL);
                branchs.get(index).setTextColor(ContextCompat.getColor(this, R.color.colorText));
                branchs.get(index).setPaintFlags(branchs.get(index).getPaintFlags() & (~ Paint.UNDERLINE_TEXT_FLAG));
                if(searching || tree.getChild(index).isLeaf()) {
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
                if(searching || tree.getChild(index).isLeaf()) {
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
                if(searching || tree.getChild(index).isLeaf()) {
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
                if(searching || tree.getChild(index).isLeaf()) {
                    branchs.get(index).setBackgroundColor(getResources().getColor(R.color.colorTransparent));
                }
                else {
                    branchs.get(index).setBackground(getResources().getDrawable(R.drawable.answer_background_parent));
                }
                branchs.get(index).setVisibility(View.VISIBLE);
                break;
        }
    }
}