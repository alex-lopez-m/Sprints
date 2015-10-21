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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

import one.sprint.alexmalvaez.com.sprintone.R;
import one.sprint.alexmalvaez.com.sprintone.adapters.SuperFlingAdapter;
import one.sprint.alexmalvaez.com.sprintone.models.SuperFling;
import one.sprint.alexmalvaez.com.sprintone.util.RequestQueueSingleton;


public class MainActivity extends AppCompatActivity {

    /* URLs */
    private static final String FLING_URL_JSON_ARRAY = "http://challenge.superfling.com";
    private static final String URL_PHOTO_STREAM = "http://challenge.superfling.com/photos";

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

        //initiateUI();
        makeJsonArrayReq();
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

/*
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

        adapter = new SuperFlingAdapter(this, superFlingList);
        recyclerView.setAdapter(adapter);
    }

*/

    public void makeJsonArrayReq(){
        JsonArrayRequest jsonArrayReq = new JsonArrayRequest(
                FLING_URL_JSON_ARRAY,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        Log.d(MainActivity.class.getSimpleName(), "RESPUESTA: " + jsonArray.toString());
                        Toast.makeText(getApplicationContext(), "RESPUESTA: " + jsonArray.toString(), Toast.LENGTH_LONG).show();

                        if(jsonArray != null && jsonArray.length() > 0) {

                            try {

                                //JSONArray jokeJSONArray = jsonArray.getJSONArray(TAG_ITEM_VALUE);
                                //jokeList = new ArrayList<Joke>();
                                superFlingList = new ArrayList<SuperFling>();

                                for(int i=0; i<jsonArray.length(); i++){

                                    JSONObject jo = jsonArray.getJSONObject(i);


                                    //ArrayList<String> categoriesList = new ArrayList<String>();
                                    //JSONArray catJSONArray = jo.getJSONArray(TAG_ITEM_CATEGORIES);

                                    //for(int j=0; j<catJSONArray.length(); j++){
                                    //    categoriesList.add(catJSONArray.get(j).toString());
                                    //}
                                    SuperFling sf = new SuperFling(jo.getString(TAG_ID), jo.getString(TAG_IMAGE_ID), jo.getString(TAG_TITLE), jo.getString(TAG_USER_ID), jo.getString(TAG_USER_NAME));
                                    //Joke joke = new Joke(categoriesList, jo.getString(TAG_ITEM_ID), jo.getString(TAG_ITEM_JOKE));

                                    Log.d(MainActivity.class.getSimpleName(), "SUPERFLING " + i + ": " + sf);

                                    superFlingList.add(sf);

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
                        Log.d(MainActivity.class.getSimpleName(), "ERROR!!! " + error.getMessage());
                        VolleyLog.d(MainActivity.class.getSimpleName(), "Error: " + error.getMessage());
                        Toast.makeText(getApplicationContext(), "ERROR!!! " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        // Adding request to request queu
        RequestQueueSingleton.getInstance().addToRequestQueue(jsonArrayReq, "", getApplicationContext());
    }

}
