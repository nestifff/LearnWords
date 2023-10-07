package com.nestifff.learnwords.presentation.ui.components.screens.collection

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.nestifff.learnwords.presentation.model.CollectionType
import com.nestifff.learnwords.presentation.ui.theme.WordsTheme

@Composable
fun CollectionsSwitcher(
    types: List<CollectionType>,
    selectedType: CollectionType,
    onCollectionTypeClick: (CollectionType) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        WordsTheme.colors.background,
                        Color.Transparent
                    )
                )
            )
            .padding(top = 4.dp)
            .border(
                width = 2.dp,
                color = WordsTheme.colors.backgroundMedium,
                shape = RoundedCornerShape(32.dp)
            )
            .clip(RoundedCornerShape(32.dp))
            .background(color = WordsTheme.colors.background)
            .padding(horizontal = 8.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            for (type in types) {
                SwitcherItem(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    collection = type.text(),
                    isActive = type == selectedType,
                    onClick = { onCollectionTypeClick(type) }
                )
            }
        }
    }
}

@Composable
private fun SwitcherItem(
    modifier: Modifier = Modifier,
    collection: String,
    isActive: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(
                if (isActive) {
                    WordsTheme.colors.primary
                } else {
                    WordsTheme.colors.primaryLight
                }
            )
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(
            text = collection,
            style = WordsTheme.typography.h2MediumTextStyle,
            color = WordsTheme.colors.text,
        )
    }
}

private fun CollectionType.text(): String {
    return when (this) {
        CollectionType.InProgress -> "In progress"
        CollectionType.Learned -> "Learned"
        CollectionType.Favorite -> "Favorite"
    }
}
