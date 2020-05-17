package com.rinchannow.toktik;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class MainPageAdapter extends RecyclerView.Adapter<ViewHolder> {

    // TODO 换成视频内容
    private List<VideoResponse.VideoData> list = new ArrayList<>();

    public void setList(List<VideoResponse.VideoData> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VideoResponse.VideoData data = list.get(position);
        holder.bindData(data);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}

// TODO 将内容换为视频内容
class ViewHolder extends RecyclerView.ViewHolder {

    private TextView topic;
    private TextView upvoteCount;
    private ImageView pictureCover;
    private TextView description;


    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        topic = itemView.findViewById(R.id.topic);
        upvoteCount = itemView.findViewById(R.id.upvote_count);
        pictureCover = itemView.findViewById(R.id.avator);
        description = itemView.findViewById(R.id.description);
    }

    void bindData(VideoResponse.VideoData data) {
        topic.setText(data.nickname);
        description.setText(data.description);

        upvoteCount.setText(data.likeCount <= 100000 ? String.valueOf(data.likeCount) : "100000+");

        loadCover(pictureCover, data.feedUrl, itemView.getContext());
    }

    private static void loadCover(ImageView imageView, String url, Context context) {
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(context).setDefaultRequestOptions(new RequestOptions().frame(4000000)
                .centerCrop()
                .placeholder(R.drawable.icon_progress_bar)
                .error(R.drawable.icon_failure)
        ).load(url).into(imageView);
    }

}
