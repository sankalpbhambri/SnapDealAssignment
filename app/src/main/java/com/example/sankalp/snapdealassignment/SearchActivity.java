package com.example.sankalp.snapdealassignment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InterfaceAddress;
import java.util.ArrayList;
import java.util.HashMap;


public class SearchActivity extends ActionBarActivity {

    private static String url;
    //JSON Node Names
    static  String TAG_RESULTS = "results";
    static  String TAG_STATUS = "status";

    static  String TAG_PHOTOS = "photos";
    static  String TAG_NAME = "name";
    static  String TAG_PHOTO_REFERENCE = "photo_reference";
    JSONArray results = null;
    JSONArray photos = null;
    ListView listView;
    ArrayList<HashMap<String, String>> arrayList;
    String query;
    ListViewAdapter adapter;
   String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        listView = (ListView) findViewById(R.id.placesList);
        Log.v("search","hey");
        Intent intent = getIntent();
        if(intent.ACTION_SEARCH.equals(intent.getAction())){
            Log.v("search","hey1");
            query = intent.getStringExtra(SearchManager.QUERY);
            Log.v("search", "query=" + query);
            if(query.equals("")){
                url= "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=50000&key=AIzaSyC2OSVNnbxBtfUSAqdSxh2T4QKZdZ4ESS0";

            }
            else{
                query = query.replaceAll("\\s", "+");
                url= "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=50000&types="+query+"&key=AIzaSyC2OSVNnbxBtfUSAqdSxh2T4QKZdZ4ESS0";

            }
            Log.v("json query    ", " " + query);
            Log.v("json data    ", " " + url);
            new JSONParse().execute();
        }
        else
        {
            Log.v("search","hey2");

        }
    }

    private class JSONParse extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SearchActivity.this);
            pDialog.setMessage("Getting Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected JSONObject doInBackground(String... args) {
            JsonParser jParser = new JsonParser();
            // Getting JSON from URL
            JSONObject json = jParser.getJSONFromUrl(url);

            arrayList = new ArrayList<HashMap<String, String>>();

            try {

                status = json.getString(TAG_STATUS);
                Log.v("status",status);

                if (status.equals("OK")) {


                    results = json.getJSONArray(TAG_RESULTS);

                    for (int i = 0; i < results.length(); i++) {
                        HashMap<String, String> map = new HashMap<String, String>();
                        Log.v("results ", "results= " + results);
                        JSONObject c = results.getJSONObject(i);
                        // Storing  JSON item in a Variable
                        String name = c.getString(TAG_NAME);
                        map.put(TAG_NAME, name);
                        photos = c.getJSONArray(TAG_PHOTOS);
                        JSONObject d = photos.getJSONObject(0);
                        String photoReference = d.getString(TAG_PHOTO_REFERENCE);
                        //    photoReference="http://www.androidbegin.com/tutorial/flag/china.png";
                        photoReference = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=" + photoReference + "&key=AIzaSyC2OSVNnbxBtfUSAqdSxh2T4QKZdZ4ESS0";
                        map.put(TAG_PHOTO_REFERENCE, photoReference);
                        //Set JSON Data in TextView
                        Log.v("result", "name= " + name + " reference= " + photoReference);
                        arrayList.add(map);
                    }

                } else {
                    Log.v("status",status);
                    finish();
                }
                    // Getting JSON Array
                }catch(JSONException e){
                    e.printStackTrace();
                }

            return json;
        }
        @Override
        protected void onPostExecute(JSONObject json) {
            listView = (ListView) findViewById(R.id.placesList);
            // Pass the results into ListViewAdapter.java
            adapter = new ListViewAdapter(SearchActivity.this, arrayList);
            // Set the adapter to the ListView
            listView.setAdapter(adapter);
            // Close the progressdialog
            pDialog.dismiss();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
