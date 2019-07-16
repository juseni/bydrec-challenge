package com.bydrecchallenge.myapplication.presenters

import android.support.design.widget.TabLayout
import android.support.design.widget.TabLayout.OnTabSelectedListener
import com.bydrecchallenge.myapplication.adapters.TabAdapter
import com.bydrecchallenge.myapplication.models.ChampionshipItems

interface MainView {

    fun addTab(tab: TabLayout.Tab)

    fun setTabAdapter(adapter: TabAdapter)

    fun setOnTabSelectedListener(tabListener: OnTabSelectedListener)

    fun setOnPageChangedListener()

    fun showLoading()

    fun hideLoading()

    fun setViewPagerVisibility(isVisible: Boolean)

    fun setFilterContainerVisibility(isVisible: Boolean)

    fun showErrorMessage()

    fun callChampionshipFilterScreen(championshipItems: ChampionshipItems)

}