package com.zachary.reddit_kotlin.ui.Main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.zachary.reddit_kotlin.R
import com.zachary.reddit_kotlin.base.BaseViewHolder
import com.zachary.reddit_kotlin.common.Util
import com.zachary.reddit_kotlin.model.Topic

/**
 * Created by user on 10/5/2017.
 */

class TopicAdapter(private var topicList: List<Topic>?) : RecyclerView.Adapter<TopicAdapter.ViewHolder>() {
    private var onClickListener: OnClickListener? = null

    fun setTopicList(topicList: List<Topic>) {
        this.topicList = topicList
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_topic, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(topicList!![position])
    }

    override fun getItemCount(): Int {
        if (topicList == null) return 0
        return topicList!!.size
    }


    inner class ViewHolder(itemView: View) : BaseViewHolder<Topic>(itemView), View.OnClickListener {
        internal var tvDate: TextView
        internal var tvTitle: TextView
        internal var tvCount: TextView
        internal var ivUp: ImageView
        internal var ivDown: ImageView

        init {
            tvDate = itemView.findViewById(R.id.tvDate) as TextView
            tvTitle = itemView.findViewById(R.id.tvTitle) as TextView
            tvCount = itemView.findViewById(R.id.tvCount) as TextView
            ivUp = itemView.findViewById(R.id.ivUp) as ImageView
            ivDown = itemView.findViewById(R.id.ivDown) as ImageView

            ivUp.setOnClickListener(this)
            ivDown.setOnClickListener(this)
        }

        override fun bindData(model: Topic) {
            super.bindData(model)
            tvTitle.setText(model.title)
            tvCount.setText(Util.addThousandseparator(model.count))
        }

        override fun onClick(v: View) {
            if (onClickListener != null) {
                when (v.id) {
                    R.id.ivUp -> onClickListener!!.onUpClick(model!!.id)
                    R.id.ivDown -> onClickListener!!.onDownClick(model!!.id)
                }
            }
        }
    }

    interface OnClickListener {
        fun onUpClick(topicId: Int)
        fun onDownClick(topicId: Int)
    }
}
