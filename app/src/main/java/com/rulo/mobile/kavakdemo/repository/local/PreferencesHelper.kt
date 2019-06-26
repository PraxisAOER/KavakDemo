package com.rulo.mobile.kavakdemo.repository.local

import android.content.Context
import android.content.SharedPreferences
import java.util.*


class PreferencesHelper(private val context: Context) {
    private val preferences: SharedPreferences = context.getSharedPreferences("KAVAK_PREFS", Context.MODE_PRIVATE)
    val LAST_UPDATE_DB = "LAST_UPDATE_DB"


    val lastUpdateDate: Long
        get() = preferences.getLong(LAST_UPDATE_DB, 0)

    fun setUpdateDatabaseTimeNow() {
        val edit = preferences.edit()
        edit.putLong(LAST_UPDATE_DB, Calendar.getInstance().timeInMillis)
        edit.apply()
    }

    fun itsTimeToUpdate(): Boolean {
        return true//(TimeUnit.MILLISECONDS.toDays(getLastUpdateDate().time - Calendar.getInstance().timeInMillis) > 1)
    }

    fun getLastUpdateDate(): Date = Date(lastUpdateDate)

    fun clear() {
        val edit = preferences.edit()
        edit.clear()
        edit.commit()
    }


    companion object {
        private val TAG = "PreferencesHelper"
    }


}