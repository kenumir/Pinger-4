package com.wt.pinger.logging

import android.util.Log

/**
 * Created by kenumir on 10.02.2018.
 *
 */
object Logs {

    lateinit var TAG : String

    @JvmStatic fun init(t : String) {
        TAG = t
    }

    @JvmStatic fun v(t : String) {
        Log.v(TAG, t)
    }

    @JvmStatic fun i(t : String) {
        Log.i(TAG, t)
    }

    @JvmStatic fun d(t : String) {
        Log.d(TAG, t)
    }

    @JvmStatic fun w(t : String) {
        Log.w(TAG, t)
    }

    @JvmStatic fun e(t : String) {
        Log.e(TAG, t)
    }

}