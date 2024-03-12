package com.jaegerapps.malmali.grammar.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jaegerapps.malmali.common.models.GrammarLevelModel

@Composable
fun GrammarListContainer(
    modifier: Modifier = Modifier,
    isEditingMode: Boolean,
    levels: List<GrammarLevelModel>,
    onSelect: (GrammarLevelModel) -> Unit
) {

}