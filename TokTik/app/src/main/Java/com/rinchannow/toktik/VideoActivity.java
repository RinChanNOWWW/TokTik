package com.rinchannow.toktik;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.rinchannow.toktik.controller.AndroidMediaController;
import com.rinchannow.toktik.player.VideoPlayerIJK;
import com.rinchannow.toktik.player.VideoPlayerListener;

import org.w3c.dom.Text;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;


public class VideoActivity extends AppCompatActivity {
    private VideoPlayerIJK ijkPlayer;
    private AndroidMediaController mediaController;
    private Aside aside = new Aside();
    private Introduction introduction = new Introduction();

    @Override
    protected void onCreate(@Nullable Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_video);
        ijkPlayer = findViewById(R.id.ijkPlayer);
        Intent intent = getIntent();
        String url = intent.getStringExtra("feedUrl");
        String topic = intent.getStringExtra("topic");
        String description = intent.getStringExtra("description");
        Integer upvoteCount = intent.getIntExtra("upvoteCount", 0);
        Log.d("video", "Get " + url);
        Log.d("video", "Get " + topic);
        Log.d("video", "Get " + description);
        Log.d("video", "Get " + String.valueOf(upvoteCount));
        aside.init();
        aside.setAside(upvoteCount > 100000 ? "100000+" : String.valueOf(upvoteCount));
        introduction.init();
        introduction.setIntroduction(topic, description);
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

    public class Introduction {

        private TextView videoTopic;
        private TextView videoDescription;

        void init () {
            videoTopic = findViewById(R.id.videoTopic);
            videoDescription = findViewById(R.id.videoDescription);
        }

        public void setIntroduction (String topic, String description) {
            this.videoTopic.setText(topic);
            this.videoDescription.setText(description);
        }
    }

    public class Aside {

        private String defaultShareCount = "100000+";
        private String defaultCommentCount = "100000+";


        private TextView upvoteCount;
        private TextView commentCount;
        private TextView shareCount;

        void init () {

            upvoteCount = findViewById(R.id.videoUpvoteCount);
            shareCount = findViewById(R.id.videoShareCount);
            commentCount = findViewById(R.id.videoCommentCount);

        }

        public void setAside (String upvoteCount) {
            this.setAside(upvoteCount, defaultShareCount, defaultCommentCount);
        }

        private void setAside (String upvoteCount, String shareCount, String commentCount) {

            this.commentCount.setText(commentCount);
            this.upvoteCount.setText(upvoteCount);
            this.shareCount.setText(shareCount);
        }

    }
}

