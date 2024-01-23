package com.jaegerapps.malmali.screen_roots

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jaegerapps.malmali.navigation.ScreenBComponent

@Composable
fun ScreenB(
    component: ScreenBComponent,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Screen B")
        Text(component.text)
        Button(
            onClick = { component.goBack()}
        ) {
            Text("Go to screen B")
        }
    }
}