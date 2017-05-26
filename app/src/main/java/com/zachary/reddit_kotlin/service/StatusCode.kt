package com.zachary.reddit_kotlin.service

/**
 * Created by user on 10/5/2017.
 */

object StatusCode {
    val SUCCESS = 0
    val FAIL_TO_CREATE = 100
    val FAIL_TO_UPDATE = 101
    val TOPIC_CANNOT_NULL = 200
    val TOPIC_ID_CANNOT_NULL = 201
    val EXCEED_MAX_TOPIC = 202
    val RECORD_NOT_FOUND = 998
    val API_ERROR = 999
}
