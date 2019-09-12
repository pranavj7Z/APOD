package app.pranavjayaraj.apod.UI;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import app.pranavjayaraj.apod.R;

/**
 * Created by Pranav on 12/9/19.
 */

public class ProgressFragment extends Fragment {
 private ProgressBar mProgressBar;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.progress, container, false);

        mProgressBar = view.findViewById(R.id.progress);

        return view;
    }


}