package io.weichao.mediamirror.sender;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;

import net.majorkernelpanic.streaming.Session;
import net.majorkernelpanic.streaming.SessionBuilder;
import net.majorkernelpanic.streaming.audio.AudioQuality;
import net.majorkernelpanic.streaming.gl.SurfaceView;
import net.majorkernelpanic.streaming.rtsp.RtspServer;
import net.majorkernelpanic.streaming.video.VideoQuality;

import io.weichao.mediamirror.R;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback {
    private SurfaceView surfaceView;

    private Session session;
    private RtspServer rtspServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        surfaceView = findViewById(R.id.surface);

        surfaceView.getHolder().addCallback(this);
        startRtspServer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopRtspServer();
    }

    private void startRtspServer() {
        rtspServer = new RtspServer();
        rtspServer.start();
    }

    private void stopRtspServer() {
        rtspServer.stop();
    }

    private void buildSession() {
        session = SessionBuilder.getInstance()
                .setSurfaceView(surfaceView)
                .setPreviewOrientation(90)
                .setContext(this)
                .setAudioEncoder(SessionBuilder.AUDIO_AAC)
                .setAudioQuality(new AudioQuality(16000, 32000))
                .setVideoEncoder(SessionBuilder.VIDEO_H264)
                .setVideoQuality(new VideoQuality(320, 240, 20, 500000))
                .build();
    }

    /*SurfaceHolder.Callback start*/

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        buildSession();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        session.release();
    }

    /*SurfaceHolder.Callback end*/
}