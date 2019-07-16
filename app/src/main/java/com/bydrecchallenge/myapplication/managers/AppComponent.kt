package com.bydrecchallenge.myapplication.managers

import com.bydrecchallenge.myapplication.interfaces.ApiRetrofitService
import com.bydrecchallenge.myapplication.views.activities.MainActivity
import com.bydrecchallenge.myapplication.views.activities.FilterChampionshipActivity
import com.bydrecchallenge.myapplication.views.fragments.FixtureFragment
import com.bydrecchallenge.myapplication.views.fragments.ResultsFragment
import dagger.Component
import javax.inject.Singleton


@Component(modules = [AppModule::class])

@Singleton
interface AppComponent {

    fun ApiRetrofitService(): ApiRetrofitService

    fun inject(activity: MainActivity)

    fun inject(fragment: FixtureFragment)

    fun inject(fragment: ResultsFragment)

    fun inject(activity: FilterChampionshipActivity)
}