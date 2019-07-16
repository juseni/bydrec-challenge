package com.bydrecchallenge.myapplication.presenters

import com.bydrecchallenge.myapplication.adapters.LisItemMatchAdapter
import com.bydrecchallenge.myapplication.interactors.BydrecDataInteractor
import com.bydrecchallenge.myapplication.interfaces.Presenter
import com.bydrecchallenge.myapplication.models.BydrecData
import com.bydrecchallenge.myapplication.models.MatchRow
import com.bydrecchallenge.myapplication.models.RowType
import com.bydrecchallenge.myapplication.utils.getFormattedDate
import com.bydrecchallenge.myapplication.utils.getMonthFormatted
import javax.inject.Inject

class ResultPresenter @Inject constructor(): Presenter<ResultView> {

    lateinit var matchesInformation: List<BydrecData>

    private lateinit var view: ResultView
    private lateinit var resultAdapter: LisItemMatchAdapter
    private lateinit var resultMatchesByMonth: Map<String, List<BydrecData>>

    override fun setView(view: ResultView) {
        this.view = view
    }

    fun init() {
        sortResultMatchesInformation()
    }

    private fun displayResultMatches(matchesInformation: List<MatchRow>) {
        resultAdapter = LisItemMatchAdapter(matchesInformation)
        view.setRecyclerViewAdapter(resultAdapter)
    }

    private fun sortResultMatchesInformation() {
        if (matchesInformation.isNotEmpty()) {
            resultMatchesByMonth = matchesInformation.groupBy {
                val date = getFormattedDate(it.date)
                getMonthFormatted(date)
            }

            val newMatchRows = ArrayList<MatchRow>()
            for ((key, value) in resultMatchesByMonth) {
                newMatchRows.add(MatchRow(RowType.HEADER, null, key))
                value.mapTo(newMatchRows) { MatchRow(RowType.ITEM, it, null) }
            }
            displayResultMatches(newMatchRows)
        } else {
            view.showEmptyState()
        }
    }
}