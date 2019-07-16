package com.bydrecchallenge.myapplication.application

import android.app.Application
import android.os.Build
import com.bydrecchallenge.myapplication.BuildConfig
import com.bydrecchallenge.myapplication.interfaces.ApiRetrofitService
import com.bydrecchallenge.myapplication.managers.AppComponent
import com.bydrecchallenge.myapplication.managers.AppModule
import com.bydrecchallenge.myapplication.managers.DaggerAppComponent
import com.bydrecchallenge.myapplication.utils.Const
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext


class AppManager : Application() {

    companion object {
        lateinit var API_SERVICE: ApiRetrofitService
        lateinit var APP_DAGGER_COMPONENT: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        setDagger()
        setServerEndPoint()
    }

    private fun setDagger() {
        APP_DAGGER_COMPONENT = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build();
    }

    private fun setServerEndPoint() {
        val apiRetrofit = Retrofit.Builder()
            .addCallAdapterFactory(
                RxJava2CallAdapterFactory.create()
            )
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .baseUrl(Const.BASE_URL)
            .build()

        API_SERVICE = apiRetrofit.create(ApiRetrofitService::class.java)

    }
}