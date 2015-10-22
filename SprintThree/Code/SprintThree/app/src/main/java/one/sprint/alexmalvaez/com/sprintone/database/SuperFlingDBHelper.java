package one.sprint.alexmalvaez.com.sprintone.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Android1 on 10/20/2015.
 */
public class SuperFlingDBHelper extends SQLiteOpenHelper {

    public SuperFlingDBHelper(Context context) {
        super(context, SuperFlingDBSquema.DATABASE_NAME, null, SuperFlingDBSquema.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlString = "CREATE TABLE " + SuperFlingDBSquema.TABLE_SUPER_FLING + "("
                + SuperFlingDBSquema.Col_Super_Fling_ID + " INTEGER PRIMARY KEY , "
                + SuperFlingDBSquema.Col_Image_Id + " INTEGER , "
                + SuperFlingDBSquema.Col_Title + " TEXT , "
                + SuperFlingDBSquema.Col_User_Id + " INTEGER , "
                + SuperFlingDBSquema.Col_User_Name + " TEXT , "
                + SuperFlingDBSquema.Col_Image_Stream + " BLOB "
                + ")";
        db.execSQL(sqlString);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion >= newVersion) {
            return;
        }
        String sqlString = "DROP TABLE IF EXISTS " + SuperFlingDBSquema.TABLE_SUPER_FLING;
        db.execSQL(sqlString);
        onCreate(db);
    }
}
