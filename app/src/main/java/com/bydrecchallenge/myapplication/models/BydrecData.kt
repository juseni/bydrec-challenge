package com.bydrecchallenge.myapplication.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BydrecData(
    val id: Int,
    val type: String,
    val homeTeam: HomeAndAwayTeam,
    val awayTeam: HomeAndAwayTeam,
    val date: String,
    val competitionStage: CompetitionStage,
    val venue: Venue,
    val state: String,
    val score: Score?
) : Parcelable