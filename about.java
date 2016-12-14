package com.example.ryan.bananafinder;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Ryan on 12/13/2016.
 */

public class about extends Activity {

    TextView greet;
    TextView inf;
    ImageView barrnana;
    String real = "Welcome to our app, where you can traverse the unknown in search for the ever so elusive bananas. Banana Finder is the latest in state of the art banana tracking. With our new, sleek design, you can now explore the world without fear of losing track of all the yellowy goodness that you encounter. Simply tap the arrow in the top right corner, record your findings and submit; it's that easy! Now go young one, embrace the void and discover all the potassium you can endure!";
    String fake = "Banana Banana Banana Banana Banana Banana Banana Banana Banana Banana Banana Banana Banana Banana Banana Banana Banana Banana Banana Banana Banana Banana Banana Banana Banana Banana Banana Banana Banana Banana Banana Banana Banana Banana Banana Banana Banana Banana Banana Banana Banana Banana Banana Banana Banana Banana Banana Banana Banana Banana Banana Banana Banana Banana Banana Banana Banana Banana Banana Banana Banana Banana Banana Banana Banana Banana Banana Banana";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_layout);

        greet = (TextView) findViewById(R.id.greet);
        inf = (TextView) findViewById(R.id.infotest);
        barrnana = (ImageView) findViewById(R.id.barrnannaImage);

        inf.setText("here");
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.about);
        String col = settings.getString("Colors","yellow");
        String name = settings.getString("Name","Guest");

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

        greet.setText("Greetings " + name + "!");

        if(settings.getBoolean("Choice",true)){
            inf.setText(fake);
        }else{
            inf.setText(real);

        }

    }

    protected void onResume(){
        super.onResume();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.about);
        String s = settings.getString("Colors","yellow");
        String name = settings.getString("Name","Guest");
        switch (s) {
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

        if(settings.getBoolean("Choice",false)){
            String uri = "@drawable/barrnana";  // where myresource (without the extension) is the file
            int imageResource = getResources().getIdentifier(uri, null, getPackageName());
            Drawable res = getResources().getDrawable(imageResource);
            barrnana.setImageDrawable(res);
            inf.setText("");
        }else{
            inf.setText(real);
            barrnana.setImageDrawable(null);
        }
    }
}
