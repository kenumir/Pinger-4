package com.wt.pinger.data;

import android.app.Application;

import com.wt.pinger.App;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

public class PingItemViewModel extends AndroidViewModel {

    private final MediatorLiveData<List<PingItem>> mObservableStationFavItem;
    private MutableLiveData<List<PingItem>> mData;
    private DataRepository mDataRepository;
    private final ExecutorService exec = Executors.newSingleThreadExecutor(runnable -> {
        Thread result = new Thread(runnable, "PingItemViewModel Task");
        result.setPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
        return result;
    });

    public PingItemViewModel(@NonNull Application application) {
        super(application);
        mDataRepository = ((App) application).getRepository();
        mObservableStationFavItem = new MediatorLiveData<>();
        mObservableStationFavItem.setValue(null);
        mObservableStationFavItem.addSource(
                mDataRepository.getPingItem(),
                mObservableStationFavItem::setValue
        );
    }

    public LiveData<List<PingItem>> getPingItem() {
        return mObservableStationFavItem;
    }

    public LiveData<List<PingItem>> getAll() {
        if (mData == null) {
            mData = new MutableLiveData<>();
        }
        exec.execute(() -> mData.postValue(mDataRepository.getPingItem().getValue()));
        return mData;
    }
}
