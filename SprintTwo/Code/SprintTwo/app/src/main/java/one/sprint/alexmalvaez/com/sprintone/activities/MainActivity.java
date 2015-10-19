package one.sprint.alexmalvaez.com.sprintone.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
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
import one.sprint.alexmalvaez.com.sprintone.adapters.JokeAdapter;
import one.sprint.alexmalvaez.com.sprintone.models.Joke;
import one.sprint.alexmalvaez.com.sprintone.util.RequestQueueSingleton;


public class MainActivity extends AppCompatActivity {

    private static final String URL_JSON_ARRAY_JOKE = "http://api.icndb.com/jokes/jokenumber";
    private static final String TAG_ITEM_ID = "id";
    private static final String TAG_ITEM_JOKE = "joke";
    private static final String TAG_ITEM_CATEGORIES = "categories";
    private static final String TAG_ITEM_VALUE = "value";
    private static final int CARDS_LIMIT = 21;

    private ArrayList<Joke> jokeList;
    private ArrayList<Joke> jokeRefreshList;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private JokeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initiateUI();
        makeJsonObjectReq();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void refresh(){

        Random mGenerator = new Random();
        int index = 0;
        jokeRefreshList = new ArrayList<Joke>();

        for(int i=0; i<CARDS_LIMIT; i++){
            index = mGenerator.nextInt(jokeList.size());
            jokeRefreshList.add(jokeList.get(index));
        }

        adapter.setJokeList(jokeRefreshList);
        adapter.notifyDataSetChanged();
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
            refresh();
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

        //layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false);
        layoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        jokeList = new ArrayList<Joke>();
        jokeRefreshList = new ArrayList<Joke>();

        /*
        ArrayList<String> categories =  new ArrayList<String>();
        categories.add("Categoria 1");
        categories.add("Categoria 2");
        categories.add("Categoria 3");

        jokeList.add(new Joke(categories,"1", "This is a joke 1"));
        jokeList.add(new Joke(categories,"2", "This is a joke 2"));
        jokeList.add(new Joke(categories,"3", "This is a joke 3"));
        jokeList.add(new Joke(categories,"4", "This is a joke 4"));
        jokeList.add(new Joke(categories,"5", "This is a joke 5"));
        jokeList.add(new Joke(categories,"6", "This is a joke 6"));
        jokeList.add(new Joke(categories,"7", "This is a joke 7"));
        jokeList.add(new Joke(categories,"8", "This is a joke 8"));
        jokeList.add(new Joke(categories,"9", "This is a joke 9"));
        */

        adapter = new JokeAdapter(this, jokeList);
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

                        refresh();
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
