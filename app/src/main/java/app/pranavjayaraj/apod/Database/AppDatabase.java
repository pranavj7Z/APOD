package app.pranavjayaraj.apod.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import app.pranavjayaraj.apod.Model.Image;

/**
 * Created by pranav.
 */


@Database(entities = {Image.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    static final int VERSION = 1;

    public abstract AppDAO appDao();

}