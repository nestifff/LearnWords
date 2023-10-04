package com.nestifff.learnwords.presentation.ui.components.screens.collection

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nestifff.learnwords.ext.noRippleClickable
import com.nestifff.learnwords.ext.rippleClickable
import com.nestifff.learnwords.presentation.screen.collection.model.AddWordDialogState
import com.nestifff.learnwords.presentation.ui.components.common.WordsTextField
import com.nestifff.learnwords.presentation.ui.theme.ThemeCommon
import com.nestifff.learnwords.presentation.ui.theme.WordsTheme

@Composable
fun AddWordDialog(
    state: AddWordDialogState,
    modifier: Modifier = Modifier,
    onValuesChange: (rus: String, eng: String) -> Unit,
    onAddWordClick: () -> Unit,
    onDismiss: () -> Unit,
    onOpenClick: () -> Unit,
) {
    val isExpanded = state is AddWordDialogState.Expanded
    val cornerRadiusDp by animateDpAsState(
        targetValue = if (isExpanded) 24.dp else 0.dp,
        animationSpec = tween(300),
        label = ""
    )
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    topEnd = cornerRadiusDp,
                    topStart = cornerRadiusDp
                )
            )
            .noRippleClickable { }
            .background(color = WordsTheme.colors.primaryLight)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = 0.4f,
                    stiffness = 1000f
                )
            )
            .padding(bottom = 6.dp)
    ) {
        if (!isExpanded) {
            Text(
                modifier = Modifier
                    .padding(top = 6.dp, start = 24.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .border(
                        width = 2.dp,
                        color = WordsTheme.colors.textLight,
                        shape = RoundedCornerShape(24.dp)
                    )
                    .rippleClickable { onOpenClick() }
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                text = "Tap to add a new word",
                style = WordsTheme.typography.h2MediumTextStyle,
                color = WordsTheme.colors.textLight
            )
        } else {
            Icon(
                modifier = Modifier
                    .padding(top = 6.dp, start = 6.dp)
                    .size(32.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .rippleClickable { onDismiss() },
                imageVector = Icons.Default.Close,
                contentDescription = null,
            )
        }

        if (state is AddWordDialogState.Expanded) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {

                val focusManager = LocalFocusManager.current

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp, horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        OneValueEnterRow(
                            text = "Rus",
                            value = state.rus,
                            onValueChange = { onValuesChange(it, state.eng) },
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                            keyboardActions = KeyboardActions(
                                onNext = {
                                    focusManager.moveFocus(FocusDirection.Down)
                                }
                            )
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        OneValueEnterRow(
                            text = "Eng",
                            value = state.eng,
                            onValueChange = { onValuesChange(state.rus, it) },
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(
                                onDone = { onAddWordClick() }
                            )
                        )
                    }
                    Icon(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .clip(RoundedCornerShape(12.dp))
                            .rippleClickable { onAddWordClick() }
                            .size(32.dp)
                            .padding(2.dp),
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = null,
                        tint = WordsTheme.colors.icons
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
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
) {
    Row(
        modifier = modifier.height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = WordsTheme.typography.h3RegularTextStyle,
            color = WordsTheme.colors.text,
        )
        Spacer(modifier = Modifier.width(12.dp))
        WordsTextField(
            modifier = Modifier.fillMaxWidth(0.75f),
            value = value,
            onValueChange = onValueChange,
            backgroundColor = WordsTheme.colors.backgroundLight.copy(alpha = 0.8f),
            textStyle = WordsTheme.typography.h2RegularTextStyle,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
        )
    }
}

@Preview
@Composable
private fun AddWordComponentPreview() {
    ThemeCommon {
        AddWordDialog(
            state = AddWordDialogState.Hidden,
            onValuesChange = { s: String, s1: String -> },
            onAddWordClick = {},
            onDismiss = {},
            onOpenClick = {},
        )
    }
}
