package com.androiddevs.mvvmnewsapp

import com.androiddevs.mvvmnewsapp.models.Article

interface OnItemClickListener {
    fun  onItemClick(article : Article)
}