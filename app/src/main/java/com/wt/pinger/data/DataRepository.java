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
    private MediatorLiveData<List<PingItem>> mObservablePingItem;

    private DataRepository(AppDatabase database) {
        mDatabase = database;
        mObservablePingItem = new MediatorLiveData<>();

        mObservablePingItem.addSource(
                mDatabase.pingItemDao().getAll(),
                favItemsEntities -> mObservablePingItem.postValue(favItemsEntities)
        );
    }

    public LiveData<List<PingItem>> getPingItem() {
        return mObservablePingItem;
    }
}
