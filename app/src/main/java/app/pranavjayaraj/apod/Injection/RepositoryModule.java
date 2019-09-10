package app.pranavjayaraj.apod.Injection;

import javax.inject.Singleton;

import app.pranavjayaraj.apod.Database.AppDAO;
import app.pranavjayaraj.apod.Network.RetrofitService;
import app.pranavjayaraj.apod.Repository.Repository;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Pranav on 10/9/19.
 */


@Module
public class RepositoryModule {

    @Provides
    @Singleton
    public Repository provideRepository(RetrofitService retrofitService, AppDAO appDao){
        Repository repository = new Repository(retrofitService, appDao);
        return repository;
    }

}
