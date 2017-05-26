package com.zachary.reddit_kotlin.ui.Main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.zachary.reddit_kotlin.base.BaseApiClient
import com.zachary.reddit_kotlin.data.DataManager
import com.zachary.reddit_kotlin.model.Topic
import com.zachary.reddit_kotlin.service.model.DownVoteResponse
import com.zachary.reddit_kotlin.service.model.GetTopicListResponse
import com.zachary.reddit_kotlin.service.model.UpVoteResponse

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by tongcheefei on 19/05/2017.
 */

class MainViewModel : ViewModel() {
    private val topicList = MutableLiveData<List<Topic>>()

    private var listener: MainViewModelListener? = null

    fun setListener(listener: MainViewModelListener) {
        this.listener = listener
    }

    fun getTopicList(): LiveData<List<Topic>> {
        return topicList
    }

    fun updateVote(topicId: Int) {
        //todo MainRepository
        if (topicId < 0) return
        Log.d(TAG, "Begin up vote")
        //fire up vote service
        val call = BaseApiClient.topicService.upVote(topicId)
        call.enqueue(object : Callback<UpVoteResponse> {
            override fun onResponse(call: Call<UpVoteResponse>, response: Response<UpVoteResponse>) {
                val upVoteResponse = response.body()
                Log.d(TAG, "complete up vote")
                if (upVoteResponse != null) {
                    if (upVoteResponse!!.status.isSuccess) {
                        Log.d(TAG, "up vote success")
                        //update DataManager
                        DataManager.instance.updateTopicCount(topicId, upVoteResponse!!.count)
                        topicList.setValue(DataManager.instance.getmTopicList())
                    } else {
                        Log.d(TAG, "up vote fail")
                        if (listener != null) listener!!.showErrorToast(upVoteResponse!!.status.statusDesc)
                    }
                }
            }

            override fun onFailure(call: Call<UpVoteResponse>, t: Throwable) {
                t.printStackTrace()
                if (listener != null) listener!!.showErrorToast(t.message.toString())
            }
        })
    }

    fun downVote(topicId: Int) {
        //todo MainRepository
        if (topicId < 0) return
        Log.d(TAG, "begin down vote")
        //fire down vote service
        val call = BaseApiClient.topicService.downVote(topicId)
        call.enqueue(object : Callback<DownVoteResponse> {
            override fun onResponse(call: Call<DownVoteResponse>, response: Response<DownVoteResponse>) {
                val downVoteResponse = response.body()
                Log.d(TAG, "down vote complete")
                if (downVoteResponse != null) {
                    if (downVoteResponse!!.status.isSuccess) {
                        Log.d(TAG, "down vote success")
                        //update DataManager
                        DataManager.instance.updateTopicCount(topicId, downVoteResponse!!.count)
                        topicList.setValue(DataManager.instance.getmTopicList())
                    } else {
                        Log.d(TAG, "down vote fail")
                        if (listener != null) listener!!.showErrorToast(downVoteResponse!!.status.statusDesc)
                    }
                }
            }

            override fun onFailure(call: Call<DownVoteResponse>, t: Throwable) {
                t.printStackTrace()
                if (listener != null) listener!!.showErrorToast(t.message.toString())
            }
        })
    }

    fun refreshTopicList() {
        //clear list if force refresh data
        DataManager.instance.clearTopicLis()
        topicList.setValue(DataManager.instance.getmTopicList())

        Log.d(TAG, "begin get topic list")
        //fire getTopicList
        val call = BaseApiClient.topicService.getTopicList(20, 0)
        call.enqueue(object : Callback<GetTopicListResponse> {
            override fun onResponse(call: Call<GetTopicListResponse>, response: Response<GetTopicListResponse>) {
                val getTopicListResponse = response.body()
                Log.d(TAG, "get topic list complete")
                if (getTopicListResponse != null) {
                    if (getTopicListResponse!!.status.isSuccess) {
                        //update data manager and notify ui
                        Log.d(TAG, "get topic list success")
                        DataManager.instance.addAllTopic(getTopicListResponse!!.topicList)
                        topicList.setValue(DataManager.instance.getmTopicList())
                    } else {
                        Log.d(TAG, "get topic list fail")
                        if (listener != null) listener!!.showErrorToast(getTopicListResponse!!.status.statusDesc)
                    }
                }
            }

            override fun onFailure(call: Call<GetTopicListResponse>, t: Throwable) {
                t.printStackTrace()
                if (listener != null) listener!!.showErrorToast(t.message.toString())
            }
        })
    }

    interface MainViewModelListener {
        fun showErrorToast(message: String)
    }

    companion object {
        private val TAG = "MainViewModel"
    }
}
