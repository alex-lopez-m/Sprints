package com.alexmalvaez.example;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.IOException;


public class MainActivity extends ActionBarActivity {

    ImageView imageView1;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView1 = (ImageView) this.findViewById(R.id.imageView1);
        db = this.openOrCreateDatabase("test.db", Context.MODE_PRIVATE, null);
        db.execSQL("create table tb (a blob)");

    }

    public void saveImage(View view){
        try{
            String path = Environment.getExternalStorageDirectory().getAbsolutePath();
            Log.d("PATH", "PATH IMAGE " + path); // "/storage/emulated/0"
            //getApplicationContext().getFilesDir()
            FileInputStream fis = new FileInputStream( path + "/100.jpg");
            byte[] image = new byte[fis.available()];
            fis.read(image);

            ContentValues values = new ContentValues();
            values.put("a", image);
            db.insert("tb", null, values);

            fis.close();

            Toast.makeText(this, "Insert Success", Toast.LENGTH_SHORT).show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void getImage(View view){

        Cursor c = db.rawQuery("select * from tb", null);

        if (c.moveToNext()){

            byte[] image = c.getBlob(0);
            Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
            imageView1.setImageBitmap(bmp);

            Toast.makeText(this, "Select Success", Toast.LENGTH_SHORT).show();
        }

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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
