package app.pranavjayaraj.apod.UI;

import android.widget.ImageView;

/**
 * Created by Pranav on 10/9/19.
 */

public interface FragmentChangeListener {

    void attachDetailAPOD (int clickedPosition, ImageView sharedImageView);

    void attachImageView(int clickedPosition, ImageView sharedImageView);

}
