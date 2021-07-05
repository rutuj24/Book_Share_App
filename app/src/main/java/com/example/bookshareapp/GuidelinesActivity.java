package com.example.bookshareapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class GuidelinesActivity extends AppCompatActivity {

    TextView guide;
    Button back;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_guidelines);

        guide = findViewById(R.id.guide);
        back = findViewById(R.id.back);

        guide.setText("1. Welcome to BookShare App, this is platform for you to share books with other people on the application" +
                "\n\n\n2.First step is to register " +
                "\n\n\n3. You need to verify you email before you could interact with the other users or change your own profile" +
                "\n\n\n4.Do not harass other people on the app \n\n\n");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
            }
        });

    }
}