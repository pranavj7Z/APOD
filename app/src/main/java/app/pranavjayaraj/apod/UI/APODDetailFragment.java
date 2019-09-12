package app.pranavjayaraj.apod.UI;

import android.support.v4.app.Fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import app.pranavjayaraj.apod.Model.Image;
import app.pranavjayaraj.apod.R;
import app.pranavjayaraj.apod.Util.NetworkUtil;
import app.pranavjayaraj.apod.ViewModel.VModel;

/**
 * Created by Pranav on 11/9/19.
 */
public class APODDetailFragment extends Fragment {

    public static final String EXTRA_KEY_PICTURE_POSITION = "extra_key_picture_position";

    private ImageView mDetailPictureImageView;
    private TextView mDescriptionTextView;
    private TextView mTitleTextView;
    private TextView mCopyrightTextView;

    private VModel mSharedViewModel;
    private FragmentChangeListener fragmentChangeListener;
    private int mPosition;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_apod_detail, container, false);
        mDescriptionTextView = view.findViewById(R.id.tv_detail_description);
        mDetailPictureImageView = view.findViewById(R.id.iv_detail_picture);
        mTitleTextView = view.findViewById(R.id.tv_title);
        mCopyrightTextView = view.findViewById(R.id.tv_copyright);

        if(getArguments() != null) {
            mPosition = getArguments().getInt(EXTRA_KEY_PICTURE_POSITION);
        }

        fragmentChangeListener = (MainActivity) getActivity();
        connectViewModel();

        return view;
    }

    private void connectViewModel(){
        mSharedViewModel = ViewModelProviders.of(getActivity()).get(VModel.class);
        mSharedViewModel.getPictureList().observe(this, new Observer<List<Image>>() {
            @Override
            public void onChanged(@Nullable List<Image> pictureList) {
                connectImageViewOnClickListener(pictureList);
                updateUi(pictureList);
            }
        });
    }

    private void updateUi(List<Image> pictureList){
        mDescriptionTextView.setText(pictureList.get(mPosition).getExplanation());
        mTitleTextView.setText(pictureList.get(mPosition).getTitle());

        String url = NetworkUtil.validateUrl(pictureList.get(mPosition).getUrl());
        Picasso.get().load(url).fit().centerCrop().into(mDetailPictureImageView);

        if(pictureList.get(mPosition).getCopyright() != null) {
            mCopyrightTextView.setText(pictureList.get(mPosition).getCopyright());
        }
        else{
            mCopyrightTextView.setVisibility(View.GONE);
        }
    }

    private void connectImageViewOnClickListener(final List<Image> pictureList){
        mDetailPictureImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = mPosition;
                fragmentChangeListener.attachImageView(position, mDetailPictureImageView);
            }
        });
    }
}