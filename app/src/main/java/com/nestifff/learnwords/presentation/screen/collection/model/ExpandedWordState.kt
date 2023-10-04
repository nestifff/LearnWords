package com.nestifff.learnwords.presentation.screen.collection.model

data class ExpandedWordState(
    val word: CollectionScreenWord,
    val oldWord: CollectionScreenWord,
    val isLoading: Boolean = false,
    val isSaveEnabled: Boolean = false,
)

fun CollectionScreenWord.toExpandedState(): ExpandedWordState {
    return ExpandedWordState(word = this.copy(), oldWord = this)
}

fun ExpandedWordState.change(rus: String, eng: String): ExpandedWordState {
    val isChanged = rus != this.oldWord.rus || eng != this.oldWord.eng
    return this.copy(word = this.word.copy(rus = rus, eng = eng), isSaveEnabled = isChanged)
}
