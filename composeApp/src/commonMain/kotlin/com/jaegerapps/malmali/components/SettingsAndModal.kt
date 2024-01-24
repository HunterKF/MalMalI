package com.jaegerapps.malmali.components

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun SettingsAndModal(
    modifier: Modifier = Modifier,
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
                onClick = {
                    onModalClick()
                }
            )
            BasicIconContainer(
                icon = Icons.Rounded.Settings,
                contentDescription = "modal open",
                onClick = {
                    onSettingsClick()
                }
            )
        }
    }

}

@Composable
private fun BasicIconContainer(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit
) {
    IconButton(
        modifier = Modifier.blackBorder(),
        onClick = { onClick()}
    ) {
        Icon(
            icon,
            contentDescription
        )
    }
}