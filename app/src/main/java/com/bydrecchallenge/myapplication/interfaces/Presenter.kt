package com.bydrecchallenge.myapplication.interfaces

interface Presenter<V> {
    fun setView(view: V)
}