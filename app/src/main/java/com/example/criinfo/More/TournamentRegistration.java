package com.example.criinfo.More;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.criinfo.R;
import com.example.criinfo.Utils.Utils;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class TournamentRegistration extends AppCompatActivity {
    public Uri imguri;
    CircularImageView tournamentimage;
    StorageReference sRef;
    FirebaseFirestore Host_firebaseStorage;
    EditText tournamentname, contactnumber, country, location;
    TextInputEditText startdate, enddate;
    TextInputLayout sdate, edate;
    FirebaseFirestore db;
    SharedPreferences sharedPreferences;
    ProgressBar progressBar;
    String downloadUri;
    SimpleDateFormat start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament__registration);

        tournamentimage = findViewById(R.id.tournamentimg);
        tournamentname = findViewById(R.id.tournamnetname);
        location = findViewById(R.id.location);
        // contactnumber = findViewById(R.id.contactnumber);
        country = findViewById(R.id.country);
        progressBar = findViewById(R.id.progressBar);
        startdate = findViewById(R.id.startdate);
        enddate = findViewById(R.id.enddate);
        sdate = findViewById(R.id.startdateInputLayout);
        edate = findViewById(R.id.enddateInputLayout);


        Host_firebaseStorage = FirebaseFirestore.getInstance();
        db = FirebaseFirestore.getInstance();
        sRef = FirebaseStorage.getInstance().getReference("TournamentImage");
        sharedPreferences = getApplicationContext().getSharedPreferences(Utils.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        System.out.println(sharedPreferences.getString("userId", ""));
        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();


            }

            private void updateLabel() {
                String myFormat = "MM/dd/yy"; //In which you need put here
                 start = new SimpleDateFormat(myFormat, Locale.US);

                startdate.setText(start.format(myCalendar.getTime()));

            }

        };

        final DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel2();



            }

            private void updateLabel2() {
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                enddate.setText(sdf.format(myCalendar.getTime()));
            }

        };

        startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(TournamentRegistration.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                }
                datePickerDialog.show();

            }
        });

        enddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(TournamentRegistration.this, date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    datePickerDialog.getDatePicker().setMinDate(myCalendar.getTimeInMillis()-1000);
                }
                datePickerDialog.show();

            }
        });


    }

    public void selection(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);

    }


    private String getExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imguri = data.getData();
            tournamentimage.setImageURI(imguri);

        }
    }

    public void toaddtournaments(View view) {

            if (tournamentname.getText().toString().isEmpty()  ||
                    location.getText().toString().isEmpty() || country.getText().toString().isEmpty() || startdate.getText().toString().isEmpty() || enddate.getText().toString().isEmpty()) {
                Toast.makeText(this, "All Field Required", Toast.LENGTH_SHORT).show();
            } else {


                progressBar.setVisibility(View.VISIBLE);

                if (imguri == null || imguri.equals(Uri.EMPTY)) {
                    downloadUri = "";
                    Toast.makeText(this, "Plz Add Image", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
                else{
                    final StorageReference storageReference = sRef.child(System.currentTimeMillis() + "." + imguri);
                    storageReference.putFile(imguri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }
                            return storageReference.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                    }
                                }, 500);
                            }

                            downloadUri = task.getResult().toString();
                            callAdd();


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Image Upload Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }



            }
        }




    private void callAdd() {


        ArrayList<String> teamsId = new ArrayList<>();
        Map<String, Object> tour = new HashMap<>();
        tour.put("tournament", tournamentname.getText().toString());
        tour.put("startdate", startdate.getText().toString());
        tour.put("enddate", enddate.getText().toString());
        tour.put("location", location.getText().toString());
        tour.put("country", country.getText().toString());
        //  tour.put("contactnumber", contactnumber.getText().toString());
        tour.put("image", downloadUri);
        tour.put("leaguemanager", sharedPreferences.getString("userId", ""));


        // Add a new document with a generated ID
        db.collection("tournaments")
                .add(tour)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        finish();
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(TournamentRegistration.this, "Tournament Created Successfully", Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(getApplicationContext(),teamManagerTeam.class));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println(e.getMessage());
                    }
                });
    }
}