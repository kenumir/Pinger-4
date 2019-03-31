package com.wt.pinger;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.perf.FirebasePerformance;
import com.google.firebase.perf.metrics.Trace;
import com.wt.pinger.data.AppDatabase;
import com.wt.pinger.data.DataRepository;
import com.wt.pinger.proto.Prefs;
import com.wt.replaioad.ReplaioAdConfig;

import androidx.annotation.NonNull;
import io.fabric.sdk.android.Fabric;

public class App extends Application {

    private ReplaioAdConfig mReplaioAdConfig;

    @Override
    public void onCreate() {
        super.onCreate();

        Trace fabricTrace = FirebasePerformance.getInstance().newTrace("fabric_init");
        fabricTrace.start();
        Fabric fabric = new Fabric.Builder(this)
                .kits(new Crashlytics())
                .debuggable(BuildConfig.DEBUG)
                .build();
        Fabric.with(fabric);
        fabricTrace.stop();

        Prefs prefs = Prefs.get(this);
        Crashlytics.setUserIdentifier(prefs.getUuid());
        Crashlytics.setLong("Build Timestamp", BuildConfig.APP_BUILD_TIMESTAMP);
        Crashlytics.setLong("UUID Timestamp", prefs.getUuidTimestamp());

        mReplaioAdConfig = new ReplaioAdConfig(this);
    }

    @NonNull
    public ReplaioAdConfig getReplaioAdConfig() {
        return mReplaioAdConfig;
    }

    @NonNull
    public DataRepository getRepository() {
        return DataRepository.getInstance(AppDatabase.getInstance(this));
    }
}
