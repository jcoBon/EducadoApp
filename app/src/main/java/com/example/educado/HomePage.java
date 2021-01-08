package com.example.educado;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomePage extends AppCompatActivity {

    Button display, join;

    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        display = findViewById(R.id.Display);
        join = findViewById(R.id.join);

        user = getIntent().getStringExtra("User");

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, Choice.class);

                intent.putExtra("User", user);

                startActivity(intent);
            }
        });

        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, Choice.class);

                intent.putExtra("User", user);

                startActivity(intent);
            }
        });

    }
}