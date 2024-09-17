package com.nestifff.learnwords.presentation.ui.components.screens.collection.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nestifff.learnwords.presentation.model.CollectionType
import com.nestifff.learnwords.presentation.model.CollectionType.Favorite
import com.nestifff.learnwords.presentation.model.CollectionType.InProgress
import com.nestifff.learnwords.presentation.model.CollectionType.Learned
import com.nestifff.learnwords.presentation.model.fromCollectionIndex
import com.nestifff.learnwords.presentation.model.toIndex
import com.nestifff.learnwords.presentation.screen.collection.model.CollectionItem
import com.nestifff.learnwords.presentation.screen.collection.model.CollectionWordItem
import com.nestifff.learnwords.presentation.screen.collection.model.ExpandedWordState
import com.nestifff.learnwords.presentation.ui.theme.AppTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@ExperimentalMaterialApi
@Composable
fun CollectionsPager(
    collections: ImmutableList<CollectionItem>,
    currCollectionType: CollectionType,
    expandedWordState: ExpandedWordState?,
    modifier: Modifier = Modifier,
    onNewPageSelect: (Int) -> Unit,
    onWordClick: (id: String) -> Unit,
    onMakeFavoriteClick: (id: String) -> Unit,
    onDeleteWordClick: (id: String) -> Unit,
    onEditWordValuesChange: (rus: String, eng: String) -> Unit,
    onEditWordSaveClick: () -> Unit,
) {

    val pagerState = rememberPagerState(
        pageCount = { collections.size }, initialPage = currCollectionType.toIndex()
    )

    HorizontalPager(state = pagerState, modifier = modifier) { page ->

        val list = collections[page].list
        if (list.isNotEmpty()) {
            ItemsList(
                list = list,
                expandedWordState = expandedWordState,
                onWordClick = onWordClick,
                onMakeFavoriteClick = onMakeFavoriteClick,
                onDeleteWordClick = onDeleteWordClick,
                onEditWordValuesChange = onEditWordValuesChange,
                onEditWordSaveClick = onEditWordSaveClick,
            )
        } else {
            EmptyListScreen(
                collectionType = CollectionType.fromCollectionIndex(page)
            )
        }
    }

    LaunchedEffect(pagerState, currCollectionType) {
        if (pagerState.currentPage != currCollectionType.toIndex()) {
            // scroll programmatically if currCollectionType was changed from different place (e.g collectionsSwitcher)
            // we need coroutine here because if user interrupts animation by scrolling to different page, animation will not be finished
            launch {
                pagerState.animateScrollToPage(currCollectionType.toIndex())
            }
            // we need this because otherwise it will be triggered while animation
            // (e.g if we scroll from 0 to 2, have collect for 1)
            delay(100)
        }
        snapshotFlow { pagerState.currentPage }.distinctUntilChanged().collect { page ->
            onNewPageSelect(page)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
private fun ItemsList(
    list: ImmutableList<CollectionWordItem>,
    expandedWordState: ExpandedWordState?,
    onWordClick: (id: String) -> Unit,
    onMakeFavoriteClick: (id: String) -> Unit,
    onDeleteWordClick: (id: String) -> Unit,
    onEditWordValuesChange: (rus: String, eng: String) -> Unit,
    onEditWordSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 64.dp, bottom = 128.dp)
    ) {
        itemsIndexed(
            items = list,
            key = { _, word -> word.id }
        ) { _, word ->

            val currentItem by rememberUpdatedState(word)
            val removeDismissState = rememberDismissState(
                confirmStateChange = {
                    val needDelete = it == DismissValue.DismissedToStart
                    if (needDelete) {
                        onDeleteWordClick(currentItem.id)
                    }
                    needDelete
                }
            )
            SwipeToDismiss(
                state = removeDismissState,
                modifier = Modifier
                    .padding(vertical = 5.dp)
                    .animateItemPlacement(),
                background = { WordListItemDeleteBackground(removeDismissState) },
                directions = setOf(DismissDirection.EndToStart),
                dismissThresholds = {
                    FractionalThreshold(0.2f)
                },
            ) {
                WordsListItem(
                    word = word,
                    onEditWordSaveClick = { onEditWordSaveClick() },
                    onClick = { onWordClick(word.id) },
                    expandedWordState = expandedWordState.takeIf {
                        word.id == expandedWordState?.word?.id
                    },
                    onEditWordValuesChange = onEditWordValuesChange,
                    onMakeFavoriteClick = {
                        onMakeFavoriteClick(word.id)
                    },
                )

                val haptic = LocalHapticFeedback.current
                LaunchedEffect(removeDismissState.targetValue) {
                    // in this state if user stops touch word will be removed
                    if (removeDismissState.targetValue != DismissValue.Default) {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyListScreen(collectionType: CollectionType, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp)
            .padding(top = 128.dp, bottom = 64.dp)
            .clip(RoundedCornerShape(32.dp))
            .border(2.dp, AppTheme.colors.backgroundMedium, RoundedCornerShape(32.dp))
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val detailsText = remember(collectionType) {
            when (collectionType) {
                InProgress -> "To add new word, use text field at the bottom"
                Learned -> "To have words here, you should enter correct word while learning several times"
                Favorite -> "You can click on Star near your word in a list to make it favorite"
            }
        }
        Text(
            text = "Your collection is empty",
            modifier = Modifier.padding(bottom = 12.dp),
            style = AppTheme.typography.h2MediumTextStyle,
            textAlign = TextAlign.Center
        )
        Text(
            text = detailsText,
            style = AppTheme.typography.h3RegularTextStyle.copy(
                color = AppTheme.colors.contentLight
            ),
            textAlign = TextAlign.Center
        )
    }
}
