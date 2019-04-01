package com.wt.pinger

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.crashlytics.android.Crashlytics
import com.google.firebase.perf.FirebasePerformance
import com.wt.pinger.data.AppDatabase
import com.wt.pinger.data.DataRepository
import com.wt.pinger.proto.Prefs
import com.wt.replaioad.ReplaioAdConfig
import io.fabric.sdk.android.Fabric


class App : MultiDexApplication() {

    var replaioAdConfig: ReplaioAdConfig? = null
        private set

    val repository: DataRepository
        get() = DataRepository.getInstance(AppDatabase.getInstance(this))

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()

        val fabricTrace = FirebasePerformance.getInstance().newTrace("fabric_init")
        fabricTrace.start()
        val fabric = Fabric.Builder(this)
                .kits(Crashlytics())
                .debuggable(BuildConfig.DEBUG)
                .build()
        Fabric.with(fabric)
        fabricTrace.stop()

        val prefs = Prefs.get(this)
        Crashlytics.setUserIdentifier(prefs.uuid)
        Crashlytics.setLong("Build Timestamp", BuildConfig.APP_BUILD_TIMESTAMP)
        Crashlytics.setLong("UUID Timestamp", prefs.uuidTimestamp)

        replaioAdConfig = ReplaioAdConfig(this)
    }
}
