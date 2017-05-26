package com.zachary.reddit_kotlin.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.zachary.reddit_kotlin.model.Topic

import java.util.ArrayList

/**
 * Created by user on 10/5/2017.
 */

class DataManager {
    private val mTopicList = ArrayList<Topic>()

    fun getmTopicList(): List<Topic> {
        return mTopicList
    }

    fun clearTopicLis() {
        mTopicList.clear()
    }

    fun addTopic(topic: Topic) {
        mTopicList.add(topic)
    }

    fun addAllTopic(topicList: List<Topic>) {
        mTopicList.addAll(topicList)
    }

    fun updateTopicCount(topicId: Int, count: Int) {
        for (topic in mTopicList) {
            if (topic.equalId(topicId)) {
                topic.count = count
                break
            }
        }
    }

    companion object {
        val instance = DataManager()

        fun getmDataManager(): DataManager {
            return instance
        }
    }
}
