package com.bydrecchallenge.myapplication.presenters

import com.bydrecchallenge.myapplication.adapters.FilterChampionshipItemAdapter
import com.bydrecchallenge.myapplication.adapters.OnfilterChampionshipListener
import com.bydrecchallenge.myapplication.interfaces.Presenter
import com.bydrecchallenge.myapplication.models.Competition
import javax.inject.Inject

class FilterChampionshipPresenter @Inject constructor() : Presenter<FilterChampionshipView>, OnfilterChampionshipListener {

    private lateinit var view: FilterChampionshipView
    private lateinit var filterAdapter: FilterChampionshipItemAdapter

    lateinit var championshipItems: List<Competition>

    override fun setView(view: FilterChampionshipView) {
        this.view = view
    }

    fun init() {
        displayChampionships()
    }

    private fun displayChampionships() {
        filterAdapter = FilterChampionshipItemAdapter(championshipItems, this)
        view.setFilterChampionshipAdapter(filterAdapter)

    }

    override fun onFilterChampionshipClicked(championshipId: Int) {
        view.setChampionshipSelected(championshipId)
    }

}