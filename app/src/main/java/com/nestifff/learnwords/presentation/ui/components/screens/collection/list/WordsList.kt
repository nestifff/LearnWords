package com.nestifff.learnwords.presentation.ui.components.screens.collection.list

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nestifff.learnwords.presentation.screen.collection.model.WordCollectionScreen

@Composable
fun WordsList(
    modifier: Modifier = Modifier,
    words: List<WordCollectionScreen>,
    saveClicked: (updatedWord: WordCollectionScreen) -> Unit,
) {

    var selectedWordInd: Int? by remember { mutableStateOf(null) }

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(top = 64.dp, bottom = 64.dp)
    ) {
        itemsIndexed(words) { ind, word ->
            WordsListItem(
                modifier = Modifier.padding(vertical = 4.dp),
                word = word,
                isSelected = selectedWordInd == ind,
                itemOnClick = { selectedWordInd = if (selectedWordInd == ind) null else ind },
                saveClicked = saveClicked
            )
        }
    }
}
