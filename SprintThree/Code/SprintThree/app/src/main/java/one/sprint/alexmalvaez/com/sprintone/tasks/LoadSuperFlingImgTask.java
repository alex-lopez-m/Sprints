package one.sprint.alexmalvaez.com.sprintone.tasks;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import one.sprint.alexmalvaez.com.sprintone.R;
import one.sprint.alexmalvaez.com.sprintone.database.SuperFlingDBManager;

/**
 * Created by Android1 on 10/27/2015.
 */
public class LoadSuperFlingImgTask extends AsyncTask<ImageView, Void, ImageView> {

    private Context context;
    private String imageId;
    private Bitmap bitmap;

    public LoadSuperFlingImgTask(Context context, String imageId){
        this.context = context;
        this.imageId = imageId;
        bitmap = null;
    }

    @Override
    protected ImageView doInBackground(ImageView... imageViews) {
        ImageView imageView = imageViews[0];

        bitmap = SuperFlingDBManager.getSuperFlingDBManager(context).getImageById(imageId);

        return imageView;
    }


    @Override
    protected void onPostExecute(ImageView imageView) {
        super.onPostExecute(imageView);
        if(bitmap != null) {
            imageView.setImageBitmap(bitmap);
        }else{
            imageView.setImageResource(R.drawable.android);
        }
    }
}
