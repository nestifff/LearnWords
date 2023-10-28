package com.nestifff.words.domain.learn.model

sealed class NextWordResultDomain {

    data object WordsEnded: NextWordResultDomain()

    data class Word(val valueToShow: String) : NextWordResultDomain()
}
