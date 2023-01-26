package com.nestifff.learnwords.app.di.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras

//class CustomFactory<T>(
//    private val viewModelInstanceCreator: () -> T
//): ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
//        return  viewModelInstanceCreator() as T
//    }
//}