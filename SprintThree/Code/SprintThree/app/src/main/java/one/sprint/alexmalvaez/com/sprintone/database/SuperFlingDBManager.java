package one.sprint.alexmalvaez.com.sprintone.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

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

    public void open() throws SQLiteException {
        try {
            dataBase = sfDBHelper.getWritableDatabase();
        } catch (SQLiteException ex) {

        }
    }

    @Override
    public int addSuperFling(SuperFling superFling) {
        return 0;
    }

    @Override
    public Cursor getSuperFlingById(String id) {
        return null;
    }

    @Override
    public Cursor getAllSuperFling() {
        return null;
    }
}
