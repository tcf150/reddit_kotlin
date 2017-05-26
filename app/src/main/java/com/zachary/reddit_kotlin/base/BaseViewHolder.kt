package com.zachary.reddit_kotlin.base

import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by user on 10/5/2017.
 */

open class BaseViewHolder<T : BaseModel>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    protected var model: T? = null

    open fun bindData(model: T) {
        this.model = model
    }
}
