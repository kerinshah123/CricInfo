package com.example.criinfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.criinfo.Home.HomeActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class LoginSignUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextInputLayout emaillayout, passwordlayout, namelayout, emailsignlayout, numberlayout, passwordsignlayout;
    EditText loginemail, loginpassword, namesign, emailsign, numbersign, passwordsign, adminemail, adminpassword;
    Button loginbtn, signinbtn;
    LinearLayout userloginlayout, usersigninlayout;
    TextView createone, logintxtview,skip;
    String usertype;
    private FirebaseAuth mAuth;

    String namepattern = "[a-zA-Z]+";
    String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    String txtloginemail, txtloginpassword, txtsignname, txtsignemail, txtsignnumber, txtsignpassword, txtadminemail, txtadminpassword;

    private FirebaseFirestore db;

    public static boolean loggedIn = false;
    public static final String SHARED_PREF_NAME = "hello,sign in";
    public static final String loginyes_no = "userlogin";
    public static final String User_shared = "username";
    public static final String Email_shared = "email";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sign_up);


        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();


        //Textview Ids

        createone = findViewById(R.id.createone);
        logintxtview = findViewById(R.id.logintxtview);
        skip = findViewById(R.id.skip);

        //Button Ids

        loginbtn = findViewById(R.id.loginbtn);
        signinbtn = findViewById(R.id.signinbtn);

        //Linear Layout Ids

        userloginlayout = findViewById(R.id.userloginlayout);
        usersigninlayout = findViewById(R.id.usersigninlayout);

        //TextInputLayout Ids

        emaillayout = findViewById(R.id.emaillayout);
        passwordlayout = findViewById(R.id.passwordlayout);
        namelayout = findViewById(R.id.nameLayout);
        emailsignlayout = findViewById(R.id.emailSignLayout);
        numberlayout = findViewById(R.id.numberLayout);
        passwordsignlayout = findViewById(R.id.passwordSignLayout);


        //EditText Ids

        //Login

        loginemail = findViewById(R.id.loginemail);
        loginpassword = findViewById(R.id.loginpassword);

        //SignIn

        emailsign = findViewById(R.id.emailSign);
        namesign = findViewById(R.id.nameSign);
        numbersign = findViewById(R.id.numberSign);
        passwordsign = findViewById(R.id.passwordSign);
        int pos=0;
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);
        spinner.setSelection(pos);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Select User");
        categories.add("Team Manager");
        categories.add("League Manager");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);


        loginemail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                emaillayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        loginpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passwordlayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        emailsign.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                emailsignlayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (emailsign.getText().toString().matches(emailpattern) && s.length() > 0) {
                    emailsignlayout.setError(null);
                } else {
                    emailsignlayout.setError("Invalid Email");
                }

            }
        });

        numbersign.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                namelayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        passwordsign.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passwordlayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        namesign.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                namelayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (namesign.getText().toString().matches(namepattern) && s.length() > 0) {
                    namelayout.setError(null);
                } else {
                    namelayout.setError("Invalid Name");
                }

            }
        });
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtloginemail = loginemail.getText().toString().trim();
                txtloginpassword = loginpassword.getText().toString().trim();

                if (txtloginemail.isEmpty()) {
                    emaillayout.setError("Enter Email Plz..");
                    emaillayout.requestFocus();
                } else if (txtloginpassword.isEmpty()) {
                    passwordlayout.setError("Enter Password Plz..");
                } else {
                    loginuser();
                }
            }
        });
        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtsignname = namesign.getText().toString().trim();
                txtsignemail = emailsign.getText().toString().trim();
                txtsignnumber = numbersign.getText().toString().trim();
                txtsignpassword = passwordsign.getText().toString().trim();

                if (txtsignname.isEmpty()) {
                    namelayout.setError("Enter Name");
                    namelayout.requestFocus();
                } else if (txtsignemail.isEmpty()) {
                    emailsignlayout.setError("Enter email");
                    emailsignlayout.requestFocus();
                } else if (txtsignnumber.isEmpty()) {
                    numberlayout.setError("Enter Number");
                    numberlayout.requestFocus();
                } else if (txtsignpassword.isEmpty()) {
                    passwordsignlayout.setError("Enter Passoword");
                    passwordsignlayout.requestFocus();
                } else if (!(txtsignnumber.length() == 10)) {
                    numberlayout.setError("Enter valid number");
                }else if(usertype == "Select User")
                {
                    Toast.makeText(LoginSignUpActivity.this, "Plzz Select Valid User type", Toast.LENGTH_SHORT).show();
                }
                else {
                    Doseuserexist();
                }
            }
        });


        createone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userloginlayout.setVisibility(View.GONE);
                usersigninlayout.setVisibility(View.VISIBLE);
            }
        });

        logintxtview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usersigninlayout.setVisibility(View.GONE);
                userloginlayout.setVisibility(View.VISIBLE);
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);

            }
        });
    }



    public void loginuser() {
        db.collection("user")
                .whereEqualTo("email", txtloginemail)
                .whereEqualTo("password", txtloginpassword)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            Toast.makeText(LoginSignUpActivity.this, "Login Success", Toast.LENGTH_LONG).show();
                            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("userName",queryDocumentSnapshots.getDocuments().get(0).get("name").toString().trim());
                            editor.putString("type",(queryDocumentSnapshots.getDocuments().get(0).get("usertype").toString().trim()));
                            editor.putString("userId",queryDocumentSnapshots.getDocuments().get(0).getId().trim());
                            editor.putBoolean(loginyes_no, true);
                            editor.putString(Email_shared, loginemail.getText().toString());

                            editor.commit();
                            startActivity(new Intent(LoginSignUpActivity.this,HomeActivity.class));
                        } else {
                            Toast.makeText(LoginSignUpActivity.this, "Email or Password Not correct", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void Doseuserexist() {
        db.collection("user")
                .whereEqualTo("email", txtsignemail)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            Toast.makeText(LoginSignUpActivity.this, "User Already Exist Plz try to login", Toast.LENGTH_LONG).show();
                        } else {
                            Adduser();
                        }
                    }
                });
    }

    public void Adduser() {
        CollectionReference dbuser = db.collection("user");

        UserPojo userPojo = new UserPojo(txtsignname, txtsignemail, txtsignpassword, txtsignnumber,usertype);
        dbuser.add(userPojo).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(loginyes_no, true);
                editor.putString("userId",documentReference.getId().trim());
                editor.putString("userName",txtsignname.trim());
                editor.putString("type",usertype.trim());
                editor.putString(Email_shared, emailsign.getText().toString());

                editor.commit();
                startActivity(new Intent(LoginSignUpActivity.this,HomeActivity.class));

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginSignUpActivity.this, "Error Adding User", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        loggedIn = sharedPreferences.getBoolean(loginyes_no, false);
        if (loggedIn) {
            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(i);
            LoginSignUpActivity.this.finish();
        }


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        usertype = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
       // Toast.makeText(parent.getContext(), "Selected: " + usertype, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
