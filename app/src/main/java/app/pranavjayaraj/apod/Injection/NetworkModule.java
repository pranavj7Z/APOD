package app.pranavjayaraj.apod.Injection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import app.pranavjayaraj.apod.Network.RetrofitService;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Pranav on 10/9/19.
 */
@Module
public class NetworkModule {

    @Provides
    @Singleton
    public Gson provideGson(){
        Gson gson = new GsonBuilder().create();
        return gson;
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(Gson gson){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.nasa.gov/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit;
    }

    @Provides
    @Singleton
    public RetrofitService provideRetrofitApi(Retrofit retrofit){
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        return retrofitService;
    }
}
