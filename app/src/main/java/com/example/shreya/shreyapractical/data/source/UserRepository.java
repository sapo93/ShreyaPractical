package com.example.shreya.shreyapractical.data.source;

import com.example.shreya.shreyapractical.data.modal.User_;
import com.example.shreya.shreyapractical.util.MyApplication;
import com.example.shreya.shreyapractical.data.modal.User;
import com.example.shreya.shreyapractical.model.Video;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.query.Query;

public class UserRepository {
    private final Box<User> userBox;

    public UserRepository() {
        userBox = MyApplication.boxStore.boxFor(User.class);
    }

    public void initializeUserVideoGallery(List<Video> videoList) {
        for (Video video : videoList) {
            User user = new User();
            user.Id = 0;
            user.UpVoteCount = 0;
            user.DownVoteCount = 0;
            user.VideoName = video.getId();
            userBox.put(user);
        }
    }

    public void increaseVideoUpVoteByUser(String videoName) {
        User user = getUserBoxDetails(videoName);
        user.UpVoteCount += 1;
        userBox.put(user);
    }

    public void increaseVideoDownVoteByUser(String videoName) {
        User user = getUserBoxDetails(videoName);
        user.DownVoteCount += 1;
        userBox.put(user);
    }

    public User getUserBoxDetails(String videoName) {
        Query query = userBox.query()
                .equal(User_.VideoName, videoName).build();
        return (User) query.find().get(0);
    }

    public long getUpVote(String videoName) {
        return getUserBoxDetails(videoName).UpVoteCount;
    }

    public long getDownVote(String videoName) {
        return getUserBoxDetails(videoName).DownVoteCount;
    }
}
