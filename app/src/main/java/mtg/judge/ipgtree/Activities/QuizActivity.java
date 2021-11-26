package mtg.judge.ipgtree.Activities;

import android.app.AlertDialog;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import mtg.judge.ipgtree.POJO.Quiz;
import mtg.judge.ipgtree.R;

import mtg.judge.ipgtree.Utilities.CustomMovementMethod;
import mtg.judge.ipgtree.Utilities.CustomSpan;
import mtg.judge.ipgtree.Utilities.Read;
import mtg.judge.ipgtree.Utilities.Repository;
import mtg.judge.ipgtree.Utilities.Symbols;
import mtg.judge.ipgtree.Utilities.Translation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuizActivity extends AppCompatActivity {

    private ArrayList<Quiz> questions;

    private TextView tv_question;
    private TextView txv_question_number;
    private LinearLayout ll_answers;
    private ScrollView scroll_answers;
    private ImageView imv_arrow_up;
    private ImageView imv_arrow_down;
    private ImageView imv_arrow_left;
    private ImageView imv_arrow_right;

    private ViewTreeObserver viewTreeObserver;

    private ArrayList<TextView> answerTVs = new ArrayList<TextView>();

    private LinearLayout.LayoutParams layoutParams;

    private final int TEXT_SIZE = 20;

    private boolean finished = false;
    private int questionNumber = 0;
    private int maxNumberOfQuestions = 10;

    private static final String KEY_SERIALIZED_QUIZ = "key_serialized_quiz";
    private static final String KEY_FINISHED = "key_finished";
    private static final String KEY_QUESTION_NUMBER = "key_question_number";

    private static final Pattern CARD_PATTERN = Pattern.compile("\\[CARD\\|(.*?)]");

    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_SERIALIZED_QUIZ, questions);
        outState.putBoolean(KEY_FINISHED, finished);
        outState.putInt(KEY_QUESTION_NUMBER, questionNumber);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        if(!Repository.repositoryLoaded) {
            Repository.createRepository(getApplicationContext());
        }

        linkViews();
        setListeners();

        tv_question.setTextSize(TEXT_SIZE);
        tv_question.setMovementMethod(CustomMovementMethod.getInstance());
        viewTreeObserver = scroll_answers.getViewTreeObserver();

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
                    moveToChosenAnswer();
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
                    moveToChosenAnswer();
                }
            });
        }

        loadQuiz(savedInstanceState);
        showQuiz();
    }

    public void linkViews() {
        ll_answers = findViewById(R.id.ll_points);
        tv_question = findViewById(R.id.tv_question);
        txv_question_number = findViewById(R.id.txv_question_number);
        scroll_answers = findViewById(R.id.scroll_answers);
        imv_arrow_up = findViewById(R.id.imv_arrow_up);
        imv_arrow_down = findViewById(R.id.imv_arrow_down);
        imv_arrow_left = findViewById(R.id.imv_arrow_left);
        imv_arrow_right = findViewById(R.id.imv_arrow_right);
    }

    public void setListeners() {
        imv_arrow_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scroll_answers.fullScroll(View.FOCUS_UP);
            }
        });
        imv_arrow_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scroll_answers.fullScroll(View.FOCUS_DOWN);
            }
        });
        imv_arrow_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionNumber--;
                showQuiz();
            }
        });
        imv_arrow_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionNumber++;
                showQuiz();
            }
        });
    }

    //You only have to read the XML the first time you start the app
    private void loadQuiz(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            questions = (ArrayList<Quiz>) savedInstanceState.getSerializable(KEY_SERIALIZED_QUIZ);
            finished = savedInstanceState.getBoolean(KEY_FINISHED);
            questionNumber = savedInstanceState.getInt(KEY_QUESTION_NUMBER);
        }
        else {
            questions = Read.readXMLQuiz("quiz_" + Repository.language + ".xml");
            if(questions == null && !Repository.language.equals("en"))
            {
                questions = Read.readXMLQuiz("quiz_en.xml");
            }
            if(questions == null)
            {
                questions = new ArrayList<Quiz>(Arrays.asList(new Quiz("Error", new ArrayList<String>(Arrays.asList(Translation.StringMap(73),"","","")))));
                questions.get(0).setCorrectAnswerPosition(0);
            }
            shuffleQuestionsAndAnswers();
        }
        if (questions.size() < maxNumberOfQuestions) {
            maxNumberOfQuestions = questions.size();
        }
    }

    //The name explains the method
    public void shuffleQuestionsAndAnswers() {
        Collections.shuffle(questions);
        String auxAnswer;
        for(int i = 0; i < questions.size(); i++) {
            auxAnswer = questions.get(i).getAnswer(questions.get(i).getCorrectAnswerPosition());
            Collections.shuffle(questions.get(i).getAnswers());
            questions.get(i).setCorrectAnswerPosition(questions.get(i).getAnswers().indexOf(auxAnswer));
        }
    }

    //Load the question and answers
    public void showQuiz() {
        int index = 0;
        if(questionNumber < maxNumberOfQuestions) {
            if(questionNumber == 0) {
                imv_arrow_left.setVisibility(View.INVISIBLE);
            }
            else {
                imv_arrow_left.setVisibility(View.VISIBLE);
            }
            imv_arrow_right.setVisibility(View.VISIBLE);
            //Show the question
            tv_question.setText(SpannableWithCards(questions.get(questionNumber).getQuestion()));
            //Show the answers
            while (index < questions.get(questionNumber).getAnswers().size()) {
                //Create new TextViews when needed, you never have more TextViews that the maximum number of answers
                if (index >= answerTVs.size()) {
                    addTextView(index);
                    answerTVs.get(index).setBackground(getResources().getDrawable(R.drawable.answer_background_parent));
                }
                if(finished) {
                    if (questions.get(questionNumber).getChosenAnswerPosition() == index) {
                        if (questions.get(questionNumber).getChosenAnswerPosition() == questions.get(questionNumber).getCorrectAnswerPosition()) {
                            answerTVs.get(index).setBackground(getResources().getDrawable(R.drawable.answer_background_correct));
                        } else {
                            answerTVs.get(index).setBackground(getResources().getDrawable(R.drawable.answer_background_wrong));
                        }
                    }
                    else if(questions.get(questionNumber).getCorrectAnswerPosition() == index) {
                        answerTVs.get(index).setBackground(getResources().getDrawable(R.drawable.answer_background_correct));
                    }
                    else {
                        answerTVs.get(index).setBackground(getResources().getDrawable(R.drawable.answer_background_parent));
                    }
                }
                else if(questions.get(questionNumber).getChosenAnswerPosition() == index) {
                    answerTVs.get(index).setBackground(getResources().getDrawable(R.drawable.answer_background_selected));
                }
                else {
                    answerTVs.get(index).setBackground(getResources().getDrawable(R.drawable.answer_background_parent));
                }
                answerTVs.get(index).setText(questions.get(questionNumber).getAnswers().get(index));
                answerTVs.get(index).setVisibility(View.VISIBLE);
                index++;
            }
            txv_question_number.setText((questionNumber + 1) + " / " + maxNumberOfQuestions + "\n\n");
        }
        else {
            imv_arrow_right.setVisibility(View.INVISIBLE);
            imv_arrow_left.setVisibility(View.VISIBLE);
            txv_question_number.setText("");
            if (finished) {
                tv_question.setText(Translation.StringMap(50) + totalPoints() + Translation.StringMap(49));
            }
            else {
                tv_question.setText(Translation.StringMap(48));
                if (answerTVs.size() < 1) {
                    addTextView(index);
                }
                answerTVs.get(index).setVisibility(View.VISIBLE);
                answerTVs.get(index).setText(Translation.StringMap(47));
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
        //Clicking an answer moves you to the next question
        answerTVs.get(index).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!finished) {
                    if(questionNumber < maxNumberOfQuestions) {
                        if(questions.get(questionNumber).getChosenAnswerPosition() == index) {
                            questions.get(questionNumber).setNoChosenAnswer();
                            answerTVs.get(index).setBackground(getResources().getDrawable(R.drawable.answer_background_parent));
                        }
                        else {
                            questions.get(questionNumber).setChosenAnswerPosition(index);
                            for(int i = 0; i < questions.get(questionNumber).getAnswers().size(); i++) {
                                if(i == index) {
                                    answerTVs.get(i).setBackground(getResources().getDrawable(R.drawable.answer_background_selected));
                                }
                                else {
                                    answerTVs.get(i).setBackground(getResources().getDrawable(R.drawable.answer_background_parent));
                                }
                            }
                            questionNumber++;
                            showQuiz();
                        }
                    }
                    else {
                        finished = true;
                        showQuiz();
                    }
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
        if(questionNumber < maxNumberOfQuestions && questions.get(questionNumber).isAnswered()) {
            scroll_answers.smoothScrollTo(0, (answerTVs.get(questions.get(questionNumber).getChosenAnswerPosition()).getTop() + answerTVs.get(questions.get(questionNumber).getChosenAnswerPosition()).getBottom()) / 2 - scroll_answers.getHeight() / 2);
        }
    }

    public float totalPoints() {
        int points = 0;
        for (Quiz quiz: questions) {
            if(quiz.getChosenAnswerPosition() > -1) {
                if(quiz.getCorrectAnswerPosition() == quiz.getChosenAnswerPosition()) {
                    points += 4;
                }
                else {
                    points--;
                }
            }
        }
        return (float)(100*points)/(4* maxNumberOfQuestions);
    }

    public SpannableStringBuilder SpannableWithCards(String text) {
        int index = 0;
        String cardWithTag;
        String cardName;
        SpannableStringBuilder spannableBuilder = new SpannableStringBuilder();
        Matcher cardMatcher = CARD_PATTERN.matcher(text);
        while (cardMatcher.find()) {
            if(index < cardMatcher.start())
            {
                spannableBuilder.append(text.substring(index, cardMatcher.start()));
            }
            cardWithTag = cardMatcher.group();
            cardName = cardWithTag.substring(6, cardWithTag.length() - 1);
            spannableBuilder.append(cardName);
            spannableBuilder.setSpan(new CustomSpan(getResources().getColor(R.color.colorLink), cardName) {
                @Override
                public void onClick(@NonNull View widget) {
                    try{
                        String cardtext = Repository.cards.get(GetRule()).showCard();
                        new AlertDialog.Builder(QuizActivity.this)
                                .setMessage(SpannableWithSymbols(cardtext, 0.5f))
                                .show();
                    } catch (Exception e) {
                    }
                }
            }, spannableBuilder.length() - cardName.length(), spannableBuilder.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            index = cardMatcher.end();
        }
        if(index < text.length()-1)
        {
            spannableBuilder.append(text.substring(index));
        }
        return spannableBuilder;
    }

    public Spannable SpannableWithSymbols(String text, float symbolSize)
    {
        Spannable spannable = new SpannableString(text);
        MatchSymbols(spannable, text, symbolSize);
        return spannable;
    }

    public void MatchSymbols(Spannable spannable, String text, float symbolSize)
    {
        Matcher symbolMatcher = Symbols.SYMBOL_PATTERN.matcher(text);
        while(symbolMatcher.find()) {
            Drawable symbol = Symbols.getSymbol(symbolMatcher.group(), getApplicationContext());
            if (symbol != null) {
                symbol.setBounds(0, 0, (int)(symbol.getIntrinsicWidth()*symbolSize), (int)(symbol.getIntrinsicHeight()*symbolSize));
                ImageSpan span = new ImageSpan(symbol, ImageSpan.ALIGN_BASELINE);
                spannable.setSpan(span, symbolMatcher.start(), symbolMatcher.end(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            }
        }
    }
}