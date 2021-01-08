package com.example.educado;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class create_account extends AppCompatActivity {

    EditText mobile,email, fname, lname, Password,studentNumber;
    Button Create;
    Spinner YearSpinner,SectionSpinner;


    FirebaseDatabase UsersDatabase;
    DatabaseReference reference,students;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_create_account);

        mobile = findViewById(R.id.mobileNum);
        email = findViewById(R.id.emailAdd);
        fname = findViewById(R.id.FirstName);
        lname = findViewById(R.id.LastName);
        Password = findViewById(R.id.pass);
        studentNumber = findViewById(R.id.StudentNumber);

        Create = findViewById(R.id.create);

        YearSpinner = (Spinner) findViewById(R.id.yearSpinner);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(create_account.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.year));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        YearSpinner.setAdapter(myAdapter);

        SectionSpinner = (Spinner) findViewById(R.id.sectionSpinner);

        ArrayAdapter<String> Section = new ArrayAdapter<String>(create_account.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.section));
        Section.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SectionSpinner.setAdapter(Section);

        Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty((studentNumber.getText().toString()))){
                    Toast.makeText(create_account.this, "Please your student number", Toast.LENGTH_SHORT).show();
                }

                else{
                    final DatabaseReference mref = FirebaseDatabase.getInstance().getReference().child("users");

                    mref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String searchUser = studentNumber.getText().toString();


                            if(dataSnapshot.child(searchUser).exists()){
                                Toast.makeText(create_account.this,"USER IS ALREADY REGISTERED",Toast.LENGTH_SHORT).show();
                            }

                            else{

                                UsersDatabase = FirebaseDatabase.getInstance();
                                reference = UsersDatabase.getReference("users");
                                students = UsersDatabase.getReference("Students");

                                String mobileNumber = mobile.getText().toString();
                                String emailAdd = email.getText().toString();
                                String fName = fname.getText().toString();
                                String lName = lname.getText().toString();
                                String password = Password.getText().toString();
                                String studentNo = studentNumber.getText().toString();
                                String userType = "Student";
                                String year = YearSpinner. getSelectedItem().toString();
                                String section = SectionSpinner. getSelectedItem().toString();

                                studentHelper studentHelper = new studentHelper(mobileNumber,emailAdd,fName,lName,password,studentNo,userType, year,section);

                                reference.child(studentNo).setValue(studentHelper);
                                students.child(studentNo).setValue(studentHelper);

                                Toast.makeText(create_account.this,"USER REGISTERED",Toast.LENGTH_LONG).show();



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