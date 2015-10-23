package one.sprint.alexmalvaez.com.sprintone.database.interfaces;

import android.database.Cursor;
import android.graphics.Bitmap;

import one.sprint.alexmalvaez.com.sprintone.models.SuperFling;

/**
 * Created by Android1 on 10/20/2015.
 */
public interface SuperFlingDBInterface {
    public long addSuperFling(SuperFling superFling);
    public Cursor getSuperFlingById(String id);
    public Cursor getAllSuperFling();
    public int updateImageById(String imageId, byte[] bArray);
    public Bitmap getImageById(String imageId);
}
