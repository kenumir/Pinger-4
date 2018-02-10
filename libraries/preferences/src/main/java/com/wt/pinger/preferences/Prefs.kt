package com.wt.pinger.preferences

import android.content.Context
import android.content.SharedPreferences
import com.wt.pinger.utils.SingletonHolder
import java.util.*

/**
 * Created by kenumir on 10.02.2018.
 * singleton based on https://medium.com/@BladeCoder/kotlin-singletons-with-argument-194ef06edd9e
 */
class Prefs private constructor(context: Context) {

    private val prefs : SharedPreferences = context
            .applicationContext
            .getSharedPreferences(context.packageName + ".PREFS", Context.MODE_PRIVATE)

    companion object : SingletonHolder<Prefs, Context>(::Prefs)

    fun uuid() : String {
        var res = prefs.getString(KeyNames.UUID, null)
        if (res == null) {
            res = UUID.randomUUID().toString()
        }
        return res;
    }
}