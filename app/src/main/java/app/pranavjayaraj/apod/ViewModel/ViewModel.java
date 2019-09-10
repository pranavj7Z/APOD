package app.pranavjayaraj.apod.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;
import app.pranavjayaraj.apod.Model.Image;
import app.pranavjayaraj.apod.Repository.Repository;
import app.pranavjayaraj.apod.Repository.RepositoryCallbacks;

/**
 * Created by Pranav.
 */

public class ViewModel extends AndroidViewModel {

    @Inject
    public Repository repository;
    private LiveData<List<Image>> mPictureList;
    private int mCurrentPosition;
    private boolean mHasAccessedViewPager;

    public ViewModel(Application application){
        super(application);
        repository.clearDatabase();
    }

    public LiveData<List<Image>> getPictureList() {
        if(mPictureList == null){
            mPictureList = new MutableLiveData<>();
            mPictureList = repository.getPictureList();
        }
        return mPictureList;
    }


    public void loadInitialData(final ViewModelCallbacks viewModelCallbacks){
        Log.d("FLOW TEST", " LOAD DATA IN VM CALLED");
        repository.retrieveLatestData(new RepositoryCallbacks() {
            @Override
            public void onResponse(String message) {
                Log.d("FLOW TEST", "ON RESPONSE RECEIVED IN VIEWMODEL: " + message);
                viewModelCallbacks.onResponse(message);

            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.d("FLOW TEST", "ON FAILURE RECEIVED IN VIEWMODEL: " + throwable.getMessage());
                throwable.printStackTrace();
                viewModelCallbacks.onFailure(throwable);

            }
        });
    }

    public void loadMoreData(String lastVisibleDate){
        repository.loadMoreData(lastVisibleDate);
    }

}
