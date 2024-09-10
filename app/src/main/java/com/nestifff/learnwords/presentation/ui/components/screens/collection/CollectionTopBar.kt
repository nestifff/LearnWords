package com.nestifff.learnwords.presentation.ui.components.screens.collection

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import com.nestifff.learnwords.BuildConfig
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nestifff.learnwords.presentation.ui.theme.AppTheme
import com.nestifff.learnwords.presentation.ui.theme.ThemeProvider

@Composable
fun CollectionTopBar(
    modifier: Modifier = Modifier,
    onSettingsButtonClick: () -> Unit,
    onMenuButtonClick: () -> Unit,
    onDebugOptionAddWordsClicked: () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .padding(start = 16.dp)
                .clip(RoundedCornerShape(8.dp))
                .clickable { onMenuButtonClick() }
                .padding(4.dp)
                .size(28.dp),
            imageVector = Icons.Default.Menu,
            contentDescription = null,
            tint = AppTheme.colors.content
        )

        if (BuildConfig.DEBUG) {
            Icon(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { onDebugOptionAddWordsClicked() }
                    .padding(4.dp)
                    .size(28.dp),
                imageVector = Icons.Default.Add,
                contentDescription = null,
                tint = AppTheme.colors.content
            )
        }

        Icon(
            modifier = Modifier
                .padding(end = 16.dp)
                .clip(RoundedCornerShape(8.dp))
                .clickable { onSettingsButtonClick() }
                .padding(4.dp)
                .size(28.dp),
            imageVector = Icons.Default.Settings,
            contentDescription = null,
            tint = AppTheme.colors.content
        )
    }
}

@Composable
@Preview
private fun CollectionTopBarPreview() {
    ThemeProvider {
        CollectionTopBar(
            onSettingsButtonClick = {},
            onMenuButtonClick = {},
            onDebugOptionAddWordsClicked = {})
    }
}
