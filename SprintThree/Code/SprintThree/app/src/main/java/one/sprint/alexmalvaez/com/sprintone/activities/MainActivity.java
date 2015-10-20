package one.sprint.alexmalvaez.com.sprintone.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

import one.sprint.alexmalvaez.com.sprintone.R;
import one.sprint.alexmalvaez.com.sprintone.adapters.SuperFlingAdapter;
import one.sprint.alexmalvaez.com.sprintone.models.Joke;
import one.sprint.alexmalvaez.com.sprintone.models.SuperFling;
import one.sprint.alexmalvaez.com.sprintone.util.RequestQueueSingleton;


public class MainActivity extends AppCompatActivity {

    /* URLs */
    private static final String URL_JSON_ARRAY_JOKE = "http://challenge.superfling.com";
    private static final String URL_PHOTO_STREAM = "http://http://challenge.superfling.com/photos";

    /* Tags used in the JSON String Response  */
    private static final String TAG_ID = "ID";
    private static final String TAG_IMAGE_ID = "ImageID";
    private static final String TAG_TITLE = "Title";
    private static final String TAG_USER_ID = "UserID";
    private static final String TAG_USER_NAME = "UserName";


    private ArrayList<SuperFling> superFlingList;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private SuperFlingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initiateUI();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.action_refresh){
            //Toast.makeText(getApplicationContext(), "Refresh yourself!", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }


    public void initiateUI(){

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        recyclerView = (RecyclerView) findViewById(R.id.rv_jokes);
        recyclerView.setHasFixedSize(true);

        // layoutManager = new GridLayoutManager(this,3);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        superFlingList = new ArrayList<SuperFling>();


        adapter = new SuperFlingAdapter(this, superFlingList);
        recyclerView.setAdapter(adapter);
    }

    public void makeJsonObjectReq(){
        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(
                URL_JSON_ARRAY_JOKE,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.d(MainActivity.class.getSimpleName(), "RESPUESTA: " + response.toString());
                        //Toast.makeText(getApplicationContext(), "RESPUESTA: " + response.toString(), Toast.LENGTH_LONG).show();

                        if(response != null && response.length() > 0) {

                            try {

                                JSONArray jokeJSONArray = response.getJSONArray(TAG_ITEM_VALUE);
                                jokeList = new ArrayList<Joke>();

                                for(int i=0; i<jokeJSONArray.length(); i++){

                                    JSONObject jo = jokeJSONArray.getJSONObject(i);

                                    ArrayList<String> categoriesList = new ArrayList<String>();
                                    JSONArray catJSONArray = jo.getJSONArray(TAG_ITEM_CATEGORIES);

                                    for(int j=0; j<catJSONArray.length(); j++){
                                        categoriesList.add(catJSONArray.get(j).toString());
                                    }

                                    Joke joke = new Joke(categoriesList, jo.getString(TAG_ITEM_ID), jo.getString(TAG_ITEM_JOKE));
                                    jokeList.add(joke);
                                    Log.d(MainActivity.class.getSimpleName(), "JOKE " + i + ": " + joke);

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.e("ServiceHandler", "Couldn't get any data from the url");
                        }

                        //refresh();
                        //adapter.setJokeList(jokeList);
                        //adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(MainActivity.class.getSimpleName(), "Error: " + error.getMessage());
                        Toast.makeText(getApplicationContext(), "ERROR!!! ", Toast.LENGTH_LONG).show();
                    }
                });

        // Adding request to request queue
        RequestQueueSingleton.getInstance().addToRequestQueue(jsonObjectReq, "", getApplicationContext());
    }

}
