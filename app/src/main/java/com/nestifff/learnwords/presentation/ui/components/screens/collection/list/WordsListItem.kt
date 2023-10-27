package com.nestifff.learnwords.presentation.ui.components.screens.collection.list

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissState
import androidx.compose.material.DismissValue.Default
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.nestifff.learnwords.ext.thenIfCondition
import com.nestifff.learnwords.presentation.screen.collection.model.CollectionScreenWord
import com.nestifff.learnwords.presentation.screen.collection.model.ExpandedWordState
import com.nestifff.learnwords.presentation.ui.theme.AppTheme

@Composable
fun WordsListItem(
    word: CollectionScreenWord,
    onClick: () -> Unit,
    onEditWordValuesChange: (rus: String, eng: String) -> Unit,
    onEditWordSaveClick: () -> Unit,
    onMakeFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier,
    expandedWordState: ExpandedWordState? = null,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(color = AppTheme.colors.backgroundLight)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (expandedWordState == null) {
                NotSelectedItemContent(word = word)
            } else {
                ExpandedWordItem(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    state = expandedWordState,
                    onEditWordValuesChange = onEditWordValuesChange,
                    onSaveButtonClick = onEditWordSaveClick,
                )
            }
            Icon(
                modifier = Modifier
                    .thenIfCondition(
                        condition = expandedWordState != null,
                        modifier = Modifier.padding(top = 6.dp)
                    )
                    .size(28.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { onMakeFavoriteClick() }
                ,
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = if (word.isFavorite) {
                    AppTheme.colors.primary
                } else {
                    AppTheme.colors.backgroundMedium
                }
            )
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WordListItemDeleteBackground(dismissState: DismissState) {
    val color by animateColorAsState(
        targetValue = when (dismissState.targetValue) {
            Default -> Color.White
            else -> Color.Red
        },
        animationSpec = tween(durationMillis = 1000),
        label = ""
    )
    val alignment = Alignment.CenterEnd
    val icon = Icons.Default.Delete

    val scale by animateFloatAsState(
        targetValue = if (dismissState.targetValue == Default) 0.75f else 1f,
        label = ""
    )

    Box(
        Modifier
            .fillMaxSize()
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(8.dp))
            .size(32.dp)
            .background(color)
            .padding(end = 12.dp),
        contentAlignment = alignment
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.scale(scale)
        )
    }
}

