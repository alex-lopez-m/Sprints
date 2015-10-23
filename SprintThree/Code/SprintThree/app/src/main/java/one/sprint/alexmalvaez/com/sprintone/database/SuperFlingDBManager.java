package one.sprint.alexmalvaez.com.sprintone.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;

import one.sprint.alexmalvaez.com.sprintone.database.interfaces.SuperFlingDBInterface;
import one.sprint.alexmalvaez.com.sprintone.models.SuperFling;

/**
 * Created by Android1 on 10/20/2015.
 */
public class SuperFlingDBManager implements SuperFlingDBInterface{

    private static SuperFlingDBManager sfDBManager;
    private SQLiteDatabase dataBase;
    private SuperFlingDBHelper sfDBHelper;
    private Context context;

    private SuperFlingDBManager(Context context){
        this.context = context;
        sfDBHelper = new SuperFlingDBHelper(this.context);
    }

    public static synchronized SuperFlingDBManager getSuperFlingDBManager(Context context){
        if(sfDBManager == null){
            sfDBManager = new SuperFlingDBManager(context);
        }
        return sfDBManager;
    }

    public void close() {
        dataBase.close();
    }

    public void openOnWritableMode() throws SQLiteException {
        try {
            dataBase = sfDBHelper.getWritableDatabase();
        } catch (SQLiteException ex) {

        }
    }

    public void openOnReadableMode() throws SQLiteException {
        try {
            dataBase = sfDBHelper.getReadableDatabase();
        } catch (SQLiteException ex) {

        }
    }

    @Override
    public long addSuperFling(SuperFling superFling) {

        openOnWritableMode();

        ContentValues values = new ContentValues();
        values.put(SuperFlingDBSquema.Col_Super_Fling_ID, Integer.valueOf(superFling.id));
        values.put(SuperFlingDBSquema.Col_Image_Id, Integer.valueOf(superFling.imageId));
        values.put(SuperFlingDBSquema.Col_Title, superFling.title);
        values.put(SuperFlingDBSquema.Col_User_Id, Integer.valueOf(superFling.userId));
        values.put(SuperFlingDBSquema.Col_User_Name, superFling.userName);

        long rowId = dataBase.insert(SuperFlingDBSquema.TABLE_SUPER_FLING, null, values);

        close();

        return rowId;
    }

    public int updateImageById(String imageId, byte[] bArray){

        openOnWritableMode();

        ContentValues values = new ContentValues();
        values.put(SuperFlingDBSquema.Col_Image_Stream, bArray);

        String[] whereArgs = new String[1];
        whereArgs[0] = imageId;
        String whereClause = " " + SuperFlingDBSquema.Col_Image_Id + "=? ";

        int res = dataBase.update(SuperFlingDBSquema.TABLE_SUPER_FLING, values, whereClause, whereArgs);

        bArray = null;
        System.gc();

        close();

        return res;

    }

    public Bitmap getImageById(String imageId){
        Bitmap bitmap = null;

        openOnReadableMode();

        String[] selectionArgs = new String[1];
        selectionArgs[0] = imageId;

        Cursor c = dataBase.rawQuery("SELECT " + SuperFlingDBSquema.Col_Image_Stream
                                    + " FROM " + SuperFlingDBSquema.TABLE_SUPER_FLING
                                    + " WHERE " + SuperFlingDBSquema.Col_Image_Id + " = ?", selectionArgs);

        if (c.moveToNext()){
            byte[] image = c.getBlob(0);
            if(image != null) {
                bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
            }
        }

        close();

        return bitmap;
    }

    @Override
    public Cursor getSuperFlingById(String id) {
        openOnReadableMode();

        Cursor cursor;

        String[] columns = { SuperFlingDBSquema.Col_Super_Fling_ID, SuperFlingDBSquema.Col_Image_Id,
                SuperFlingDBSquema.Col_Title, SuperFlingDBSquema.Col_User_Id,
                SuperFlingDBSquema.Col_User_Name};
        String selection = SuperFlingDBSquema.Col_Super_Fling_ID + " = " + id;
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;

        cursor = dataBase.query(SuperFlingDBSquema.TABLE_SUPER_FLING, columns, selection,
                selectionArgs, groupBy, having, orderBy);
        return cursor;
    }

    @Override
    public Cursor getAllSuperFling() {

        Log.d(SuperFlingDBManager.class.getSimpleName(), "ENTRANDO A getAllSuperFling");

        openOnReadableMode();

        Cursor cursor;

        String[] columns = { SuperFlingDBSquema.Col_Super_Fling_ID, SuperFlingDBSquema.Col_Image_Id,
                             SuperFlingDBSquema.Col_Title, SuperFlingDBSquema.Col_User_Id,
                             SuperFlingDBSquema.Col_User_Name};
        String selection = null;
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;

        cursor = dataBase.query(SuperFlingDBSquema.TABLE_SUPER_FLING, columns, selection, selectionArgs, groupBy, having, orderBy);

        Log.d(SuperFlingDBManager.class.getSimpleName(), "CURSOR OBTENIDO CON: " + cursor.getCount());
        return cursor;
    }
}
