package com.zachary.reddit_kotlin.ui.Main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.zachary.reddit_kotlin.R
import com.zachary.reddit_kotlin.base.BaseAppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.Toolbar
import com.zachary.reddit_kotlin.ui.AddTopic.AddTopicActivity
import android.content.Intent
import android.support.design.widget.FloatingActionButton
import android.view.View
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.zachary.reddit_kotlin.model.Topic




class MainActivity : BaseAppCompatActivity(), MainViewModel.MainViewModelListener {
    private var viewModel : MainViewModel? = null

    private var swipeRefreshLayout: SwipeRefreshLayout? = null
    private var rvTopic: RecyclerView? = null
    private var adapter : TopicAdapter? = null
//    private val adapter: TopicAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun setupContentView() {
        setContentView(R.layout.activity_main)
    }

    override fun setupUI() {
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        swipeRefreshLayout = getView(R.id.swipeRefreshLayout)
        rvTopic = getView(R.id.rvTopic)


        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@MainActivity, AddTopicActivity::class.java)
            startActivity(intent)
        })

        swipeRefreshLayout!!.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener { viewModel!!.refreshTopicList() })

        //setup layout manager for recyclerview
        val linearLayoutManager = LinearLayoutManager(this)
        rvTopic!!.setLayoutManager(linearLayoutManager)

        //add divider for recyclerview
        val dividerItemDecoration = DividerItemDecoration(this, linearLayoutManager.orientation)
        rvTopic!!.addItemDecoration(dividerItemDecoration)
    }

    override fun setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel!!.setListener(this)
        viewModel!!.getTopicList().observe(this, Observer<List<Topic>> { topics ->
            adapter!!.setTopicList(topics!!)
            swipeRefreshLayout!!.setRefreshing(false)
            adapter!!.notifyDataSetChanged()
        })

        //set adapter for recycler view and onClickListener
        adapter = TopicAdapter(viewModel!!.getTopicList().value)
        adapter!!.setOnClickListener(object : TopicAdapter.OnClickListener {
            override fun onUpClick(topicId: Int) {
                viewModel!!.updateVote(topicId)
            }

            override fun onDownClick(topicId: Int) {
                viewModel!!.downVote(topicId)
            }
        })
        rvTopic!!.setAdapter(adapter)

    }

    override fun showErrorToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
