package com.bydrecchallenge.myapplication.presenters

import com.bydrecchallenge.myapplication.adapters.LisItemMatchAdapter

interface ResultView {

    fun setRecyclerViewAdapter(adapter: LisItemMatchAdapter)
}