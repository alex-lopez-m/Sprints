package one.sprint.alexmalvaez.com.sprintone.activities;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Random;

import one.sprint.alexmalvaez.com.sprintone.R;
import one.sprint.alexmalvaez.com.sprintone.adapters.SuperFlingAdapter;
import one.sprint.alexmalvaez.com.sprintone.adapters.SuperFlingSSPAdapter;
import one.sprint.alexmalvaez.com.sprintone.database.SuperFlingDBManager;
import one.sprint.alexmalvaez.com.sprintone.models.SuperFling;
import one.sprint.alexmalvaez.com.sprintone.services.DownloadImageIntentService;
import one.sprint.alexmalvaez.com.sprintone.util.RequestQueueSingleton;
import one.sprint.alexmalvaez.com.sprintone.views.VerticalViewPager;


public class MainActivity extends FragmentActivity { //AppCompatActivity {

    /* URLs */
    private static final String FLING_URL_JSON_ARRAY = "http://challenge.superfling.com";
    private static final String URL_PHOTO_STREAM = "http://challenge.superfling.com/photos";

    /* Tags used in the JSON String Response  */
    private static final String TAG_ID = "ID";
    private static final String TAG_IMAGE_ID = "ImageID";
    private static final String TAG_TITLE = "Title";
    private static final String TAG_USER_ID = "UserID";
    private static final String TAG_USER_NAME = "UserName";

    private SuperFlingDBManager sfDBManager;
    private ArrayList<SuperFling> superFlingList;

    private long enqueue;
    private DownloadManager dm;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private SuperFlingAdapter adapter;

    /**
     * The pager widget, which handles animation and allows swiping horizontally
     * to access previous and next pages.
     */
    VerticalViewPager verticalViewPager = null;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    SuperFlingSSPAdapter superFlingSSPAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(MainActivity.class.getSimpleName(), "UNO");

        sfDBManager = SuperFlingDBManager.getSuperFlingDBManager(getApplicationContext());

        Log.d(MainActivity.class.getSimpleName(), "DOS");

        // Instantiate a ViewPager and a PagerAdapter.
        verticalViewPager = (VerticalViewPager) findViewById(R.id.pager);

        Log.d(MainActivity.class.getSimpleName(), "TRES");

        superFlingSSPAdapter = new SuperFlingSSPAdapter(getSupportFragmentManager());

        Log.d(MainActivity.class.getSimpleName(), "CUATRO");

        superFlingList = new ArrayList<SuperFling>();

        Log.d(MainActivity.class.getSimpleName(), "CINCO superFlingList: " + superFlingList.size());

        superFlingList.add(new SuperFling(" ", " ", " ", " ", " "));

        Log.d(MainActivity.class.getSimpleName(), "SEIS superFlingList: " + superFlingList.size());

        superFlingSSPAdapter.setSuperFlingList(superFlingList);

        Log.d(MainActivity.class.getSimpleName(), "SIETE");

        verticalViewPager.setAdapter(superFlingSSPAdapter);

        Log.d(MainActivity.class.getSimpleName(), "OCHO");

        //initiateUI();
        makeJsonArrayReq();

        Log.d(MainActivity.class.getSimpleName(), "NUEVE");
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

    public void loadDataIntoDB(){
        Log.d(MainActivity.class.getSimpleName(), " loadDataIntoDB() ");
        int count = 0;

        for (int i = 0; i < superFlingList.size(); i++) {

            long rowId = sfDBManager.addSuperFling(superFlingList.get(i));

            if (rowId == -1) {
                Toast.makeText(this, "There is a problem adding SuperFling", Toast.LENGTH_SHORT).show();
                //Make a rollback
            } else {
                count++;
            }
        }

        Toast.makeText(this, count + " SuperFlings added to the DB", Toast.LENGTH_SHORT).show();
        loadImagesIntoDB(superFlingList);
    }

