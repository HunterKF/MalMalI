import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.jaegerapps.malmali.di.AppModule
import com.jaegerapps.malmali.RootComponent
import platform.UIKit.UIScreen
import platform.UIKit.UIUserInterfaceStyle



fun MainViewController() = ComposeUIViewController {
    val isDarkTheme =
        UIScreen.mainScreen.traitCollection.userInterfaceStyle ==
                UIUserInterfaceStyle.UIUserInterfaceStyleDark
    val appModule = AppModule()
    val root = remember {
        RootComponent(
            DefaultComponentContext(LifecycleRegistry()),
            appModule = appModule
        )
    }

    App(darkTheme = isDarkTheme, root)
}
