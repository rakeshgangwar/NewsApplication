package com.rakeshgangwar.newsapplication.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel;
import com.rakeshgangwar.newsapplication.models.Article
import com.rakeshgangwar.newsapplication.network.common.Resource
import com.rakeshgangwar.newsapplication.repository.NewsRepository
import javax.inject.Inject

class NewsListViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    fun getNews(): LiveData<Resource<List<Article>>> {
        return newsRepository.getNews()
    }

}
