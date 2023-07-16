package com.reservebankofmalawi;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class ReportActivity extends AppCompatActivity {


    AutoCompleteTextView person_name;
    AutoCompleteTextView person_number;
    AutoCompleteTextView person_email;
    EditText person_concern;


    ProgressDialog progressDialog;
    boolean firstTime = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        progressDialog = new ProgressDialog(this);

        person_name = findViewById(R.id.person_name);
        person_number = findViewById(R.id.person_number);
        person_email = findViewById(R.id.person_email);
        person_concern = findViewById(R.id.person_concern);

    }

    public void sendReportRequest(View view) {
        String personName = person_name.getText().toString().trim();
        String personNumber = person_number.getText().toString().trim();
        String personEmail = person_email.getText().toString().trim();
        String personConcern = person_concern.getText().toString().trim();
        if(personName.isEmpty() && personNumber.isEmpty()&&personConcern.isEmpty()){

        }else if(personName.isEmpty()){

            Toast.makeText(this, "Please write your full name", Toast.LENGTH_SHORT).show();
        }else if(personNumber.isEmpty()){

            Toast.makeText(this, "Please write your number", Toast.LENGTH_SHORT).show();
        }else if(personConcern.isEmpty()){

            Toast.makeText(this, "Please write your concern", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, personName+" your report is sent, thank you", Toast.LENGTH_SHORT).show();
            finish();
        }
    }


    private void reportViaCall() {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mBuilder.setTitle("Report Now");
        mBuilder.setMessage("Use the following numbers to report:\nLilongwe Head Office:\n(265) (0) 1 770 600\n(265) (0) 1 771 600\n\nBlantyre Branch:\n(265) (0) 1 820 299\n\nMzuzu Branch:\n(265) (0) 1 311 399");
        mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        mBuilder.create();
        mBuilder.show();
    }

    private void reportViaEmail() {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mBuilder.setTitle("Report Now");
        mBuilder.setMessage("Use the following email address to report:\nEmail Address: reserve-bank@rbm.mw");
        mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        mBuilder.create();
        mBuilder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_report, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                break;

            case R.id.call:
                reportViaCall();
                break;

            case R.id.email:
                reportViaEmail();
                break;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


}
