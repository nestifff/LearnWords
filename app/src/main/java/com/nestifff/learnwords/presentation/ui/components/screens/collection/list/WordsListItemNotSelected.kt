package com.nestifff.learnwords.presentation.ui.components.screens.collection.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nestifff.learnwords.presentation.screen.collection.model.CollectionWordItem
import com.nestifff.learnwords.presentation.ui.theme.AppTheme
import com.nestifff.learnwords.presentation.ui.theme.ThemeProvider

@Composable
fun NotSelectedItemContent(
    modifier: Modifier = Modifier,
    word: CollectionWordItem,
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = word.value,
            style = AppTheme.typography.h1MediumTextStyle,
            color = AppTheme.colors.content,
        )
        Text(
            modifier = Modifier.padding(top = 4.dp),
            text = word.translation,
            style = AppTheme.typography.h2RegularTextStyle,
            color = AppTheme.colors.contentLight,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NotSelectedItemContent_Preview() {
    ThemeProvider {
        NotSelectedItemContent(
            word = CollectionWordItem(
                id = "123",
                translation = "rus value",
                value = "eng value",
                isFavorite = false
            ),
            modifier = Modifier.width(300.dp)
        )
    }
}
