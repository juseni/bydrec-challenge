package com.bydrecchallenge.myapplication.presenters

import com.bydrecchallenge.myapplication.adapters.LisItemMatchAdapter
import com.bydrecchallenge.myapplication.interfaces.Presenter
import com.bydrecchallenge.myapplication.models.BydrecData
import com.bydrecchallenge.myapplication.models.MatchRow
import com.bydrecchallenge.myapplication.models.RowType
import com.bydrecchallenge.myapplication.utils.getFormattedDate
import com.bydrecchallenge.myapplication.utils.getMonthFormatted

import javax.inject.Inject
import kotlin.collections.ArrayList

class FixturePresenter @Inject constructor(): Presenter<FixtureView> {

    lateinit var matchesInformation: List<BydrecData>

    private lateinit var view: FixtureView
    private lateinit var fixtureAdapter: LisItemMatchAdapter
    private lateinit var fixtureMatchesByMonth: Map<String, List<BydrecData>>

    override fun setView(view: FixtureView) {
        this.view = view
    }

    fun init() {
        sortFixtureMatchesInformation()
    }

    private fun displayFixtureMatches(matchesInformation: List<MatchRow>) {
        fixtureAdapter = LisItemMatchAdapter(matchesInformation)
        view.setupRecyclerView(fixtureAdapter)
    }

    private fun sortFixtureMatchesInformation() {
        fixtureMatchesByMonth = matchesInformation.groupBy {
            val date = getFormattedDate(it.date)
            getMonthFormatted(date)
        }

        val newMatchRows = ArrayList<MatchRow>()
        for ((key, value) in fixtureMatchesByMonth) {
            newMatchRows.add(MatchRow(RowType.HEADER, null, key))
            value.mapTo(newMatchRows) { MatchRow(RowType.ITEM, it, null) }
        }
        displayFixtureMatches(newMatchRows)
    }
}