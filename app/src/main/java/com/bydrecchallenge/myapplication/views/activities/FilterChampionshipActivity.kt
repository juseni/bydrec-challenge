package com.bydrecchallenge.myapplication.views.activities


import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.View.OnClickListener

import com.bydrecchallenge.myapplication.R
import com.bydrecchallenge.myapplication.adapters.FilterChampionshipItemAdapter
import com.bydrecchallenge.myapplication.application.AppManager
import com.bydrecchallenge.myapplication.models.ChampionshipItems
import com.bydrecchallenge.myapplication.presenters.FilterChampionshipPresenter
import com.bydrecchallenge.myapplication.presenters.FilterChampionshipView
import com.bydrecchallenge.myapplication.utils.Const
import kotlinx.android.synthetic.main.layout_filter_championship_activity.*
import javax.inject.Inject

class FilterChampionshipActivity : AppCompatActivity(), FilterChampionshipView, OnClickListener {

    @Inject
    lateinit var presenter: FilterChampionshipPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_filter_championship_activity)

        AppManager.APP_DAGGER_COMPONENT.inject(this)

        intent.extras?.let {
            if (it.containsKey(Const.CHAMPIONSHIP_ITEMS_KEY)) {
                val championshipItems = it.getParcelable(Const.CHAMPIONSHIP_ITEMS_KEY) as ChampionshipItems
                presenter.championshipItems = championshipItems.cahmpionshipItems
            }
            presenter.setView(this)
            presenter.init()
            setClickEvent()
        }
    }

    private fun setClickEvent() {
        noFilterContainer.setOnClickListener(this)
    }

    override fun setFilterChampionshipAdapter(adapter: FilterChampionshipItemAdapter) {
            filterRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            filterRecyclerView.adapter = adapter
    }

    override fun setChampionshipSelected(championshipId: Int) {
        val output = Intent()
        output.putExtra(Const.ID_CHAMPIONSHIP_KEY, championshipId)
        setResult(RESULT_OK, output)

        finish()
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.noFilterContainer -> setChampionshipSelected(Const.NO_FILTER)
        }
    }
}
