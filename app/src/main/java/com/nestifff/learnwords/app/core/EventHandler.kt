package com.nestifff.learnwords.app.core

interface EventHandler<T> {
    fun onEvent(event: T)
}
