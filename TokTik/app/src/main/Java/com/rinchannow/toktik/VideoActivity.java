package com.rinchannow.toktik;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.rinchannow.toktik.controller.AndroidMediaController;
import com.rinchannow.toktik.player.VideoPlayerIJK;
import com.rinchannow.toktik.player.VideoPlayerListener;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;


public class VideoActivity extends AppCompatActivity {
    private VideoPlayerIJK ijkPlayer;
    private AndroidMediaController mediaController;

    @Override
    protected void onCreate(@Nullable Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_video);
        ijkPlayer = findViewById(R.id.ijkPlayer);
        Intent intent = getIntent();
        String url = intent.getStringExtra("feedUrl");
        Log.d("video", "Get " + url);


        try {
            IjkMediaPlayer.loadLibrariesOnce(null);
            IjkMediaPlayer.native_profileBegin("libijkplayer.so");

        } catch (Exception e) {
            this.finish();
        }
        ijkPlayer.setListener(new VideoPlayerListener());
        ijkPlayer.setVideoPath(url);

        mediaController = new AndroidMediaController(this);
        ijkPlayer.setMediaController(mediaController);
        mediaController.setMediaPlayer(ijkPlayer);

        ijkPlayer.start();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mediaController.show();
        return super.onTouchEvent(event);
    }

        @Override
    protected void onStop() {
        super.onStop();
        if (ijkPlayer.isPlaying()) {
            ijkPlayer.stop();
        }

        IjkMediaPlayer.native_profileEnd();
    }

}

