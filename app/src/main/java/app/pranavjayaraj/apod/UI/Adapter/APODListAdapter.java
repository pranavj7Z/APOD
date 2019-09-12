package app.pranavjayaraj.apod.UI.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import app.pranavjayaraj.apod.Model.Image;
import app.pranavjayaraj.apod.R;
import app.pranavjayaraj.apod.Repository.Repository;
import app.pranavjayaraj.apod.UI.FragmentChangeListener;
import app.pranavjayaraj.apod.UI.MainActivity;
import app.pranavjayaraj.apod.Util.NetworkUtil;

/**
 * Created by Pranav on 10/9/19.
 */


public class APODListAdapter extends android.support.v7.recyclerview.extensions.ListAdapter<Image, APODListAdapter.ViewHolder> {
    private Context mContext;
    Repository repository;
    private FragmentChangeListener mFragmentChangeListener;

    public APODListAdapter(Context context, @NonNull DiffUtil.ItemCallback<Image> diffCallback) {
        super(diffCallback);
        mContext = context;
        mFragmentChangeListener = (MainActivity) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final Image picture = getItem(i);
        final int position = viewHolder.getAdapterPosition();

        viewHolder.mPictureCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                mFragmentChangeListener.attachDetailAPOD(position, viewHolder.mPictureImageView);
            }
        });

        String url = NetworkUtil.validateUrl(picture.getUrl());
        Picasso.get().load(url).fit().centerCrop().into(viewHolder.mPictureImageView);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final CardView mPictureCardView;
        final ImageView mPictureImageView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mPictureCardView = itemView.findViewById(R.id.cv_picture);
            mPictureImageView = itemView.findViewById(R.id.iv_picture);
        }
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public int getItemViewType(int position)
    {
        return 1;
    }

}
