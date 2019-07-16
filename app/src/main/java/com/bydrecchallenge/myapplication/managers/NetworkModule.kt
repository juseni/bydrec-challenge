package com.bydrecchallenge.myapplication.managers

import android.content.Context
import com.bydrecchallenge.myapplication.application.AppManager
import com.bydrecchallenge.myapplication.interfaces.ApiRetrofitService
import com.bydrecchallenge.myapplication.utils.Const
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    fun provideApiRetrofitService(): ApiRetrofitService = AppManager.API_SERVICE

    @Singleton
    @Provides
    internal fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    internal fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Const.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(createClientPost())
            .build()
    }

    private fun createClientPost(): OkHttpClient {

        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.connectTimeout(45, TimeUnit.SECONDS)
        httpClientBuilder.readTimeout(45, TimeUnit.SECONDS)
        httpClientBuilder.writeTimeout(45, TimeUnit.SECONDS)

        return httpClientBuilder.build()
    }

}