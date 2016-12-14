package com.example.ryan.bananafinder;

/**
 * Created by Eric on 12/11/16.
 */
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class SubmitBanana extends Activity {
    EditText amountOfBananas;
    Button backButton;
    Button submitButton;
    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.banana);
        db = new DBHandler(this);
        //resultToDisplay = intent.getStringExtra("ResultToDisplay");

        // I *INTEND* to send the results.
        //       Thank you,
        //     Eric Christopher Ghaly, Esq.
        initialize();
        registerChangeListener();

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.banana);
        String col = settings.getString("Colors","yellow");
        switch (col) {
            case "white":
                layout.setBackgroundColor(getResources().getColor(R.color.white));
                break;
            case "red":
                layout.setBackgroundColor(getResources().getColor(R.color.red));
                break;
            case "yellow":
                layout.setBackgroundColor(getResources().getColor(R.color.yellow));
                break;
            case "blue":
                layout.setBackgroundColor(getResources().getColor(R.color.blue));
                break;
        }


    }

    public void initialize(){
        amountOfBananas = (EditText) findViewById(R.id.editBananaText);
        backButton = (Button) findViewById(R.id.backToMapButton);
        submitButton = (Button) findViewById(R.id.submitBananaButton);
    }

    private void registerChangeListener() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SubmitBanana.this, MapsActivity.class)); //when back button is clicked, app takes you back to survey
                finish();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                double longitude = intent.getDoubleExtra("Longitude", 0);
                double latitude = intent.getDoubleExtra("Latitude", 0);
                String value = amountOfBananas.getText().toString();
                int finalValue = Integer.parseInt(value);
                Log.d("Amount To Add: ", Integer.toString(finalValue));
                db.addBanana(new Banana(db.getBananaCount(), finalValue, longitude, latitude));

             /*   intent.putExtra("Longitude", longitude);
                intent.putExtra("Latitude", latitude);
                intent.putExtra("Amount", finalValue);
                intent.putExtra("Check", true);*/
                startActivity(new Intent(SubmitBanana.this, MapsActivity.class));
                finish();
            }
        });

    }


}


