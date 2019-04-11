package com.wt.pinger.data;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface PingDao {
    @Query("SELECT * FROM Ping")
    LiveData<List<Ping>> getAll();

    @Query("SELECT * FROM Ping WHERE id IN (:ids)")
    List<Ping> loadAllByIds(int[] ids);

    @Query("SELECT * FROM Ping WHERE address LIKE :first LIMIT 1")
    Ping findByName(String first);

    @Insert
    void insertAll(Ping... users);

    @Delete
    void delete(Ping user);
}
