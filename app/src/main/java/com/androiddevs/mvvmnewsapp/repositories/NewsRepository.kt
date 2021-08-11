package com.androiddevs.mvvmnewsapp.repositories

import com.androiddevs.mvvmnewsapp.api.RetrofiInstance
import com.androiddevs.mvvmnewsapp.db.ArticleDatabase

class NewsRepository(val db : ArticleDatabase) {

    suspend fun getBreakingNews(countryCode : String , pageNumber : Int) =
        RetrofiInstance.api.getBreakingNews(countryCode,pageNumber)
}