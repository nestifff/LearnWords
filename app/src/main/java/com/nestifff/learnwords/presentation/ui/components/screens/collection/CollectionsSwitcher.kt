package com.nestifff.learnwords.presentation.ui.components.screens.collection

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.nestifff.learnwords.ext.rippleClickable
import com.nestifff.learnwords.presentation.ui.theme.WordsTheme

@Composable
fun CollectionsSwitcher(
    collections: List<String>
) {
    Box(
        modifier = Modifier
            .padding(top = 8.dp)
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = WordsTheme.colors.collectionSwitcherBordersColor,
                shape = RoundedCornerShape(32.dp)
            )
            .clip(RoundedCornerShape(32.dp))
            .padding(horizontal = 8.dp, vertical = 8.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            for (collection in collections) {
                SwitcherItem(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    collection = collection,
                    isActive = collection == collections.first()
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
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
//            .border(
//                width = if (isActive) 2.dp else 1.dp,
//                color = WordsTheme.colors.collectionSwitcherBordersColor,
//                shape = RoundedCornerShape(16.dp)
//            )
            .background(
                if (isActive) {
                    WordsTheme.colors.primaryColor
                } else {
                    WordsTheme.colors.primaryLightColor
                }
            )
            .rippleClickable { }
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Text(
            text = collection,
            style = WordsTheme.typography.h3MediumTextStyle,
            color = WordsTheme.colors.textColor,
        )
    }
}
