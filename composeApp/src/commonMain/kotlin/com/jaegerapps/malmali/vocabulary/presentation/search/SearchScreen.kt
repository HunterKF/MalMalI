package com.jaegerapps.malmali.vocabulary.presentation.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.jaegerapps.malmali.components.ActionButton

@Composable
fun SearchScreen(
    component: SearchSetComponent
) {
    val state = component.state.collectAsState()
    LazyColumn {
        item {
            if (state.value.loading) {
                CircularProgressIndicator()
            }
            Text(
                text = "Hello"
            )
            state.value.sets.forEach {
                Text(text = it.title)
            }
            Text("Start: ${state.value.startPageRange}. End: ${state.value.endPageRange}")
            if (state.value.loadingMore) {
                CircularProgressIndicator()
            }
            if (state.value.endOfRange) {
                Text("All done!")
            }
            ActionButton(
                text = "Load more",
                onClick = {
                    component.onEvent(SearchUiEvent.LoadMore)
                }
            )
        }
    }

}