package com.nestifff.learnwords.presentation.screen.collection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.nestifff.learnwords.app.navigation.Graph
import com.nestifff.learnwords.ext.noRippleClickable
import com.nestifff.learnwords.ext.onEffect
import com.nestifff.learnwords.presentation.screen.collection.CollectionViewModel.Effect
import com.nestifff.learnwords.presentation.screen.collection.CollectionViewModel.Event
import com.nestifff.learnwords.presentation.screen.collection.model.AddWordDialogState
import com.nestifff.learnwords.presentation.screen.collection.model.CollectionScreenWord
import com.nestifff.learnwords.presentation.ui.components.screens.collection.*
import com.nestifff.learnwords.presentation.ui.components.screens.collection.list.WordsList
import com.nestifff.learnwords.presentation.ui.theme.WordsTheme

@Composable
fun CollectionScreen(
    viewModel: CollectionViewModel,
    navigateToSettingsScreen: () -> Unit,
    navigateToLearnScreen: () -> Unit,
) {

    val state by viewModel.uiState.collectAsState()

    onEffect(effect = viewModel.uiEffect) {
        when (it) {
            Effect.NavigateToSettingsScreen -> navigateToSettingsScreen()
            Effect.NavigateToLearnScreen -> navigateToLearnScreen()
        }
    }

    val onEvent: (Event) -> Unit = remember { { viewModel.onEvent(it) } }

    CollectionScreen(
        state = state,
        onSettingsClick = { onEvent(Event.SettingsClicked) },
        onLearnButtonClick = { onEvent(Event.LearnButtonClicked) },
        onWordsInProgressClick = { onEvent(Event.WordsInProgressClicked) },
        onWordsLearnedClick = { onEvent(Event.WordsLearnedClicked) },
        onWordsFavoriteClick = { onEvent(Event.WordsFavoriteClicked) },
        onWordItemClick = { onEvent(Event.WordItemClicked(it)) },
        onWordItemValueChange = { (rus, eng) -> onEvent(Event.WordItemValueChanged(rus, eng)) },
        onWordUpdateClick = { onEvent(Event.WordUpdateClicked) },
        onWordDeleteClick = { onEvent(Event.WordDeleteClicked(it)) },
        onOpenAddWordDialogClick = { onEvent(Event.OpenAddWordDialogClicked) },
        onCloseAddWordDialogClick = { onEvent(Event.CloseAddWordDialogClicked) },
        onAddWordValuesChange = { (rus, eng) -> onEvent(Event.AddWordValuesChanged(rus, eng)) },
        onAddWordClick = { onEvent(Event.AddWordClicked) },
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CollectionScreen(
    state: CollectionViewModel.State,
    onSettingsClick: () -> Unit,
    onLearnButtonClick: () -> Unit,
    onWordsInProgressClick: () -> Unit,
    onWordsLearnedClick: () -> Unit,
    onWordsFavoriteClick: () -> Unit,
    onWordItemClick: (CollectionScreenWord) -> Unit,
    onWordItemValueChange: (rus: String, eng: String) -> Unit,
    onWordUpdateClick: () -> Unit,
    onWordDeleteClick: (CollectionScreenWord) -> Unit,
    onOpenAddWordDialogClick: () -> Unit,
    onCloseAddWordDialogClick: () -> Unit,
    onAddWordValuesChange: (rus: String, eng: String) -> Unit,
    onAddWordClick: () -> Unit,
) {

    Box(modifier = Modifier.fillMaxSize()) {

        if (state.addWordDialogState is AddWordDialogState.Expanded) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(color = WordsTheme.colors.expandedDialogBackground)
                    .noRippleClickable { onCloseAddWordDialogClick() }
            )
        }

        Scaffold(
            modifier = Modifier.imePadding(),
            topBar = {
                CollectionTopBar(
                    modifier = Modifier.padding(top = 3.dp, end = 4.dp),
                    onSettingsButtonClick = onSettingsClick
                )
            },
            bottomBar = {
                AddWordDialog(
                    onEnterWord = { rus, eng -> }
                )
            }
        ) { scaffoldPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(scaffoldPadding)
            ) {
                WordsList(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    words = state.currWordsCollection,
                    onSaveClick = { updatedWord ->
                        vm.onEvent(Event.WordUpdated(updatedWord))
                    },
                    onDeleteWordTrigger = {
                        vm.onEvent(Event.WordDeleted(it))
                    }
                )
                CollectionsSwitcher(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    collections = listOf("In progress", "Learned", "Favorites")
                )
                CollectionLearnButton(
                    modifier = Modifier
                        .padding(bottom = 68.dp, end = 10.dp)
                        .align(Alignment.BottomEnd)
                )
            }
        }
    }
}
