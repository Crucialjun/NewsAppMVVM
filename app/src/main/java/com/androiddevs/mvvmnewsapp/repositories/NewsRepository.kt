package com.androiddevs.mvvmnewsapp.repositories

import com.androiddevs.mvvmnewsapp.api.RetrofiInstance
import com.androiddevs.mvvmnewsapp.db.ArticleDatabase
import com.androiddevs.mvvmnewsapp.models.Article

class NewsRepository(val db : ArticleDatabase) {

    suspend fun getBreakingNews(countryCode : String , pageNumber : Int) =
        RetrofiInstance.api.getBreakingNews(countryCode,pageNumber)

    suspend fun searchNews(searchQuery : String , pageNumber : Int) =
        RetrofiInstance.api.searchForNews(searchQuery,pageNumber)

    suspend fun upsert(article : Article) = db.getArticleDao().upsert(article)

     fun getSavedNews() = db.getArticleDao().getAllArticles()

    suspend fun deleteArticle (article: Article) = db.getArticleDao().deleteArticle(article)


}