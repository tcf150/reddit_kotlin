package com.zachary.reddit_kotlin.ui.AddTopic

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.zachary.reddit_kotlin.base.BaseApiClient
import com.zachary.reddit_kotlin.service.model.CreateTopicResponse

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by tongcheefei on 19/05/2017.
 */

class AddTopicViewModel : ViewModel() {
    private val mText = MutableLiveData<String>()

    private var listener: AddTopicViewModelListener? = null

    fun setListener(listener: AddTopicViewModelListener) {
        this.listener = listener
    }

    val text: LiveData<String>
        get() = mText

    fun onTextChanged(text: String) {
        mText.setValue(text)
    }

    fun addTopic() {
        if (mText.toString().trim().length == 0) {
            if (listener != null) listener!!.showEmptyTextToast()
            return
        }

        Log.d(TAG, "Begin up vote")
        //fire up vote service
        val call = BaseApiClient.topicService.createTopic(mText.toString())
        call.enqueue(object : Callback<CreateTopicResponse> {
            override fun onResponse(call: Call<CreateTopicResponse>, response: Response<CreateTopicResponse>) {
                val createTopicResponse = response.body()
                Log.d(TAG, "complete up vote")
                if (createTopicResponse != null) {
                    if (createTopicResponse!!.status.isSuccess) {
                        Log.d(TAG, "up vote success")
                        if (listener != null) listener!!.addTopicSuccessful(createTopicResponse!!.status.statusDesc)
                    } else {
                        Log.d(TAG, "up vote fail")
                        if (listener != null) listener!!.showErrorToast(createTopicResponse!!.status.statusDesc)
                    }
                }
            }

            override fun onFailure(call: Call<CreateTopicResponse>, t: Throwable) {
                t.printStackTrace()
                if (listener != null) listener!!.showErrorToast(t.message!!)
            }
        })
    }

    interface AddTopicViewModelListener {
        fun showErrorToast(message: String)
        fun showEmptyTextToast()
        fun addTopicSuccessful(message: String)
    }

    companion object {
        private val TAG = "AddTopicViewModel"
    }
}