    public void printSuperFlingData(){
        Log.d(MainActivity.class.getSimpleName(), "ENTRANDO A OBTENER DATOS ");
        Cursor superFlingRecords = sfDBManager.getAllSuperFling();

        if(superFlingRecords.getCount() == 0){
            Log.d(MainActivity.class.getSimpleName(), "NOTHING TO DISPLAY");
        } else {
            Log.d(MainActivity.class.getSimpleName(), "SE OBTUVIERON DATOS ");
            ArrayList<SuperFling> superFlings = new ArrayList<>();

            while (superFlingRecords.moveToNext()) {
                SuperFling sf = new SuperFling(superFlingRecords.getString(0),
                                               superFlingRecords.getString(1),
                                               superFlingRecords.getString(2),
                                               superFlingRecords.getString(3),
                                               superFlingRecords.getString(4));
                superFlings.add(sf);
            }

            superFlingRecords.close();
            sfDBManager.close();

            superFlingSSPAdapter.setSuperFlingList(superFlings);
            superFlingSSPAdapter.notifyDataSetChanged();
            for(int i=0; i<superFlings.size(); i++){
                Log.d(MainActivity.class.getSimpleName(), "SUPERFLING " + i + ": " + superFlings.get(i));
            }
        }
    }

    public void makeJsonArrayReq(){
        Log.d(MainActivity.class.getSimpleName(), " makeJsonArrayReq() ");
        JsonArrayRequest jsonArrayReq = new JsonArrayRequest(
                FLING_URL_JSON_ARRAY,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        Log.d(MainActivity.class.getSimpleName(), " JsonArrayRequest -> onResponse() ");
                        //Log.d(MainActivity.class.getSimpleName(), "RESPUESTA: " + jsonArray.toString());
                        //Toast.makeText(getApplicationContext(), "RESPUESTA: " + jsonArray.toString(), Toast.LENGTH_LONG).show();

                        if(jsonArray != null && jsonArray.length() > 0) {

                            try {

                                superFlingList = new ArrayList<SuperFling>();

                                for(int i=0; i<jsonArray.length(); i++){

                                    JSONObject jo = jsonArray.getJSONObject(i);
                                    SuperFling sf = new SuperFling(jo.getString(TAG_ID), jo.getString(TAG_IMAGE_ID), jo.getString(TAG_TITLE), jo.getString(TAG_USER_ID), jo.getString(TAG_USER_NAME));
                                    //Log.d(MainActivity.class.getSimpleName(), "SUPERFLING " + i + ": " + sf);

                                    superFlingList.add(sf);

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.e("ServiceHandler", "Couldn't get any data from the url");
                        }
                        if(superFlingList != null && superFlingList.size() > 0) {
                            loadDataIntoDB();
                            printSuperFlingData();
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

        // Adding request to request queue
        RequestQueueSingleton.getInstance().addToRequestQueue(jsonArrayReq, "", getApplicationContext());
    }

    public void loadImagesIntoDB(ArrayList<SuperFling> superFlings){
        Log.d(MainActivity.class.getSimpleName(), " loadImagesIntoDB() ");

        Display display = getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();

        Log.d(MainActivity.class.getSimpleName(), " width: " + width + " " + "height: " + height);

        for(int i=0; i<superFlings.size(); i++) {
            Intent intent = new Intent(this, DownloadImageIntentService.class);
            intent.putExtra(DownloadImageIntentService.URL_EXTRA_STR, URL_PHOTO_STREAM);
            intent.putExtra(DownloadImageIntentService.IMAGE_ID_EXTRA_STR, superFlings.get(i).imageId);
            startService(intent);
        }

        /*
        for(int i=0; i<superFlings.size(); i++) {

            final String idImage = superFlings.get(i).imageId;
            ImageRequest request = new ImageRequest(URL_PHOTO_STREAM + "/" + superFlings.get(i).imageId,
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap bitmap) {

                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 50, bos);
                            byte[] bArray = bos.toByteArray();

                            sfDBManager.updateImageById(idImage, bArray);
                            bArray = null;
                            bitmap = null;
                            System.gc();
                            Log.e(MainActivity.class.getSimpleName(), " IMAGE: " + idImage + " UPDATED");

                        }
                    }, width/2, height/2, null,
                    new Response.ErrorListener() {
                        public void onErrorResponse(VolleyError error) {
                           Log.e(MainActivity.class.getSimpleName(), "ERROR WHEN DOWNLOADING IMAGE: "+ idImage + "\n"+ error.getMessage());
                        }
                    });

            //Access the RequestQueue through your singleton class.
            RequestQueueSingleton.getInstance().addToRequestQueue(request, "", getApplicationContext());


        }
        Toast.makeText(this, superFlings.size() + " SuperFlings images added to the DB", Toast.LENGTH_SHORT).show();
        */

    }

    @Override
    public void onBackPressed() {

        // Return to previous page when we press back button
        if (this.verticalViewPager.getCurrentItem() == 0)
            super.onBackPressed();
        else
            this.verticalViewPager.setCurrentItem(this.verticalViewPager.getCurrentItem() - 1);

    }

}
