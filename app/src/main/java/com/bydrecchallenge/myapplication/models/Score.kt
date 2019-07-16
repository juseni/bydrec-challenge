package com.bydrecchallenge.myapplication.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Score(
    val home: Int,
    val away: Int,
    val winner: String
) : Parcelable
