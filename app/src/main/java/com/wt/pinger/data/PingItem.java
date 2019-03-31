package com.wt.pinger.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "ping")
public class PingItem {

    @ColumnInfo(name = "_id")
    @PrimaryKey(autoGenerate = true)
    public long _id;

    @Ignore
    @NonNull
    @Override
    public String toString() {
        return "{_id=" + _id + "}";
    }
}
