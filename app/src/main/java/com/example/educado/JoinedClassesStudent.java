package com.example.educado;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class JoinedClassesStudent extends AppCompatActivity {

    StorageReference storageReference;
    DatabaseReference databaseReference;

    ListView listView;
    List<classHelper> EntryList;

    String user,sec,year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_joined_classes_student);

        listView = findViewById(R.id.Nasamahan);
        EntryList = new ArrayList<>();
        user = getIntent().getStringExtra("User");
        sec = getIntent().getStringExtra("Section");
        year = getIntent().getStringExtra("Year");

        RetrieveList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int intention, long id) {
                classHelper classHelper = EntryList.get(intention);
                String title = EntryList.get(intention).toString();
                String classcode = classHelper.getSubjectCode();

                Intent intent = new Intent(JoinedClassesStudent.this,DisplayJoinedClasses.class);
                intent.putExtra("ClassCode",classcode);
                intent.putExtra("User",user);
                intent.putExtra("Section",sec);
                intent.putExtra("Year",year);
                intent.putExtra("Title", title);
                startActivity(intent);
            }
        });
    }

    private void RetrieveList() {

        String searchUser = user;

        databaseReference = FirebaseDatabase.getInstance().getReference("JoinClasses").child(searchUser);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds:snapshot.getChildren()){

                    classHelper classHelper = ds.getValue(com.example.educado.classHelper.class);
                    EntryList.add(classHelper);
                }

                String[] uploadsName= new String[EntryList.size()];

                for (int i=0;i<uploadsName.length; i++){

                    uploadsName[i]=EntryList.get(i).getSubjectTitle();
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, uploadsName){

                    @NonNull
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);
                        TextView textView = (TextView) view.findViewById(android.R.id.text1);

                        textView.setTextColor(Color.BLACK);

                        return view;
                    }

                };

                listView.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}