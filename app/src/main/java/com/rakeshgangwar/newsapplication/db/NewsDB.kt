package com.rakeshgangwar.newsapplication.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rakeshgangwar.newsapplication.models.Article

@Database(
    entities = [Article::class],
    version = 1,
    exportSchema = false
)
abstract class NewsDB: RoomDatabase() {
    abstract fun newsDao(): NewsDao
}