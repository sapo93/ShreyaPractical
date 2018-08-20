package com.example.shreya.shreyapractical.data.modal;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;

@Entity
public class User {

    @Id
    public long Id;

    public long UpVoteCount;

    public long DownVoteCount;

    @Index
    public String VideoName;

    public User() {
    }

    public User(long id, long upVoteCount, long downVoteCount, String videoName) {
        Id = id;
        UpVoteCount = upVoteCount;
        DownVoteCount = downVoteCount;
        VideoName = videoName;
    }
}