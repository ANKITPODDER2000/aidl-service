package com.example.serverapp.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.example.serverapp.AppConstant
import com.example.serverapp.IDataShareAidlInterface

class MainService : Service() {

    private val binder = object : IDataShareAidlInterface.Stub() {
        override fun getStoredData(): String {
            val sp =
                applicationContext.getSharedPreferences(AppConstant.PREF_NAME, AppConstant.MODE)
            return sp.getString(AppConstant.STORED_STRING_KEY, "") ?: ""
        }
    }

    override fun onBind(p0: Intent?): IBinder {
        return binder
    }
}