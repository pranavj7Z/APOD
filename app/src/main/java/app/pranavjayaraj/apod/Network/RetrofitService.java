package app.pranavjayaraj.apod.Network;

import java.util.List;

import app.pranavjayaraj.apod.Model.Image;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by pranav.
 */


public interface RetrofitService {

    String API_KEY = "ghVo0DzOoUgLGI1eqFc55sd5faiVEZpHZbu31tEQ";

    @GET("planetary/apod?api_key=" + API_KEY)
    Single<Image> getLatestPicture();

    //String date is in YYYY-MM-DD format
    @GET("planetary/apod?api_key=" + API_KEY)
    Single<List<Image>> getPicturesFromDateRange (@Query("start_date") String startDate, @Query("end_date") String endDate);

}
