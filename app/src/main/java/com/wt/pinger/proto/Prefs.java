package com.wt.pinger.proto;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by Kenumir on 2016-08-30.
 *
 */
public class Prefs {

    public static final String PREF_UUID = "uuid";
    public static final String PREF_UUID_TIMESTAMP = "uuid_timestamp";
    public static final String PREF_REFERRER = "referrer";
    public static final String PREF_REFERRER_SAVED = "referrer_saved";

    // user settings
    public static final String PREF_START_PING_FROM_LIST = "start_ping_from_list";
    public static final String PREF_MEMBER_OLD_SESSIONS = "member_old_sessions";

    private volatile static Prefs singleton;

    public static Prefs get(@NonNull Context ctx) {
        if (singleton == null) {
            Prefs res = new Prefs(ctx);
            synchronized (Prefs.class) {
                if (singleton == null) {
                    singleton = res;
                }
            }
        }
        return singleton;
    }

    private SharedPreferences pref;

    private Prefs(@NonNull Context ctx) {
        pref = ctx.getApplicationContext()
                .getSharedPreferences(ctx.getPackageName() + ".PREFS", Context.MODE_PRIVATE);
    }

    // String
    public Prefs save(@NonNull String key, String value) {
        pref.edit().putString(key, value).apply();
        return this;
    }
    @Nullable
    public String load(@NonNull String key, @Nullable String defaultValue) {
        return pref.getString(key, defaultValue);
    }
    @Nullable
    public String load(@NonNull String key) {
        return load(key, (String) null);
    }

    // Boolean
    public Prefs save(@NonNull String key, boolean value) {
        pref.edit().putBoolean(key, value).apply();
        return this;
    }

    public boolean load(@NonNull String key, boolean defaultValue) {
        return pref.getBoolean(key, defaultValue);
    }

    // int
    public Prefs save(@NonNull String key, int value) {
        pref.edit().putInt(key, value).apply();
        return this;
    }

    public int load(@NonNull String key, int defaultValue) {
        return pref.getInt(key, defaultValue);
    }

    // long
    public Prefs save(@NonNull String key, long value) {
        pref.edit().putLong(key, value).apply();
        return this;
    }

    public long load(@NonNull String key, long defaultValue) {
        return pref.getLong(key, defaultValue);
    }

    // float
    public Prefs save(@NonNull String key, float value) {
        pref.edit().putFloat(key, value).apply();
        return this;
    }

    public float load(@NonNull String key, float defaultValue) {
        return pref.getFloat(key, defaultValue);
    }

    // Set<String>
    public Prefs save(@NonNull String key, Set<String> value) {
        pref.edit().putStringSet(key, value).apply();
        return this;
    }

    public Set<String> load(@NonNull String key, Set<String> defaultValue) {
        return pref.getStringSet(key, defaultValue);
    }

    @NonNull
    public String getUuid() {
        String uuid = load(PREF_UUID, (String) null);
        if (uuid == null) {
            uuid = UUID.randomUUID().toString();
            save(PREF_UUID, uuid);
            save(PREF_UUID_TIMESTAMP, System.currentTimeMillis());
        }
        return uuid;
    }

    public long getUuidTimestamp() {
        return load(PREF_UUID_TIMESTAMP, 0L);
    }

    public void saveInstallReferrer(@Nullable String s) {
        save(PREF_REFERRER, s);
    }

    @Nullable
    public String loadInstallReferrer() {
        return load(PREF_REFERRER);
    }

    public void saveInstallReferrerSaved(boolean b) {
        save(PREF_REFERRER_SAVED, b);
    }

    public boolean loadInstallReferrerSaved() {
        return load(PREF_REFERRER_SAVED, false);
    }

}
