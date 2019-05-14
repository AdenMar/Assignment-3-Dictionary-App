package edu.quinnipiac.ser210.dictionaryapp;

/*
Dictionary App
5/13/19
Created by: Aden Mariyappa
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DefinitionActivity extends AppCompatActivity {
    private TextView definition; // definition output

    // onCreate, set the TextView definition
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definition);

        definition = (TextView)findViewById(R.id.definitiontext);
        String def = (String)getIntent().getExtras().get("definition");
        definition.setText(def);
    }
}
