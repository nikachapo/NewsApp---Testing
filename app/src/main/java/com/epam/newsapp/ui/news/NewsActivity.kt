package com.epam.newsapp.ui.news

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.epam.newsapp.BaseActivity
import com.epam.newsapp.NewsApplication
import com.epam.newsapp.R
import com.epam.newsapp.data.LoginRepository
import com.epam.newsapp.ui.login.LoginActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class NewsActivity : BaseActivity(R.layout.activity_news) {

    private var isFullscreen: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root = findViewById<FrameLayout>(R.id.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_log_out_24)

        isFullscreen = true
        root.setOnClickListener { toggle() }
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
            logOut()
        }
        return super.onOptionsItemSelected(item)
    }

}