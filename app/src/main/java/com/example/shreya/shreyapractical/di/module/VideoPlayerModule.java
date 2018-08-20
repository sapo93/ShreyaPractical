package com.example.shreya.shreyapractical.di.module;

import com.example.shreya.shreyapractical.contract.VideoPlayerContract;

import dagger.Module;
import dagger.Provides;

@Module public class VideoPlayerModule {

    private final VideoPlayerContract.VideoPlayerView videoPlayerView;

    public VideoPlayerModule(VideoPlayerContract.VideoPlayerView videoPlayerView) {
        this.videoPlayerView = videoPlayerView;
    }

    @Provides
    VideoPlayerContract.VideoPlayerView provideVideoPlayerView(){
        return videoPlayerView;
    }
}
