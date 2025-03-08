package com.android.newsapp.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.newsapp.data.NewsRepository
import com.android.newsapp.di.Injection

class ViewModelFactory private constructor(private val newsRepository: NewsRepository): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            return NewsViewModel(newsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class:" + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstence(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}

