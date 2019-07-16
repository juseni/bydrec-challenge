package com.bydrecchallenge.myapplication.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HomeAndAwayTeam(
    val id: Int,
    val name: String,
    val shortName: String,
    val abbr: String,
    val alias: String
) : Parcelable
