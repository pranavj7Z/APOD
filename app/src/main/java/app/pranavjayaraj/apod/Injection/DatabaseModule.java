package app.pranavjayaraj.apod.Injection;

import android.app.Application;
import android.arch.persistence.room.Room;

import javax.inject.Singleton;

import app.pranavjayaraj.apod.Database.AppDAO;
import app.pranavjayaraj.apod.Database.AppDatabase;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Pranav on 10/9/19.
 */


@Module
public class DatabaseModule {

    @Provides
    @Singleton
    AppDatabase provideAppDatabase(Application application){
        AppDatabase appDatabase = Room.databaseBuilder(application,
                AppDatabase.class,
                "app_database")
                .build();
        return appDatabase;
    }

    @Provides
    @Singleton
    AppDAO provideAppDao(AppDatabase appDatabase){
        return appDatabase.appDao();
    }
}
