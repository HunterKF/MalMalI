package com.jaegerapps.malmali.onboarding.completion

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jaegerapps.malmali.common.components.ActionButton

@Composable
fun CompletionScreen(
    component: CompletionComponent
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "All set!"
        )
        Spacer(Modifier.height(24.dp))
        Text(
            text = "You're all set to further your Korean journey. Good luck!"
        )
        ActionButton(
            onClick = {
                component.onEvent(CompletionUiEvent.OnNavigate)
            },
            text = "Complete"
        )
    }
}