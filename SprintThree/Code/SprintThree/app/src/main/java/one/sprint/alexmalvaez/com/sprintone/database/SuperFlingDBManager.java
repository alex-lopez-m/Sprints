package one.sprint.alexmalvaez.com.sprintone.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import one.sprint.alexmalvaez.com.sprintone.database.interfaces.SuperFlingDBInterface;
import one.sprint.alexmalvaez.com.sprintone.models.SuperFling;

/**
 * Created by Android1 on 10/20/2015.
 */
public class SuperFlingDBManager implements SuperFlingDBInterface{

    private SQLiteDatabase dataBase;
    private final Context context;
    private final SuperFlingDBHelper sfDBHelper;

    public SuperFlingDBManager(Context context){
        this.context = context;
        sfDBHelper = new SuperFlingDBHelper(this.context);
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

        long rowId = dataBase.insert(SuperFlingDBSquema.TABLE_NAME, null, values);

        close();

        return rowId;
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

        cursor = dataBase.query(SuperFlingDBSquema.TABLE_NAME, columns, selection,
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

        cursor = dataBase.query(SuperFlingDBSquema.TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);

        Log.d(SuperFlingDBManager.class.getSimpleName(), "CURSOR OBTENIDO CON: " + cursor.getCount());
        return cursor;
    }
}
