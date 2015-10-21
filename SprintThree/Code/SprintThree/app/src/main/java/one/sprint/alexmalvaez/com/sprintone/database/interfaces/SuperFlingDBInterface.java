package one.sprint.alexmalvaez.com.sprintone.database.interfaces;

import android.database.Cursor;

import one.sprint.alexmalvaez.com.sprintone.models.SuperFling;

/**
 * Created by Android1 on 10/20/2015.
 */
public interface SuperFlingDBInterface {
    public int addSuperFling(SuperFling superFling);
    public Cursor getSuperFlingById(String id);
    public Cursor getAllSuperFling();
}
