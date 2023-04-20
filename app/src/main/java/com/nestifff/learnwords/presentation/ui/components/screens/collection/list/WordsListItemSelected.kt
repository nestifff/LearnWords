package com.nestifff.learnwords.presentation.ui.components.screens.collection.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nestifff.learnwords.presentation.screen.collection.model.CollectionScreenWord
import com.nestifff.learnwords.presentation.ui.components.common.WordsTextField
import com.nestifff.learnwords.presentation.ui.components.screens.collection.SaveChangedButton
import com.nestifff.learnwords.presentation.ui.theme.ThemeCommon
import com.nestifff.learnwords.presentation.ui.theme.WordsTheme

@Composable
internal fun SelectedItemContent(
    modifier: Modifier = Modifier,
    word: CollectionScreenWord,
    onSaveButtonClick: (updatedWord: CollectionScreenWord) -> Unit,
    isSavingLoadingVisible: Boolean,
) {
    var textEng by rememberSaveable { mutableStateOf(word.eng) }
    var textRus by rememberSaveable { mutableStateOf(word.rus) }

    var isSaveButtonEnabled by remember {
        mutableStateOf(false)
    }
    val isTextChanged: () -> Boolean = { textEng != word.eng || textRus != word.rus }

    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier.padding(vertical = 12.dp)
    ) {
        WordsTextField(
            value = textEng,
            onValueChange = {
                textEng = it
                isSaveButtonEnabled = isTextChanged()
            },
            backgroundColor = WordsTheme.colors.backgroundMedium.copy(alpha = 0.6f),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            )
        )
        WordsTextField(
            modifier = Modifier.padding(top = 6.dp),
            value = textRus,
            onValueChange = {
                textRus = it
                isSaveButtonEnabled = isTextChanged()
            },
            backgroundColor = WordsTheme.colors.backgroundMedium.copy(alpha = 0.6f),
            keyboardActions = KeyboardActions(onDone = {
                if (isTextChanged()) {
                    isSaveButtonEnabled = true
                    onSaveButtonClick(word.copy(rus = textRus, eng = textEng))
                }
            })
        )
        Box(
            modifier = Modifier.padding(top = 16.dp)
        ) {
            SaveChangedButton(
                onClick = { onSaveButtonClick(word.copy(rus = textRus, eng = textEng)) },
                isEnabled = isSaveButtonEnabled
            )
            if (isSavingLoadingVisible) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(24.dp),
                    color = WordsTheme.colors.primary,
                    strokeWidth = 3.dp
                )
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
private fun SelectedItemContentPreview() {
    ThemeCommon {
        SelectedItemContent(
            word = CollectionScreenWord(
                id = "",
                eng = "English",
                rus = "Russian",
                isFavorite = false
            ),
            onSaveButtonClick = { },
            isSavingLoadingVisible = true,
        )
    }
}
