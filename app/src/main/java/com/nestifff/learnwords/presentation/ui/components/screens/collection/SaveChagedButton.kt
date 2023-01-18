package com.nestifff.learnwords.presentation.ui.components.screens.collection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.nestifff.learnwords.ext.rippleClickable
import com.nestifff.learnwords.presentation.ui.theme.WordsTheme

@Composable
fun SaveChangedButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    isEnabled: Boolean = true,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .then(
                if (isEnabled) {
                    Modifier
                        .background(color = WordsTheme.colors.primaryLightColor)
                        .rippleClickable(onClick)
                } else {
                    Modifier.background(color = WordsTheme.colors.mediumBackgroundColor)
                }
            )
            .padding(vertical = 6.dp, horizontal = 12.dp)

    ) {
        Text(
            text = "Save",
            style = WordsTheme.typography.h3MediumTextStyle,
            color = WordsTheme.colors.textColor,
        )
    }
}