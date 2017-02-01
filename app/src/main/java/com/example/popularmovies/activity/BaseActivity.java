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

    protected void showBasicToast(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    protected void restartActivity(Class activityClass) {
        goToActivity(activityClass, Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    protected void openActivity(Class activityClass) {
        goToActivity(activityClass, Intent.FLAG_ACTIVITY_NEW_TASK,
                                    Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }

    protected void goToActivity(Class activityClass, int... flags) {
        Intent intent = new Intent(mContext, activityClass);
        for (int flag: flags) {
            intent.addFlags(flag);
        }
        startActivity(intent);
    }
}