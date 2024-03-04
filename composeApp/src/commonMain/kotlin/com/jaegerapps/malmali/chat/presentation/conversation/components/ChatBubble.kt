package com.jaegerapps.malmali.chat.presentation.conversation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jaegerapps.malmali.components.blackBorder

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChatBubble(
    modifier: Modifier = Modifier,
    text: String,
    isUser: Boolean,
    showOptions: Boolean,
    onClick: () -> Unit,
) {

    Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isUser) {
                Spacer(modifier = Modifier.weight(0.1f))
            }
            Box(
                modifier = modifier.weight(1f).blackBorder().clickable {
                    onClick()
                }.padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Text(
                    text = text,
                )

            }
            if (!isUser) {
                Spacer(modifier = Modifier.weight(0.1f))
            }

        }
        AnimatedVisibility(showOptions) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                if (isUser) {
                    Spacer(modifier = Modifier.weight(0.1f))
                }
                ChatPopUpAlert(
                    onFavoriteClick = {},
                    onShareClick = {},
                    onInformationClick = {},
                    onCopyClick = {}
                )
                if (!isUser) {
                    Spacer(modifier = Modifier.weight(0.1f))
                }
            }
        }
    }
}