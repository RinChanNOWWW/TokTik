package com.rinchannow.toktik;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rinchannow.toktik.animator.Love;
import com.rinchannow.toktik.controller.AndroidMediaController;
import com.rinchannow.toktik.player.VideoPlayerIJK;
import com.rinchannow.toktik.player.VideoPlayerListener;

import org.w3c.dom.Text;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;


public class VideoActivity extends AppCompatActivity {
    private VideoPlayerIJK ijkPlayer;
    private AndroidMediaController mediaController;
    private Aside aside = new Aside(this);
    private Introduction introduction = new Introduction();
    private Love loveAnimator;
    private GestureDetector myGestureDetector;

    @Override
    protected void onCreate(@Nullable Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_video);
        ijkPlayer = findViewById(R.id.ijkPlayer);

        // add Animator
        loveAnimator = findViewById(R.id.lovelayout);
        myGestureDetector = new GestureDetector(this, new myOnGestureListener());

        ijkPlayer.setOnTouchListener(new View.OnTouchListener() {

            @Override//可以捕获触摸屏幕发生的Event事件
            public boolean onTouch(View v, MotionEvent event) {
                //使用GestureDetector转发MotionEvent对象给OnGestureListener
                myGestureDetector.onTouchEvent(event);
                return true;
            }
        });

        Intent intent = getIntent();
        String url = intent.getStringExtra("feedUrl");
        String topic = intent.getStringExtra("topic");
        String description = intent.getStringExtra("description");
        Integer upvoteCount = intent.getIntExtra("upvoteCount", 0);
        String avator = intent.getStringExtra("avator");
//        Log.d("video", "Get " + url);
//        Log.d("video", "Get " + topic);
//        Log.d("video", "Get " + description);
//        Log.d("video", "Get " + String.valueOf(upvoteCount));
//        Log.d("video", "Get " + avator);
        aside.init();
        aside.setAside(upvoteCount > 100000 ? "100000+" : String.valueOf(upvoteCount), avator);

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

    // Add Gesture Listener
    class myOnGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            loveAnimator.addLoveView(e.getRawX(),e.getRawY());
            return super.onDoubleTap(e);
        }
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

        private ImageView avatorImg;

        private Context context;

        Aside (Context context) {
            this.context = context;
        }

        void init () {

            upvoteCount = findViewById(R.id.videoUpvoteCount);
            shareCount = findViewById(R.id.videoShareCount);
            commentCount = findViewById(R.id.videoCommentCount);
            avatorImg = findViewById(R.id.avator);

        }

        public void setAside (String upvoteCount, String avator) {
            this.setAside(upvoteCount, avator, defaultShareCount, defaultCommentCount);
        }

        private void setAside (String upvoteCount, String avator, String shareCount, String commentCount) {

            Glide.with(this.context).setDefaultRequestOptions(new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.icon_progress_bar)
                    .error(R.drawable.icon_failure)
            ).load(avator).into(avatorImg);
            this.commentCount.setText(commentCount);
            this.upvoteCount.setText(upvoteCount);
            this.shareCount.setText(shareCount);
        }
    }
}

