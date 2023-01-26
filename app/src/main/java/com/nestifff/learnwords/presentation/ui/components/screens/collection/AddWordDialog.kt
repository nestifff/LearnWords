package com.nestifff.learnwords.presentation.ui.components.screens.collection

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nestifff.learnwords.ext.emptyString
import com.nestifff.learnwords.ext.generateUUID
import com.nestifff.learnwords.ext.noRippleClickable
import com.nestifff.learnwords.ext.rippleClickable
import com.nestifff.learnwords.presentation.screen.collection.model.Word
import com.nestifff.learnwords.presentation.ui.components.common.WordsTextField
import com.nestifff.learnwords.presentation.ui.theme.ThemeCommon
import com.nestifff.learnwords.presentation.ui.theme.WordsTheme

@Composable
fun AddWordDialog(
    modifier: Modifier = Modifier,
    onEnterWord: (newWord: Word) -> Unit,
) {
    var isVisible by rememberSaveable { mutableStateOf(false) }
    if (isVisible) {
        Box(
            Modifier
                .fillMaxSize()
                .background(WordsTheme.colors.black.copy(alpha = 0.1f))
                .noRippleClickable { isVisible = false }
        )
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (isVisible) {
                    Modifier.clip(RoundedCornerShape(topEnd = 24.dp, topStart = 24.dp))
                } else {
                    Modifier
                }
            )
            .noRippleClickable { }
            .background(color = WordsTheme.colors.primaryLightColor)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = 0.4f,
                    stiffness = 1000f
                )
            )
            .padding(bottom = 6.dp)
    ) {
        if (!isVisible) {
            Text(
                modifier = Modifier
                    .padding(top = 6.dp, start = 24.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .border(
                        width = 2.dp,
                        color = WordsTheme.colors.textLightColor,
                        shape = RoundedCornerShape(24.dp)
                    )
                    .rippleClickable { isVisible = true }
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                text = "Tap to add a new word",
                style = WordsTheme.typography.h2MediumTextStyle,
                color = WordsTheme.colors.textLightColor
            )
        } else {
            Icon(
                modifier = Modifier
                    .padding(top = 6.dp, start = 6.dp)
                    .size(32.dp)
                    .rippleClickable { isVisible = false },
                imageVector = Icons.Default.Close,
                contentDescription = null,
            )
        }

        if (isVisible) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                var textRus by rememberSaveable { mutableStateOf(emptyString()) }
                var textEng by rememberSaveable { mutableStateOf(emptyString()) }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp, horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        OneValueEnterRow(
                            text = "Rus",
                            value = textRus,
                            onValueChange = { textRus = it }
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        OneValueEnterRow(
                            text = "Eng",
                            value = textEng,
                            onValueChange = { textEng = it }
                        )
                    }
                    Icon(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .clip(RoundedCornerShape(8.dp))
                            .rippleClickable {
                                onEnterWord(
                                    Word(
                                        id = generateUUID(),
                                        rus = textRus,
                                        eng = textEng,
                                        isFavorite = false
                                    )
                                )
                            }
                            .size(32.dp)
                            .padding(2.dp),
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = null,
                        tint = WordsTheme.colors.iconsColor
                    )
                }
            }
        }
    }
}

@Composable
private fun OneValueEnterRow(
    modifier: Modifier = Modifier,
    text: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Row(
        modifier = modifier.height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = WordsTheme.typography.h3RegularTextStyle,
            color = WordsTheme.colors.textColor,
        )
        Spacer(modifier = Modifier.width(12.dp))
        WordsTextField(
            modifier = Modifier.fillMaxWidth(0.75f),
            value = value,
            onValueChange = onValueChange,
            backgroundColor = WordsTheme.colors.backgroundLightColor.copy(alpha = 0.8f),
            textStyle = WordsTheme.typography.h2RegularTextStyle,
        )
    }
}

@Preview
@Composable
fun AddWordComponentPreview() {
    ThemeCommon {
        AddWordDialog(onEnterWord = {})
    }
}