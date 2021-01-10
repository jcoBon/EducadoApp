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
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DisplayJoinedClasses extends AppCompatActivity {
    DatabaseReference databaseReference;
    Button upload;

    ListView listView;
    List<putPDFTeacher> EntryList;

    String user,year,sec,CLASS,title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_display_joined_classes);
        listView = findViewById(R.id.ModulesClass);
        EntryList = new ArrayList<>();
        user = getIntent().getStringExtra("User");
        sec = getIntent().getStringExtra("Section");
        year = getIntent().getStringExtra("Year");
        title = getIntent().getStringExtra("Title");
        String classCode= getIntent().getStringExtra("ClassCode");
        CLASS = classCode;


        upload = findViewById(R.id.UploadModules);

        RetrieveList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int intention, long id) {
                putPDFTeacher putPDFTeacher = EntryList.get(intention);

                Intent intent = new Intent();
                intent.setType(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(putPDFTeacher.getUrl()));
                startActivity(intent);
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayJoinedClasses.this,UploadPdfStudent.class);
                intent.putExtra("ClassCode",CLASS);
                intent.putExtra("User",user);
                intent.putExtra("Section",sec);
                intent.putExtra("Year",year);
                intent.putExtra("Title",title);
                startActivity(intent);
            }
        });
    }

    private void RetrieveList() {

        databaseReference = FirebaseDatabase.getInstance().getReference("Classrooms").child(year).child(sec).child(CLASS).child("pdf");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds:snapshot.getChildren()){

                    putPDFTeacher putPDFTeacher = ds.getValue(com.example.educado.putPDFTeacher.class);
                    EntryList.add(putPDFTeacher);
                }

                String[] uploadsName= new String[EntryList.size()];

                for (int i=0;i<uploadsName.length; i++){

                    uploadsName[i]=EntryList.get(i).getName();
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