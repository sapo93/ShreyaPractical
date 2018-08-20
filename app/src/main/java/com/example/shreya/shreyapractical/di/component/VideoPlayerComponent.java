package com.example.shreya.shreyapractical.di.component;

import com.example.shreya.shreyapractical.di.module.VideoPlayerModule;
import com.example.shreya.shreyapractical.view.VideoPlayerActivity;

import dagger.Component;

@Component(modules = VideoPlayerModule.class)
public interface VideoPlayerComponent {
    void inject(VideoPlayerActivity videoPlayerActivity);
}
