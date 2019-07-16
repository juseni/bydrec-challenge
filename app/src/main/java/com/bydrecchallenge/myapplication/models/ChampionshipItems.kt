package com.bydrecchallenge.myapplication.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ChampionshipItems(
    val cahmpionshipItems: List<Competition>
) : Parcelable
