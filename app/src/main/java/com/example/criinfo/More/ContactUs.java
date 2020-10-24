package com.example.criinfo.More;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.criinfo.R;

public class ContactUs extends AppCompatActivity {
    EditText sub,email,query;
    Button send;
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        sub = findViewById(R.id.sub);
        email = findViewById(R.id.backmail);
        query = findViewById(R.id.fullquery);
        send = findViewById(R.id.reply);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sub.getText().toString().isEmpty() || email.getText().toString().isEmpty()
                || query.getText().toString().isEmpty()){
                    Toast.makeText(ContactUs.this, "All Field Required", Toast.LENGTH_SHORT).show();
                }
                else if(!email.getText().toString().matches(EMAIL_PATTERN)){
                    Toast.makeText(ContactUs.this, "Enter Valid Email", Toast.LENGTH_SHORT).show();
                }
                else {

                    String[] TO = {"projectcarrental82@gmail.com"};
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);

                    emailIntent.setData(Uri.parse("mailto:"));
                    emailIntent.setType("text/plain");
                    emailIntent.putExtra(Intent.EXTRA_EMAIL,TO );
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, sub.getText().toString());
                    emailIntent.putExtra(Intent.EXTRA_TEXT, query.getText().toString());

                    try {
                        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                        Toast.makeText(ContactUs.this, "Send", Toast.LENGTH_SHORT).show();
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(ContactUs.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}