package com.jaegerapps.malmali.screen_roots

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.jaegerapps.malmali.navigation.ScreenAComponent
import com.jaegerapps.malmali.navigation.ScreenAEvent

@Composable
fun ScreenA(
    component: ScreenAComponent,
) {
    val text by component.text.subscribeAsState()
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Screen A")
        OutlinedTextField(
            value = text,
            onValueChange = { component.onEvent(ScreenAEvent.UpdateText(it))},
            modifier = Modifier.fillMaxWidth().padding(12.dp)
        )
        Button(
            onClick = { component.onEvent(ScreenAEvent.ClickButton)}
        ) {
            Text("Go to screen B")
        }
        Button(
            onClick = { component.onEvent(ScreenAEvent.ClickCreateScreen)}
        ) {
            Text("Go to Create Set")
        }
        Button(
            onClick = { component.onEvent(ScreenAEvent.ClickFlashcardHomeScreen)}
        ) {
            Text("Go to Vocab Home")
        }
    }
}