package com.nestifff.learnwords.presentation.ui.components.screens.collection

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nestifff.learnwords.ext.rippleClickable
import com.nestifff.learnwords.presentation.screen.collection.model.WordCollectionScreen
import com.nestifff.learnwords.presentation.ui.components.common.WordsTextField
import com.nestifff.learnwords.presentation.ui.theme.ThemeCommon
import com.nestifff.learnwords.presentation.ui.theme.WordsTheme

@Composable
fun WordsList(
    modifier: Modifier = Modifier,
    words: List<WordCollectionScreen>,
    selectedIndex: Int? = null,
    onNewSelected: (Int) -> Unit,
    saveClicked: (newWord: WordCollectionScreen) -> Unit,
) {

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(top = 64.dp, bottom = 64.dp)
    ) {
        itemsIndexed(words) { ind, word ->
            WordsListItem(
                modifier = Modifier.padding(vertical = 4.dp),
                word = word,
                isSelected = selectedIndex == ind,
                itemOnClick = { onNewSelected(ind) },
                saveClicked = saveClicked
            )
        }
    }
}

@Composable
fun WordsListItem(
    modifier: Modifier = Modifier,
    word: WordCollectionScreen,
    isSelected: Boolean,
    itemOnClick: () -> Unit,
    saveClicked: (newWord: WordCollectionScreen) -> Unit,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .border(
                width = 2.dp,
                color = WordsTheme.colors.backgroundMediumColor,
                shape = RoundedCornerShape(8.dp)
            )
            .background(color = WordsTheme.colors.backgroundLightColor)
            .rippleClickable(itemOnClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (!isSelected) {
                NotSelectedItemContent(word = word)
            } else {
                SelectedItemContent(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    word = word,
                    saveButtonClicked = {
                        saveClicked(WordCollectionScreen(id = "", eng = "", rus = "", isFavorite = false))
                        itemOnClick()
                    })
            }
            Icon(
                modifier = Modifier
                    .then(if (isSelected) Modifier.padding(top = 6.dp) else Modifier)
                    .size(28.dp),
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = if (word.isFavorite) {
                    WordsTheme.colors.primaryColor
                } else {
                    WordsTheme.colors.backgroundMediumColor
                }
            )
        }
    }
}

@Composable
private fun SelectedItemContent(
    modifier: Modifier = Modifier,
    word: WordCollectionScreen,
    saveButtonClicked: (newWord: WordCollectionScreen) -> Unit,
) {
    var textEng by rememberSaveable { mutableStateOf(word.eng) }
    var textRus by rememberSaveable { mutableStateOf(word.rus) }

    var isSaveButtonEnabled by remember {
        mutableStateOf(false)
    }
    val isTextChanged: () -> Boolean = { textEng != word.eng || textRus != word.rus }

    Column(
        modifier = modifier
            .padding(vertical = 12.dp)
    ) {
        WordsTextField(
            value = textEng,
            onValueChange = {
                textEng = it
                isSaveButtonEnabled = isTextChanged()
            },
            backgroundColor = WordsTheme.colors.backgroundMediumColor.copy(alpha = 0.6f)
        )
        WordsTextField(
            modifier = Modifier.padding(top = 6.dp),
            value = textRus,
            onValueChange = {
                textRus = it
                isSaveButtonEnabled = isTextChanged()
            },
            backgroundColor = WordsTheme.colors.backgroundMediumColor.copy(alpha = 0.6f)
        )
        SaveChangedButton(
            modifier = Modifier.padding(top = 16.dp),
            onClick = {
                saveButtonClicked(word.copy(rus = textRus, eng = textEng))
            },
            isEnabled = isSaveButtonEnabled
        )
    }
}

@Composable
private fun NotSelectedItemContent(
    modifier: Modifier = Modifier,
    word: WordCollectionScreen,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            modifier = Modifier.padding(end = 6.dp),
            text = word.eng,
            style = WordsTheme.typography.h2RegularTextStyle,
            color = WordsTheme.colors.textColor,
        )
        Icon(
            modifier = Modifier.offset(y = 3.dp),
            imageVector = Icons.Default.ArrowForward,
            contentDescription = null,
            tint = WordsTheme.colors.textColor
        )
        Text(
            modifier = Modifier.padding(start = 6.dp),
            text = word.rus,
            style = WordsTheme.typography.h2RegularTextStyle,
            color = WordsTheme.colors.textColor,
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun SelectedItemContentPreview() {
    ThemeCommon {
        SelectedItemContent(
            word = WordCollectionScreen(
                id = "",
                eng = "English",
                rus = "Russian",
                isFavorite = false
            ),
            saveButtonClicked = {}
        )
    }
}
