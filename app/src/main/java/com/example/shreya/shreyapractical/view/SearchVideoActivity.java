package com.example.shreya.shreyapractical.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.example.shreya.shreyapractical.R;
import com.example.shreya.shreyapractical.di.component.DaggerSearchResultComponent;
import com.example.shreya.shreyapractical.di.module.SearchResultModule;
import com.example.shreya.shreyapractical.adapter.SearchResultAdapter;
import com.example.shreya.shreyapractical.contract.SearchResultContract;
import com.example.shreya.shreyapractical.util.Utility;
import com.example.shreya.shreyapractical.model.Video;
import com.example.shreya.shreyapractical.presenter.SearchListPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class SearchVideoActivity extends AppCompatActivity implements SearchResultContract.SearchResultView, SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener, SearchResultAdapter.OnVideoClickListener {

    private static final String TAG = SearchVideoActivity.class.getSimpleName();
    @BindView(R.id.rvVideoList)
    RecyclerView rvVideoList;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tvErrorMsg)
    TextView tvErrorMsg;
    @BindInt(R.integer.item_count)
    int gridItemCount;

    ProgressDialog mLoader;
    private SearchResultAdapter searchAdapter;

    @Inject
    public SearchListPresenter searchListPresenter;
    private MenuItem mSearchItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_video);
        ButterKnife.bind(this);


        //Injecting View
        DaggerSearchResultComponent.builder()
                .searchResultModule(new SearchResultModule(this))
                .build()
                .inject(this);

        //TOOLBAR
        setSupportActionBar(mToolbar);

        initializeRecyclerView();
        getSearchResultsWSCall("dance");
    }

    private void initializeRecyclerView() {
        rvVideoList.setLayoutManager(new GridLayoutManager(SearchVideoActivity.this, gridItemCount));
        searchAdapter = new SearchResultAdapter(this);
        searchAdapter.setOnVideoClickListener(this);
        rvVideoList.setAdapter(searchAdapter);
    }

    private void getSearchResultsWSCall(String query) {
        searchListPresenter.getSearchResults(query);
    }

    private void creatingVideoObserable(List<Video> videoList) {
        final Observable<List<Video>> listObserable = Observable.just(videoList);
        listObserable.subscribe(new Observer<List<Video>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<Video> videos) {
                searchAdapter.setData(videos);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void onSearchResultReady(List<Video> videoList) {
        creatingVideoObserable(videoList);
        tvErrorMsg.setVisibility(View.GONE);
    }

    @Override
    public void onError() {
        tvErrorMsg.setVisibility(View.VISIBLE);
    }

    @Override
    public void showProgressBar(String query) {
        if (mLoader == null) {
            mLoader = new ProgressDialog(SearchVideoActivity.this);
        }
        mLoader.setMessage(String.format(getResources().getString(R.string.msg_loader_searching), query));

        if (!isFinishing() && !mLoader.isShowing()) {
            mLoader.show();
        }
    }

    @Override
    public void hideProgressBar() {
        if (mLoader != null && !isFinishing() && mLoader.isShowing()) {
            mLoader.dismiss();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        mSearchItem = menu.findItem(R.id.m_search);
        SearchView mSearchView = (SearchView) mSearchItem.getActionView();
        mSearchView.setOnQueryTextListener(this);
        mSearchItem.setOnActionExpandListener(this);

        return true;
    }

    private static int getThemeColor(Context context, int id) {
        Resources.Theme theme = context.getTheme();
        TypedArray a = theme.obtainStyledAttributes(new int[]{id});
        int result = a.getColor(0, 0);
        a.recycle();
        return result;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Utility.hideKeyboard(this);
        getSearchResultsWSCall(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public int getToolbarWidth() {
        return mToolbar.getWidth();
    }

    @Override
    public int getToolbarHeight() {
        return mToolbar.getHeight();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void animateToolbar(int width) {
        mToolbar.setBackgroundColor(ContextCompat.getColor(this, android.R.color.white));
        Animator createCircularReveal = ViewAnimationUtils.createCircularReveal(mToolbar,
                isRtl(getMyResources()) ? mToolbar.getWidth() - width : width, mToolbar.getHeight() / 2, 0.0f, (float) width);
        createCircularReveal.setDuration(250);
        createCircularReveal.start();
    }

    @Override
    public void animateToolbar() {
        mToolbar.setBackgroundColor(ContextCompat.getColor(this, android.R.color.white));
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, (float) (-mToolbar.getHeight()), 0.0f);
        translateAnimation.setDuration(220);
        mToolbar.clearAnimation();
        mToolbar.startAnimation(translateAnimation);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void animateToolbarListener(int width) {
        Animator createCircularReveal = ViewAnimationUtils.createCircularReveal(mToolbar,
                isRtl(getMyResources()) ? mToolbar.getWidth() - width : width, mToolbar.getHeight() / 2, (float) width, 0.0f);
        createCircularReveal.setDuration(250);
        createCircularReveal.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mToolbar.setBackgroundColor(getThemeColor(SearchVideoActivity.this, R.attr.colorPrimary));
            }
        });
        createCircularReveal.start();
    }

    @Override
    public void animateToolbar1() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        Animation translateAnimation = new TranslateAnimation(0.0f, 0.0f, 0.0f, (float) (-mToolbar.getHeight()));
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(translateAnimation);
        animationSet.setDuration(220);
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mToolbar.setBackgroundColor(getThemeColor(SearchVideoActivity.this, R.attr.colorPrimary));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mToolbar.startAnimation(animationSet);
    }

    private boolean isRtl(Resources resources) {
        return resources.getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL;
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem menuItem) {
        searchListPresenter.startAnimate(1, true, true);
        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem menuItem) {
        if (mSearchItem.isActionViewExpanded()) {
            searchListPresenter.startAnimate(1, false, false);
        }
        return true;
    }

    @Override
    public Resources getMyResources() {
        return getResources();
    }

    @Override
    public int getDimensionPixelSize(int res) {
        return getMyResources().getDimensionPixelSize(res);
    }

    @Override
    public int getThemeColor(int res) {
        Resources.Theme theme = getTheme();
        TypedArray a = theme.obtainStyledAttributes(new int[]{res});
        int result = a.getColor(0, 0);
        a.recycle();
        return result;
    }

    @Override
    public void onVideoSelect(Video video) {
        VideoPlayerActivity.start(SearchVideoActivity.this, video);
    }
}
