package com.rakeshgangwar.newsapplication.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rakeshgangwar.newsapplication.models.Article

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNews(news: List<Article>)

    @Query("SELECT * FROM Article")
    fun getNews(): LiveData<List<Article>>
}