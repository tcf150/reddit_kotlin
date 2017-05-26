package com.zachary.reddit_kotlin.ui.AddTopic

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.zachary.reddit_kotlin.R
import com.zachary.reddit_kotlin.base.BaseAppCompatActivity

class AddTopicActivity : BaseAppCompatActivity(), AddTopicViewModel.AddTopicViewModelListener {
    private var viewModel: AddTopicViewModel? = null

    private var etTopic: EditText? = null
    private var tvWordCount: TextView? = null

    override fun setupContentView() {
        setContentView(R.layout.activity_add_topic)
    }

    protected override fun setupUI() {
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        etTopic = getView(R.id.etTopic)
        tvWordCount = getView(R.id.tvWordCount)

        etTopic!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                viewModel!!.onTextChanged(s.toString())
            }
        })
    }

     override fun setupViewModel() {
         viewModel = ViewModelProviders.of(this).get(AddTopicViewModel::class.java)
        viewModel!!.setListener(this)
        viewModel!!.text.observe(this, Observer<String> { s -> tvWordCount!!.text = String.format(getString(R.string.word_count_format), s!!.length) })
        etTopic!!.setText(viewModel!!.text.value)
    }

    override fun showEmptyTextToast() {
        Toast.makeText(this, R.string.error_topic_empty, Toast.LENGTH_SHORT).show()
    }

    override fun addTopicSuccessful(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun showErrorToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = getMenuInflater()
        inflater.inflate(R.menu.add_topic_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_add -> {
                viewModel!!.addTopic()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
