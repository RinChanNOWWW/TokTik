package com.rinchannow.toktik;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements MainListAdapter.ListItemClickListener {

    static private List<VideoResponse.VideoData> videoDataList;
    private MainListAdapter mAdapter;
    private RecyclerView mListView;
    private LinearLayoutManager layoutManager;

    @Override
    public void onCreate(@Nullable Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_main);
        mListView = findViewById(R.id.video_list);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mListView.setLayoutManager(layoutManager);
        mListView.setHasFixedSize(true);
        // TODO 美化这个界面
        mListView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        try {
            getVideoData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mAdapter = new MainListAdapter(this);
        mListView.setAdapter(mAdapter);
    }

    private void getVideoData() {
        Log.d("network", "Send Request");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://beiyou.bytedance.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService apiService = retrofit.create(APIService.class);
        apiService.getVideos().enqueue(new Callback<List<VideoResponse.VideoData>>() {
            @Override
            public void onResponse(Call<List<VideoResponse.VideoData>> call, Response<List<VideoResponse.VideoData>> response) {
                if (response.body() != null) {
                    Log.d("network", "Get Response");
                    List<VideoResponse.VideoData> list = response.body();
                    videoDataList = list;
                    mAdapter.setDataList(list);
                }
            }

            @Override
            public void onFailure(Call<List<VideoResponse.VideoData>> call, Throwable t) {
                Log.d("network", t.getMessage());
            }
        });
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Intent intent = new Intent(MainActivity.this, VideoActivity.class);
        String url = videoDataList.get(clickedItemIndex).feedUrl;
        intent.putExtra("feedUrl", url);
        Log.d("video", "Send " + url);
        // TODO 携带更多的信息

        startActivity(intent);
    }
}
