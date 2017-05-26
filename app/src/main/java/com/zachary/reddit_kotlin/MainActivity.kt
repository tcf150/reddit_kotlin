package com.zachary.reddit_kotlin

import android.os.Bundle
import com.zachary.reddit_mvvm.base.BaseAppCompatActivity

class MainActivity : BaseAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun setupContentView() {
        setContentView(R.layout.activity_main)
    }

    override fun setupUI() {

    }

    override fun setupViewModel() {

    }
}
