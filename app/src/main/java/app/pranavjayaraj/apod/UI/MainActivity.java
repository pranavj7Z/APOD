package app.pranavjayaraj.apod.UI;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import app.pranavjayaraj.apod.R;


public class MainActivity extends AppCompatActivity implements FragmentChangeListener{
    @Override
    public void attachDetailViewPager(int clickedPosition, ImageView sharedImageView) {

    }

    @Override
    public void attachFullScreenFragment(String url, ImageView sharedImageView) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadInitialData();
    }

    private void loadInitialData(){

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
