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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.nestifff.learnwords.presentation.model.CollectionType
import com.nestifff.learnwords.presentation.screen.collection.model.CollectionItem
import com.nestifff.learnwords.presentation.ui.theme.AppTheme

@Composable
fun CollectionsSwitcher(
    collections: List<CollectionItem>,
    selectedTypeIndex: Int,
    onCollectionTypeClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        AppTheme.colors.background,
                        Color.Transparent
                    )
                )
            )
            .padding(top = 4.dp)
            .border(
                width = 2.dp,
                color = AppTheme.colors.backgroundMedium,
                shape = RoundedCornerShape(32.dp)
            )
            .clip(RoundedCornerShape(32.dp))
            .background(color = AppTheme.colors.background)
            .padding(horizontal = 8.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            collections.forEachIndexed { index, collection ->
                SwitcherItem(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    collection = collection.type.text(),
                    isActive = index == selectedTypeIndex,
                    onClick = { onCollectionTypeClick(index) }
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
                    AppTheme.colors.primary
                } else {
                    AppTheme.colors.primaryLight
                }
            )
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(
            text = collection,
            style = AppTheme.typography.h2MediumTextStyle,
            color = AppTheme.colors.text,
        )
    }
}

private fun CollectionType.text(): String {
    return when (this) {
        CollectionType.InProcess -> "In progress"
        CollectionType.Learned -> "Learned"
        CollectionType.Favorite -> "Favorite"
    }
}
