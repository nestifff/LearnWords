package com.nestifff.learnwords.app.core

import androidx.lifecycle.LiveData

interface UpdateHandler<T> {

    val data: LiveData<T>

    fun update(value: T)
}
