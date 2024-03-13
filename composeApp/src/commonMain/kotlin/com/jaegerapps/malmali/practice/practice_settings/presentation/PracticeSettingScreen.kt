package com.jaegerapps.malmali.practice.practice_settings.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jaegerapps.malmali.common.components.BasicIconContainer

@Composable
fun PracticeSettingScreen() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(it).padding(horizontal = 12.dp)) {
            Box(modifier = Modifier.fillMaxWidth()) {
                BasicIconContainer(
                    icon = Icons.Rounded.Menu,
                    contentDescription = "modal open",
                    tint = MaterialTheme.colorScheme.outline,
                    onClick = {

                    }
                )
            }

        }
    }
}

