package com.example.mvvmexample.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmexample.db.SubscriberRepository

class SubscriberViewModelFactory(private val repo:SubscriberRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SubscribersViewModel::class.java))
            return SubscribersViewModel(repo) as T

        throw IllegalArgumentException("Unknown View Model")
    }


}