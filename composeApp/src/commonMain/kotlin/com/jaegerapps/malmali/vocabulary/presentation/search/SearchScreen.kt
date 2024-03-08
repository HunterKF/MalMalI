package com.jaegerapps.malmali.vocabulary.presentation.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.jaegerapps.malmali.components.ActionButton

@Composable
fun SearchScreen(
    component: SearchSetComponent
) {
    val state = component.state.collectAsState()
    Column {
        OutlinedTextField(
            value = state.value.searchText,
            onValueChange = {
                component.onEvent(SearchUiEvent.OnSearchTextValueChange(it))
            },
            placeholder = {
                Text(
                    text = "Search..."
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        component.onEvent(SearchUiEvent.SearchByTitle)
                    }
                ) {
                    Icon(
                        Icons.Default.Search,
                        "search"
                    )
                }
            },
            singleLine = true
        )
        if (state.value.loading) {
            CircularProgressIndicator()
        } else {
            LazyColumn {
                item {
                    ActionButton(
                        text = "Search",
                        onClick = {
                            component.onEvent(SearchUiEvent.SearchByTitle)
                        }
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
    }

}