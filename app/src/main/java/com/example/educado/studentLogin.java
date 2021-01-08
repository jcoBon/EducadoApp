package com.example.educado;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class studentLogin extends AppCompatActivity {

    Button join,view,Joined;

    String user,sec,year;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_student_login);


        view = findViewById(R.id.viewClass);
        join = findViewById(R.id.joinClass);
        Joined = findViewById(R.id.Joined);


        user = getIntent().getStringExtra("User");
        sec = getIntent().getStringExtra("Section");
        year = getIntent().getStringExtra("Year");



        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(studentLogin.this, ViewClassrooms.class);
                startActivity(intent);
            }
        });

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(studentLogin.this, JoinClassroom.class);
                intent.putExtra("User", user);
                startActivity(intent);
            }
        });

        Joined.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(studentLogin.this, JoinedClassesStudent.class);
                intent.putExtra("User", user);
                intent.putExtra("Section", sec);
                intent.putExtra("Year", year);

                startActivity(intent);
            }
        });
    }
}