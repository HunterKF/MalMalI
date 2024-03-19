package com.jaegerapps.malmali.practice.practice_settings.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jaegerapps.malmali.common.components.BasicIconContainer
import com.jaegerapps.malmali.practice.practice_settings.presentation.component.SelectLevelContainer
import com.jaegerapps.malmali.practice.practice_settings.presentation.component.SelectSetContainer

@Composable
fun PracticeSettingScreen(
    state: PracticeSettingUiState,
    onEvent: (PracticeSettingsUiEvent) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surface
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxWidth().padding(paddingValues).padding(12.dp)
        ) {
            Box(modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp)) {
                BasicIconContainer(
                    icon = Icons.Rounded.ArrowBack,
                    contentDescription = "return to practice",
                    tint = MaterialTheme.colorScheme.outline,
                    onClick = {

                    }
                )
            }
            SelectLevelContainer(
                modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                levels = state.levels,
                onSelect = { level ->
                    onEvent(PracticeSettingsUiEvent.ToggleLevel(level))
                }
            )
            SelectSetContainer(
                modifier = Modifier.fillMaxWidth(),
                levels = state.sets,
                onSelect = { set ->
                    onEvent(PracticeSettingsUiEvent.ToggleSet(set))
                }
            )
            Divider(Modifier.fillMaxWidth().padding(vertical = 16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Enable translation")
                Switch(
                    state.enableTranslation,
                    onCheckedChange = { value ->
                        onEvent(PracticeSettingsUiEvent.ToggleTranslationOn(value))
                    },
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Enable teacher")
                Switch(state.enableTeacher,
                    onCheckedChange = { value ->
                        onEvent(PracticeSettingsUiEvent.ToggleTeacherOn(value))
                    })
            }
        }
    }
}

