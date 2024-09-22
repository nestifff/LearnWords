package com.nestifff.learnwords.presentation.ui.components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.nestifff.learnwords.presentation.ui.theme.AppTheme

@Composable
fun DataSectionTitle(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = AppTheme.typography.h2BoldTextStyle.copy(
            AppTheme.colors.contentLight
        ),
        modifier = modifier.padding(bottom = 24.dp)
    )
}

@Composable
fun Modifier.dataSectionShape(): Modifier {
    return this.then(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(AppTheme.colors.backgroundLight)
            .border(1.dp, AppTheme.colors.sectionDivider, RoundedCornerShape(12.dp))
            .padding(16.dp)
    )
}
