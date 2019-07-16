package com.bydrecchallenge.myapplication.presenters

import android.content.Context
import android.content.res.Resources
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.ViewPager
import com.bydrecchallenge.myapplication.R
import com.bydrecchallenge.myapplication.adapters.TabAdapter
import com.bydrecchallenge.myapplication.interactors.BydrecDataInteractor
import com.bydrecchallenge.myapplication.interactors.BydrecDataInteractor.BydrecInteractorListener
import com.bydrecchallenge.myapplication.interfaces.Presenter
import com.bydrecchallenge.myapplication.models.BydrecData
import com.bydrecchallenge.myapplication.models.ChampionshipItems
import com.bydrecchallenge.myapplication.models.Competition
import com.bydrecchallenge.myapplication.models.MatchesInformation
import com.bydrecchallenge.myapplication.utils.Const
import com.bydrecchallenge.myapplication.views.fragments.FixtureFragment
import com.bydrecchallenge.myapplication.views.fragments.ResultsFragment
import java.util.ArrayList
import javax.inject.Inject

class MainPresenter @Inject constructor(private val interactor: BydrecDataInteractor) : Presenter<MainView>,
    BydrecInteractorListener {

    private lateinit var view: MainView

    lateinit var resources: Resources
    lateinit var fixtureMatchesInformation: MatchesInformation
    lateinit var resultMatchesInformation: MatchesInformation
    lateinit var fragmentManager: FragmentManager
    lateinit var viewPager: ViewPager
    lateinit var tabLayout: TabLayout
    lateinit var context: Context

    var selectedTab = Const.FIXTURE_FRAGMENT_POSITION
    var championshipIdFilter = Const.NO_FILTER

    override fun setView(view: MainView) {
        this.view = view
    }

    fun init() {
        view.setViewPagerVisibility(false)
        view.setFilterContainerVisibility(false)
        view.showLoading()
        callFixtureService()
    }

    fun setFilterClicked() {
        if (fixtureMatchesInformation.matchesInformation.isNotEmpty() && resultMatchesInformation.matchesInformation.isNotEmpty()) {

            val fixtureChampionshipMap = fixtureMatchesInformation.matchesInformation.groupBy { it.competitionStage.competition  }
            val resultChampionshipMap = resultMatchesInformation.matchesInformation.groupBy { it.competitionStage.competition  }

            val championshipList = ArrayList<Competition>()

            for ((key) in fixtureChampionshipMap) {
                if (!championshipList.contains(key)) {
                    championshipList.add(key)
                }
            }
            for ((key) in resultChampionshipMap) {
                if (!championshipList.contains(key)) {
                    championshipList.add(key)
                }
            }

            if (championshipList.size != 0) {
                view.callChampionshipFilterScreen(ChampionshipItems(championshipList))
            } else {
                view.showErrorMessage()
            }

        }

    }

    private fun callFixtureService() {
        interactor.getFixturesBydrecData(this)
    }

    private fun callResultService() {
        interactor.getResultsBydrecData(this)
    }

    override fun onBydrecFixtureDataSuccess(matchesInformation: MatchesInformation) {
        fixtureMatchesInformation = matchesInformation
        callResultService()
    }

    override fun onBydrecResultDataSuccess(matchesInformation: MatchesInformation) {
        resultMatchesInformation = matchesInformation
        setupPagerAdapter()
    }

    override fun onBydrecDataFailure() {
        view.showErrorMessage()
    }

    private fun setupPagerAdapter() {

        val fixtureFragmentInstance = FixtureFragment.newInstance(getMatchList(fixtureMatchesInformation.matchesInformation))
        val resultFragmentInstance = ResultsFragment.newInstance(getMatchList(resultMatchesInformation.matchesInformation))

        view.addTab(tabLayout.newTab().setText(R.string.fixture_text))
        view.addTab(tabLayout.newTab().setText(R.string.result_text))

        val fragments = ArrayList<Fragment>()
        fragments.add(fixtureFragmentInstance)
        fragments.add(resultFragmentInstance)

        val adapter = TabAdapter(context, fragmentManager, tabLayout.tabCount, fragments)

        val tabSelectedListener = object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                tab.position.let {
                    viewPager.currentItem = it
                    selectedTab = it
                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        }

        view.setTabAdapter(adapter)
        view.setOnTabSelectedListener(tabSelectedListener)
        view.setOnPageChangedListener()
        view.hideLoading()
        view.selectTab(selectedTab)
        view.setViewPagerVisibility(true)
        view.setFilterContainerVisibility(true)
    }

    private fun getMatchList(matchesInformation: List<BydrecData>) : MatchesInformation {
        if (championshipIdFilter != Const.NO_FILTER) {
            return sortMatchListWithFilter(matchesInformation)
        } else {
            return MatchesInformation(matchesInformation)
        }
    }

    private fun sortMatchListWithFilter(matchesInformation: List<BydrecData>) : MatchesInformation {
        val newMatchesInformationSorted = ArrayList<BydrecData>()
        for (matchItem in matchesInformation) {
            if (matchItem.competitionStage.competition.id == championshipIdFilter) {
                newMatchesInformationSorted.add(matchItem)
            }
        }
        return MatchesInformation(newMatchesInformationSorted)
    }

}