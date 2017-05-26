package com.zachary.reddit_mvvm.base

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleActivity
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.LifecycleRegistryOwner
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View

/**
 * Created by user on 10/5/2017.
 */

abstract class BaseAppCompatActivity : AppCompatActivity(), LifecycleRegistryOwner {
    private val mRegistry = LifecycleRegistry(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupContentView()
        setupUI()
        setupViewModel()
    }

    /**
     * setup the content layout
     */
    protected abstract fun setupContentView()

    /**
     * setup ui logic
     */
    protected abstract fun setupUI()

    /**
     * setup view model
     */
    protected abstract fun setupViewModel()

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    /**
     * get view by using id
     * @param resId view id
     * *
     * @param <T> view class
     * *
     * @return T type View
    </T> */
    protected fun <T : View> getView(@IdRes resId: Int): T {
        return findViewById(resId) as T
    }

    /**
     * get view from view by using id
     * @param view rootView
     * *
     * @param resId view id
     * *
     * @param <T> view class
     * *
     * @return T type View
    </T> */
    protected fun <T : View> getViewByView(view: View, @IdRes resId: Int): T {
        return view.findViewById(resId) as T
    }

    override fun getLifecycle(): LifecycleRegistry {
        return this.mRegistry
    }
}
