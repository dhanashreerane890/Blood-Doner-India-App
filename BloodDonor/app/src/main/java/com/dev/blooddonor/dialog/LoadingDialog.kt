package com.dev.blooddonor.dialog

import android.app.Activity
import android.app.AlertDialog
import com.dev.blooddonor.R

class LoadingDialog(private var activity: Activity) {
    private lateinit var alertDialod: AlertDialog


    fun startLoadingDialog() {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        builder.setView(inflater.inflate(R.layout.custom_dailog, null))
        builder.setCancelable(false)

        alertDialod = builder.create()
        alertDialod.show()
    }

    fun dismissDialog() {
        alertDialod.dismiss()
    }
}