package com.example.educado;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewClassrooms extends AppCompatActivity {

    Button ViewList;
    Spinner YearSpinner,secSpinner;

    ListView listView;
    DatabaseReference databaseReference;
    List<classHelper> EntryList;

    String subSec,subYear,sic,yer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_view_classrooms);


        listView = findViewById(R.id.ClassroomListView);
        ViewList = findViewById(R.id.ClassBtn);
        EntryList = new ArrayList<>();

        YearSpinner = (Spinner) findViewById(R.id.ClassYearSpinner);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(ViewClassrooms.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.year));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        YearSpinner.setAdapter(myAdapter);

        secSpinner = (Spinner) findViewById(R.id.ClassSectionSpinner);

        ArrayAdapter<String> sec = new ArrayAdapter<String>(ViewClassrooms.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.section));
        sec.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        secSpinner.setAdapter(sec);

        ViewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subYear = YearSpinner. getSelectedItem().toString();
                subSec = secSpinner. getSelectedItem().toString();
                yer = subYear;
                sic= subSec;


                RetrieveList();

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int intention, long id) {
                classHelper classHelper = EntryList.get(intention);
                String Code = classHelper.getSubjectCode();
                String Section = classHelper.getSubjectSection();
                String Title = classHelper.getSubjectTitle();
                String Year = classHelper.getSubjectYear();



                Intent intent = new Intent(ViewClassrooms.this,ViewClassDetails.class);

                intent.putExtra("Code", Code);
                intent.putExtra("Section", Section);
                intent.putExtra("Title", Title);
                intent.putExtra("Year", Year);

                startActivity(intent);
            }
        });


    }

    private void RetrieveList() {


        databaseReference = FirebaseDatabase.getInstance().getReference("Classrooms").child(subYear).child(subSec);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    classHelper classHelper = ds.getValue(com.example.educado.classHelper.class);
                    EntryList.add(classHelper);

                }

                String[] entryList = new String[EntryList.size()];

                for(int i = 0; i<entryList.length;i++){
                    entryList[i]= EntryList.get(i).getSubjectTitle();

                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,entryList){

                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
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