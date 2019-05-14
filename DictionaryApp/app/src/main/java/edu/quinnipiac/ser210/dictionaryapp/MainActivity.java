package edu.quinnipiac.ser210.dictionaryapp;

/*
Dictionary App
5/13/19
Created by: Aden Mariyappa
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuItem;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
public class MainActivity extends AppCompatActivity implements MainFragment.WordListener {
    private String url;
    private EditText input;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input = (EditText) findViewById(R.id.searchword);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    // inflates the toolbar
    @Override
    public boolean onCreateOptionsMenu (Menu menu)
    {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // determines the desired action of each action bar action
    @Override
    public boolean onOptionsItemSelected (MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.action_help:
                // creates a dialog box
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setTitle("Help");

                builder.setMessage("This app uses WordsAPI on RapidAPI to define a user inputted word");
                builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        dialog.cancel();
                    }
                });
                builder.show();
                return true;

            case R.id.action_paint:
                return true;

            case R.id.action_send:
                Toast.makeText(this, "You shared this page", Toast.LENGTH_LONG).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // when the search button is clicked, take the text inside the EditText
    // and pass it into the url
    @Override
    public void buttonClickedSearch(View view) {
        url = "https://wordsapiv1.p.rapidapi.com/words/" + input.getText().toString() + "/definitions";
        new Network().execute(url);
    }

    // AsyncTask is used to connect to our app to the url
    // and gather the definition from the API
    private class Network extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... urls) {

            HttpURLConnection urlConnection =null;
            BufferedReader reader =null;
            String wordDef = null;

            try {
                //creates URL, establishes connection and method call as GET
                URL url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("X-RapidAPI-Key","868c21ddbamsh5c8e18ac3a1c208p17a3e2jsnbabe0c69f636");;
                //connects to URL
                urlConnection.connect();
                Log.v("msg","url connection open");

                InputStream in = urlConnection.getInputStream();
                if (in == null) {
                    return null;
                }
                //debug: code is reaching here, so in != null
                reader  = new BufferedReader(new InputStreamReader(in));
                //debug:something going wrong here, reader is set to null

                // call getBufferString to get the string from the buffer.
                String wordDefJsonString = getBufferStringFromBuffer(reader).toString();

                // call a method to parse the json data and return a string.
                wordDef=  getWordDef(wordDefJsonString);


            }catch(Exception e){
                return null;
            }finally{
                if (urlConnection != null){
                    urlConnection.disconnect();
                }
                if (reader != null){
                    try{
                        reader.close();
                    }catch (IOException e){
                        return null;
                    }
                }
            }

            return wordDef;
        }

        // when the result has been found, pass it to DefinitionActivity using
        // an intent
        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            Intent intent = new Intent(MainActivity.this, DefinitionActivity.class);
            intent.putExtra("definition",result);
            startActivity(intent);
        }

        // formats the incoming JSON data
        private StringBuffer getBufferStringFromBuffer(BufferedReader br) throws Exception{
            StringBuffer buffer = new StringBuffer();

            String line;
            while((line = br.readLine()) != null){
                buffer.append(line + '\n');
            }

            if (buffer.length() == 0)
                return null;

            return buffer;
        }

        // creates string from JSON to display definition
        public String getWordDef(String JSONString) throws JSONException {
            String word,definition,partOfSpeech;
            JSONObject lyricsData = new JSONObject(JSONString);

            // parse through JSON data and set it to strings
            JSONObject details = lyricsData.getJSONArray("definitions").getJSONObject(0);

            word = lyricsData.getString("word");
            definition = details.getString("definition");
            partOfSpeech = details.getString("partOfSpeech");

            // creates string of all information
            String toset = "Word:\n" + capitalize(word) + "\n"
                    + "\nDefinition:\n" + capitalize(definition)+ "\n"
                    + "\nPart of Speech:\n" + capitalize(partOfSpeech);
            return toset;
        }

        public String capitalize(String word){
            return word.substring(0, 1).toUpperCase() + word.substring(1);
        }
    }
}
