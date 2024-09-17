package com.nestifff.words.domain.learn.usecase

import com.nestifff.words.domain.learn.model.WordUserAnswerDomain
import javax.inject.Inject

class CheckIsWordCorrectUseCase @Inject constructor(
    private val getCorrectAnswerUseCase: GetCorrectAnswerUseCase,
) {

    // answer is considered as correct if entered value is equal to correct one with one allowed typo
    fun getIsCorrect(userAnswer: WordUserAnswerDomain): Boolean {
        val correctValue = getCorrectAnswerUseCase.execute()
        return getTyposNumber(entered = userAnswer.enteredValue, correct = correctValue) <= 1
    }

    fun getIsContainsTypos(userAnswer: WordUserAnswerDomain): Boolean {
        val correctValue = getCorrectAnswerUseCase.execute()
        return getTyposNumber(entered = userAnswer.enteredValue, correct = correctValue) > 0
    }

    private fun getTyposNumber(entered: String, correct: String): Int {
        val str1 = entered.trim().lowercase()
        val str2 = correct.trim().lowercase()

        // If the length difference is more than 1, return the maximum possible typos
        val lengthDifference = kotlin.math.abs(str1.length - str2.length)
        if (lengthDifference > 1) return lengthDifference

        var typos = 0
        var i = 0
        var j = 0

        while (i < str1.length && j < str2.length) {
            if (str1[i] != str2[j]) {
                typos++
                if (typos > 1) return typos

                // Adjust the index based on length difference
                if (str1.length > str2.length) {
                    i++ // Skip a character in str1
                } else if (str1.length < str2.length) {
                    j++ // Skip a character in str2
                } else {
                    i++
                    j++ // Skip characters in both strings (substitution case)
                }
            } else {
                i++
                j++
            }
        }

        // Count any extra characters in either string as additional typos
        typos += (str1.length - i) + (str2.length - j)
        return typos
    }
}
