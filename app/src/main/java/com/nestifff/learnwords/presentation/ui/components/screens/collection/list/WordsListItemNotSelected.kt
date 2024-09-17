package com.nestifff.learnwords.presentation.ui.components.screens.collection.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
            text = word.eng,
            style = AppTheme.typography.h1MediumTextStyle,
            color = AppTheme.colors.content,
        )
        Text(
            modifier = Modifier.padding(top = 4.dp),
            text = word.rus,
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
                rus = "rus value",
                eng = "eng value",
                isFavorite = false
            ),
            modifier = Modifier.width(300.dp)
        )
    }
}
