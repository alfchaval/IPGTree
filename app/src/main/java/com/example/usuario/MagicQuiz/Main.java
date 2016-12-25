package com.example.usuario.MagicQuiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class Main extends AppCompatActivity {

    TextView tv_question;
    Tree<Quiz> questionTree;
    Tree<Quiz> actualNode;
    ArrayList<TextView> answerTVs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipg_tree);

        tv_question = (TextView)findViewById(R.id.tv_question);
        answerTVs = new ArrayList<TextView>();
        answerTVs.add((TextView)findViewById(R.id.tv_answer1));
        answerTVs.add((TextView)findViewById(R.id.tv_answer2));
        answerTVs.add((TextView)findViewById(R.id.tv_answer3));
        answerTVs.add((TextView)findViewById(R.id.tv_answer4));
        answerTVs.add((TextView)findViewById(R.id.tv_answer5));
        answerTVs.add((TextView)findViewById(R.id.tv_answer6));
        answerTVs.add((TextView)findViewById(R.id.tv_answer7));
        answerTVs.add((TextView)findViewById(R.id.tv_answer8));

        for(int i = 0; i < answerTVs.size(); i++) {
            final int position = i;
            answerTVs.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    actualNode.getData().setChosedAnswerPosition(position);
                    actualNode = actualNode.getChild(position);
                    loadQuiz();
                }
            });
        }

        questionTree = Read.readXMLQuiz(this.getResources().getXml(R.xml.ipg_tree));
        actualNode = questionTree.getRoot();
        loadQuiz();
    }

    @Override
    public void onBackPressed() {
        if(actualNode.isRoot()) super.onBackPressed();
        else {
            actualNode.getData().setNoChosedAnswer();
            actualNode = actualNode.getParent();
            loadQuiz();
        }
    }

    public void loadQuiz() {
        tv_question.setText(actualNode.getData().getQuestion());
        for (TextView tv : answerTVs) {
            tv.setVisibility(View.INVISIBLE);

        }
        for(int i = 0; i < actualNode.getData().getAnswers().size(); i++) {
            answerTVs.get(i).setVisibility(View.VISIBLE);
            answerTVs.get(i).setText("- "+actualNode.getData().getAnswers().get(i));
            if(actualNode.getData().isAnswered() && actualNode.getData().getChosedAnswerPosition() == i) {
                answerTVs.get(i).setBackgroundColor(getResources().getColor(R.color.colorSelectedAnswer));
            }
            else {
                answerTVs.get(i).setBackgroundColor(getResources().getColor(R.color.colorBackground));
            }
        }
    }
}
