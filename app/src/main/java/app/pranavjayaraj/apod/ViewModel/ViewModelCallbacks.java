package app.pranavjayaraj.apod.ViewModel;

/**
 * Created by kuttanz on 10/9/19.
 */


public interface ViewModelCallbacks {

    void onResponse(String message);

    void onFailure(Throwable throwable);

}
