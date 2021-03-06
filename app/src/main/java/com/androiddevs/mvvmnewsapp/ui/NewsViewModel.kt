package com.androiddevs.mvvmnewsapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddevs.mvvmnewsapp.models.Article
import com.androiddevs.mvvmnewsapp.models.NewsResponse
import com.androiddevs.mvvmnewsapp.repositories.NewsRepository
import com.androiddevs.mvvmnewsapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(val newsRepository : NewsRepository) : ViewModel() {

    val breakingNews : MutableLiveData<Resource<NewsResponse>> = MutableLiveData()

    var breakingNewsPage = 1

    var breakkingNewsResponse : NewsResponse? = null

    val searchedNews : MutableLiveData<Resource<NewsResponse>> = MutableLiveData()

    var searchedNewsPage = 1

    var searchingNewsResponse : NewsResponse? = null


    init {
        getBreakingNews("us")
    }

    fun getBreakingNews(countryCode:String) = viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        val response = newsRepository.getBreakingNews(countryCode,breakingNewsPage)
        breakingNews.postValue(handleBreakingNewsResponse(response))

    }

    fun searchNews(searchQuery :String) = viewModelScope.launch {
        searchedNews.postValue(Resource.Loading())
        val response = newsRepository.searchNews(searchQuery,breakingNewsPage)
        searchedNews.postValue(handleSearchNewsResponse(response))

    }


    private fun handleBreakingNewsResponse (response: Response<NewsResponse>) : Resource<NewsResponse>{

        if(response.isSuccessful){
            response.body()?.let {
                breakingNewsPage++
                if(breakkingNewsResponse == null){
                    breakkingNewsResponse = it
                }else{
                    val oldArticles = breakkingNewsResponse?.articles
                    val newArticles = it.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(breakkingNewsResponse?:it)
            }
        }

        return Resource.Error(response.message(),null)
    }

    private fun handleSearchNewsResponse (response: Response<NewsResponse>) : Resource<NewsResponse>{

        if(response.isSuccessful){
            response.body()?.let {
                searchedNewsPage++
                if(searchingNewsResponse != null){
                    searchingNewsResponse = it
                }else{
                    val oldArticles = searchingNewsResponse?.articles
                    val newArticles = it.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(breakkingNewsResponse?:it)
            }
        }

        return Resource.Error(response.message(),null)
    }

    fun saveArticle(article: Article) = viewModelScope.launch {
        newsRepository.upsert(article)
    }

    fun getSavedNews() = newsRepository.getSavedNews()


    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }



}