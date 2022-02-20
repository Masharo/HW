package com.masharo.hw

import android.content.Context.MODE_PRIVATE
import android.content.ContextWrapper
import android.content.SharedPreferences

object CountConfigChange {

    const val COUNT = "count"
    private const val INC_SAVE = "inc_save"

    var count: Int = 0
    private var sharedPreferences: SharedPreferences? = null

    fun countNotValid() = count == 0

    fun instanceSP(context: ContextWrapper) {
        sharedPreferences = context.getSharedPreferences(INC_SAVE, MODE_PRIVATE)
    }

    fun incCount() {
        sharedPreferences?.edit()?.putBoolean(INC_SAVE, true)?.apply()
    }

    fun isConfigChange() {
        sharedPreferences?.let {

            if (it.contains(INC_SAVE) &&
                it.getBoolean(INC_SAVE, false)
            ) {
                it.edit().putBoolean(INC_SAVE, false).apply()
                count++
            }

        }
    }
}