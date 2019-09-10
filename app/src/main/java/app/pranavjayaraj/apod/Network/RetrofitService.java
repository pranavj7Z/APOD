package app.pranavjayaraj.apod.Network;

import java.util.List;

import app.pranavjayaraj.apod.Model.Image;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by kuttanz on 10/9/19.
 */


public interface RetrofitService {

    String API_KEY = "ghVo0DzOoUgLGI1eqFc55sd5faiVEZpHZbu31tEQ";

    @GET("planetary/apod?api_key=" + API_KEY)
    Call<Image> getLatestPicture();

    //String date is in YYYY-MM-DD format
    @GET("planetary/apod?api_key=" + API_KEY)
    Call<List<Image>> getPicturesFromDateRange (@Query("start_date") String startDate, @Query("end_date") String endDate);

}
