package app.pranavjayaraj.apod.UI;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.transition.Fade;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import app.pranavjayaraj.apod.R;
import app.pranavjayaraj.apod.ViewModel.VModel;
import app.pranavjayaraj.apod.ViewModel.ViewModelCallbacks;


public class MainActivity extends AppCompatActivity{
    private static final String TAG_PICTURE_LIST_FRAGMENT = "tag_picture_list_fragment";
    private VModel mVModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mVModel = ViewModelProviders.of(this).get(VModel.class);
        loadInitialData();
    }

    private void loadInitialData(){
        mVModel.loadInitialData(new ViewModelCallbacks() {
            @Override
            public void onResponse(String message) {
                attachPictureListFragment();
                Log.d("FLOW TEST", "ON RESPONSE RECEIVED IN MAINACTIVITY: " + message);
            }

            @Override
            public void onFailure(Throwable throwable) {
                throwable.printStackTrace();
                Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_LONG)
                        .show();
                Log.d("FLOW TEST", "ON FAILURE RECEIVED IN MAINACTIVITY: " + throwable.getMessage());
            }
        });
    }



    private void attachPictureListFragment() {
        APODListFragment apodListFragment = new APODListFragment();
        apodListFragment.setEnterTransition(new Fade());

        Bundle args = new Bundle();
        apodListFragment.setArguments(args);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container,
                        apodListFragment,
                        APODListFragment.class.getSimpleName())
                .addToBackStack(TAG_PICTURE_LIST_FRAGMENT)
                .commit();
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    @Override
    public void onBackPressed() {
        Log.d("BACKSTACKTEST", ""+ getSupportFragmentManager().getBackStackEntryCount());
        if(getSupportFragmentManager().getBackStackEntryCount() == 1){
            finish();
        }else{
            super.onBackPressed();
        }
    }

}
