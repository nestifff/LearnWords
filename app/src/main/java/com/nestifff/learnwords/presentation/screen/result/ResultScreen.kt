package com.nestifff.learnwords.presentation.screen.result

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nestifff.learnwords.ext.onEffect
import com.nestifff.learnwords.presentation.screen.result.ResultViewModel.Effect.ReturnToCollectionScreen
import com.nestifff.learnwords.presentation.screen.result.ResultViewModel.State
import com.nestifff.learnwords.presentation.ui.components.common.DataSectionTitle
import com.nestifff.learnwords.presentation.ui.components.common.PrimaryButton
import com.nestifff.learnwords.presentation.ui.components.common.PrimaryTopBar
import com.nestifff.learnwords.presentation.ui.components.common.dataSectionShape
import com.nestifff.learnwords.presentation.ui.theme.AppTheme
import com.nestifff.learnwords.presentation.ui.theme.ThemeProvider
import com.nestifff.words.domain.learn.model.LearnProcessResult
import com.nestifff.words.domain.word.model.WordDomain

@Composable
fun ResultScreen(
    viewModel: ResultViewModel,
    navigateToCollectionScreen: () -> Unit,
) {
    val state by viewModel.uiState.collectAsState()

    onEffect(effect = viewModel.uiEffect) {
        when (it) {
            ReturnToCollectionScreen -> navigateToCollectionScreen()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.background)
            .systemBarsPadding()
            .padding(horizontal = 16.dp)
    ) {
        PrimaryTopBar(
            title = "Results",
            onNavigationButtonClick = { viewModel.onBackTriggered() },
            navigationButtonVector = Icons.Default.Close
        )

        when (val currState = state) {
            is State.Loading -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = AppTheme.colors.primary
                    )
                }
            }

            is State.Display -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 16.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    ResultDataComponent(
                        data = currState.data,
                        modifier = Modifier.weight(1f)
                    )
                    PrimaryButton(
                        text = "Return to main ->",
                        onClick = { viewModel.onReturnToMainClicked() },
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .align(Alignment.End)
                            .padding(top = 32.dp, bottom = 16.dp, end = 16.dp)
                    )
                }
            }
        }
    }

    BackHandler {
        viewModel.onBackTriggered()
    }
}

@Composable
private fun ResultDataComponent(
    data: LearnProcessResult,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        Column(modifier = Modifier.dataSectionShape()) {
            DataSectionTitle(text = "General")
            ResultItemRow(
                title = "On first try:",
                content = data.wordsAnsweredOnFirstTry.size.toString() + " words"
            )
            Spacer(Modifier.height(10.dp))
            ResultItemRow(
                title = "Average amount of tries:",
                content = data.averageTriesCountToAnswerWord.toFloat().toString(),
            )
        }

        if (data.wordsMovedToLearnedCollection.isNotEmpty()
            || data.wordsRemovedFromLearnedCollection.isNotEmpty()
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .dataSectionShape()
            ) {
                DataSectionTitle(text = "Learned stats")
                if (data.wordsMovedToLearnedCollection.isNotEmpty()) {
                    ResultItemColumn(
                        title = "Moved to Learned collection:",
                        content = data.wordsMovedToLearnedCollection.joinToString(", ") { it.eng },
                    )
                }
                if (data.wordsRemovedFromLearnedCollection.isNotEmpty()) {
                    if (data.wordsMovedToLearnedCollection.isNotEmpty()) {
                        Spacer(Modifier.height(20.dp))
                    }
                    ResultItemColumn(
                        title = "Removed from Learned collection:",
                        content = data.wordsRemovedFromLearnedCollection.joinToString(", ") { it.eng }
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .padding(top = 16.dp)
                .dataSectionShape()
        ) {
            DataSectionTitle(text = "Words stats")
            if (data.mostDifficultWordsWithTriesCount.isNotEmpty()) {
                ResultItemColumn(
                    title = "The most difficult words with amount of tries:",
                    content = data.mostDifficultWordsWithTriesCount.joinToString("\n") { it.first.eng + "  -  " + it.second },
                )
            }
            if (data.wordsAnsweredOnFirstTry.isNotEmpty()) {
                if (data.mostDifficultWordsWithTriesCount.isNotEmpty()) {
                    Spacer(Modifier.height(20.dp))
                }
                ResultItemColumn(
                    title = "All words answered on first try:",
                    content = data.wordsAnsweredOnFirstTry.joinToString(", ") { it.eng },
                )
            }
        }
    }
}

@Composable
private fun ResultItemRow(
    title: String,
    content: String,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(
            text = title,
            style = AppTheme.typography.h2RegularTextStyle
        )
        Divider(Modifier.width(4.dp))
        Text(
            text = content,
            style = AppTheme.typography.h2MediumTextStyle
        )
    }
}

@Composable
private fun ResultItemColumn(
    title: String,
    content: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            style = AppTheme.typography.h2MediumTextStyle
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = content,
            style = AppTheme.typography.h2RegularTextStyle,
            modifier = Modifier.padding(start = 24.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ResultScreenContent_Preview() {
    ThemeProvider {
        val words = remember {
            listOf(
                WordDomain("1", "rus1", "eng1", 0, false, false),
                WordDomain("2", "rus2", "eng2", 0, false, false),
                WordDomain("3", "rus3", "eng3", 0, false, false),
            )
        }
        ResultDataComponent(
            data = LearnProcessResult(
                wordsAnsweredOnFirstTry = words,
                mostDifficultWordsWithTriesCount = words.mapIndexed { i, w -> Pair(w, i) }
                    .sortedByDescending { it.second },
                wordsMovedToLearnedCollection = listOf(words[0], words[1]),
                wordsRemovedFromLearnedCollection = listOf(words[0], words[1]),
                averageTriesCountToAnswerWord = 3.33333333333
            )
        )
    }
}
