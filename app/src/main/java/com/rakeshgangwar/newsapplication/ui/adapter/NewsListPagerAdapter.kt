package com.rakeshgangwar.newsapplication.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rakeshgangwar.newsapplication.R
import com.rakeshgangwar.newsapplication.models.Article
import com.rakeshgangwar.newsapplication.ui.CustomTabsHelper

class NewsListPagerAdapter(private val customTabsHelper: CustomTabsHelper?) : RecyclerView.Adapter<NewsListViewHolder>() {

    private val newsList: ArrayList<Article> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
                R.layout.news_pager_layout,
                parent, false
        )
        return NewsListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return newsList.count()
    }

    override fun onBindViewHolder(holder: NewsListViewHolder, position: Int) {
        val article = newsList[position]
        holder.setView(article, customTabsHelper)
    }

    fun addNewsItems(items: List<Article>) {
        newsList.clear()
        newsList.addAll(items)
        notifyDataSetChanged()
    }
}