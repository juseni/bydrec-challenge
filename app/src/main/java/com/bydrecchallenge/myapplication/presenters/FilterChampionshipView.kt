package com.bydrecchallenge.myapplication.presenters

import com.bydrecchallenge.myapplication.adapters.FilterChampionshipItemAdapter

interface FilterChampionshipView {

    fun setFilterChampionshipAdapter(adapter: FilterChampionshipItemAdapter)

    fun setChampionshipSelected(championshipId: Int)
}