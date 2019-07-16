package com.bydrecchallenge.myapplication.adapters

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater.from
import com.bydrecchallenge.myapplication.R
import com.bydrecchallenge.myapplication.adapters.FilterChampionshipItemAdapter.ChampionshipViewHolder
import com.bydrecchallenge.myapplication.models.Competition
import kotlinx.android.synthetic.main.list_item_filter_championship.view.*

class FilterChampionshipItemAdapter (private val championships: List<Competition>, val listener: OnfilterChampionshipListener) : RecyclerView.Adapter<ChampionshipViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ChampionshipViewHolder(from(parent.context).inflate(R.layout.list_item_filter_championship, parent, false))

    override fun getItemCount() = championships.size

    override fun onBindViewHolder(holder: ChampionshipViewHolder, position: Int) {
        holder.championshipText.text = championships[position].name
        holder.filterChampionshipContainer.setOnClickListener {
            listener.onFilterChampionshipClicked(championships[position].id)
        }
    }

    inner class ChampionshipViewHolder(view: View) : ViewHolder(view) {
        val championshipText = view.filterText
        val filterChampionshipContainer = view.filterContainer
    }
}

interface OnfilterChampionshipListener {
    fun onFilterChampionshipClicked(championshipId: Int)
}