package com.bydrecchallenge.myapplication.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class MatchesInformation (
    var matchesInformation: List<BydrecData>
) : Parcelable