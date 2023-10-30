package com.nestifff.learnwords.presentation.ui.components.screens.collection.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nestifff.learnwords.presentation.screen.collection.model.CollectionItem
import com.nestifff.learnwords.presentation.screen.collection.model.ExpandedWordState
import kotlinx.collections.immutable.ImmutableList

@OptIn(ExperimentalFoundationApi::class)
@ExperimentalMaterialApi
@Composable
fun CollectionsPager(
    collections: ImmutableList<CollectionItem>,
    currCollectionInd: Int,
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
        pageCount = { collections.size }, initialPage = currCollectionInd
    )

    HorizontalPager(state = pagerState) { page ->

        LazyColumn(
            modifier = modifier,
            contentPadding = PaddingValues(top = 64.dp, bottom = 64.dp)
        ) {
            itemsIndexed(
                items = collections[page].list,
                key = { _, word -> word.id }
            ) { _, word ->

                val currentItem by rememberUpdatedState(word)

                val removeDismissState = rememberDismissState(
                    confirmStateChange = {
                        if (it == DismissValue.DismissedToStart) {
                            onDeleteWordClick(currentItem.id)
                            true
                        } else {
                            false
                        }
                    }
                )

                SwipeToDismiss(
                    state = removeDismissState,
                    modifier = Modifier
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
                        modifier = Modifier.padding(vertical = 4.dp),
                        expandedWordState = expandedWordState.takeIf {
                            word.id == expandedWordState?.word?.id
                        },
                        onEditWordValuesChange = onEditWordValuesChange,
                        onMakeFavoriteClick = {
                            onMakeFavoriteClick(word.id)
                        },
                    )
                }
            }
        }
    }

    LaunchedEffect(key1 = currCollectionInd) {
        pagerState.animateScrollToPage(currCollectionInd)
    }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            if (page != currCollectionInd) {
                onNewPageSelect(page)
            }
        }
    }
}
