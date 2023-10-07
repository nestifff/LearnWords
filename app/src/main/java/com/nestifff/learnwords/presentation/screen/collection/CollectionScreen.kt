package com.nestifff.learnwords.presentation.screen.collection

import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nestifff.learnwords.ext.onEffect
import com.nestifff.learnwords.presentation.screen.collection.CollectionViewModel.Effect
import com.nestifff.learnwords.presentation.ui.components.screens.collection.*
import com.nestifff.learnwords.presentation.ui.components.screens.collection.dialog.AddWordDialog
import com.nestifff.learnwords.presentation.ui.components.screens.collection.dialog.CustomLearnDialog
import com.nestifff.learnwords.presentation.ui.components.screens.collection.list.WordsList

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

    CollectionScreen(
        state = state,
        onSettingsClick = { viewModel.onSettingsClicked() },
        onLearnButtonClick = { viewModel.onLearnButtonClicked() },
        onLearnButtonLongClick = { viewModel.onLearnButtonLongClicked() },
        onCustomLeanDialogDismiss = { viewModel.onCustomLeanDialogDismissed() },
        onCustomLeanDialogNumberChange = { viewModel.onCustomLeanDialogNumberChanged(it) },
        onCustomLeanDialogLearnClick = { viewModel.onCustomLeanDialogLearnClicked() },
        onWordsInProgressClick = { viewModel.onWordsInProgressClicked() },
        onWordsLearnedClick = { viewModel.onWordsLearnedClicked() },
        onWordsFavoriteClick = { viewModel.onWordsFavoriteClicked() },
        onWordItemClick = { viewModel.onWordItemClicked(it) },
        onEditWordValuesChange = { rus, eng -> viewModel.onEditWordValuesChanged(rus, eng) },
        onWordUpdateClick = { viewModel.onWordUpdateClicked() },
        onDeleteWordClick = { viewModel.onWordDeleteClicked(it) },
        onOpenAddWordDialogClick = { viewModel.onOpenAddWordDialogClicked() },
        onCloseAddWordDialogClick = { viewModel.onCloseAddWordDialogClicked() },
        onAddWordValuesChange = { rus, eng -> viewModel.onAddWordValuesChanged(rus, eng) },
        onAddWordClick = { viewModel.onAddWordClicked() },
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CollectionScreen(
    state: CollectionViewModel.State,
    onSettingsClick: () -> Unit,
    onLearnButtonClick: () -> Unit,
    onLearnButtonLongClick: () -> Unit,
    onWordsInProgressClick: () -> Unit,
    onCustomLeanDialogDismiss: () -> Unit,
    onCustomLeanDialogNumberChange: (Int) -> Unit,
    onCustomLeanDialogLearnClick: () -> Unit,
    onWordsLearnedClick: () -> Unit,
    onWordsFavoriteClick: () -> Unit,
    onWordItemClick: (String) -> Unit,
    onEditWordValuesChange: (rus: String, eng: String) -> Unit,
    onWordUpdateClick: () -> Unit,
    onDeleteWordClick: (String) -> Unit,
    onOpenAddWordDialogClick: () -> Unit,
    onCloseAddWordDialogClick: () -> Unit,
    onAddWordValuesChange: (rus: String, eng: String) -> Unit,
    onAddWordClick: () -> Unit,
) {

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            modifier = Modifier.imePadding(),
            topBar = {
                CollectionTopBar(
                    modifier = Modifier.padding(top = 4.dp, end = 4.dp),
                    onSettingsButtonClick = onSettingsClick
                )
            },
            bottomBar = {
                AddWordDialog(
                    state = state.addWordDialogState,
                    onValuesChange = { rus, eng -> onAddWordValuesChange(rus, eng) },
                    onAddWordClick = { onAddWordClick() },
                    onDismiss = { onCloseAddWordDialogClick() },
                    onOpenClick = { onOpenAddWordDialogClick() }
                )
            }
        ) { scaffoldPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(scaffoldPadding)
            ) {
                WordsList(
                    expandedWordState = state.expandedWordState,
                    words = state.currWordsCollection,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    onEditWordSaveClick = { onWordUpdateClick() },
                    onDeleteWordClick = { onDeleteWordClick(it) },
                    onWordClick = { onWordItemClick(it) },
                    onEditWordValuesChange = onEditWordValuesChange,
                )
                CollectionsSwitcher(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    collections = listOf("In progress", "Learned", "Favorites")
                )
                CollectionLearnButton(
                    onClick = onLearnButtonClick,
                    onLongClick = onLearnButtonLongClick,
                    modifier = Modifier
                        .padding(bottom = 24.dp, end = 10.dp)
                        .align(Alignment.BottomEnd),
                )

                CustomLearnDialog(
                    state = state.customLearnDialogState,
                    onNumberToLearnChange = onCustomLeanDialogNumberChange,
                    onLearnClick = onCustomLeanDialogLearnClick,
                    onDismiss = onCustomLeanDialogDismiss
                )
            }
        }
    }
}
