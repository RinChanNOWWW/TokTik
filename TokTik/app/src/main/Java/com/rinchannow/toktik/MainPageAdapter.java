//package com.rinchannow.toktik;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class MainPageAdapter extends RecyclerView.Adapter<ViewHolder> {
//
//    // TODO 换成视频内容
//    private List<VideoResponse.VideoData> list = new ArrayList<>();
//
//    public void setList(List<VideoResponse.VideoData> list) {
//        this.list = list;
//        notifyDataSetChanged();
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        VideoResponse.VideoData data = list.get(position);
//        String text =
//                "id: " + data.id + "\n" +
//                "feed url: " + data.feedUrl + "\n" +
//                "nickname: " + data.nickname + "\n" +
//                "description: " + data.description + "\n" +
//                "like count: " + data.likeCount + "\n" +
//                "avatar url:" + data.avatarUrl;
//        holder.bindData(text);
//    }
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//
//}
//
//// TODO 将内容换为视频内容
//class ViewHolder extends RecyclerView.ViewHolder {
//    private TextView textView;
//
//    public ViewHolder(@NonNull View itemView) {
//        super(itemView);
//        textView = itemView.findViewById(R.id.video_info);
//    }
//
//    void bindData(String s) {
//        textView.setText(s);
//    }
//
//}
