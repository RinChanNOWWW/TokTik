package com.rinchannow.toktik;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {
    // https://beiyou.bytedance.com/api/invoke/video/invoke/video
    @GET("api/invoke/video/invoke/video")
    Call<List<VideoResponse.VideoData>> getVideos();
}
