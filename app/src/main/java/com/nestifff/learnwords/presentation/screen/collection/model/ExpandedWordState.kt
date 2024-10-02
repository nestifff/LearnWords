package com.nestifff.learnwords.presentation.screen.collection.model

data class ExpandedWordState(
    val word: CollectionWordItem,
    val oldWord: CollectionWordItem,
    val isLoading: Boolean = false,
    val isSaveEnabled: Boolean = false,
)

fun CollectionWordItem.toExpandedState(): ExpandedWordState {
    return ExpandedWordState(word = this.copy(), oldWord = this)
}

fun ExpandedWordState.change(translation: String, value: String): ExpandedWordState {
    val isChanged = translation != this.oldWord.translation || value != this.oldWord.value
    return this.copy(
        word = this.word.copy(translation = translation, value = value),
        isSaveEnabled = isChanged
    )
}
