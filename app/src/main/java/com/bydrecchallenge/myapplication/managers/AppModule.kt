package com.bydrecchallenge.myapplication.managers

import android.app.Application
import android.content.Context
import com.bydrecchallenge.myapplication.application.AppManager
import com.bydrecchallenge.myapplication.presenters.MainPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
class AppModule(private val app: AppManager) {

    @Provides
    @Singleton
    fun provideContext(): Context = app

    @Provides
    @Singleton
    fun provideApplication(): Application = app
}