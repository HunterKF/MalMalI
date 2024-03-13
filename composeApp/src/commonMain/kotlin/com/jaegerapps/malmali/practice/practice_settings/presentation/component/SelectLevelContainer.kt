package com.jaegerapps.malmali.practice.practice_settings.presentation.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jaegerapps.malmali.common.components.blackBorder
import com.jaegerapps.malmali.common.models.GrammarLevelModel

@Composable
fun SelectLevelContainer(
    modifier: Modifier = Modifier,
    levels: List<GrammarLevelModel>,
    onSelect: () -> Unit,
) {
    Column(modifier) {
        Text("Levels")
        LazyVerticalGrid(
            columns = GridCells.Adaptive(36.dp),
        ) {
            items(levels) { level ->
                val backgroundColor =
                    animateColorAsState(
                        if (level.isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                        tween()
                    )
                val textColor =
                    animateColorAsState(
                        if (level.isSelected) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.outline,
                        tween()
                    )

                Box(
                    modifier = Modifier.padding(vertical = 6.dp, horizontal = 12.dp)
                        .clickable { onSelect() }
                        .background(backgroundColor.value).blackBorder()
                ) {
                    Text(level.title, color = textColor.value)
                }
            }
        }
    }
}