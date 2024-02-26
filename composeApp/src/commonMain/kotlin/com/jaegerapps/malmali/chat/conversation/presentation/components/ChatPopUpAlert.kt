package com.jaegerapps.malmali.chat.conversation.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.jaegerapps.malmali.components.blackBorder

@Composable
fun ChatPopUpAlert(
    modifier: Modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp).blackBorder(),
    horizontal: Arrangement.Horizontal = Arrangement.spacedBy(16.dp),
    onFavoriteClick: () -> Unit,
    onShareClick: () -> Unit,
    onInformationClick: () -> Unit,
    onCopyClick: () -> Unit,
) {

    Row(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = horizontal
    ) {
        PopUpIcon(Icons.Default.Favorite, "", {})
        PopUpIcon(Icons.Default.Share, "", {})
        PopUpIcon(Icons.Default.Info, "", {})
        PopUpIcon(Icons.Default.ContentCopy, "", {})
    }
}

@Composable
private fun PopUpIcon(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    tint: Color = MaterialTheme.colorScheme.primary,
) {
    IconButton(
        onClick = {
            onClick()
        }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = tint
        )
    }
}