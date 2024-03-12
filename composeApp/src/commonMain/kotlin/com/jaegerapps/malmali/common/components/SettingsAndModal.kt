package com.jaegerapps.malmali.common.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.MenuOpen
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun SettingsAndModal(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.outline,
    onSettingsClick: () -> Unit,
    onModalClick: () -> Unit
) {
    Column {
        Spacer(Modifier.height(12.dp))

        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BasicIconContainer(
                icon = Icons.Rounded.Menu,
                contentDescription = "modal open",
                tint = color,
                onClick = {
                    onModalClick()
                }
            )
            BasicIconContainer(
                icon = Icons.Rounded.Settings,
                contentDescription = "modal open",
                tint = color,
                onClick = {
                    onSettingsClick()
                }
            )
        }
        Spacer(Modifier.height(18.dp))

    }

}

@Composable
private fun BasicIconContainer(
    icon: ImageVector,
    contentDescription: String,
    tint: Color,
    onClick: () -> Unit,
) {
    IconButton(
        modifier = Modifier.blackBorder(color = tint),
        onClick = { onClick()}
    ) {
        Icon(
            icon,
            contentDescription,
            tint = tint
        )
    }
}