package com.example.shreya.shreyapractical.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Video implements Serializable {

    private String id;
    private String title;
    private String type;
    private VideoImage images;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public VideoImage getImages() {
        return images;
    }

    public class VideoImage implements Serializable {
        @SerializedName("preview_gif")
        private ImageDetail preview_gif;
        private OriginalVideo original;

        public ImageDetail getImagePath() {
            return preview_gif;
        }

        public OriginalVideo getOriginal() {
            return original;
        }

        public class ImageDetail implements Serializable {

            private String url;

            public String getUrl() {
                return url;
            }
        }

        public class OriginalVideo implements Serializable {
            private String mp4;
//            private String mp4;
//            private String mp4;


            public String getMp4() {
                return mp4;
            }
        }
    }
}
