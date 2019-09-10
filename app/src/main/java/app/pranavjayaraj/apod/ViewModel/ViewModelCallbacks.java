package app.pranavjayaraj.apod.ViewModel;

/**
 * Created by Pranav.
 */


public interface ViewModelCallbacks
{

    void onResponse(String message);

    void onFailure(Throwable throwable);

}
