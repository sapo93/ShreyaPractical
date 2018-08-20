package com.example.shreya.shreyapractical.contract;

import android.content.res.Resources;

public interface BaseContract {

    /**
     * This class represents the progressbar view interface.
     *
     * @author Shreya Prajapati
     * @date 14/08/18.
     */
    public interface ProgressBarView {
        void showProgressBar(String query);

        void hideProgressBar();
    }

    /**
     * This class represents the animation view interface.
     *
     * @author Shreya Prajapati
     * @date 14/08/18.
     */
    public interface AnimationView {
        int getToolbarWidth();

        int getToolbarHeight();

        void animateToolbar(int width);

        void animateToolbar();

        void animateToolbarListener(int width);

        void animateToolbar1();
    }

    /**
     * This class represents the search result view interface.
     *
     * @author Shreya Prajapati
     * @date 14/08/18.
     */
    public interface MyResources {
        Resources getMyResources();

        int getDimensionPixelSize(int res);

        int getThemeColor(int res);

    }
}
