//package com.rinchannow.toktik;
//
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.viewpager2.widget.ViewPager2;
//
//import com.scwang.smartrefresh.layout.SmartRefreshLayout;
//import com.scwang.smartrefresh.layout.api.RefreshLayout;
//import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
//
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//
//public class MainActivity2 extends AppCompatActivity {
//    SmartRefreshLayout refreshLayout;
//    ViewPager2 viewPager2;
//    MainPageAdapter adapter;
//
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main2);
//        refreshLayout = findViewById(R.id.refresh_layout);
//        viewPager2 = findViewById(R.id.view_page2);
////        refreshLayout.setEnableLoadMore(true);
//        adapter = new MainPageAdapter();
//        viewPager2.setAdapter(adapter);
//        // 从 API 获取视频信息
//        getData();
//        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                refreshLayout.finishRefresh(1000);
//                Toast.makeText(getApplicationContext(), "Refresh", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//    }
//
//    private void getData() {
//        Log.d("network", "Send Request");
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://beiyou.bytedance.com/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        APIService apiService = retrofit.create(APIService.class);
//        apiService.getVideos().enqueue(new Callback<List<VideoResponse.VideoData>>() {
//            @Override
//            public void onResponse(Call<List<VideoResponse.VideoData>> call, Response<List<VideoResponse.VideoData>> response) {
//                if (response.body() != null) {
//                    Log.d("network", "Get Response");
//                    List<VideoResponse.VideoData> list = response.body();
//                    adapter.setList(list);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<VideoResponse.VideoData>> call, Throwable t) {
//                Log.d("network", t.getMessage());
//            }
//        });
//    }
//}
