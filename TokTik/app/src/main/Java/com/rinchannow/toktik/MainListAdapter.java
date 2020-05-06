package com.rinchannow.toktik;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        String str =
                "id: " + data.id + "\n" +
                        "feed url: " + data.feedUrl + "\n" +
                        "nickname: " + data.nickname + "\n" +
                        "description: " + data.description + "\n" +
                        "like count: " + data.likeCount + "\n" +
                        "avatar url:" + data.avatarUrl;

        holder.text.setText(str);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class VideoInfoItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // TODO 之后需要制作成视频预览，这里只是先用来看效果
        private TextView text;

        public VideoInfoItemHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.video_info);
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

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }
}

