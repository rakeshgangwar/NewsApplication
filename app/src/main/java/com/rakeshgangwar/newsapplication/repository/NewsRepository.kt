package com.rakeshgangwar.newsapplication.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.rakeshgangwar.newsapplication.AppExecutors
import com.rakeshgangwar.newsapplication.db.NewsDao
import com.rakeshgangwar.newsapplication.models.Article
import com.rakeshgangwar.newsapplication.models.NewsData
import com.rakeshgangwar.newsapplication.network.NewsService
import com.rakeshgangwar.newsapplication.network.common.ApiResponse
import com.rakeshgangwar.newsapplication.network.common.NetworkBoundResource
import com.rakeshgangwar.newsapplication.network.common.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(
    private val newsService: NewsService,
    private val newsDao: NewsDao,
    private val appExecutors: AppExecutors

) {
    fun getNews(): LiveData<Resource<List<Article>>> {
        return object : NetworkBoundResource<List<Article>, NewsData>(appExecutors){
            override fun saveCallResult(item: NewsData) {
                Log.d("","")
                newsDao.insertNews(item.articles)
            }

            override fun shouldFetch(data: List<Article>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun loadFromDb(): LiveData<List<Article>> {
                return newsDao.getNews()
            }

            override fun createCall(): LiveData<ApiResponse<NewsData>> {
                return newsService.getNews()
            }

        }.asLiveData()
    }
}