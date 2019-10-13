package mtg.judge.ipgtree.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import mtg.judge.ipgtree.R;

public class DocumentsMenuActivity extends AppCompatActivity {

    private Button btn_cr, btn_jar, btn_aipg, btn_amtr, btn_dq, btn_banned, btn_links;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documents_menu);

        linkViews();
        setListeners();
    }

    public void linkViews() {
        btn_cr = findViewById(R.id.btn_cr);
        btn_jar = findViewById(R.id.btn_jar);
        btn_aipg = findViewById(R.id.btn_aipg);
        btn_amtr = findViewById(R.id.btn_amtr);
        btn_dq = findViewById(R.id.btn_dq);
        btn_banned = findViewById(R.id.btn_banned);
        btn_links = findViewById(R.id.btn_links);
    }

    public void setListeners() {
        btn_cr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(DocumentsMenuActivity.this, DocumentActivity.class);
                intent.putExtra("document", "cr");
                startActivityForResult(intent,1);
            }
        });
        btn_jar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(DocumentsMenuActivity.this, DocumentActivity.class);
                intent.putExtra("document", "jar");
                startActivityForResult(intent,1);
            }
        });
        btn_aipg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(DocumentsMenuActivity.this, DocumentActivity.class);
                intent.putExtra("document", "aipg");
                startActivityForResult(intent,1);
            }
        });
        btn_amtr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(DocumentsMenuActivity.this, DocumentActivity.class);
                intent.putExtra("document", "amtr");
                startActivityForResult(intent,1);
            }
        });
        btn_dq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(DocumentsMenuActivity.this, DocumentActivity.class);
                intent.putExtra("document", "dq");
                startActivityForResult(intent,1);
            }
        });
        btn_banned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(DocumentsMenuActivity.this, DocumentActivity.class);
                intent.putExtra("document", "banned");
                startActivityForResult(intent,1);
            }
        });
        btn_links.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(DocumentsMenuActivity.this, DocumentActivity.class);
                intent.putExtra("document", "links");
                startActivityForResult(intent,1);
            }
        });
    }

    public void enableButtons(Boolean state) {
        btn_cr.setEnabled(state);
        btn_jar.setEnabled(state);
        btn_aipg.setEnabled(state);
        btn_amtr.setEnabled(state);
        btn_dq.setEnabled(state);
        btn_banned.setEnabled(state);
        btn_links.setEnabled(state);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        enableButtons(true);
    }
}
