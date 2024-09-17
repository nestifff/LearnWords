package com.nestifff.learnwords.presentation.ui.components.screens.collection

import android.util.Log
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
import com.nestifff.learnwords.presentation.model.fromCollectionIndex
import com.nestifff.learnwords.presentation.screen.collection.model.CollectionItem
import com.nestifff.learnwords.presentation.ui.theme.AppTheme
import kotlinx.collections.immutable.ImmutableList

@Composable
fun CollectionsSwitcher(
    collections: ImmutableList<CollectionItem>,
    selectedType: CollectionType,
    onCollectionTypeClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.5.dp,
                color = AppTheme.colors.backgroundMedium,
                shape = RoundedCornerShape(percent = 50)
            )
            .clip(RoundedCornerShape(percent = 50))
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
                    isActive = CollectionType.fromCollectionIndex(index) == selectedType,
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
            color = AppTheme.colors.content,
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
