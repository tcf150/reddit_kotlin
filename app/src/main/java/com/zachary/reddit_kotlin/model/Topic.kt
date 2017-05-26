package com.zachary.reddit_kotlin.model

import com.zachary.reddit_kotlin.base.BaseModel

/**
 * Created by user on 10/5/2017.
 */

class Topic : BaseModel() {
    var id: Int = 0
    var title: String? = null
    var count: Int = 0
    var createAt: String? = null

    fun equalId(id: Int): Boolean {
        return this.id == id
    }
}
