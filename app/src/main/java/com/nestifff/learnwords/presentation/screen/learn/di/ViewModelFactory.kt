package com.nestifff.learnwords.presentation.screen.learn.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nestifff.learnwords.app.navigation.destinations.LearnScreenArgument
import com.nestifff.learnwords.presentation.screen.learn.LearnViewModel
import dagger.assisted.AssistedFactory

@Suppress("UNCHECKED_CAST")
fun learnViewModelFactory(
    factory: LearnViewModelFactory,
    argument: LearnScreenArgument
) : ViewModelProvider.Factory {
    return object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            factory.create(argument) as T
    }
}

@AssistedFactory
interface LearnViewModelFactory {
    fun create(argument: LearnScreenArgument): LearnViewModel
}
