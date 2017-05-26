package com.zachary.reddit_kotlin.service

import com.zachary.reddit_kotlin.service.model.CreateTopicResponse
import com.zachary.reddit_kotlin.service.model.DownVoteResponse
import com.zachary.reddit_kotlin.service.model.GetTopicListResponse
import com.zachary.reddit_kotlin.service.model.UpVoteResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by user on 10/5/2017.
 */

interface RedditService {
    @GET("/getTopicList.php")
    fun getTopicList(@Query("limit") limit: Int, @Query("offset") offset: Int): Call<GetTopicListResponse>

    @FormUrlEncoded
    @POST("/createTopic.php")
    fun createTopic(@Field("topicTitle") topicTitle: String): Call<CreateTopicResponse>

    @FormUrlEncoded
    @POST("/upVote.php")
    fun upVote(@Field("topicId") topicId: Int): Call<UpVoteResponse>

    @FormUrlEncoded
    @POST("/downVote.php")
    fun downVote(@Field("topicId") topicId: Int): Call<DownVoteResponse>
}
