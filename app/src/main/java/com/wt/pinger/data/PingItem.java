package com.wt.pinger.data;

import android.os.Bundle;

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

    @ColumnInfo(name = "address")
    public String address;

    @Ignore
    @NonNull
    @Override
    public String toString() {
        return "{_id=" + _id + ", address=" + address + "}";
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

    @Ignore
    @NonNull
    public Bundle toBundle() {
        Bundle res = new Bundle();
        res.putLong("_id", _id);
        res.putString("address", address);
        return res;
    }

    @Ignore
    @NonNull
    public static PingItem fromBundle(@Nullable Bundle b) {
        PingItem res = new PingItem();
        if (b != null) {
            res._id = b.getLong("_id", 0);
            res.address = b.getString("address");
        }
        return res;
    }
}
