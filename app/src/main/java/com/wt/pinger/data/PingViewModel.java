package com.wt.pinger.data;


import android.app.Application;

import com.wt.pinger.App;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class PingViewModel extends AndroidViewModel {

    private DataRepository mDataRepository;
    private final ExecutorService exec = Executors.newSingleThreadExecutor(runnable -> {
        Thread result = new Thread(runnable, "PingItemViewModel Task");
        result.setPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
        return result;
    });

    public PingViewModel(@NonNull Application application) {
        super(application);
        mDataRepository = ((App) application).getRepository();
    }

    public LiveData<List<Ping>> getAll() {
        return mDataRepository.getPingDao().getAll();
    }

    public void addItem(final Ping item) {
        exec.execute(() -> {
            mDataRepository.getPingDao().insertAll(item);
        });
    }

}
