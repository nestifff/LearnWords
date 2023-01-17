package com.nestifff.learnwords.presentation.ui.components.screens.collection

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.nestifff.learnwords.ext.rippleClickable
import com.nestifff.learnwords.presentation.ui.theme.ThemeCommon
import com.nestifff.learnwords.presentation.ui.theme.WordsTheme

@Composable
fun CollectionTopBar(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .padding(top = 4.dp)
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 16.dp),
    ) {

        Icon(
            modifier = modifier
                .align(Alignment.CenterEnd)
                .clip(RoundedCornerShape(8.dp))
                .rippleClickable {}
                .padding(2.dp),
            imageVector = Icons.Default.Settings,
            contentDescription = null,
            tint = WordsTheme.colors.iconsColor
        )
    }
}

@Composable
@Preview
private fun CollectionTopBarPreview() {
    ThemeCommon {
        CollectionTopBar()
    }
}
