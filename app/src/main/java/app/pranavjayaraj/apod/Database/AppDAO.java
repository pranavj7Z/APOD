package app.pranavjayaraj.apod.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import app.pranavjayaraj.apod.Model.Image;

/**
 * Created by pranav.
 */


@Dao
public interface AppDAO {
    @Query("SELECT * FROM picture")
    LiveData<List<Image>> getAll();

    @Query("SELECT * FROM picture WHERE date LIKE :queryDate")
    Image getPictureByDate(String queryDate);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Image> pictureList);

    @Query("DELETE FROM picture")
    void deleteAll();
}
