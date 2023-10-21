package com.nestifff.learnwords.presentation.ui.components.screens.collection.dialog

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import com.nestifff.learnwords.presentation.screen.collection.model.AddWordDialogState
import com.nestifff.learnwords.presentation.ui.components.common.WordsTextField
import com.nestifff.learnwords.presentation.ui.theme.AppTheme
import com.nestifff.learnwords.presentation.ui.theme.ThemeProvider

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
            .background(color = AppTheme.colors.primaryLight)
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
                        color = AppTheme.colors.textLight,
                        shape = RoundedCornerShape(24.dp)
                    )
                    .clickable { onOpenClick() }
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                text = "Tap to add a new word",
                style = AppTheme.typography.h1MediumTextStyle,
                color = AppTheme.colors.textLight
            )
        } else {
            Icon(
                modifier = Modifier
                    .padding(top = 6.dp, start = 6.dp)
                    .size(32.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { onDismiss() },
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
                            .clickable { onAddWordClick() }
                            .size(32.dp)
                            .padding(2.dp),
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = null,
                        tint = AppTheme.colors.icons
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
            style = AppTheme.typography.h2RegularTextStyle,
            color = AppTheme.colors.text,
        )
        Spacer(modifier = Modifier.width(12.dp))
        WordsTextField(
            modifier = Modifier.fillMaxWidth(0.75f),
            value = value,
            onValueChange = onValueChange,
            backgroundColor = AppTheme.colors.backgroundLight.copy(alpha = 0.8f),
            textStyle = AppTheme.typography.h1RegularTextStyle,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
        )
    }
}

@Preview
@Composable
private fun AddWordComponentPreview() {
    ThemeProvider {
        AddWordDialog(
            state = AddWordDialogState.Hidden,
            onValuesChange = { s: String, s1: String -> },
            onAddWordClick = {},
            onDismiss = {},
            onOpenClick = {},
        )
    }
}
