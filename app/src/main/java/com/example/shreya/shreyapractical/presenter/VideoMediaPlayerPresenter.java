package com.example.shreya.shreyapractical.presenter;

import com.example.shreya.shreyapractical.contract.VideoPlayerContract;
import com.example.shreya.shreyapractical.data.source.UserRepository;

import javax.inject.Inject;

public class VideoMediaPlayerPresenter implements VideoPlayerContract.VideoPlayerPresenter{

    private final UserRepository userRepository;

    @Inject
    public VideoMediaPlayerPresenter() {
        userRepository = new UserRepository();
    }

    public void increaseVideoUpVoteByUser(String id) {
        userRepository.increaseVideoUpVoteByUser(id);
    }

    public void increaseVideoDownVoteByUser(String id) {
        userRepository.increaseVideoDownVoteByUser(id);
    }

    public long getUpVote(String id) {
        return userRepository.getUpVote(id);
    }

    public long getDownVote(String id) {
        return userRepository.getDownVote(id);
    }
}
