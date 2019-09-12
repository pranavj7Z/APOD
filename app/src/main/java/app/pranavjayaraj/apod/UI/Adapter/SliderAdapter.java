package app.pranavjayaraj.apod.UI.Adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import app.pranavjayaraj.apod.Model.Image;
import app.pranavjayaraj.apod.UI.APODImageView;

/**
 * Created by Pranav on 12/9/19.
 */

public class SliderAdapter extends FragmentStatePagerAdapter {

    private List<Image> mPictureList;

    public SliderAdapter(FragmentManager fm, List<Image> pictureList) {
        super(fm);
        mPictureList = pictureList;
    }

    @Override
    public Fragment getItem(int i) {
        APODImageView apodImageView = new APODImageView();

        Bundle args = new Bundle();
        args.putInt(APODImageView.EXTRA_KEY_IMAGE_URL, i);
        apodImageView.setArguments(args);

        return apodImageView;
    }

    @Override
    public int getCount() {
        return mPictureList.size();
    }
}