package com.nestifff.learnwords.presentation.screen.collection.model

data class ExpandedWordState(
    val word: CollectionScreenWord,
    val oldWord: CollectionScreenWord,
    val isChanged: Boolean,
)

fun CollectionScreenWord.toExpandedState(): ExpandedWordState {
    return ExpandedWordState(word = this.copy(), oldWord = this, isChanged = false)
}

fun ExpandedWordState.change(rus: String, eng: String): ExpandedWordState {
    val isChanged = rus != this.oldWord.rus || eng != this.oldWord.eng
    return this.copy(word = this.word.copy(rus = rus, eng = eng), isChanged = isChanged)
}
