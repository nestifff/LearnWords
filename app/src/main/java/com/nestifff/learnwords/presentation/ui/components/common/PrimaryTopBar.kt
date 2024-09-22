package com.nestifff.learnwords.presentation.ui.components.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nestifff.learnwords.presentation.ui.theme.AppTheme
import com.nestifff.learnwords.presentation.ui.theme.ThemeProvider

@Composable
fun PrimaryTopBar(
    title: String,
    onNavigationButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    navigationButtonVector: ImageVector = Icons.AutoMirrored.Filled.ArrowBack
) {
    Row(
        modifier = modifier.padding(bottom = 32.dp, top = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(12.dp))
                .clickable { onNavigationButtonClick() }
                .padding(4.dp),
            imageVector = navigationButtonVector,
            contentDescription = null,
            tint = AppTheme.colors.content
        )
        Text(
            text = title,
            style = AppTheme.typography.h0MediumTextStyle,
            color = AppTheme.colors.content,
            modifier = Modifier.padding(start = 10.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PrimaryTopBar_Preview() {
    ThemeProvider {
        PrimaryTopBar("Settings", {})
    }
}