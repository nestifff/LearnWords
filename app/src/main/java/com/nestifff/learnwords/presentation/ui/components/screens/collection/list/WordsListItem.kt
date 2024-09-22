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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nestifff.learnwords.ext.thenIfCondition
import com.nestifff.learnwords.presentation.screen.collection.model.CollectionWordItem
import com.nestifff.learnwords.presentation.screen.collection.model.ExpandedWordState
import com.nestifff.learnwords.presentation.ui.theme.AppTheme
import com.nestifff.learnwords.presentation.ui.theme.ThemeProvider

@Composable
fun WordsListItem(
    word: CollectionWordItem,
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
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            if (expandedWordState == null) {
                NotSelectedItemContent(
                    word = word,
                    modifier = Modifier.weight(1f)
                )
            } else {
                ExpandedWordItem(
                    modifier = Modifier.weight(1f),
                    state = expandedWordState,
                    onEditWordValuesChange = onEditWordValuesChange,
                    onSaveButtonClick = onEditWordSaveClick,
                )
            }
            Icon(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .size(30.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { onMakeFavoriteClick() }
                    .padding(2.dp),
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = if (word.isFavorite) {
                    AppTheme.colors.primary
                } else {
                    AppTheme.colors.contentLight.copy(alpha = 0.6f)
                }
            )
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WordListItemDeleteBackground(dismissState: DismissState) {
    val color by animateColorAsState(
        targetValue = AppTheme.colors.warning,
        animationSpec = tween(durationMillis = 500),
        label = ""
    )
    val alignment = Alignment.CenterEnd
    val icon = Icons.Default.Delete

    val scale by animateFloatAsState(
        targetValue = if (dismissState.targetValue == Default) 0.6f else 1f,
        label = ""
    )

    Box(
        Modifier
            .fillMaxSize()
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(8.dp))
            .size(36.dp)
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

@Preview(showBackground = true)
@Composable
private fun WordsListItem_Preview() {
    ThemeProvider {
        WordsListItem(
            word = CollectionWordItem(
                id = "11",
                rus = "lfjgkldfj mdfgnkjfdgjk  dfgjdfkj  d,fjgjkfdjg",
                eng = "gfdlkjg mfdkjgh kdfkghfd dkghfkd dkkdgkfdhg fdjgh",
                isFavorite = false
            ),
            onClick = {},
            onEditWordValuesChange = { s: String, s1: String -> },
            onEditWordSaveClick = {},
            onMakeFavoriteClick = {},
            expandedWordState = null
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun WordsListItem_Expanded_Preview() {
    ThemeProvider {
        val word = remember {
            CollectionWordItem(
                id = "11",
                rus = "lfjgkldfj mdfgnkjfdgjk  dfgjdfkj  d,fjgjkfdjg",
                eng = "gfdlkjg mfdkjgh kdfkghfd dkghfkd dkkdgkfdhg fdjgh",
                isFavorite = false
            )
        }
        WordsListItem(
            word = word,
            onClick = {},
            onEditWordValuesChange = { s: String, s1: String -> },
            onEditWordSaveClick = {},
            onMakeFavoriteClick = {},
            expandedWordState = ExpandedWordState(
                word = word,
                oldWord = word
            )
        )
    }
}

