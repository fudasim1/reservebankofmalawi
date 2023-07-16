package com.reservebankofmalawi;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AlertDialogLayout;
import androidx.appcompat.widget.Toolbar;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.reservebankofmalawi.adapters.CountriesAdapter;

import org.json.JSONObject;

import java.util.Arrays;


public class ExchangeActivity extends AppCompatActivity {

    String currencyFromName;
    String currencyToName;

    String currencyFromSymbol;
    String currencyToSymbol;

    String currencyFromAmount;

    AutoCompleteTextView currency_from_amount;
    AutoCompleteTextView currency_to_amount;

    Spinner currencySpinner1;
    Spinner currencySpinner2;

    String[] countryNames = {"United States", "Britain","South Africa","Japan", "Tanzania", "Zimbabwe", "UK", "Malawi", "Mozambique", "Zambia"};
    String[] countrySymbols = {"USD", "GBP", "ZAR","JPY", "TZS", "ZWL","EUR", "MWK", "MZN", "ZMW"};

    int[] countryFlags = {R.drawable.ic_flag_united_states,
            R.drawable.ic_flag_united_kingdom,
            R.drawable.ic_flag_south_africa,
            R.drawable.ic_flag_japan,
            R.drawable.ic_flag_tanzania,
            R.drawable.ic_flag_zimbabwe,
            R.drawable.ic_flg_europe,
            R.drawable.ic_flag_malawi,
            R.drawable.ic_flag_mozambique,
            R.drawable.ic_flag_zambia};

    ProgressDialog progressDialog;
    boolean firstTime = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        progressDialog = new ProgressDialog(this);

        currency_from_amount = findViewById(R.id.currency_from_amount);
        currency_to_amount = findViewById(R.id.currency_to_amount);

        currencySpinner1 = findViewById(R.id.currency_spinner1);
        currencySpinner2 = findViewById(R.id.currency_spinner2);

        currencySpinner1.setAdapter(new CountriesAdapter(this, countryFlags, countryNames, countrySymbols));
        currencySpinner1.setSelection(Arrays.asList(countryNames).indexOf("Malawi"));
        currencySpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currencySpinner1.setSelection(currencySpinner1.getSelectedItemPosition());

                TextView country_name = view.findViewById(R.id.country_name);
                currencyFromName = country_name.getText().toString().trim();

                TextView country_symbol = view.findViewById(R.id.country_symbol);
                currencyFromSymbol = country_symbol.getText().toString().trim();

                currency_from_amount.setText("");
                currency_from_amount.setHint("0.00");

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        currencySpinner2.setAdapter(new CountriesAdapter(this, countryFlags, countryNames, countrySymbols));
        currencySpinner2.setSelection(Arrays.asList(countryNames).indexOf("United States"));
        currencySpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currencySpinner2.setSelection(currencySpinner2.getSelectedItemPosition());

                TextView country_name = view.findViewById(R.id.country_name);
                currencyToName = country_name.getText().toString().trim();

                TextView country_symbol = view.findViewById(R.id.country_symbol);
                currencyToSymbol = country_symbol.getText().toString().trim();

                currency_to_amount.setText("");
                currency_to_amount.setHint("0.00");

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void exchangeCurrencyRequest(View v) {

        currencyFromAmount = currency_from_amount.getText().toString().trim();

        if (currencyFromAmount.isEmpty()) {
            Toast.makeText(ExchangeActivity.this, "Please fill in first amount", Toast.LENGTH_SHORT).show();

        } else {
            if (firstTime) {
                firstTime = false;
                exchangeCurrencyProcess(currencyFromAmount);
            }
        }

    }

    private void exchangeCurrencyProcess(final String currencyFromAmount) {

        progressDialog.setTitle("Converting");
        progressDialog.setMessage("Please wait a moment...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String URL = "https://api.exchangerate.host/convert?from="+currencyFromSymbol+"&to="+currencyToSymbol+"&amount="+currencyFromAmount+"";

        AndroidNetworking.get(URL)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jObject) {
                        Log.e("Response ", jObject.toString());

                        firstTime = true;
                        progressDialog.dismiss();

                        try {
                            String currencyToAmount = jObject.getString("result");

                            showExchangeResult(currencyFromName, currencyToName, currencyFromSymbol, currencyToSymbol, currencyFromAmount, currencyToAmount);

                        } catch (Exception e) {
                            Log.e("Error ", e.toString());
                            showResultError("Sorry, Request Not Processed");
                        }

                    }


                    @Override
                    public void onError(ANError error) {
                        Log.e("Error ", error.toString());
                        firstTime = true;
                        progressDialog.dismiss();
                        showResultError("Sorry, Network Is Unavailable");
                    }
                });


    }

    private void showExchangeResult(String currencyFromName, String currencyToName, String currencyFromSymbol, String currencyToSymbol, String currencyFromAmount, String currencyToAmount) {
        currency_to_amount.setText(currencyToAmount);
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mBuilder.setTitle("Exchange Result");
        mBuilder.setMessage(currencyFromName + " " + currencyFromSymbol + " " + currencyFromAmount + " to " + currencyToName + " " + currencyToSymbol + " = " + currencyToAmount);
        mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        mBuilder.create();
        mBuilder.show();
    }

    private void showResultError(String errorMessage) {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mBuilder.setTitle("Error");
        mBuilder.setMessage(errorMessage);
        mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        mBuilder.create();
        mBuilder.show();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
