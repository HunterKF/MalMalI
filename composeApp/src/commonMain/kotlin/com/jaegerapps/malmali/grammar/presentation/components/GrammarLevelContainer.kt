package com.jaegerapps.malmali.grammar.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jaegerapps.malmali.components.ActionButton
import com.jaegerapps.malmali.components.blackBorder

@Composable
fun GrammarLevelContainer(
    modifier: Modifier = Modifier,
    title: String,
    isEditing: Boolean = false,
    isSelected: Boolean = false,
    isUnlocked: Boolean = false,
    background: Color = MaterialTheme.colorScheme.primary,
    selectedBackground: Color = MaterialTheme.colorScheme.primary,
    deselectedBackground: Color = MaterialTheme.colorScheme.secondary,
    onExpandClick: () -> Unit,
    onSelectClick: () -> Unit,
) {
    val animateColor by animateColorAsState(
        if (isUnlocked) {
            background
        } else {
            Color.Gray
        }
    )
    Row(
        modifier = modifier.fillMaxWidth().height(IntrinsicSize.Min).blackBorder()
            .background(animateColor),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.clickable { onExpandClick() }.weight(1f)
                .padding(vertical = 8.dp, horizontal = 12.dp),
        ) {
            Text(
                text = title,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        if (isEditing && isUnlocked) {
            Box(
                modifier = Modifier.clickable { onSelectClick() }.aspectRatio(1f).fillMaxHeight()
                    .blackBorder().background(
                    if (isSelected) {
                        selectedBackground
                    } else {
                        deselectedBackground
                    }
                ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isSelected) Icons.Rounded.Check else Icons.Rounded.Cancel,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

    }
}