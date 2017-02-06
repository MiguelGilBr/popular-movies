package com.example.popularmovies.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class BaseActivity extends AppCompatActivity {
    protected Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
    }

    public void goToActivity(Class activityClass, Bundle b, int... flags) {
        Intent intent = new Intent(mContext, activityClass);
        intent.putExtras(b);

        for (int flag: flags) {
            intent.addFlags(flag);
        }

        startActivity(intent);
    }
}