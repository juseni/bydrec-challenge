package com.bydrecchallenge.myapplication.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Competition(
    val id: Int,
    val name: String
) : Parcelable
