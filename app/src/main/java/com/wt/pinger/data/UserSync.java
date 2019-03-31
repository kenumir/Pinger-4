package com.wt.pinger.data;

import android.content.Context;

import com.wt.pinger.data.api.Api;
import com.wt.pinger.data.api.NewUser;
import com.wt.pinger.proto.Prefs;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;

/**
 * Created by Kenumir on 2016-12-29.
 *
 */

public class UserSync {

    private volatile static UserSync singleton;

    public static UserSync get() {
        if (singleton == null) {
            UserSync res = new UserSync();
            synchronized (UserSync.class) {
                singleton = res;
            }
        }
        return singleton;
    }

    private final ExecutorService exec = Executors.newSingleThreadExecutor(runnable -> {
        Thread result = new Thread(runnable, "UserSync Task");
        result.setPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
        return result;
    });

    private UserSync() {

    }

    public void saveUser(@NonNull Context c) {
        final Context ctx = c.getApplicationContext();
        exec.execute(() -> {
            boolean andyDataToSend = false;
            Prefs prefs = Prefs.get(ctx);
            String uuid = prefs.getUuid();
            String referrer = prefs.loadInstallReferrer();
            if (!prefs.loadInstallReferrerSaved() && referrer != null) {
                andyDataToSend = true;
            }
            if (andyDataToSend) {
                NewUser user = NewUser.init(ctx, uuid, referrer);
                boolean saveSuccess = Api.getInstance().saveNewUser(user);
                if (saveSuccess) {
                    prefs.saveInstallReferrerSaved(true);
                }
            }
        });
    }

}
