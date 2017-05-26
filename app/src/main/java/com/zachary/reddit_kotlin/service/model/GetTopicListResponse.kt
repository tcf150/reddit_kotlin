package com.zachary.reddit_kotlin.service.model

import com.zachary.reddit_kotlin.base.BaseResponse
import com.zachary.reddit_kotlin.model.Topic

/**
 * Created by user on 10/5/2017.
 */

class GetTopicListResponse : BaseResponse() {
    var topicList: List<Topic> = ArrayList<Topic>()
}
