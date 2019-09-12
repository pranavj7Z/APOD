package app.pranavjayaraj.apod.UI.ViewPager;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

import app.pranavjayaraj.apod.Model.Image;
import app.pranavjayaraj.apod.R;
import app.pranavjayaraj.apod.UI.Adapter.SliderAdapter;
import app.pranavjayaraj.apod.ViewModel.VModel;

/**
 * Created by Pranav on 12/9/19.
 */

public class APODViewPager extends Fragment {

    private ViewPager mViewPager;
    private SliderAdapter mPagerAdapter;
    private ProgressBar mProgressBar;
    private VModel mSharedViewModel;
    private String lastVisibleDate;
    private boolean isLoadingMoreData;


    public static final String EXTRA_KEY_CLICKED_POSITION = "extra_key_clicked_position";

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(isLoadingMoreData){
            mProgressBar.setVisibility(View.VISIBLE);
        }
        else{
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mSharedViewModel = ViewModelProviders.of(getActivity()).get(VModel.class);

        View view = inflater.inflate(R.layout.view_pager, container, false);

        mViewPager = view.findViewById(R.id.fullscreen);
        mProgressBar = view.findViewById(R.id.loading);

        connectViewModel();

        connectPageChangeListener();

        return view;
    }


    private void connectViewModel(){
        mSharedViewModel.getPictureList().observe(this, new Observer<List<Image>>() {
            @Override
            public void onChanged(@Nullable List<Image> pictureList) {
                isLoadingMoreData = false;
                mProgressBar.setVisibility(View.GONE);

                lastVisibleDate = pictureList.get(pictureList.size()-1).getDate();

                mPagerAdapter = new SliderAdapter(getChildFragmentManager(), pictureList);
                mViewPager.setAdapter(mPagerAdapter);

                setCurrentPage();
            }
        });
    }

    private void setCurrentPage(){
        mViewPager.setCurrentItem(mSharedViewModel.getCurrentPosition());
    }

    private void connectPageChangeListener(){
        ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                mSharedViewModel.setCurrentPosition(i);
                if(!isLoadingMoreData &&(i == (mPagerAdapter.getCount()) - 1)){
                    mSharedViewModel.loadMoreData(lastVisibleDate);

                    isLoadingMoreData = true;
                    mProgressBar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        };
        mViewPager.addOnPageChangeListener(onPageChangeListener);
    }

    @Override
    public void onPause() {
        mViewPager.clearOnPageChangeListeners();
        super.onPause();
    }

}