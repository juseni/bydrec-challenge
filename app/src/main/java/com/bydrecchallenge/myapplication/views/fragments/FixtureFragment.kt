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
import com.bydrecchallenge.myapplication.presenters.FixturePresenter
import com.bydrecchallenge.myapplication.presenters.FixtureView
import com.bydrecchallenge.myapplication.utils.Const
import kotlinx.android.synthetic.main.fragment_fixture.*
import javax.inject.Inject

class FixtureFragment : Fragment(), FixtureView {

    @Inject
    lateinit var presenter: FixturePresenter

    companion object {
        fun newInstance(matchesInformation: MatchesInformation): FixtureFragment {
            val bundle = Bundle()
            val fixtureFragment = FixtureFragment()

            bundle.putParcelable(Const.MATCHES_INFORMATION_KEY, matchesInformation)
            fixtureFragment.arguments = bundle

            return fixtureFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.APP_DAGGER_COMPONENT.inject(this)
        arguments?.let {
            if(it.containsKey(Const.MATCHES_INFORMATION_KEY)) {
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
        return inflater.inflate(R.layout.fragment_fixture, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.init()
    }

    override fun setupRecyclerView(adapter: LisItemMatchAdapter) {
        if (isAdded) {
            recyclerViewFixture.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            recyclerViewFixture.adapter = adapter
        }
    }

    override fun showEmptyState() {
        if (isAdded) {
            recyclerViewFixture.visibility = GONE
            fixtureEmptyState.visibility = VISIBLE
        }
    }
}
