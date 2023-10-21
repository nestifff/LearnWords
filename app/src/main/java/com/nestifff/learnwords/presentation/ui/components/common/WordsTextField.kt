package com.nestifff.learnwords.presentation.ui.components.common

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.nestifff.learnwords.presentation.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordsTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    backgroundColor: Color = AppTheme.colors.backgroundLight,
    textStyle: TextStyle = AppTheme.typography.h2RegularTextStyle,
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
    keyboardActions: KeyboardActions = KeyboardActions()
) {
    TextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        shape = RoundedCornerShape(8.dp),
        textStyle = textStyle,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = backgroundColor,
            cursorColor = AppTheme.colors.primary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
    )
}
