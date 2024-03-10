package com.jaegerapps.malmali.grammar.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jaegerapps.malmali.components.blackBorder

@Composable
fun LevelHeader(
    modifier: Modifier = Modifier,
    title: String,
    isEditing: Boolean = false,
    isSelected: Boolean = false,
    isUnlocked: Boolean = true,
    background: Color = MaterialTheme.colorScheme.primary,
    selectedBackground: Color = MaterialTheme.colorScheme.primary,
    deselectedBackground: Color = MaterialTheme.colorScheme.secondary,
    onExpandClick: () -> Unit,
    onSelectClick: () -> Unit,
) {
    val animateFloat by animateFloatAsState(
        targetValue = if (isEditing && isSelected) 1f else 0.9f,
        animationSpec = tween(200)
    )
    val animateColor by animateColorAsState(
        if (isSelected) {
            background
        } else {
            Color.Gray
        }
    )
    Row(
        modifier = modifier.fillMaxWidth().height(IntrinsicSize.Min).scale(if (isEditing && !isSelected) animateFloat else 1f).blackBorder()
            .background(animateColor),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.clickable {
                if (isEditing) onSelectClick() else {
                    onExpandClick()
                }
            }.weight(1f)
                .padding(vertical = 8.dp, horizontal = 12.dp),
        ) {
            Text(
                text = title,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}