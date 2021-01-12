package com.epam.newsapp.ui.news

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.epam.newsapp.R
import com.epam.newsapp.data.news.model.NewsModel
import java.text.SimpleDateFormat
import java.util.*

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private val news = mutableListOf<NewsModel>()

    fun submitItem(newsModel: NewsModel) {
        news.add(newsModel)
        notifyItemInserted(news.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bindNews(news[position])
    }

    override fun getItemCount() = news.size

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SimpleDateFormat")
        fun bindNews(newsModel: NewsModel) {
            itemView.findViewById<TextView>(R.id.titleTV).text = newsModel.title
            itemView.findViewById<TextView>(R.id.descriptionTV).text = newsModel.title
            itemView.findViewById<TextView>(R.id.dateTV).text = SimpleDateFormat("MM-dd").format(
                Date(newsModel.date)
            )
        }
    }
}