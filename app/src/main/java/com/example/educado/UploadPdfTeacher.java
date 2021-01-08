package com.example.educado;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class UploadPdfTeacher extends AppCompatActivity {
    EditText editText,Cude;
    Button btn,view;
    String CLASS;
    Spinner yearSpinner,sectionSpinner;

    StorageReference storageReference;
    DatabaseReference databaseReference;

    ListView listView;
    List<putPDFTeacher> EntryList;

    String subSec,subYear,sic,yer,Kode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_upload_pdf_teacher);

        editText = findViewById(R.id.EnterPdf);
        Cude = findViewById(R.id.code);
        btn = findViewById(R.id.addPDFBtn);
        view = findViewById(R.id.ViewPdfs);
        listView = findViewById(R.id.AddPdftoClass);
        EntryList = new ArrayList<>();
        Kode = Cude.getText().toString();


//        String classname= getIntent().getStringExtra("ClassName");
//        CLASS = classname;


        yearSpinner = (Spinner) findViewById(R.id.Year);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(UploadPdfTeacher.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.year));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(myAdapter);

        sectionSpinner = (Spinner) findViewById(R.id.Sec);

        ArrayAdapter<String> Section = new ArrayAdapter<String>(UploadPdfTeacher.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.section));
        Section.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sectionSpinner.setAdapter(Section);

        btn.setEnabled(false);


        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subYear = yearSpinner. getSelectedItem().toString();
                subSec = sectionSpinner. getSelectedItem().toString();
                yer = subYear;
                sic= subSec;
                Kode = Cude.getText().toString();
                selectPDF();
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                subYear = yearSpinner. getSelectedItem().toString();
                subSec = sectionSpinner. getSelectedItem().toString();
                yer = subYear;
                sic= subSec;
                Kode = Cude.getText().toString();

                RetrieveList();

            }
        });

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

    }

    private void RetrieveList() {

        databaseReference = FirebaseDatabase.getInstance().getReference("Classrooms").child(yearSpinner.getSelectedItem().toString()).child(sectionSpinner.getSelectedItem().toString()).child(Cude.getText().toString()).child("pdf");
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

    private void selectPDF() {

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("Classrooms").child(yearSpinner.getSelectedItem().toString()).child(sectionSpinner.getSelectedItem().toString()).child(Cude.getText().toString()).child("pdf");

        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "PDF File Select"), 12);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 12 && resultCode==RESULT_OK && data != null && data.getData() != null){

            btn.setEnabled(true);
            editText.setText(data.getDataString().substring(data.getDataString().lastIndexOf("/") +1));

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    uploadPDFFileFirebase(data.getData());

                }
            });
        }
    }

    private void uploadPDFFileFirebase (Uri data){

        final ProgressDialog progressDialog = new ProgressDialog(this);

        progressDialog.setTitle("File is uploading...");
        progressDialog.show();

        StorageReference reference = storageReference.child("uploadPDF" +System.currentTimeMillis()+ ".pdf");
        reference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while(!uriTask.isComplete());
                Uri uri = uriTask.getResult();

                putPDFTeacher putPDFTeacher = new putPDFTeacher(editText.getText().toString(),uri.toString());

                databaseReference.child(databaseReference.push().getKey()).setValue(putPDFTeacher);
                Toast.makeText(UploadPdfTeacher.this,"File Upload", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                double progress =(100.0* snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                progressDialog.setMessage("File uploaded..." +(int)progress+"%");

            }
        });

    }


}