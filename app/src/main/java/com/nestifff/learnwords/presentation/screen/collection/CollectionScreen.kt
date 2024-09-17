package com.nestifff.learnwords.presentation.screen.collection

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.nestifff.learnwords.app.navigation.destinations.LearnScreenArgument
import com.nestifff.learnwords.ext.onEffect
import com.nestifff.learnwords.presentation.model.CollectionType
import com.nestifff.learnwords.presentation.screen.collection.CollectionViewModel.Effect.ErrorCreatingWordEmptyValue
import com.nestifff.learnwords.presentation.screen.collection.CollectionViewModel.Effect.LearningZeroOrEmptyWordsErrorMessage
import com.nestifff.learnwords.presentation.screen.collection.CollectionViewModel.Effect.NavigateToLearnScreen
import com.nestifff.learnwords.presentation.screen.collection.CollectionViewModel.Effect.NavigateToSettingsScreen
import com.nestifff.learnwords.presentation.screen.collection.CollectionViewModel.Effect.NotAvailableYetMessage
import com.nestifff.learnwords.presentation.screen.collection.model.AddWordDialogState
import com.nestifff.learnwords.presentation.ui.components.screens.collection.CollectionLearnButton
import com.nestifff.learnwords.presentation.ui.components.screens.collection.CollectionTopBar
import com.nestifff.learnwords.presentation.ui.components.screens.collection.CollectionsSwitcher
import com.nestifff.learnwords.presentation.ui.components.screens.collection.dialog.AddWordBottomBar
import com.nestifff.learnwords.presentation.ui.components.screens.collection.dialog.CustomLearnDialog
import com.nestifff.learnwords.presentation.ui.components.screens.collection.list.CollectionsPager
import com.nestifff.learnwords.presentation.ui.theme.AppTheme
import com.nestifff.learnwords.presentation.utils.keyboardAsState
import com.nestifff.learnwords.presentation.utils.showToast

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun CollectionScreen(
    vm: CollectionViewModel,
    navigateToSettingsScreen: () -> Unit,
    navigateToLearnScreen: (LearnScreenArgument) -> Unit,
) {

    val state by vm.uiState.collectAsState()

    val context = LocalContext.current
    onEffect(effect = vm.uiEffect) { effect ->
        when (effect) {
            is NavigateToSettingsScreen -> navigateToSettingsScreen()
            is NavigateToLearnScreen -> navigateToLearnScreen(effect.data)

            is LearningZeroOrEmptyWordsErrorMessage -> context.showToast("Number of words to learn must be more than 0")
            is ErrorCreatingWordEmptyValue -> context.showToast("You can't create word with empty value")
            is NotAvailableYetMessage -> context.showToast("Coming soon")
        }
    }

    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AppTheme.colors.background),
        containerColor = AppTheme.colors.background,
        bottomBar = {
            AnimatedVisibility(
                visible = state.currCollectionType == CollectionType.InProgress,
                enter = fadeIn(tween(120)) + expandIn(tween(120)),
                exit = shrinkOut(tween(120)) + fadeOut(tween(120)),
            ) {
                AddWordBottomBar(
                    state = state.addWordDialogState,
                    onValuesChange = { rus, eng -> vm.onAddWordValuesChanged(rus, eng) },
                    onAddWordClick = { vm.onAddWordClicked() },
                    onDismiss = { vm.onCloseAddWordDialogClicked() },
                    onOpenClick = { vm.onOpenAddWordDialogClicked() }
                )
            }
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = state.isLearnButtonVisible,
                enter = fadeIn() + scaleIn(),
                exit = fadeOut() + scaleOut(),
            ) {
                CollectionLearnButton(
                    onClick = { vm.onLearnButtonClicked() },
                    onCustomizeClick = { vm.onCustomLearnButtonClicked() }
                )
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) {
                Snackbar(
                    snackbarData = it,
                    actionColor = AppTheme.colors.primaryLight,
                    containerColor = AppTheme.colors.content,
                    contentColor = AppTheme.colors.backgroundLight
                )
            }
        },
    ) { scaffoldPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding)
        ) {
            if (state.collections.isEmpty()) {
                return@Box
            }

            CollectionsPager(
                expandedWordState = state.expandedWordState,
                collections = state.collections,
                currCollectionType = state.currCollectionType,
                modifier = Modifier.fillMaxSize(),
                onNewPageSelect = { vm.onNewCollectionTypeSelected(it) },
                onEditWordSaveClick = { vm.onWordUpdateClicked() },
                onDeleteWordClick = { vm.onWordDeleteClicked(it) },
                onWordClick = { vm.onWordItemClicked(it) },
                onMakeFavoriteClick = { vm.onMakeFavoriteClicked(it) },
                onEditWordValuesChange = { rus, eng ->
                    vm.onEditWordValuesChanged(rus, eng)
                },
            )
            Column(
                modifier = Modifier.transparentGradientBackground()
            ) {
                CollectionTopBar(
                    onSettingsButtonClick = { vm.onSettingsClicked() },
                    onMenuButtonClick = { vm.onMenuClicked() },
                    onDebugOptionAddWordsClicked = { vm.onDebugOptionAddWordsClicked() }
                )
                CollectionsSwitcher(
                    collections = state.collections,
                    selectedType = state.currCollectionType,
                    onCollectionTypeClick = { vm.onNewCollectionTypeSelected(it) },
                    modifier = Modifier.padding(horizontal = 10.dp),
                )
            }

            state.customLearnDialogState?.let {
                CustomLearnDialog(
                    state = it,
                    onNumberToLearnChange = { vm.onCustomLearnDialogNumberChanged(it) },
                    onLearnClick = { vm.onCustomLearnDialogLearnClicked() },
                    onDismiss = { vm.onCustomLearnDialogDismiss() },
                    onSelectWayToLearnClick = { vm.onCustomLearnDialogSelectWayToLearnClicked() },
                    onWayToLearnSelect = { vm.onCustomLearnDialogWayToLearnSelected(it) },
                    onDismissWayToLearnMenu = { vm.onCustomLearnDialogWayToLearnMenuDismiss() },
                )
            }
        }
    }

    LaunchedEffect(state.isUndoRemoveWordVisible) {
        if (state.isUndoRemoveWordVisible) {
            val result = snackbarHostState.showSnackbar(
                message = "Word deleted",
                actionLabel = "UNDO",
                duration = SnackbarDuration.Short
            )
            when (result) {
                SnackbarResult.ActionPerformed -> vm.onUndoDeleteClicked()
                SnackbarResult.Dismissed -> vm.undoDeleteWordShownWithoutUndoing()
            }
        }
    }

    val isKeyboardVisible by keyboardAsState()
    BackHandler {
        if (state.addWordDialogState is AddWordDialogState.Expanded && !isKeyboardVisible) {
            vm.onCloseAddWordDialogClicked()
        }
    }
}

@Composable
private fun Modifier.transparentGradientBackground() =
    this.then(
        Modifier.background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    AppTheme.colors.background,
                    Color.Transparent
                )
            )
        )
    )
