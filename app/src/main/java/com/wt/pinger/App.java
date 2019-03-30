package com.wt.pinger;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.perf.FirebasePerformance;
import com.google.firebase.perf.metrics.Trace;

import io.fabric.sdk.android.Fabric;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Trace fabricTrace = FirebasePerformance.getInstance().newTrace("fabric_init");
        fabricTrace.start();
        Fabric fabric = new Fabric.Builder(this)
                .kits(new Crashlytics())
                .debuggable(true)
                .build();
        Fabric.with(fabric);
        fabricTrace.stop();
        Crashlytics.setLong("Build Time", BuildConfig.APP_BUILD_TIMESTAMP);
    }
}
