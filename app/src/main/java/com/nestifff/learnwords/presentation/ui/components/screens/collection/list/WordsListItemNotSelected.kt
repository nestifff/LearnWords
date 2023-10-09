package com.nestifff.learnwords.presentation.ui.components.screens.collection.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nestifff.learnwords.presentation.screen.collection.model.CollectionScreenWord
import com.nestifff.learnwords.presentation.ui.theme.AppTheme

@Composable
internal fun NotSelectedItemContent(
    modifier: Modifier = Modifier,
    word: CollectionScreenWord,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            modifier = Modifier.padding(end = 6.dp),
            text = word.eng,
            style = AppTheme.typography.h1RegularTextStyle,
            color = AppTheme.colors.text,
        )
        Icon(
            modifier = Modifier.offset(y = 3.dp),
            imageVector = Icons.Default.ArrowForward,
            contentDescription = null,
            tint = AppTheme.colors.text
        )
        Text(
            modifier = Modifier.padding(start = 6.dp),
            text = word.rus,
            style = AppTheme.typography.h1RegularTextStyle,
            color = AppTheme.colors.text,
        )
    }
}
