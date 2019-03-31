package com.wt.pinger.data;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface PingItemDao {

    @Query("SELECT * FROM ping ORDER BY _id ASC")
    LiveData<List<PingItem>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addItem(PingItem item);

    @Delete
    void delItem(PingItem... items);

    @Query("SELECT * FROM ping WHERE _id = :entryId")
    PingItem findItemById(long entryId);

}
