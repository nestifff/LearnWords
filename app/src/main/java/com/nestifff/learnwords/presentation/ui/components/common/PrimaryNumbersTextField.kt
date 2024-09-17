package com.nestifff.learnwords.presentation.ui.components.common

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.nestifff.learnwords.presentation.ui.theme.ThemeProvider

@Composable
fun PrimaryNumbersTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    PrimaryTextField(
        value = value,
        onValueChange = {
            if (it.isEmpty() || it.isDigitsOnly()) {
                onValueChange(it)
            }
        },
        modifier = modifier
            .width(64.dp)
            .heightIn(min = 48.dp),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Number
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun PrimaryNumbersTextField_Preview() {
    ThemeProvider {
        PrimaryNumbersTextField(
            value = "test text",
            onValueChange = {},
            modifier = Modifier.padding(16.dp),
        )
    }
}