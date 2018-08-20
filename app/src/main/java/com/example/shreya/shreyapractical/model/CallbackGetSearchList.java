package com.example.shreya.shreyapractical.model;

import java.util.List;

public class CallbackGetSearchList {
    private List<Video> data;
    private MetaData meta;

    public List<Video> getData() {
        return data;
    }

    public MetaData getMeta() {
        return meta;
    }

    public class MetaData {
        private int status;
        private String msg;
        private String response_id;

        public int getStatus() {
            return status;
        }

        public String getMsg() {
            return msg;
        }

        public String getResponse_id() {
            return response_id;
        }
    }
}
