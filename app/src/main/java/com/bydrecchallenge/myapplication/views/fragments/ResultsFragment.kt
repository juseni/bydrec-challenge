package com.bydrecchallenge.myapplication.views.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup

import com.bydrecchallenge.myapplication.R
import com.bydrecchallenge.myapplication.adapters.LisItemMatchAdapter
import com.bydrecchallenge.myapplication.application.AppManager
import com.bydrecchallenge.myapplication.models.MatchesInformation
import com.bydrecchallenge.myapplication.presenters.ResultPresenter
import com.bydrecchallenge.myapplication.presenters.ResultView
import com.bydrecchallenge.myapplication.utils.Const
import kotlinx.android.synthetic.main.fragment_results.*
import javax.inject.Inject

class ResultsFragment : Fragment(), ResultView {

    @Inject
    lateinit var presenter: ResultPresenter

    companion object {
        fun newInstance(matchesInformation: MatchesInformation): ResultsFragment {
            val bundle = Bundle()
            val resultFragment = ResultsFragment()

            bundle.putParcelable(Const.MATCHES_INFORMATION_KEY, matchesInformation)
            resultFragment.arguments = bundle

            return resultFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.APP_DAGGER_COMPONENT.inject(this)
        arguments?.let {
            if (it.containsKey(Const.MATCHES_INFORMATION_KEY)) {
                val matchInformationBundle = it.getParcelable(Const.MATCHES_INFORMATION_KEY) as MatchesInformation
                presenter.matchesInformation = matchInformationBundle.matchesInformation
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        presenter.setView(this)

        return inflater.inflate(R.layout.fragment_results, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.init()
    }

    override fun setRecyclerViewAdapter(adapter: LisItemMatchAdapter) {
        if (isAdded) {
            recyclerViewResult.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            recyclerViewResult.adapter = adapter
        }
    }

    override fun showEmptyState() {
        if (isAdded) {
            recyclerViewResult.visibility = GONE
            resultEmptyState.visibility = VISIBLE
        }
    }

}
