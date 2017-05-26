package com.zachary.reddit_kotlin.base

import com.zachary.reddit_kotlin.service.RedditService

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by user on 10/5/2017.
 */

object BaseApiClient {
    private var service: RedditService? = null

    val topicService: RedditService
        get() {
            if (service == null) {
                val retrofit = Retrofit.Builder()
                        .baseUrl("http://zacharytongreddit.000webhostapp.com")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()

                service = retrofit.create<RedditService>(RedditService::class.java!!)
            }
            return service!!
        }
}
