package com.jaegerapps.malmali.vocabulary.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowCircleLeft
import androidx.compose.material.icons.rounded.ArrowLeft
import androidx.compose.material.icons.rounded.ArrowUpward
import androidx.compose.material.icons.rounded.Expand
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

@Composable
fun MultiFloatingActionButtons(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.outline,
    foregroundColor: Color = MaterialTheme.colorScheme.onPrimary,
    isExpanded: Boolean,
    onSearchClick: () -> Unit,
    onExpandClick: () -> Unit,
    onAddClick: () -> Unit
) {
    val animateRotation by animateFloatAsState(
        if (isExpanded) {
            270f
        } else {
            90f
        }
    )
    val animatedSizeAction by animateDpAsState(
        if (isExpanded) {
            48.dp
        } else {
            36.dp
        }
    )
    val animatedSizeFloat by animateDpAsState(
        if (!isExpanded) {
            48.dp
        } else {
            36.dp
        }
    )
    val animatedOffset by animateDpAsState(
        if (isExpanded) {
            0.dp
        } else {
            (90).dp
        }
    )
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End
    ) {
        IconButton(
            modifier = Modifier.offset(x = animatedOffset, 0.dp).size(48.dp).clip(CircleShape),
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = backgroundColor,
                contentColor = foregroundColor
            ),
            onClick = {
                onAddClick()
            }
        ) {
            Icon(
                Icons.Rounded.Add,
                null
            )
        }
        Spacer(Modifier.height(12.dp))

        IconButton(
            modifier = Modifier.offset(x = animatedOffset, 0.dp).size(48.dp).clip(CircleShape),
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = backgroundColor,
                contentColor = foregroundColor
            ),
            onClick = {
                onSearchClick()
            }
        ) {
            Icon(
                Icons.Rounded.Search,
                null
            )
        }
        Spacer(Modifier.height(12.dp))

        IconButton(
            modifier = Modifier.clip(CircleShape).size(48.dp),
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = backgroundColor,
                contentColor = foregroundColor
            ),
            onClick = {
                onExpandClick()
            }
        ) {
            Icon(
                Icons.Rounded.ExpandMore,
                null,
                modifier = Modifier.rotate(animateRotation)
            )
        }


    }

}