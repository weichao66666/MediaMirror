package io.weichao.mediamirror.player;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

import io.weichao.mediamirror.R;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener {
    private static final String VIDEO_URL_HOST = "10.4.70.156";
    private static final String VIDEO_URL_PORT = "8086";// RtspServer.DEFAULT_RTSP_PORT

    private SurfaceView surfaceView;

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        surfaceView = findViewById(R.id.surface);

        surfaceView.getHolder().addCallback(this);
    }

    private void configureMediaPlayer(Uri videoUri) {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        mediaPlayer.setDisplay(surfaceView.getHolder());
        mediaPlayer.setOnPreparedListener(this);
        try {
            mediaPlayer.setDataSource(this, videoUri);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void releaseMediaPlayer() {
        surfaceView.getHolder().removeCallback(this);
        mediaPlayer.release();
    }

    /*SurfaceHolder.Callback start*/

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        StringBuilder videoUrlBuilder = new StringBuilder();
        videoUrlBuilder.append("rtsp://")
                .append(VIDEO_URL_HOST)
                .append(":")
                .append(VIDEO_URL_PORT)
                .append("/");
        Uri videoUri = Uri.parse(videoUrlBuilder.toString());
        configureMediaPlayer(videoUri);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        releaseMediaPlayer();
    }

    /*SurfaceHolder.Callback end*/

    /*MediaPlayer.OnPreparedListener start*/

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    /*MediaPlayer.OnPreparedListener end*/
}