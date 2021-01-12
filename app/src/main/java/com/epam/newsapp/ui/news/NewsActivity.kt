package com.epam.newsapp.ui.news

import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.epam.newsapp.NewsApplication
import com.epam.newsapp.ui.BaseActivity
import com.epam.newsapp.R
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class NewsActivity : BaseActivity(R.layout.activity_news) {

    private var isFullscreen: Boolean = false

    private lateinit var viewModel:NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val newsViewModelFactory = NewsViewModelFactory((application as NewsApplication).newsRepo)
        viewModel = ViewModelProvider(this, newsViewModelFactory).get(NewsViewModel::class.java)

        val root = findViewById<FrameLayout>(R.id.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_log_out_24)

        isFullscreen = true
        root.setOnClickListener { toggle() }

        val newsList = findViewById<RecyclerView>(R.id.newsList)
        val newsAdapter = NewsAdapter()
        newsList.adapter = newsAdapter

        viewModel.news.observe(this, {
            newsAdapter.submitItem(it)
        })
    }

    private fun toggle() {
        if (isFullscreen) {
            hide()
        } else {
            show()
        }
    }

    private fun hide() {
        supportActionBar?.hide()
        isFullscreen = false
    }

    private fun show() {
        supportActionBar?.show()
        isFullscreen = true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            lifecycleScope.launch { logOut() }
        }
        return super.onOptionsItemSelected(item)
    }

}