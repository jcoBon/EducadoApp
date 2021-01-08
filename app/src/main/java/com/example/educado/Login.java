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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    EditText studentNumber, studentPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        Button Signin = (Button)findViewById(R.id.btnSignIn);
        TextView create = (TextView)findViewById(R.id.txtCreateAccount);

        studentNumber = findViewById(R.id.txtStudent_Number);
        studentPassword = findViewById(R.id.txtPassword);


        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                if(TextUtils.isEmpty(studentNumber.getText().toString()))
                {
                    Toast.makeText(Login.this, "No empty keyword allowed", Toast.LENGTH_SHORT).show();
                }
                else{
                    final DatabaseReference mRef= FirebaseDatabase.getInstance().getReference().child("users");

                    mRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                            String searchUser= studentNumber.getText().toString();
                            String searchPass = studentPassword.getText().toString();


                            if (dataSnapshot.child(searchUser).exists())
                            {
                                String passwordmo = dataSnapshot.child(searchUser).child("password").getValue().toString();
                                if(searchPass.equals(passwordmo)){

                                    if((dataSnapshot.child(searchUser).child("userType").getValue().toString()).equals("Teacher")){
                                        Intent intent = new Intent(Login.this, Choice.class);
                                        intent.putExtra("User", searchUser);
                                        startActivity(intent);
                                    }

                                    else if((dataSnapshot.child(searchUser).child("userType").getValue().toString()).equals("Student")){
                                        Intent intent = new Intent(Login.this, studentLogin.class);
                                        String year = dataSnapshot.child(searchUser).child("year").getValue().toString();
                                        String section = dataSnapshot.child(searchUser).child("section").getValue().toString();
                                        intent.putExtra("User", searchUser);
                                        intent.putExtra("Year", year);
                                        intent.putExtra("Section", section);

                                        startActivity(intent);
                                    }


                                }

                                else{
                                    Toast.makeText(Login.this, "Wrong password", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(Login.this, "Student not registered", Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, CreateAccType.class);
                startActivity(intent);

            }
        });
    }
}