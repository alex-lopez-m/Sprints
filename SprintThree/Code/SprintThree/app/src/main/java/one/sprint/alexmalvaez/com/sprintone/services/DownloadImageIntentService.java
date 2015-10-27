package one.sprint.alexmalvaez.com.sprintone.services;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

import one.sprint.alexmalvaez.com.sprintone.database.SuperFlingDBManager;
import one.sprint.alexmalvaez.com.sprintone.database.interfaces.SuperFlingDBInterface;

/**
 * Created by Android1 on 10/23/2015.
 */
public class DownloadImageIntentService extends IntentService {

    public static String URL_EXTRA_STR = "URL_STR";
    public static String IMAGE_ID_EXTRA_STR = "IMAGE_STR";
    private SuperFlingDBManager sfDBManager;
    //private SuperFlingDBInterface superFlingDBInterface;

    public DownloadImageIntentService(/*SuperFlingDBInterface superFlingDBInterface */){
        super("DownloadImageIntentService");
        //this.superFlingDBInterface = superFlingDBInterface;
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        String urlStr = intent.getStringExtra(URL_EXTRA_STR);
        String idImageStr = intent.getStringExtra(IMAGE_ID_EXTRA_STR);

        try {

            URL imageURL = new URL(urlStr + "/" + idImageStr);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 1;
            Bitmap bitmap = BitmapFactory.decodeStream(imageURL.openStream(), null, options);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 70, bos);
            byte[] bArray = bos.toByteArray();

            SuperFlingDBManager.getSuperFlingDBManager(getApplicationContext()).updateImageById(idImageStr, bArray);

        }catch(IOException e){

        }
    }
}
