package com.bydrecchallenge.myapplication.views.activities

/*
MADE FOR BYDREC COMPANY
TECHNICAL CHALLENGE

AUTHOR: JUAN SEBASTIAN NIÑO BECERRA
sept 15th 2019
 */

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.View.*
import com.bydrecchallenge.myapplication.R
import com.bydrecchallenge.myapplication.adapters.TabAdapter
import com.bydrecchallenge.myapplication.application.AppManager
import com.bydrecchallenge.myapplication.interfaces.OnClickErrorListener
import com.bydrecchallenge.myapplication.models.ChampionshipItems
import com.bydrecchallenge.myapplication.presenters.MainPresenter
import com.bydrecchallenge.myapplication.presenters.MainView
import com.bydrecchallenge.myapplication.utils.Const
import com.bydrecchallenge.myapplication.utils.showSnackInformation
import kotlinx.android.synthetic.main.layout_main_activity.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainView, OnClickListener, OnClickErrorListener {

    @Inject
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_main_activity)

        AppManager.APP_DAGGER_COMPONENT.inject(this)

        intent.extras?.let {
            if (it.containsKey(Const.ID_CHAMPIONSHIP_KEY) && it.containsKey(Const.TAB_SELECTED_KEY)) {
                presenter.championshipIdFilter = it.getInt(Const.ID_CHAMPIONSHIP_KEY)
                presenter.selectedTab = it.getInt(Const.TAB_SELECTED_KEY)
            }
        }

        presenter.setView(this)
        presenter.resources = resources
        presenter.fragmentManager = supportFragmentManager
        presenter.viewPager = viewPager
        presenter.tabLayout = tabLayout
        presenter.context = this

        presenter.init()
        setClickListener()

    }

    private fun setClickListener() {
        filterClickableContainer.setOnClickListener(this)
    }

    override fun addTab(tab: TabLayout.Tab) {
        tabLayout.addTab(tab)
    }

    override fun setTabAdapter(adapter: TabAdapter) {
        viewPager.adapter = adapter
    }

    override fun setOnTabSelectedListener(tabListener: TabLayout.OnTabSelectedListener) {
        tabLayout.addOnTabSelectedListener(tabListener)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Const.FILTER_REQUEST && resultCode == RESULT_OK && data != null) {
            data.extras?.getInt(Const.ID_CHAMPIONSHIP_KEY).let {
                finish()
                val bundle = Bundle()
                bundle.putInt(Const.ID_CHAMPIONSHIP_KEY, it!!)
                bundle.putInt(Const.TAB_SELECTED_KEY, presenter.selectedTab)
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtras(bundle)
                startActivity(intent)
            }
        }
    }

    override fun setOnPageChangedListener() {
        viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
    }

    override fun showLoading() {
        progressBar.visibility = VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = GONE
    }

    override fun setViewPagerVisibility(isVisible: Boolean) {
        viewPager.visibility =
            when (isVisible) {
                true -> VISIBLE
                false -> GONE
            }
    }

    override fun setFilterContainerVisibility(isVisible: Boolean) {
        filterClickableContainer.visibility =
            when (isVisible) {
                true -> VISIBLE
                false -> GONE
            }
    }

    override fun showErrorMessage() {
        showSnackInformation(this, this)
    }

    override fun callChampionshipFilterScreen(championshipItems: ChampionshipItems) {
        val bundle = Bundle()
        bundle.putParcelable(Const.CHAMPIONSHIP_ITEMS_KEY, championshipItems)
        val intent = Intent(this, FilterChampionshipActivity::class.java)
        intent.putExtras(bundle)

        startActivityForResult(intent, Const.FILTER_REQUEST)
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.filterClickableContainer -> presenter.setFilterClicked()
        }
    }

    override fun onErrorClicked(which: Int) {
        presenter.init()
    }

    override fun selectTab(position: Int) {
        tabLayout.getTabAt(position)?.select()
    }


}