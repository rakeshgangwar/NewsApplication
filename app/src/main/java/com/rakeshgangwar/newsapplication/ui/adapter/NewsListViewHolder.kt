package com.rakeshgangwar.newsapplication.ui.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.rakeshgangwar.newsapplication.R
import com.rakeshgangwar.newsapplication.models.Article
import com.rakeshgangwar.newsapplication.ui.CustomTabsHelper
import java.text.SimpleDateFormat
import java.util.*


class NewsListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val newsImage: ImageView = itemView.findViewById(R.id.news_image)
    private val newsTitle: TextView = itemView.findViewById(R.id.news_title)
    private val newsContent: TextView = itemView.findViewById(R.id.news_content)
    private val newsDate: TextView = itemView.findViewById(R.id.news_date)

    fun setView(article: Article, customTabsHelper: CustomTabsHelper?) {
        newsTitle.text = article.title
        newsContent.text = article.content
        if (!article.urlToImage.isNullOrEmpty())
            Glide.with(itemView.context)
                    .applyDefaultRequestOptions(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop())
                    .load(article.urlToImage)
                    .into(newsImage)
        itemView.setOnClickListener {
            if (customTabsHelper != null && !article.url.isNullOrEmpty()) {
                customTabsHelper.openCustomTab(article.url)
            }
        }
        newsDate.text = getDate(article.publishedAt)
    }

    private fun getDate(date: String): String {
        val format = SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        format.timeZone = TimeZone.getTimeZone("UTC")
        val toFormat = SimpleDateFormat("dd MMMM yyyy", Locale.US)
        val dateObject = format.parse(date)
        return toFormat.format(dateObject)
    }
}