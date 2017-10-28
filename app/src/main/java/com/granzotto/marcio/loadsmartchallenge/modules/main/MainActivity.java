package com.granzotto.marcio.loadsmartchallenge.modules.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import butterknife.BindView
import com.granzotto.marcio.loadsmartchallenge.R

class MainActivity: AppCompatActivity() {

    @BindView(R.id.textView)
    TextView textView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}