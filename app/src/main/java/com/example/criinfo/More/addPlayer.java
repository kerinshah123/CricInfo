package com.example.criinfo.More;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.criinfo.More.MyTeamTabs.MyTeamPlayer;
import com.example.criinfo.R;
import com.example.criinfo.Utils.Utils;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class addPlayer extends AppCompatActivity {
    public Uri imguri;
    CircularImageView playerimg;
    private Uri filePath;
    EditText name,role,age,country,city;
    StorageReference sRef;
    FirebaseFirestore Host_firebaseStorage;
    SharedPreferences sharedPreferences;
    FirebaseFirestore db;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);
        playerimg=findViewById(R.id.playerimg);
        name=findViewById(R.id.playername);
        age=findViewById(R.id.playerage);
        role=findViewById(R.id.type);
        city=findViewById(R.id.playerCity);
        country=findViewById(R.id.playerCountry);
        progressBar = findViewById(R.id.progrssPlayer);

        Host_firebaseStorage = FirebaseFirestore.getInstance();
        db = FirebaseFirestore.getInstance();
        sRef = FirebaseStorage.getInstance().getReference("PlayerImage");
        sharedPreferences = getApplicationContext().getSharedPreferences(Utils.SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public void selection(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    private String getExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==1 && resultCode == RESULT_OK && data !=null && data.getData()!= null)
        {
            imguri = data.getData();
            filePath=data.getData();
            playerimg.setImageURI(imguri);

        }
    }

    public void addtosquad(View view) {
        if(name.getText().toString().isEmpty() || age.getText().toString().isEmpty() ||
                city.getText().toString().isEmpty() || country.getText().toString().isEmpty()
        || role.getText().toString().isEmpty()){
            Toast.makeText(this, "All Field Required", Toast.LENGTH_SHORT).show();
        }
        else {
            progressBar.setVisibility(View.VISIBLE);
            final StorageReference storageReference = sRef.child(System.currentTimeMillis() + "." +imguri);
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
                    if (task.isSuccessful())
                    {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                            }
                        }, 500);
                    }
                    ArrayList<String> playersId = new ArrayList<>();
                    String downloadUri = task.getResult().toString();
                    Map<String, Object> team = new HashMap<>();
                    team.put("name",name.getText().toString());
                    team.put("role", role.getText().toString());
                    team.put("age",age.getText().toString());
                    team.put("city",city.getText().toString());
                    team.put("country",country.getText().toString());
                    team.put("image",downloadUri);
                    team.put("teamId",sharedPreferences.getString("teamId",""));


                    // Add a new document with a generated ID
                    db.collection("players")
                            .add(team)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    progressBar.setVisibility(View.GONE);
                                    finish();
                                    Toast.makeText(addPlayer.this, "Player Addes Successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), MyTeamInfo.class));
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            });


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}