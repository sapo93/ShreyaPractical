package com.example.shreya.shreyapractical.di.module;

import com.example.shreya.shreyapractical.contract.SearchResultContract;

import dagger.Module;
import dagger.Provides;

@Module public class SearchResultModule {

    private final SearchResultContract.SearchResultView view;

    public SearchResultModule(SearchResultContract.SearchResultView view) {
        this.view = view;
    }

    @Provides
    SearchResultContract.SearchResultView provideSearchResultContractView(){
        return this.view;
    }

}
