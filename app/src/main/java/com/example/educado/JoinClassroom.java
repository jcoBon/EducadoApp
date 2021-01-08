package com.example.educado;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class JoinClassroom extends AppCompatActivity {

    EditText classCode;
    Button join;

    String user;

    FirebaseDatabase UsersDatabase;
    DatabaseReference reference,Joined;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_join_classroom);

        classCode = findViewById(R.id.enterClassCode);
        join = findViewById(R.id.JoinBtn);

        user = getIntent().getStringExtra("User");


        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty((classCode.getText().toString()))){
                    Toast.makeText(JoinClassroom.this, "Please Enter Subject Code", Toast.LENGTH_SHORT).show();
                }

                else{
                    final DatabaseReference mRef= FirebaseDatabase.getInstance().getReference().child("users");



                    mRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                            String searchUser= user;

                            if (dataSnapshot.child(searchUser).exists())
                            {
                                String userSection = dataSnapshot.child(searchUser).child("section").getValue().toString();
                                String userYear = dataSnapshot.child(searchUser).child("year").getValue().toString();

                                DatabaseReference AddStudent= FirebaseDatabase.getInstance().getReference().child("Classrooms").child(userYear).child(userSection);

                                AddStudent.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                        String classTitle = snapshot.child(classCode.getText().toString()).child("subjectTitle").getValue().toString();

                                        if(snapshot.child(classCode.getText().toString()).exists()){

                                            UsersDatabase = FirebaseDatabase.getInstance();
                                            reference = UsersDatabase.getReference("Classrooms").child(userYear).child(userSection);
                                            reference.child(classCode.getText().toString()).child("StudentsJoined").child(user).setValue(user);

                                            Joined =  UsersDatabase.getReference("JoinClasses");
                                            Joined.child(user).child(classCode.getText().toString()).child("subjectCode").setValue(classCode.getText().toString());
                                            Joined.child(user).child(classCode.getText().toString()).child("subjectTitle").setValue(classTitle);

                                            Toast.makeText(JoinClassroom.this, "You Have Joined the class", Toast.LENGTH_SHORT).show();
                                        }

                                        else{
                                            Toast.makeText(JoinClassroom.this, "Class does not exist for this section", Toast.LENGTH_SHORT).show();

                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });


                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }
        });
    }
}