package app.pranavjayaraj.apod.UI;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.transition.TransitionInflater;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import java.util.List;

import app.pranavjayaraj.apod.Model.Image;
import app.pranavjayaraj.apod.R;
import app.pranavjayaraj.apod.Util.NetworkUtil;
import app.pranavjayaraj.apod.ViewModel.VModel;

/**
 * Created by kuttanz on 11/9/19.
 */

public class APODImageView extends android.support.v4.app.Fragment
{
    public static final String EXTRA_KEY_IMAGE_URL = "extra_key_image_url";
    private PhotoView mFullScreenPhotoView;
    private int mPosition;
    private String URL;
    private VModel mSharedViewModel;


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
            mPosition = getArguments().getInt(EXTRA_KEY_IMAGE_URL);
            connectViewModel();
        }


        return view;

}

    private void connectViewModel(){
        mSharedViewModel = ViewModelProviders.of(getActivity()).get(VModel.class);
        mSharedViewModel.getPictureList().observe(this, new Observer<List<Image>>() {
            @Override
            public void onChanged(@Nullable List<Image> pictureList) {
                URL = NetworkUtil.validateUrl(pictureList.get(mPosition).getUrl());
        Picasso.get().load(URL).fit().centerInside().into(mFullScreenPhotoView);
            }
        });
    }

}
