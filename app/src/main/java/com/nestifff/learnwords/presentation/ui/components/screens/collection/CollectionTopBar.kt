package com.nestifff.learnwords.presentation.ui.components.screens.collection

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nestifff.learnwords.presentation.ui.theme.ThemeProvider
import com.nestifff.learnwords.presentation.ui.theme.AppTheme

@Composable
fun CollectionTopBar(
    modifier: Modifier = Modifier,
    onSettingsButtonClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth(),
    ) {

        Icon(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .clip(RoundedCornerShape(8.dp))
                .clickable { onSettingsButtonClick() }
                .padding(2.dp)
                .size(24.dp)
            ,
            imageVector = Icons.Default.Settings,
            contentDescription = null,
            tint = AppTheme.colors.icons
        )
    }
}

@Composable
@Preview
private fun CollectionTopBarPreview() {
    ThemeProvider {
        CollectionTopBar(onSettingsButtonClick = {})
    }
}
