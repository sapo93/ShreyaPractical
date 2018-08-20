package com.example.shreya.shreyapractical.di.component;

import com.example.shreya.shreyapractical.di.module.SearchResultModule;
import com.example.shreya.shreyapractical.view.SearchVideoActivity;

import dagger.Component;

@Component(modules = SearchResultModule.class)
public interface SearchResultComponent {
    void inject(SearchVideoActivity searchVideoActivity);
}
