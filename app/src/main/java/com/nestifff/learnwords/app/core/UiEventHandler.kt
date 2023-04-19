package com.nestifff.learnwords.app.core

interface UiEventHandler<T : UiEvent> {
    fun onEvent(event: T)
}
