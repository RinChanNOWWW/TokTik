package com.rinchannow.toktik;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.VideoInfoItemHolder> {

    private final ListItemClickListener mOnClickListener;

    private List<VideoResponse.VideoData> dataList = new ArrayList<>();

    public MainListAdapter(ListItemClickListener listener) {
        mOnClickListener = listener;
    }

    public void setDataList(List<VideoResponse.VideoData> list) {
        dataList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VideoInfoItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        VideoInfoItemHolder viewHolder = new VideoInfoItemHolder(LayoutInflater
                                                                .from(parent.getContext())
                                                                .inflate(R.layout.item, parent, false));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VideoInfoItemHolder holder, int position) {
        VideoResponse.VideoData data = dataList.get(position);
        holder.topic.setText(data.nickname);
        holder.description.setText(data.description);
        Log.d("yuanziqi", data.feedUrl);

        holder.upvoteCount.setText(data.likeCount <= 100000 ? String.valueOf(data.likeCount) : "100000+");

        loadCover(holder.pictureCover, data.feedUrl, holder.itemView.getContext());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class VideoInfoItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView topic;
        private TextView upvoteCount;
        private ImageView pictureCover;
        private TextView description;

        public VideoInfoItemHolder(@NonNull View itemView) {
            super(itemView);

            topic = itemView.findViewById(R.id.topic);
            pictureCover = itemView.findViewById(R.id.avator);
            upvoteCount = itemView.findViewById(R.id.upvote_count);
            description = itemView.findViewById(R.id.description);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            if (mOnClickListener != null) {
                mOnClickListener.onListItemClick(clickedPosition);
            }
        }


    }

    private static void loadCover(ImageView imageView, String url, Context context) {
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(context).setDefaultRequestOptions(new RequestOptions().frame(4000000)
                        .centerCrop()
                        .placeholder(R.drawable.icon_progress_bar)
                        .error(R.drawable.icon_failure)
                ).load(url).into(imageView);
//                .load(url)
//                .into(imageView);
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }
}

