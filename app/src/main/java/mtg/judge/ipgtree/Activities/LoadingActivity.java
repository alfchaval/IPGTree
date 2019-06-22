package mtg.judge.ipgtree.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import mtg.judge.ipgtree.R;
import mtg.judge.ipgtree.Repository;

public class LoadingActivity extends AppCompatActivity {

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        new Thread(new Runnable() {
            public void run() {
                Repository.createRepository(getApplicationContext());
                intent = new Intent(LoadingActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }).start();
    }
}