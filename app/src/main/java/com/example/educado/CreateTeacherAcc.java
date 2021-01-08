package com.example.educado;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class CreateTeacherAcc extends AppCompatActivity {

    EditText UserName,Pass, EmailIn;
    Button Register;

    FirebaseDatabase UsersDatabase;
    DatabaseReference reference,list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_create_teacher_acc);


        UserName = findViewById(R.id.userNameInput);
        Pass = findViewById(R.id.passwordInput);
        EmailIn = findViewById(R.id.emailInput);

        Register = findViewById(R.id.submitBtn);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty((UserName.getText().toString()))){
                    Toast.makeText(CreateTeacherAcc.this, "Please Enter a Username", Toast.LENGTH_SHORT).show();
                }

                else{
                    final DatabaseReference mref = FirebaseDatabase.getInstance().getReference().child("users");

                    mref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String searchUser = UserName.getText().toString();


                            if(dataSnapshot.child(searchUser).exists()){
                                Toast.makeText(CreateTeacherAcc.this,"USER IS ALREADY REGISTERED",Toast.LENGTH_SHORT).show();
                            }

                            else{

                                UsersDatabase = FirebaseDatabase.getInstance();
                                reference = UsersDatabase.getReference("users");
                                list = UsersDatabase.getReference("teachers");

                                String userName = UserName.getText().toString();
                                String email = EmailIn.getText().toString();
                                String password = Pass.getText().toString();
                                String userType = "Teacher";

                                teacherHelper teacherHelper = new teacherHelper(email, password, userName,userType);

                                reference.child(userName).setValue(teacherHelper);
                                list.child(userName).setValue(teacherHelper);

                                Toast.makeText(CreateTeacherAcc.this,"USER REGISTERED",Toast.LENGTH_LONG).show();



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