package com.jaegerapps.malmali.grammar.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jaegerapps.malmali.components.ActionButton
import com.jaegerapps.malmali.grammar.models.GrammarLevel

@Composable
fun GrammarContainer(
    modifier: Modifier = Modifier,
    grammarLevel: GrammarLevel,
    isEditingMode: Boolean,
    isExpanded: Boolean,
    onExpandClick: () -> Unit,
    onSelectClick: () -> Unit
) {
    Column(modifier = modifier.fillMaxWidth().animateContentSize()) {
        GrammarLevelContainer(
            title = grammarLevel.title,
            isEditing = isEditingMode,
            isUnlocked = grammarLevel.isUnlocked,
            onExpandClick = {
                onExpandClick()
            },
            onSelectClick = {
                //selects everything in the grammar container
                onSelectClick()
            }
        )
        if (isExpanded) {
            Spacer(Modifier.height(12.dp))
            Column(
                modifier = Modifier.padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                grammarLevel.grammarList.forEach {
                    var pointExpanded by remember {
                        mutableStateOf(false)
                    }
                    GrammarPointContainer(
                        title = it.grammarTitle,
                        definition1 = it.grammarDef1,
                        definition2 = it.grammarDef2,
                        exampleEn1 = it.exampleEn1,
                        exampleEn2 = it.exampleEn2,
                        exampleKo1 = it.exampleKo1,
                        exampleKo2 = it.exampleKo2,
                        isUnlocked = grammarLevel.isUnlocked,
                        isEditing = isEditingMode,
                        isExpanded = pointExpanded,
                        onExpandClick = {
                            pointExpanded = !pointExpanded
                        },
                        onSelectClick = {
                            //selects only the current grammar point

                        }
                    )
                }
                if (!grammarLevel.isUnlocked) {
                    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        ActionButton(

                            text = "Unlock ${grammarLevel.title}",
                            onClick = {

                            }
                        )
                    }

                }

            }
        }
    }
}