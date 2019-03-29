package com.example.usuario.MagicQuiz.Activities;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.usuario.MagicQuiz.Quiz;
import com.example.usuario.MagicQuiz.R;
import com.example.usuario.MagicQuiz.Read;

import java.util.ArrayList;
import java.util.Collections;

public class QuizActivity extends AppCompatActivity {

    ArrayList<Quiz> questions;

    TextView tv_question;
    TextView txv_question_number;
    LinearLayout ll_answers;
    ScrollView scroll_answers;
    ImageView imv_arrow_up;
    ImageView imv_arrow_down;
    ImageView imv_arrow_left;
    ImageView imv_arrow_right;

    ViewTreeObserver viewTreeObserver;

    ArrayList<TextView> answerTVs = new ArrayList<TextView>();

    LinearLayout.LayoutParams layoutParams;

    final int TEXT_SIZE = 20;

    boolean finished = false;
    int questionNumber = 0;

    private static final String KEY_SERIALIZED_QUIZ = "key_serialized_quiz";
    private static final String KEY_FINISHED = "key_finished";
    private static final String KEY_QUESTION_NUMBER = "key_question_number";

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

        ll_answers = findViewById(R.id.ll_points);
        tv_question = findViewById(R.id.tv_question);
        tv_question.setTextSize(TEXT_SIZE);
        txv_question_number = findViewById(R.id.txv_question_number);

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

        imv_arrow_left = findViewById(R.id.imv_arrow_left);
        imv_arrow_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionNumber--;
                showQuiz();
            }
        });
        imv_arrow_right = findViewById(R.id.imv_arrow_right);
        imv_arrow_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionNumber++;
                showQuiz();
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

    //You only have to read the XML the first time you start the app
    private void loadQuiz(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            questions = (ArrayList<Quiz>) savedInstanceState.getSerializable(KEY_SERIALIZED_QUIZ);
            finished = savedInstanceState.getBoolean(KEY_FINISHED);
            questionNumber = savedInstanceState.getInt(KEY_QUESTION_NUMBER);
        }
        else {
            questions = Read.readXMLQuiz(this.getResources().getXml(R.xml.quiz));
            shuffleQuestionsAndAnswers();
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
        if(questionNumber < questions.size()) {
            if(questionNumber == 0) {
                imv_arrow_left.setVisibility(View.INVISIBLE);
            }
            else {
                imv_arrow_left.setVisibility(View.VISIBLE);
            }
            imv_arrow_right.setVisibility(View.VISIBLE);
            //Show the question
            tv_question.setText(questions.get(questionNumber).getQuestion());
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
            txv_question_number.setText((questionNumber + 1) + " / " + questions.size() + "\n\n");
        }
        else {
            imv_arrow_right.setVisibility(View.INVISIBLE);
            txv_question_number.setText("");
            if (finished) {
                tv_question.setText("Tu puntuación total es: " + totalPoints() + "\n\nPuedes ver las respuestas correctas desplazándote por las preguntas con las flechas");
            }
            else {
                tv_question.setText("No hay más preguntas, pulsa en finalizar para comprobar tus resultados... o puedes usar la flecha para volver y repasar tus respuestas");
                if (answerTVs.size() < 1) {
                    addTextView(index);
                }
                answerTVs.get(index).setVisibility(View.VISIBLE);
                answerTVs.get(index).setText("FINALIZAR");
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
                    if(questionNumber < questions.size()) {
                        questions.get(questionNumber).setChosenAnswerPosition(index);
                        for(int i = 0; i < questions.get(questionNumber).getAnswers().size(); i++) {
                            if(i == index) {
                                answerTVs.get(i).setBackground(getResources().getDrawable(R.drawable.answer_background_selected));
                            }
                            else {
                                answerTVs.get(i).setBackground(getResources().getDrawable(R.drawable.answer_background_parent));
                            }
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
        if(questionNumber < questions.size() && questions.get(questionNumber).isAnswered()) {
            scroll_answers.smoothScrollTo(0, (answerTVs.get(questions.get(questionNumber).getChosenAnswerPosition()).getTop() + answerTVs.get(questions.get(questionNumber).getChosenAnswerPosition()).getBottom()) / 2 - scroll_answers.getHeight() / 2);
        }
    }

    public float totalPoints() {
        int points = 0;
        for (Quiz quiz: questions) {
            if(quiz.getChosenAnswerPosition() != -1) {
                if(quiz.getCorrectAnswerPosition() == quiz.getChosenAnswerPosition()) {
                    points += 4;
                }
                else {
                    points--;
                }
            }
        }
        return (float)(100*points)/(4*questions.size());
    }
}