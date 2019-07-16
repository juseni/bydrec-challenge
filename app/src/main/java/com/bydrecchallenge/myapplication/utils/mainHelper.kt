package com.bydrecchallenge.myapplication.utils

import android.content.Context
import android.content.res.Resources
import android.support.v4.os.ConfigurationCompat
import com.bydrecchallenge.myapplication.R
import com.bydrecchallenge.myapplication.utils.Const.Companion.API_DATE_FORMAT
import com.bydrecchallenge.myapplication.utils.Const.Companion.DAY_OF_WEEK_FORMAT
import com.bydrecchallenge.myapplication.utils.Const.Companion.MMM_DD_YYYY_HH_MM_FORMAT
import com.bydrecchallenge.myapplication.utils.Const.Companion.MONTH_FORMAT
import com.bydrecchallenge.myapplication.utils.Const.Companion.POSTPONED_STATE
import com.bydrecchallenge.myapplication.utils.Const.Companion.TYPE_MATCH_INFORMATION
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.DAY_OF_MONTH
import java.util.Calendar.DAY_OF_WEEK

fun stringFormateDate(stringDate: String, context: Context): String {
    val formattedDate = getFormattedDate(stringDate)
    val dateFormat = SimpleDateFormat(MMM_DD_YYYY_HH_MM_FORMAT, Locale.ENGLISH)
    return dateFormat.format(formattedDate).replace("-", context.resources.getString(R.string.date_separator))
}

fun getDayOrDayWeek(stringDate: String, dayType: Int): String {
    val formattedDate = getFormattedDate(stringDate)
    val calendar = Calendar.getInstance()
    calendar.time = formattedDate
    return when (dayType) {
        DAY_OF_WEEK -> {
            val apiDateFormat = SimpleDateFormat(DAY_OF_WEEK_FORMAT,
                ConfigurationCompat.getLocales(Resources.getSystem().configuration).get(0))
            apiDateFormat.format(formattedDate)
        }
        DAY_OF_MONTH -> calendar.get(DAY_OF_MONTH).toString()
        else -> ""
    }
}

fun getFormattedDate(stringDate: String): Date {
    val apiDateFormat = SimpleDateFormat(API_DATE_FORMAT, Locale.ENGLISH)
    return apiDateFormat.parse(adjustDateFormat(stringDate))
}

fun getMonthFormatted(date: Date): String {
    val dateFormat = SimpleDateFormat(MONTH_FORMAT,
        ConfigurationCompat.getLocales(Resources.getSystem().configuration).get(0))
    return dateFormat.format(date)
}

fun isPostponedMatch(postponed: String?): Boolean {
    if (postponed != null) {
        return postponed.equals(POSTPONED_STATE, true)
    } else {
        return false
    }
}

fun isResultMatchInformation(typeMatch: String?): Boolean {
    if (typeMatch != null) {
        return typeMatch.equals(TYPE_MATCH_INFORMATION, true)
    } else{
        return false
    }
}

private fun adjustDateFormat(stringDate: String): String {
    return stringDate.replace("T", " ").replace("Z", "")
}