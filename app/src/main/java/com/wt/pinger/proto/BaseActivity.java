package com.wt.pinger.proto;

import com.wt.pinger.App;
import com.wt.replaioad.ReplaioAdConfig;

import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    public ReplaioAdConfig getReplaioAdConfig() {
        return ((App) getApplication()).getReplaioAdConfig();
    }

}
