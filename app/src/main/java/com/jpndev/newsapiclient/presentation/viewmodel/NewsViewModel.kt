package com.jpndev.newsapiclient.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.*
import com.jpndev.newsapiclient.data.model.APIResponse
import com.jpndev.newsapiclient.data.model.Article
import com.jpndev.newsapiclient.data.util.Resource
import com.jpndev.newsapiclient.domain.usecase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception

class NewsViewModel(
    private val app:Application,
    private val getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase,
    private val getSearchedNewsUseCase: GetSearchedNewsUseCase,
    private val saveNewsUseCase: SaveNewsUseCase,
    private val getSavedNewsUseCase: GetSavedNewsUseCase,
    private val deleteSavedNewsUseCase: DeleteSavedNewsUseCase
) : AndroidViewModel(app) {
    val newsHeadLines: MutableLiveData<Resource<APIResponse>> = MutableLiveData()

    fun getNewsHeadLines(country: String, page: Int) = viewModelScope.launch(Dispatchers.IO) {
        newsHeadLines.postValue(Resource.Loading())

        try{
      if(isNetworkAvailable(app)) {

          val apiResult = getNewsHeadlinesUseCase.execute(country, page)
          newsHeadLines.postValue(apiResult)
      }else{
          newsHeadLines.postValue(Resource.Error("No Internet Connection"))
      }

        }catch (e:Exception){
            newsHeadLines.postValue(Resource.Error(e.message.toString()))
        }

    }




    val searchedNews: MutableLiveData<Resource<APIResponse>> = MutableLiveData()

    fun getSearchedNews(country: String,search_query:String, page: Int) = viewModelScope.launch(Dispatchers.IO) {
        searchedNews.postValue(Resource.Loading())
        try{
            if(isNetworkAvailable(app)) {

                val apiResult = getSearchedNewsUseCase.execute(country,search_query, page)
                searchedNews.postValue(apiResult)
            }else{
                searchedNews.postValue(Resource.Error("No Internet Connection"))
            }

        }catch (e:Exception){
            searchedNews.postValue(Resource.Error(e.message.toString()))
        }

    }


    private fun isNetworkAvailable(context: Context?):Boolean{
        if (context == null) return false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false


    }

     fun deleteArticle(article: Article) =viewModelScope.launch {
        deleteSavedNewsUseCase.execute(article)
    }


    fun saveArticle(article: Article) =viewModelScope.launch {
        saveNewsUseCase.execute(article)
    }

    fun getSavedNews():LiveData<List<Article>> = liveData<List<Article>>{
        getSavedNewsUseCase.execute().collect{
            emit(it)
        }
    }


  /*  fun getSavedNews()= liveData {
        getSavedNewsUseCase.execute().collect{
            emit(it)
        }

    }*/


}
















