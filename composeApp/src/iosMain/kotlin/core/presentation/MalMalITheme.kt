package core.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.jaegerapps.malmali.ui.theme.DarkColorScheme
import com.jaegerapps.malmali.ui.theme.LightColorScheme
import com.jaegerapps.malmali.ui.theme.Typography


@Composable
actual fun MalMalITheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if(darkTheme) DarkColorScheme else LightColorScheme,
        typography = Typography,
        content = content
    )
}