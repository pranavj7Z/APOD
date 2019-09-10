package app.pranavjayaraj.apod.UI;

import android.widget.ImageView;

/**
 * Created by kuttanz on 10/9/19.
 */

interface FragmentChangeListener {

    void attachDetailViewPager (int clickedPosition, ImageView sharedImageView);

    void attachFullScreenFragment(String url, ImageView sharedImageView);

}
