package com.bydrecchallenge.myapplication.utils

import android.app.Activity
import android.widget.Button
import com.bydrecchallenge.myapplication.R
import com.bydrecchallenge.myapplication.interfaces.OnClickErrorListener

fun showSnackInformation(activity: Activity, listener: OnClickErrorListener) {
    val view = activity.layoutInflater.inflate(R.layout.custom_message_error, null, false)
    val alertDialog = android.support.v7.app.AlertDialog.Builder(activity).create()

    val tryButton = view.findViewById(R.id.tryBtnError) as Button

    with(alertDialog) {
        setView(view)
        setCanceledOnTouchOutside(true)
        show()
    }

    tryButton.setOnClickListener{ v ->
        listener.onErrorClicked(0)
        alertDialog.dismiss()
    }

}