package com.nestifff.words.data.local.database.mapper

fun Int.flagsGetIsFavorite(): Boolean = (this and (1 shl 0)) != 0

fun Int.flagsGetIsLearned(): Boolean = (this and (1 shl 1)) != 0

// 2^1 = isEnabled, 2^0 = isAdWatched
// 2^0 = isFavorite, 2^1 = isLearned
fun createFlags(isFavorite: Boolean, isLearned: Boolean): Int {
    return 0.setBitToValue(0, isFavorite).setBitToValue(1, isLearned)
}

private fun Int.setBitToValue(bitNumber: Int, value: Boolean): Int {
    return if (value) {
        this or (1 shl bitNumber)
    } else {
        this and (1 shl bitNumber).inv()
    }
}
