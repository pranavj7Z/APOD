package app.pranavjayaraj.apod.UI;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;
import android.support.v7.recyclerview.extensions.ListAdapter;
import app.pranavjayaraj.apod.Model.Image;
import app.pranavjayaraj.apod.R;
import app.pranavjayaraj.apod.ViewModel.VModel;

/**
 * Created by Pranav on 10/9/19.
 */

public class APODListFragment extends Fragment {

    private VModel mVModel;
    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;
    private GridLayoutManager mGridLayoutManager;
    private ListAdapter mListAdapter;
    private String lastVisibleDate;
    private boolean isLoadingMoreData;

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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mVModel = ViewModelProviders.of(getActivity()).get(VModel.class);

        View view = inflater.inflate(R.layout.fragment_apod_list, container, false);

        mProgressBar = view.findViewById(R.id.pb_loading_list);

        connectRecyclerView(view);

        connectViewModel();

        return view;
    }

    private void connectViewModel() {
        mVModel.getPictureList().observe(this, new Observer<List<Image>>() {
            @Override
            public void onChanged(@Nullable List<Image> pictureList) {
                isLoadingMoreData = false;
                mProgressBar.setVisibility(View.GONE);

                mListAdapter.submitList(pictureList);
                lastVisibleDate = pictureList.get(pictureList.size()-1).getDate();
            }
        });
    }

    private void connectRecyclerView(View view) {
        mRecyclerView = view.findViewById(R.id.rv_pictures);
        mListAdapter = new APODListAdapter(getActivity(), new DiffUtil.ItemCallback<Image>() {
            @Override
            public boolean areItemsTheSame(@NonNull Image picture, @NonNull Image t1) {
                return picture.getDate().equals(t1.getDate());
            }

            @Override
            public boolean areContentsTheSame(@NonNull Image picture, @NonNull Image t1) {
                return picture.getDate().equals(t1.getDate());
            }
        });
            mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
            mRecyclerView.setLayoutManager(mGridLayoutManager);

        mRecyclerView.setAdapter(mListAdapter);

        RecyclerView.OnScrollListener mScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int lastVisiblePosition;
                lastVisiblePosition = mGridLayoutManager.findLastCompletelyVisibleItemPosition();

                if (!isLoadingMoreData && (lastVisiblePosition == (mListAdapter.getItemCount() - 1))) {
                    mVModel.loadMoreData(lastVisibleDate);

                    isLoadingMoreData = true;
                    mProgressBar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        };

        mRecyclerView.addOnScrollListener(mScrollListener);
    }


    @Override
    public void onPause() {
        mRecyclerView.clearOnScrollListeners();
        super.onPause();
    }
}

