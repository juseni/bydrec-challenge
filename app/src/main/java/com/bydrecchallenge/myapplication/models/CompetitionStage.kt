package com.bydrecchallenge.myapplication.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CompetitionStage(
    val competition: Competition,
    val stage: String?,
    val leg: String?
) : Parcelable
