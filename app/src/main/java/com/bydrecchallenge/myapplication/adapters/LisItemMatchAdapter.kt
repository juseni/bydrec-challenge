package com.bydrecchallenge.myapplication.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView.Adapter
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater.from
import android.view.View.*
import com.bydrecchallenge.myapplication.R
import com.bydrecchallenge.myapplication.adapters.LisItemMatchAdapter.MatchViewHolder
import com.bydrecchallenge.myapplication.models.BydrecData

import com.bydrecchallenge.myapplication.models.MatchRow
import com.bydrecchallenge.myapplication.models.RowType
import com.bydrecchallenge.myapplication.utils.getDayOrDayWeek
import com.bydrecchallenge.myapplication.utils.isPostponedMatch
import com.bydrecchallenge.myapplication.utils.isResultMatchInformation
import com.bydrecchallenge.myapplication.utils.stringFormateDate
import kotlinx.android.synthetic.main.list_item_header_month.view.*
import kotlinx.android.synthetic.main.list_item_soccer_matches.view.*
import java.util.*


class LisItemMatchAdapter(private var matchRowList: List<MatchRow>) : Adapter<MatchViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        context = parent.context
        val layoutInflater = from(context)
        val inflatedView: View = when (viewType) {
            RowType.ITEM.ordinal -> layoutInflater.inflate(R.layout.list_item_soccer_matches, parent, false)
            else -> layoutInflater.inflate(R.layout.list_item_header_month, parent, false)
        }
        return MatchViewHolder(inflatedView)
    }

    override fun getItemCount() = matchRowList.size

    override fun getItemViewType(position: Int) = matchRowList[position].type.ordinal

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {

        val matchItemRow = matchRowList[position]

        if (matchItemRow.type == RowType.ITEM) {
            val matchInformationItem = matchItemRow.item

            matchInformationItem!!.let {
                with(holder) {
                    competitionName.text = it.competitionStage.competition.name
                    stadiumName.text = it.venue.name
                    dateMatch.text = stringFormateDate(it.date, context)
                    homeTeam.text = it.homeTeam.shortName
                    awayTeam.text = it.awayTeam.shortName

                    if (isResultMatchInformation(it.type)) {
                        setResultMatches(this, it)

                    } else {
                        setFixtureMatches(this, it)
                    }
                }
            }
        } else {
            holder.monthText.text = matchItemRow.header
        }
    }

    private fun setFixtureMatches(holder: MatchViewHolder, matchInformation: BydrecData) {
        with(holder) {
            lineDateSeparator.visibility = VISIBLE
            dayMatchContainer.visibility = VISIBLE
            scoreMatchContainer.visibility = GONE
            matchInformation.let {
                dayNumberMatch.text = getDayOrDayWeek(it.date, Calendar.DAY_OF_MONTH)
                dayWeekMatch.text = getDayOrDayWeek(it.date, Calendar.DAY_OF_WEEK)

                if (isPostponedMatch(it.state)) {
                    postponedMatch.visibility = VISIBLE
                    dateMatch.typeface = Typeface.DEFAULT_BOLD
                }
            }
        }
    }

    private fun setResultMatches(holder: MatchViewHolder, matchInformation: BydrecData) {
        with(holder) {
            lineDateSeparator.visibility = GONE
            dayMatchContainer.visibility = GONE
            scoreMatchContainer.visibility = VISIBLE
            matchInformation.let {
                localScore.text = it.score!!.home.toString()
                awayScore.text = it.score.away.toString()
                when {
                    it.score.home > it.score.away -> setScoreColor(this, Color.BLUE, Color.BLACK)
                    it.score.home < it.score.away -> setScoreColor(this, Color.BLACK, Color.BLUE)
                    else -> setScoreColor(this, Color.BLACK, Color.BLACK)
                }
            }
        }

    }

    private fun setScoreColor(holder: MatchViewHolder, colorHomeTeam: Int, colorAwayteam: Int) {
        with(holder) {
            localScore.setTextColor(colorHomeTeam)
            awayScore.setTextColor(colorAwayteam)
        }
    }

    inner class MatchViewHolder(itemView: View) : ViewHolder(itemView) {
        val monthText = itemView.monthText
        val competitionName = itemView.competitionName
        val stadiumName = itemView.stadiumName
        val dateMatch = itemView.dateMatch
        val postponedMatch = itemView.postponedMatch
        val homeTeam = itemView.homeTeam
        val awayTeam = itemView.awayTeam
        val lineDateSeparator = itemView.lineDateSeparator
        val dayMatchContainer = itemView.dayMatchContainer
        val dayNumberMatch = itemView.dayNumberMatch
        val dayWeekMatch = itemView.dayWeekMatch
        val scoreMatchContainer = itemView.scoreMatchContainer
        val localScore = itemView.localScore
        val awayScore = itemView.awayScore
    }
}


class MatchRowDiffCallback(private val newRows: List<MatchRow>, private val oldRows: List<MatchRow>) :
    DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldRow = oldRows[oldItemPosition]
        val newRow = newRows[newItemPosition]
        return oldRow.type == newRow.type
    }

    override fun getOldListSize(): Int = oldRows.size

    override fun getNewListSize(): Int = newRows.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldRow = oldRows[oldItemPosition]
        val newRow = newRows[newItemPosition]
        return oldRow == newRow
    }
}




