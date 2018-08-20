package com.example.shreya.shreyapractical.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.shreya.shreyapractical.R;
import com.example.shreya.shreyapractical.di.component.DaggerVideoPlayerComponent;
import com.example.shreya.shreyapractical.di.module.VideoPlayerModule;
import com.example.shreya.shreyapractical.contract.VideoPlayerContract;
import com.example.shreya.shreyapractical.model.Video;
import com.example.shreya.shreyapractical.presenter.VideoMediaPlayerPresenter;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.io.Serializable;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VideoPlayerActivity extends AppCompatActivity implements VideoPlayerContract.VideoPlayerView{


    private static final String EXTRA_VIDEO = "video";
    private static final String TAG = VideoPlayerActivity.class.getSimpleName();

    @BindView(R.id.playerView)
    PlayerView playerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tvUpVote)
    TextView tvUpVote;
    @BindView(R.id.tvDownVote)
    TextView tvDownVote;

    private Video mVideo;
    private SimpleExoPlayer player;

    @Inject
    public VideoMediaPlayerPresenter videoMediaPlayerPresenter;

    public static void start(Context context, Video video) {
        Intent starter = new Intent(context, VideoPlayerActivity.class);
        starter.putExtra(EXTRA_VIDEO, (Serializable) video);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        ButterKnife.bind(this);
        mVideo = (Video) getIntent().getSerializableExtra(EXTRA_VIDEO);

        DaggerVideoPlayerComponent.builder().videoPlayerModule(new VideoPlayerModule(this)).build().inject(this);
        //Initialize up and down counts
        setUpDownCounts();

        //TOOLBAR
        setToolBar();

        //PLAYER
        initializePlayer();
        preparePlayerView();

    }

    private void setToolBar() {
        toolbar.setTitle(mVideo.getTitle());
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void preparePlayerView() {
        // Measures bandwidth during playback. Can be null if not required.
        DefaultBandwidthMeter bandwidthMeter1 = new DefaultBandwidthMeter();
        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this, getPackageName()), bandwidthMeter1);
        // This is the MediaSource representing the media to be played.
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(mVideo.getImages().getOriginal().getMp4()));
        // Prepare the player with the source.
        player.prepare(videoSource);
        player.setPlayWhenReady(true);

        playerView.setPlayer(player);
    }

    private void initializePlayer() {
        // 1. Create a default TrackSelector
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        DefaultTrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);

        // 2. Create the player
        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);

    }

    @OnClick(R.id.tvUpVote)
    public void increaseUpVote(View view) {
        videoMediaPlayerPresenter.increaseVideoUpVoteByUser(mVideo.getId());
        setUpDownCounts();
    }

    @OnClick(R.id.tvDownVote)
    public void increaseDownVote(View view) {
        videoMediaPlayerPresenter.increaseVideoDownVoteByUser(mVideo.getId());
        setUpDownCounts();
    }

    private void setUpDownCounts() {
        tvUpVote.setText(String.valueOf(videoMediaPlayerPresenter.getUpVote(mVideo.getId())));
        tvDownVote.setText(String.valueOf(videoMediaPlayerPresenter.getDownVote(mVideo.getId())));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
