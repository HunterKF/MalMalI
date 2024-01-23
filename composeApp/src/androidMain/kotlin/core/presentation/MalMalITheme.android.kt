package core.presentation

import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.jaegerapps.malmali.ui.theme.DarkColorScheme
import com.jaegerapps.malmali.ui.theme.LightColorScheme
import com.jaegerapps.malmali.ui.theme.Typography


@Composable
actual fun MalMalITheme(darkTheme: Boolean, content: @Composable () -> Unit) {
    val colorScheme =  if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}