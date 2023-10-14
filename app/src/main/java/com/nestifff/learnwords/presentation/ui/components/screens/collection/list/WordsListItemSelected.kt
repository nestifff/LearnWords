package com.nestifff.learnwords.presentation.ui.components.screens.collection.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nestifff.learnwords.presentation.screen.collection.model.CollectionScreenWord
import com.nestifff.learnwords.presentation.screen.collection.model.ExpandedWordState
import com.nestifff.learnwords.presentation.screen.collection.model.toExpandedState
import com.nestifff.learnwords.presentation.ui.components.common.WordsTextField
import com.nestifff.learnwords.presentation.ui.components.screens.collection.SaveChangedButton
import com.nestifff.learnwords.presentation.ui.theme.ThemeProvider
import com.nestifff.learnwords.presentation.ui.theme.AppTheme

@Composable
internal fun ExpandedWordItem(
    modifier: Modifier = Modifier,
    state: ExpandedWordState,
    onEditWordValuesChange: (rus: String, eng: String) -> Unit,
    onSaveButtonClick: () -> Unit,
) {

    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier.padding(vertical = 12.dp)
    ) {
        WordsTextField(
            value = state.word.eng,
            onValueChange = {
                onEditWordValuesChange(state.word.rus, it)
            },
            backgroundColor = AppTheme.colors.backgroundMedium.copy(alpha = 0.6f),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            )
        )
        WordsTextField(
            modifier = Modifier.padding(top = 6.dp),
            value = state.word.rus,
            onValueChange = {
                onEditWordValuesChange(it, state.word.eng)
            },
            backgroundColor = AppTheme.colors.backgroundMedium.copy(alpha = 0.6f),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            )
        )
        Box(
            modifier = Modifier.padding(top = 16.dp)
        ) {
            SaveChangedButton(
                enabled = state.isSaveEnabled,
                onClick = onSaveButtonClick,
            )
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(24.dp),
                    color = AppTheme.colors.primary,
                    strokeWidth = 3.dp
                )
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
private fun SelectedItemContentPreview() {
    ThemeProvider {
        ExpandedWordItem(
            state = CollectionScreenWord(
                id = "",
                eng = "English",
                rus = "Russian",
                isFavorite = false
            ).toExpandedState(),
            onSaveButtonClick = { },
            onEditWordValuesChange = { s: String, s1: String -> },
        )
    }
}
