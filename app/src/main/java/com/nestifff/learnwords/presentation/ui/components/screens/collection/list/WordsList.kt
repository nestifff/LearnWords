package com.nestifff.learnwords.presentation.ui.components.screens.collection.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nestifff.learnwords.presentation.screen.collection.model.WordCollectionScreen

@OptIn(ExperimentalFoundationApi::class)
@ExperimentalMaterialApi
@Composable
fun WordsList(
    modifier: Modifier = Modifier,
    words: List<WordCollectionScreen>,
    saveClicked: (updatedWord: WordCollectionScreen) -> Unit,
    deleteWordTriggered: (id: String) -> Unit,
) {

    var selectedWordInd: Int? by remember { mutableStateOf(null) }

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(top = 64.dp, bottom = 64.dp)
    ) {
        itemsIndexed(words, key = { _, word -> word.id }) { ind, word ->

            val currentItem by rememberUpdatedState(word)

            val dismissState2 = rememberDismissState(
                confirmStateChange = {
                    if (it == DismissValue.DismissedToStart) {
                        deleteWordTriggered(currentItem.id)
                        true
                    } else {
                        false
                    }
                }
            )

            SwipeToDismiss(
                state = dismissState2,
                modifier = Modifier
                    .animateItemPlacement(),
                background = { WordListItemDeleteBackground(dismissState2) },
                directions = setOf(DismissDirection.EndToStart),
                dismissThresholds = {
                    FractionalThreshold(0.2f)
                },
            ) {
                WordsListItem(
                    modifier = Modifier.padding(vertical = 4.dp),
                    word = word,
                    isSelected = selectedWordInd == ind,
                    itemOnClick = {
                        selectedWordInd = if (selectedWordInd == ind) null else ind
                    },
                    saveClicked = saveClicked
                )
            }
        }
    }
}
