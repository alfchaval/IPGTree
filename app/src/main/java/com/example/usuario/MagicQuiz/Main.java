package com.example.usuario.MagicQuiz;

import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

public class Main extends AppCompatActivity {

    Tree<Quiz> questionTree;

    TextView tv_question;
    LinearLayout ll_answers;
    ScrollView scroll_answers;
    ImageView imv_arrow_up;
    ImageView imv_arrow_down;

    ViewTreeObserver viewTreeObserver;

    ArrayList<TextView> answerTVs = new ArrayList<TextView>();

    LinearLayout.LayoutParams layoutParams;

    int TEXT_SIZE = 20;
    boolean back = false;

    private static final String KEY_SERIALIZED_TREE = "key_serialized_tree";

    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_SERIALIZED_TREE, questionTree);
    }

    //When you press back you go to the previous question (if there is previous question)
    @Override
    public void onBackPressed() {
        if(questionTree.isRoot()) {
            super.onBackPressed();
        }
        else {
            questionTree.getData().setNoChosenAnswer();
            questionTree = questionTree.getParent();
            //I need this variable for OnGlobalLayoutListener, that will do additional things after everything
            back = true;
            showQuiz();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipg_tree);

        ll_answers = findViewById(R.id.ll_answers);
        tv_question = findViewById(R.id.tv_question);
        tv_question.setTextSize(TEXT_SIZE);

        scroll_answers = findViewById(R.id.scroll_answers);
        viewTreeObserver = scroll_answers.getViewTreeObserver();

        imv_arrow_up = findViewById(R.id.imv_arrow_up);
        imv_arrow_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scroll_answers.fullScroll(View.FOCUS_UP);
            }
        });
        imv_arrow_down = findViewById(R.id.imv_arrow_down);
        imv_arrow_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scroll_answers.fullScroll(View.FOCUS_DOWN);
            }
        });

        layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,  LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 4, 0, 4);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //Arrows disapear when you full scroll to let you read the first/last question
            scroll_answers.setOnScrollChangeListener(new View.OnScrollChangeListener() {
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
                    if(back) {
                        moveToChosenAnswer();
                        back = false;
                    }
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
                    if(back) {
                        moveToChosenAnswer();
                        back = false;
                    }
                }
            });
        }

        loadQuiz(savedInstanceState);
        showQuiz();
    }

    //You only have to read the XML the first time you start the app
    private void loadQuiz(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            questionTree = (Tree<Quiz>) savedInstanceState.getSerializable(KEY_SERIALIZED_TREE);
        }
        else {
            questionTree = Read.readXMLQuiz(this.getResources().getXml(R.xml.ipg_tree));
        }
    }

    //Load the question and answers
    public void showQuiz() {
        int index = 0;
        //If there is no more questions, that question is really the solution to the problem
        if(questionTree.isLeaf()) {
            tv_question.setText(R.string.solution);
            //Most time you have already a TextView, but you need this check in case you change the orientation while you are in a solution
            if (answerTVs.size() < 1) {
                addTextView(index);
            }
            answerTVs.get(index).setText(questionTree.getData().getQuestion());
            answerTVs.get(index).setVisibility(View.VISIBLE);
            index++;
        }
        else {
            //Show the question
            tv_question.setText(questionTree.getData().getQuestion());
            //Show the answers
            while (index < questionTree.getData().getAnswers().size()) {
                //Create new TextViews when needed, you never have more TextViews that the maximum number of answers
                if (index >= answerTVs.size()) {
                    addTextView(index);
                }
                //When I go back I want to see what answer I chosed last time
                if (questionTree.getData().isAnswered() && questionTree.getData().getChosenAnswerPosition() == index) {
                    answerTVs.get(index).setBackground(getResources().getDrawable(R.drawable.answer_background_selected));
                } else {
                    answerTVs.get(index).setBackground(getResources().getDrawable(R.drawable.answer_background));
                }
                //Show questions
                answerTVs.get(index).setText(questionTree.getData().getAnswers().get(index));
                answerTVs.get(index).setVisibility(View.VISIBLE);
                index++;
            }
        }
        //Hide void TextViews
        while (index < answerTVs.size()) {
            answerTVs.get(index).setVisibility(View.GONE);
            index++;
        }
    }

    //Create a TextView
    public void addTextView(final int index) {
        TextView textView = new TextView(this);
        textView.setLayoutParams(layoutParams);
        textView.setTextSize(TEXT_SIZE);
        textView.setTextColor(ContextCompat.getColor(this, R.color.colorText));
        textView.setPadding(8, 8, 8, 8);

        answerTVs.add(textView);
        //Clicking an answer moves you to the next question if there is anyone
        answerTVs.get(index).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(questionTree.existChild(index)) {
                    questionTree.getData().setChosenAnswerPosition(index);
                    questionTree = questionTree.getChild(index);
                    showQuiz();
                }
            }
        });
        ll_answers.addView(textView);
    }

    //Arrows disapear when you full scroll to let you read the first/last question
    public void showOrHideArrows() {
        if(scroll_answers.canScrollVertically(-1)) {
            imv_arrow_up.setVisibility(View.VISIBLE);
        }
        else {
            imv_arrow_up.setVisibility(View.INVISIBLE);
        }
        if(scroll_answers.canScrollVertically(1)) {
            imv_arrow_down.setVisibility(View.VISIBLE);
        }
        else {
            imv_arrow_down.setVisibility(View.INVISIBLE);
        }
    }

    //In older versions you can't hide an arrow after scrolling, so you always show both arrows
    public void showOrHideArrowsOldDevices() {
        //You really don't need the "canScrollVertically(-1)"
        if(scroll_answers.canScrollVertically(1)) {
            imv_arrow_up.setVisibility(View.VISIBLE);
            imv_arrow_down.setVisibility(View.VISIBLE);
        }
        else {
            imv_arrow_up.setVisibility(View.INVISIBLE);
            imv_arrow_down.setVisibility(View.INVISIBLE);
        }
    }

    //Scroll until the chosen answer is in the middle of the ScrollView
    public void moveToChosenAnswer() {
        scroll_answers.smoothScrollTo(0, (answerTVs.get(questionTree.getData().getChosenAnswerPosition()).getTop() + answerTVs.get(questionTree.getData().getChosenAnswerPosition()).getBottom()) / 2 - scroll_answers.getHeight() / 2);
    }
}