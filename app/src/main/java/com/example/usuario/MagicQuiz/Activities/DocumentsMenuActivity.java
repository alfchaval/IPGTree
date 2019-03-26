package com.example.usuario.MagicQuiz.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.usuario.MagicQuiz.R;

public class DocumentsMenuActivity extends AppCompatActivity {

    Button btn_cr, btn_jar, btn_aipg, btn_amtr, btn_dq, btn_banned;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documents_menu);

        btn_cr = findViewById(R.id.btn_cr);
        btn_cr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(DocumentsMenuActivity.this, DocumentActivity.class);
                intent.putExtra("document", "cr");
                startActivity(intent);
            }
        });

        btn_jar = findViewById(R.id.btn_jar);
        btn_jar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(DocumentsMenuActivity.this, DocumentActivity.class);
                intent.putExtra("document", "jar");
                startActivity(intent);
            }
        });

        btn_aipg = findViewById(R.id.btn_aipg);
        btn_aipg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(DocumentsMenuActivity.this, DocumentActivity.class);
                intent.putExtra("document", "aipg");
                startActivity(intent);
            }
        });

        btn_amtr = findViewById(R.id.btn_amtr);
        btn_amtr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(DocumentsMenuActivity.this, DocumentActivity.class);
                intent.putExtra("document", "amtr");
                startActivity(intent);
            }
        });

        btn_dq = findViewById(R.id.btn_dq);
        btn_dq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(DocumentsMenuActivity.this, DocumentActivity.class);
                intent.putExtra("document", "dq");
                startActivity(intent);
            }
        });

        btn_banned = findViewById(R.id.btn_banned);
        btn_banned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(DocumentsMenuActivity.this, DocumentActivity.class);
                intent.putExtra("document", "banned");
                startActivity(intent);
            }
        });
    }
}
