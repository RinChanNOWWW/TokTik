package com.rinchannow.toktik;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainPageAdapter extends RecyclerView.Adapter<ViewHolder> {

    // TODO 换成视频内容
    private List<String> list = new ArrayList<>();

    public void setList(List<String> list) {
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
        holder.bindData(list.get(position) + "");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}

// TODO 将内容换为视频内容
class ViewHolder extends RecyclerView.ViewHolder {
    private TextView textView;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.num);
    }

    void bindData(String s) {
        textView.setText(s);
    }

}
