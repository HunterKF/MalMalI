package com.jaegerapps.malmali.practice.practice.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp

@Composable
fun ContainerDropClickable(
    modifier: Modifier = Modifier,
    title: String,
    isExpanded: Boolean = false,
    onClick: () -> Unit
) {
    val animateFloat by animateFloatAsState(if (!isExpanded) 0f else 180f, tween())
    TextButton(
        onClick = {
            onClick()
        }
    ) {

        Row(modifier.clip(RoundedCornerShape(25.dp)).clickable { onClick() }.padding(horizontal = 8.dp, vertical = 4.dp)) {
            Text(title)
            Spacer(modifier = Modifier.width(6.dp))
            Icon(
                modifier = Modifier.rotate(animateFloat),
                imageVector = Icons.Outlined.ArrowDropDown,
                contentDescription = null
            )
        }
    }
}