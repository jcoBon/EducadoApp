package com.example.educado;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class ViewClassDetails extends AppCompatActivity {

    TextView code, title, year,section;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_view_class_details);

        code = findViewById(R.id.ClassCode);
        title = findViewById(R.id.ClassTitle);
        year = findViewById(R.id.ClassYear);
        section = findViewById(R.id.ClassSection);

        String cod = getIntent().getStringExtra("Code");
        String tit = getIntent().getStringExtra("Title");
        String yer = getIntent().getStringExtra("Year");
        String sec = getIntent().getStringExtra("Section");

        code.setText(cod);
        title.setText(tit);
        year.setText(yer);
        section.setText(sec);
    }
}