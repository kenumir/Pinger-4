package com.wt.pinger

import android.app.Application
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.answers.Answers
import com.google.firebase.perf.metrics.AddTrace
import com.wt.pinger.logging.Logs
import com.wt.pinger.preferences.Prefs
import io.fabric.sdk.android.Fabric

/**
 * Created by kenumir on 10.02.2018.
 *
 */
class App : Application() {

    @AddTrace(name = "App.onCreate")
    override fun onCreate() {
        super.onCreate()
        val uuid = Prefs.init(this).uuid()

        Fabric.with(Fabric.Builder(this)
                .kits(Crashlytics(), Answers())
                .debuggable(true)
                .build())
        Crashlytics.setUserIdentifier(uuid)

        Logs.init("pinger")

        if (BuildConfig.DEBUG) {
            Logs.i("App init with uuid `${uuid}`")
        }
    }

}