package com.nestifff.learnwords.presentation.screen.collection

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
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
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.nestifff.learnwords.app.navigation.destinations.LearnScreenArgument
import com.nestifff.learnwords.ext.noRippleClickable
import com.nestifff.learnwords.ext.onEffect
import com.nestifff.learnwords.presentation.screen.collection.CollectionViewModel.Effect.ErrorCreatingWordEmptyValue
import com.nestifff.learnwords.presentation.screen.collection.CollectionViewModel.Effect.NavigateToLearnScreen
import com.nestifff.learnwords.presentation.screen.collection.CollectionViewModel.Effect.NavigateToSettingsScreen
import com.nestifff.learnwords.presentation.screen.collection.CollectionViewModel.Effect.NotAvailableYetMessage
import com.nestifff.learnwords.presentation.screen.collection.model.AddWordDialogState
import com.nestifff.learnwords.presentation.ui.components.screens.collection.CollectionLearnButton
import com.nestifff.learnwords.presentation.ui.components.screens.collection.CollectionTopBar
import com.nestifff.learnwords.presentation.ui.components.screens.collection.CollectionsSwitcher
import com.nestifff.learnwords.presentation.ui.components.screens.collection.dialog.AddWordDialog
import com.nestifff.learnwords.presentation.ui.components.screens.collection.dialog.CustomLearnDialog
import com.nestifff.learnwords.presentation.ui.components.screens.collection.list.CollectionsPager
import com.nestifff.learnwords.presentation.ui.theme.AppTheme
import com.nestifff.learnwords.presentation.utils.showToast

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun CollectionScreen(
    viewModel: CollectionViewModel,
    navigateToSettingsScreen: () -> Unit,
    navigateToLearnScreen: (LearnScreenArgument) -> Unit,
) {

    val state by viewModel.uiState.collectAsState()

    val context = LocalContext.current
    onEffect(effect = viewModel.uiEffect) { effect ->
        when (effect) {
            is NavigateToSettingsScreen -> navigateToSettingsScreen()
            is NavigateToLearnScreen -> navigateToLearnScreen(effect.data)

            is ErrorCreatingWordEmptyValue -> context.showToast("You can't create word with empty value")
            is NotAvailableYetMessage -> context.showToast("Coming soon")
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AppTheme.colors.background)
            .statusBarsPadding(),
        topBar = {
            CollectionTopBar(
                modifier = Modifier.padding(top = 4.dp, end = 4.dp),
                onSettingsButtonClick = { viewModel.onSettingsClicked() },
                onMenuButtonClick = { viewModel.onMenuClicked() }
            )
        },
        bottomBar = {
            AddWordDialog(
                state = state.addWordDialogState,
                onValuesChange = { rus, eng -> viewModel.onAddWordValuesChanged(rus, eng) },
                onAddWordClick = { viewModel.onAddWordClicked() },
                onDismiss = { viewModel.onCloseAddWordDialogClicked() },
                onOpenClick = { viewModel.onOpenAddWordDialogClicked() }
            )
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = state.isLearnButtonVisible,
                enter = fadeIn() + scaleIn(),
                exit = fadeOut() + scaleOut(),
            ) {
                CollectionLearnButton(
                    onClick = { viewModel.onLearnButtonClicked() },
                    onLongClick = { viewModel.onLearnButtonLongClicked() },
                )
            }
        }
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
                onNewPageSelect = { viewModel.onNewCollectionTypeSelected(it) },
                onEditWordSaveClick = { viewModel.onWordUpdateClicked() },
                onDeleteWordClick = { viewModel.onWordDeleteClicked(it) },
                onWordClick = { viewModel.onWordItemClicked(it) },
                onMakeFavoriteClick = { viewModel.onMakeFavoriteClicked(it) },
                onEditWordValuesChange = { rus, eng ->
                    viewModel.onEditWordValuesChanged(
                        rus,
                        eng
                    )
                },
            )
            CollectionsSwitcher(
                collections = state.collections,
                selectedType = state.currCollectionType,
                onCollectionTypeClick = { viewModel.onNewCollectionTypeSelected(it) },
                modifier = Modifier.padding(horizontal = 10.dp),
            )

            CustomLearnDialog(
                state = state.customLearnDialogState,
                onNumberToLearnChange = { viewModel.onCustomLeanDialogNumberChanged(it) },
                onLearnClick = { viewModel.onCustomLeanDialogLearnClicked() },
                onDismiss = { viewModel.onCustomLeanDialogDismissed() }
            )
        }
    }
}
