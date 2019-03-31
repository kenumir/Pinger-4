package com.wt.pinger.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

    @Ignore
    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof PingItem) {
            PingItem o = (PingItem) obj;
            return _id == o._id;
        }
        return false;
    }
}
