package com.jaegerapps.malmali.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun TopBarLogo(
//    height: Dp = 36.dp,dp
    content: @Composable () -> Unit
) {
    Column(modifier = Modifier.background(MaterialTheme.colorScheme.onBackground)) {
        Box(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            contentAlignment = Alignment.Center,

        ) {
            content()
        }
        Divider(thickness = 1.dp, color = MaterialTheme.colorScheme.background)
        Spacer(Modifier.height(3.dp))
        Divider(thickness = 1.dp, color = MaterialTheme.colorScheme.background)
        Spacer(Modifier.height(3.dp))


    }

}