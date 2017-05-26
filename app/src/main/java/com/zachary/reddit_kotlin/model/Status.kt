package com.zachary.reddit_kotlin.model

import com.zachary.reddit_kotlin.base.BaseModel
import com.zachary.reddit_kotlin.service.StatusCode


/**
 * Created by user on 10/5/2017.
 */

class Status : BaseModel() {
    var statusCode: Int = 0
    var statusDesc: String? = null

    val isSuccess: Boolean
        get() = statusCode == StatusCode.SUCCESS
}
