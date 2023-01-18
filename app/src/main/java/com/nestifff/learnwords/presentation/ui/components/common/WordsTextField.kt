package com.nestifff.learnwords.presentation.ui.components.common

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.nestifff.learnwords.presentation.ui.theme.WordsTheme

@Composable
fun WordsTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    backgroundColor: Color = WordsTheme.colors.lightBackgroundColor
) {
    TextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        shape = RoundedCornerShape(8.dp),
        textStyle = WordsTheme.typography.h3RegularTextStyle,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = backgroundColor,
            cursorColor = WordsTheme.colors.primaryColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        )
    )
}