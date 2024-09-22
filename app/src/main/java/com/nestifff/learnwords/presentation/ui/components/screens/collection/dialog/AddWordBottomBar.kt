package com.nestifff.learnwords.presentation.ui.components.screens.collection.dialog

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nestifff.learnwords.ext.noRippleClickable
import com.nestifff.learnwords.presentation.screen.collection.model.AddWordDialogState
import com.nestifff.learnwords.presentation.screen.collection.model.AddWordDialogState.Expanded
import com.nestifff.learnwords.presentation.ui.components.common.PrimaryTextField
import com.nestifff.learnwords.presentation.ui.theme.AppTheme
import com.nestifff.learnwords.presentation.ui.theme.ThemeProvider
import kotlinx.coroutines.delay

@Composable
fun AddWordBottomBar(
    state: AddWordDialogState,
    modifier: Modifier = Modifier,
    onValuesChange: (rus: String, eng: String) -> Unit,
    onAddWordClick: () -> Unit,
    onDismiss: () -> Unit,
    onOpenClick: () -> Unit,
) {
    val cornerRadiusDp by animateDpAsState(
        targetValue = if (state is Expanded) 24.dp else 0.dp,
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
            .navigationBarsPadding()
            .imePadding()
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = 0.4f,
                    stiffness = 1000f
                )
            )
            .padding(bottom = 16.dp)
    ) {
        if (state !is Expanded) {
            Text(
                modifier = Modifier
                    .padding(top = 8.dp, start = 24.dp)
                    .clip(RoundedCornerShape(percent = 50))
                    .border(
                        width = 1.dp,
                        color = AppTheme.colors.content,
                        shape = RoundedCornerShape(percent = 50)
                    )
                    .clickable { onOpenClick() }
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                text = "Tap to add a new word",
                style = AppTheme.typography.h1MediumTextStyle,
                color = AppTheme.colors.content
            )
        } else {
            Icon(
                modifier = Modifier
                    .padding(top = 4.dp, start = 4.dp)
                    .size(36.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { onDismiss() }
                    .padding(4.dp),
                imageVector = Icons.Default.Close,
                contentDescription = null,
                tint = AppTheme.colors.content
            )
        }

        if (state !is Expanded) {
            return
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val focusRequester = remember { FocusRequester() }
            val keyboard = LocalSoftwareKeyboardController.current
            val focusManager = LocalFocusManager.current
            Column {
                OneValueEnterRow(
                    text = "Rus",
                    value = state.rus,
                    onValueChange = { onValuesChange(it, state.eng) },
                    modifier = Modifier.focusRequester(focusRequester),
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
                    .size(52.dp)
                    .padding(10.dp),
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null,
                tint = AppTheme.colors.content
            )
            // open keyboard when in expanded state
            LaunchedEffect(Unit) {
                delay(500)
                focusRequester.requestFocus()
                delay(100)
                keyboard?.show()
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
            color = AppTheme.colors.content,
        )
        Spacer(modifier = Modifier.width(12.dp))
        PrimaryTextField(
            modifier = Modifier.fillMaxWidth(0.75f),
            value = value,
            onValueChange = onValueChange,
            backgroundColor = AppTheme.colors.backgroundLight.copy(alpha = 0.8f),
            textStyle = AppTheme.typography.h1RegularTextStyle,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            isIndicatorVisible = false
        )
    }
}

@Preview
@Composable
private fun AddWordBottomBar_Collapsed_Preview() {
    ThemeProvider {
        AddWordBottomBar(
            state = AddWordDialogState.Collapsed,
            onValuesChange = { s: String, s1: String -> },
            onAddWordClick = {},
            onDismiss = {},
            onOpenClick = {},
        )
    }
}

@Preview
@Composable
private fun AddWordBottomBar_Expanded_Preview() {
    ThemeProvider {
        AddWordBottomBar(
            state = Expanded(),
            onValuesChange = { s: String, s1: String -> },
            onAddWordClick = {},
            onDismiss = {},
            onOpenClick = {},
        )
    }
}
