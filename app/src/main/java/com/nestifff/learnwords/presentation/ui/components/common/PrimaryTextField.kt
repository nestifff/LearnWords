package com.nestifff.learnwords.presentation.ui.components.common

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nestifff.learnwords.presentation.ui.theme.AppTheme
import com.nestifff.learnwords.presentation.ui.theme.ThemeProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrimaryTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    backgroundColor: Color = AppTheme.colors.backgroundLight,
    textStyle: TextStyle = AppTheme.typography.h2RegularTextStyle,
    isIndicatorVisible: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
    keyboardActions: KeyboardActions = KeyboardActions()
) {
    TextField(
        modifier = modifier,
        value = value,
        enabled = isEnabled,
        onValueChange = onValueChange,
        shape = if (isIndicatorVisible) {
            RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
        } else {
            RoundedCornerShape(8.dp)
        },
        textStyle = textStyle,
        colors = getTextFieldColors(backgroundColor, isIndicatorVisible = isIndicatorVisible),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun getTextFieldColors(
    backgroundColor: Color = AppTheme.colors.backgroundLight,
    cursorColor: Color = AppTheme.colors.primary,
    selectionColors: TextSelectionColors = TextSelectionColors(
        handleColor = AppTheme.colors.primary,
        backgroundColor = AppTheme.colors.primaryLight
    ),
    isIndicatorVisible: Boolean = true
): TextFieldColors {
    val indicatorColor = if(isIndicatorVisible) {
        AppTheme.colors.contentLight.copy(alpha = 0.8f)
    } else {
        Color.Transparent
    }
    return TextFieldDefaults.textFieldColors(
        containerColor = backgroundColor,
        cursorColor = cursorColor,
        focusedIndicatorColor = indicatorColor,
        unfocusedIndicatorColor = indicatorColor,
        disabledIndicatorColor = indicatorColor,
        selectionColors = selectionColors,
    )
}

@Preview(showBackground = true)
@Composable
private fun PrimaryTextField_Preview() {
    ThemeProvider {
        PrimaryTextField(
            value = "test text",
            onValueChange = {},
            modifier = Modifier.padding(16.dp),
            isIndicatorVisible = false
        )
    }
}