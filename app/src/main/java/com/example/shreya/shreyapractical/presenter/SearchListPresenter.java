package com.example.shreya.shreyapractical.presenter;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.res.Resources;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;

import com.example.shreya.shreyapractical.R;
import com.example.shreya.shreyapractical.contract.SearchResultContract;
import com.example.shreya.shreyapractical.data.source.UserRepository;
import com.example.shreya.shreyapractical.model.CallbackGetSearchList;
import com.example.shreya.shreyapractical.model.Video;
import com.example.shreya.shreyapractical.service.MyDemoService;
import com.example.shreya.shreyapractical.view.SearchVideoActivity;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * This class represents the country view interface.
 *
 * @author Shreya Prajapati
 * @date 15/08/18.
 */
public class SearchListPresenter implements SearchResultContract.SearchResultPresenter {

    private static final String TAG = SearchListPresenter.class.getSimpleName();
    private SearchResultContract.SearchResultView searchResultView;
    private MyDemoService myDemoService;
    private UserRepository userRepository;


    @Inject
    public SearchListPresenter(SearchResultContract.SearchResultView view) {
        this.searchResultView = view;

        if (this.myDemoService == null) {
            this.myDemoService = new MyDemoService();
        }
        userRepository = new UserRepository();
    }

    @Override
    public void getSearchResults(String query) {
        searchResultView.showProgressBar(query);
        myDemoService
                .getAPI()
                .getSearchResults(MyDemoService.API_KEY, query, "25", "0", "G", "en")
                .enqueue(new Callback<CallbackGetSearchList>() {
                    @Override
                    public void onResponse(Call<CallbackGetSearchList> call, Response<CallbackGetSearchList> response) {
                        CallbackGetSearchList data = response.body();

                        if (data != null && data.getData() != null) {
                            List<Video> videoList = data.getData();
                            userRepository.initializeUserVideoGallery(videoList);
                            searchResultView.onSearchResultReady(videoList);
                        }
                        searchResultView.hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<CallbackGetSearchList> call, Throwable t) {
                        try {
                            throw new InterruptedException("Something went wrong!");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            searchResultView.hideProgressBar();
                        }
                    }
                });
    }

    @Override
    public void startAnimate(int numberOfMenuIcon, boolean containsOverflow, boolean show) {
        if (show) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                int width = searchResultView.getToolbarWidth() -
                        (containsOverflow ? searchResultView.getDimensionPixelSize(R.dimen.abc_action_button_min_width_overflow_material) : 0) -
                        (searchResultView.getDimensionPixelSize(R.dimen.abc_action_button_min_width_material) * numberOfMenuIcon) / 2;
                searchResultView.animateToolbar(width);
            } else {
                searchResultView.animateToolbar();
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                int width = searchResultView.getToolbarWidth() -
                        (containsOverflow ? searchResultView.getDimensionPixelSize(R.dimen.abc_action_button_min_width_overflow_material) : 0) -
                        (searchResultView.getDimensionPixelSize(R.dimen.abc_action_button_min_width_material) * numberOfMenuIcon) / 2;
                searchResultView.animateToolbarListener(width);
            } else {
                searchResultView.animateToolbar1();
            }
        }
    }
}