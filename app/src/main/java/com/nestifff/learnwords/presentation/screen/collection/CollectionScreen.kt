package com.nestifff.learnwords.presentation.screen.collection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nestifff.learnwords.app.navigation.destinations.LearnScreenArgument
import com.nestifff.learnwords.ext.onEffect
import com.nestifff.learnwords.presentation.screen.collection.CollectionViewModel.Effect.NavigateToLearnScreen
import com.nestifff.learnwords.presentation.screen.collection.CollectionViewModel.Effect.NavigateToSettingsScreen
import com.nestifff.learnwords.presentation.ui.components.screens.collection.CollectionLearnButton
import com.nestifff.learnwords.presentation.ui.components.screens.collection.CollectionTopBar
import com.nestifff.learnwords.presentation.ui.components.screens.collection.CollectionsSwitcher
import com.nestifff.learnwords.presentation.ui.components.screens.collection.dialog.AddWordDialog
import com.nestifff.learnwords.presentation.ui.components.screens.collection.dialog.CustomLearnDialog
import com.nestifff.learnwords.presentation.ui.components.screens.collection.list.CollectionsPager
import com.nestifff.learnwords.presentation.ui.theme.AppTheme

@Composable
fun CollectionScreen(
    viewModel: CollectionViewModel,
    navigateToSettingsScreen: () -> Unit,
    navigateToLearnScreen: (LearnScreenArgument) -> Unit,
) {

    val state by viewModel.uiState.collectAsState()

    onEffect(effect = viewModel.uiEffect) { effect ->
        when (effect) {
            is NavigateToSettingsScreen -> navigateToSettingsScreen()
            is NavigateToLearnScreen -> navigateToLearnScreen(effect.data)
        }
    }

    CollectionScreenContent(
        state = state,
        onSettingsClick = { viewModel.onSettingsClicked() },
        onLearnButtonClick = { viewModel.onLearnButtonClicked() },
        onLearnButtonLongClick = { viewModel.onLearnButtonLongClicked() },
        onCustomLeanDialogDismiss = { viewModel.onCustomLeanDialogDismissed() },
        onCustomLeanDialogNumberChange = { viewModel.onCustomLeanDialogNumberChanged(it) },
        onCustomLeanDialogLearnClick = { viewModel.onCustomLeanDialogLearnClicked() },
        onNewCollectionTypeSelect = { viewModel.onNewCollectionTypeSelected(it) },
        onWordItemClick = { viewModel.onWordItemClicked(it) },
        onMakeFavoriteClick = { viewModel.onMakeFavoriteClicked(it) },
        onEditWordValuesChange = { rus, eng -> viewModel.onEditWordValuesChanged(rus, eng) },
        onWordUpdateClick = { viewModel.onWordUpdateClicked() },
        onDeleteWordClick = { viewModel.onWordDeleteClicked(it) },
        onOpenAddWordDialogClick = { viewModel.onOpenAddWordDialogClicked() },
        onCloseAddWordDialogClick = { viewModel.onCloseAddWordDialogClicked() },
        onAddWordValuesChange = { rus, eng -> viewModel.onAddWordValuesChanged(rus, eng) },
        onAddWordClick = { viewModel.onAddWordClicked() },
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
private fun CollectionScreenContent(
    state: CollectionViewModel.State,
    onSettingsClick: () -> Unit,
    onLearnButtonClick: () -> Unit,
    onLearnButtonLongClick: () -> Unit,
    onCustomLeanDialogDismiss: () -> Unit,
    onCustomLeanDialogNumberChange: (Int) -> Unit,
    onCustomLeanDialogLearnClick: () -> Unit,
    onNewCollectionTypeSelect: (Int) -> Unit,
    onWordItemClick: (String) -> Unit,
    onMakeFavoriteClick: (String) -> Unit,
    onEditWordValuesChange: (rus: String, eng: String) -> Unit,
    onWordUpdateClick: () -> Unit,
    onDeleteWordClick: (String) -> Unit,
    onOpenAddWordDialogClick: () -> Unit,
    onCloseAddWordDialogClick: () -> Unit,
    onAddWordValuesChange: (rus: String, eng: String) -> Unit,
    onAddWordClick: () -> Unit,
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AppTheme.colors.background)
            .statusBarsPadding()
    ) {
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
            },
            containerColor = AppTheme.colors.background,
        ) { scaffoldPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(scaffoldPadding)
            ) {
                if (state.collections.isNotEmpty()) {

                    CollectionsPager(
                        expandedWordState = state.expandedWordState,
                        collections = state.collections,
                        currCollectionInd = state.currCollectionInd,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        onNewPageSelect = onNewCollectionTypeSelect,
                        onEditWordSaveClick = { onWordUpdateClick() },
                        onDeleteWordClick = { onDeleteWordClick(it) },
                        onWordClick = { onWordItemClick(it) },
                        onMakeFavoriteClick = onMakeFavoriteClick,
                        onEditWordValuesChange = onEditWordValuesChange,
                    )
                    CollectionsSwitcher(
                        collections = state.collections,
                        selectedTypeIndex = state.currCollectionInd,
                        onCollectionTypeClick = onNewCollectionTypeSelect,
                        modifier = Modifier.padding(horizontal = 10.dp),
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
}
