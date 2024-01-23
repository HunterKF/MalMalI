package core.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.jaegerapps.malmali.ui.theme.DarkColorScheme
import com.jaegerapps.malmali.ui.theme.LightColorScheme

@Composable
expect fun MalMalITheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
)