package com.jaegerapps.malmali.grammar.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jaegerapps.malmali.grammar.domain.GrammarLevel

@Composable
fun GrammarListContainer(
    modifier: Modifier = Modifier,
    isEditingMode: Boolean,
    levels: List<GrammarLevel>
) {
    var expanded by remember {
        mutableStateOf(0)
    }
   LazyColumn(
       modifier = modifier,
       verticalArrangement = Arrangement.spacedBy(12.dp)
   ) {
       itemsIndexed(levels) { index, level ->

           GrammarContainer(
               grammarLevel = level,
               isExpanded = index == expanded,
               isEditingMode = isEditingMode,
               onExpandClick = {
                   if (expanded != index) {
                       expanded = index
                   } else {
                       expanded = -1
                   }
               },
               onSelectClick = {

               }
           )
       }
       item {
           Spacer(Modifier.height(56.dp))
       }

   }
}