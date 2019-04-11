package com.wt.pinger.data;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Ping {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "address")
    public String address;

    @Ignore
    @NonNull
    @Override
    public String toString() {
        return "{id=" + id + ", address=" + address + "}";
    }

    public Bundle toBundle() {
        Bundle res = new Bundle();
        res.putInt("id", id);
        res.putString("address", address);
        return res;
    }

    @Ignore
    public static Ping fromBundle(@Nullable Bundle b) {
        Ping res = new Ping();
        if (b != null) {
            res.id = b.getInt("id");
            res.address = b.getString("address");
        }
        return res;
    }
}
