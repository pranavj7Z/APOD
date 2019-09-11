package app.pranavjayaraj.apod.UI;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import app.pranavjayaraj.apod.R;
import app.pranavjayaraj.apod.Util.NetworkUtil;

/**
 * Created by kuttanz on 11/9/19.
 */

public class APODImageView extends android.support.v4.app.Fragment{
    public static final String EXTRA_KEY_IMAGE_URL = "extra_key_image_url";
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL = 1;

    private PhotoView mFullScreenPhotoView;
    private String mImageUrl;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_view, container, false);

        mFullScreenPhotoView = view.findViewById(R.id.pv_picture_fullscreen);

        if(getArguments() != null){
            mImageUrl = getArguments().getString(EXTRA_KEY_IMAGE_URL);
            mImageUrl = NetworkUtil.validateUrl(mImageUrl);
        }

        Picasso.get().load(mImageUrl).fit().centerInside().into(mFullScreenPhotoView);

        return view;

}
}
