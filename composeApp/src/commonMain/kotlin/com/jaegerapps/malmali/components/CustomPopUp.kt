package com.jaegerapps.malmali.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomPopUp(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    title: String,
    text: String,
    contentText: (@Composable () -> Unit)? = null,
    buttonContent: @Composable () -> Unit,
) {
    AlertDialog(
        onDismissRequest = {
            onDismissRequest()
        },
    ) {
        Box(
            modifier = Modifier.blackBorder()
                .background(MaterialTheme.colorScheme.background).fillMaxWidth()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                Modifier.fillMaxWidth().padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(12.dp))

                Text(text = title, style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(12.dp))

                Divider(Modifier.fillMaxWidth())
                Spacer(Modifier.height(12.dp))
                if (contentText != null) {
                    contentText()
                } else {
                    Text(text)
                }
                Spacer(Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    buttonContent()
                }
            }
        }
    }
}