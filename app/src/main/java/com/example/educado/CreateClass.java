package com.example.educado;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreateClass extends AppCompatActivity {

    EditText subCode,subTitle;
    Spinner YearSpinner,secSpinner;
    Button addClassroom;

    FirebaseDatabase UsersDatabase;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_create_class);

        subCode = findViewById(R.id.SubjectCode);
        subTitle = findViewById(R.id.SubjectTitle);

        addClassroom = findViewById(R.id.AddClassroom);

        YearSpinner = (Spinner) findViewById(R.id.YearClassroomSpinner);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(CreateClass.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.year));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        YearSpinner.setAdapter(myAdapter);

        secSpinner = (Spinner) findViewById(R.id.SectionClassroomSpinner);

        ArrayAdapter<String> sec = new ArrayAdapter<String>(CreateClass.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.section));
        sec.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        secSpinner.setAdapter(sec);



        addClassroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty((subTitle.getText().toString()))){
                    Toast.makeText(CreateClass.this, "Please Enter Subject Title", Toast.LENGTH_SHORT).show();
                }

                else if(TextUtils.isEmpty((subCode.getText().toString()))){
                    Toast.makeText(CreateClass.this, "Please Enter Subject Code", Toast.LENGTH_SHORT).show();
                }


                else{
                    String subYear = YearSpinner. getSelectedItem().toString();
                    String subSec = secSpinner. getSelectedItem().toString();
                    final DatabaseReference mref = FirebaseDatabase.getInstance().getReference().child("Classrooms").child(subYear).child(subSec);

                    mref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String searchUser = subTitle.getText().toString();


                            if(dataSnapshot.child(searchUser).exists()){
                                Toast.makeText(CreateClass.this,"SUBJECT ALREADY ADDED REGISTERED",Toast.LENGTH_SHORT).show();
                            }

                            else{

                                UsersDatabase = FirebaseDatabase.getInstance();
                                reference = UsersDatabase.getReference("Classrooms").child(subYear).child(subSec);

                                String subjectCode = subCode.getText().toString();
                                String subjectTitle = subTitle.getText().toString();

                                String subjectYear =  YearSpinner. getSelectedItem().toString();
                                String subjectSection = secSpinner. getSelectedItem().toString();



                                classHelper classHelper = new classHelper(subjectCode, subjectTitle,subjectYear,subjectSection);

                                reference.child(subjectCode).setValue(classHelper);

                                Toast.makeText(CreateClass.this,"Classroom Added",Toast.LENGTH_LONG).show();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });
    }
}