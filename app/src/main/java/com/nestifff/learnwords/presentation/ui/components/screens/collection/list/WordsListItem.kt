package com.nestifff.learnwords.presentation.ui.components.screens.collection.list

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.nestifff.learnwords.ext.rippleClickable
import com.nestifff.learnwords.presentation.screen.collection.model.WordCollectionScreen
import com.nestifff.learnwords.presentation.ui.theme.WordsTheme
import kotlinx.coroutines.delay

@Composable
fun WordsListItem(
    modifier: Modifier = Modifier,
    word: WordCollectionScreen,
    isSelected: Boolean,
    itemOnClick: () -> Unit,
    saveClicked: (updatedWord: WordCollectionScreen) -> Unit,
) {
    var isSavingLoadingVisible by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .border(
                width = 2.dp,
                color = WordsTheme.colors.backgroundMediumColor,
                shape = RoundedCornerShape(8.dp)
            )
            .background(color = WordsTheme.colors.backgroundLightColor)
            .rippleClickable(itemOnClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (!isSelected) {
                NotSelectedItemContent(word = word)
            } else {
                SelectedItemContent(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    word = word,
                    saveButtonClicked = {
                        saveClicked(it)
                        isSavingLoadingVisible = true
                    },
                    isSavingLoadingVisible = isSavingLoadingVisible,
                )
            }
            Icon(
                modifier = Modifier
                    .then(if (isSelected) Modifier.padding(top = 6.dp) else Modifier)
                    .size(28.dp),
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = if (word.isFavorite) {
                    WordsTheme.colors.primaryColor
                } else {
                    WordsTheme.colors.backgroundMediumColor
                }
            )
        }
    }

    LaunchedEffect(isSavingLoadingVisible) {
        if (isSavingLoadingVisible) {
            delay(400)
            isSavingLoadingVisible = false
            itemOnClick()
        }
    }
}
