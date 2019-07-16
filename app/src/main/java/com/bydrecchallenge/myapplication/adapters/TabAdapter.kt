package com.bydrecchallenge.myapplication.adapters

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.bydrecchallenge.myapplication.utils.Const
import com.bydrecchallenge.myapplication.views.fragments.FixtureFragment
import com.bydrecchallenge.myapplication.views.fragments.ResultsFragment


class TabAdapter(private val context: Context, fragmentManager: FragmentManager,
                 internal var totalTabs: Int, internal var fragments: ArrayList<Fragment>) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment? {
        when (position) {
            Const.FIXTURE_FRAGMENT_POSITION -> return fragments[Const.FIXTURE_FRAGMENT_POSITION]
            Const.RESULTS_FRAGMENT_POSITION -> return fragments[Const.RESULTS_FRAGMENT_POSITION]
            else -> return null
        }
    }

    override fun getCount() = totalTabs
}
