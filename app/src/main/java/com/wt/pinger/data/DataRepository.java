package com.wt.pinger.data;


import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

public class DataRepository {

    private static volatile DataRepository sInstance;

    @NonNull
    public static DataRepository getInstance(final AppDatabase database) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(database);
                }
            }
        }
        return sInstance;
    }

    private final AppDatabase mDatabase;
    private MediatorLiveData<List<Ping>> mObservablePingItem;

    private DataRepository(AppDatabase database) {
        mDatabase = database;
        mObservablePingItem = new MediatorLiveData<>();

        mObservablePingItem.addSource(
                mDatabase.pingDao().getAll(),
                items -> mObservablePingItem.postValue(items)
        );
    }

    public PingDao getPingDao() {
        return mDatabase.pingDao();
    }

    public LiveData<List<Ping>> getPingItem() {
        return mObservablePingItem;
    }
}
