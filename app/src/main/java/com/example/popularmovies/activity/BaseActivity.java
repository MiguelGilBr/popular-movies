package com.example.popularmovies.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.popularmovies.popularmovies.R;

public class BaseActivity extends AppCompatActivity {
    protected Context mContext;
    protected ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
    }

    protected void showLoadingDialog() {
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setMessage(getString(R.string.loading));
        mProgressDialog.show();
    }

    protected void hideLoadingDialog() {
        mProgressDialog.dismiss();
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