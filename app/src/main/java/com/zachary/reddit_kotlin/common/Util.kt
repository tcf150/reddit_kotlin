package com.zachary.reddit_kotlin.common

/**
 * Created by user on 10/5/2017.
 */

object Util {

    fun addThousandseparator(value: Int): String {
        return String.format("%,d", value)
    }
}
