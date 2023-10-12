package com.nestifff.words.domain.model.learn

sealed class NextWordResultDomain {

    data object WordsEnded: NextWordResultDomain()

    data class Word(val valueToShow: String) : NextWordResultDomain()
}
