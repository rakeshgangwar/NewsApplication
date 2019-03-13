package com.rakeshgangwar.newsapplication.network

import androidx.lifecycle.LiveData
import com.rakeshgangwar.newsapplication.models.NewsData
import com.rakeshgangwar.newsapplication.network.common.ApiResponse
import retrofit2.http.GET

interface NewsService {
    @GET("top-headlines?country=us")
    fun getNews(): LiveData<ApiResponse<NewsData>>
}