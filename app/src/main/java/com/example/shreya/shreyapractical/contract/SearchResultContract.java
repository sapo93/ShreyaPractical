package com.example.shreya.shreyapractical.contract;

import com.example.shreya.shreyapractical.model.Video;

import java.util.List;

public interface SearchResultContract {

    /**
     * This class represents the search result view interface.
     *
     * @author Shreya Prajapati
     * @date 14/08/18.
     */
    public interface SearchResultView extends BaseContract.ProgressBarView, BaseContract.AnimationView, BaseContract.MyResources{
        void onSearchResultReady(List<Video> videoList);
        void onError();

    }

    /**
     * This class represents the search result presenter interface.
     *
     * @author Shreya Prajapati
     * @date 14/08/18.
     */
    public interface SearchResultPresenter{
        void getSearchResults(String query);
        void startAnimate( int numberOfMenuIcon, boolean containsOverflow, boolean show);
    }
}
