import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.jaegerapps.malmali.composeApp.database.MalMalIDatabase
import com.jaegerapps.malmali.di.AppModule
import com.jaegerapps.malmali.grammar.data.GrammarRepoImpl
import com.jaegerapps.malmali.navigation.RootComponent
import com.jaegerapps.malmali.vocabulary.data.VocabularySetSourceFunctionsImpl
import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import core.data.DatabaseDriverFactory
import core.data.SupabaseClientFactory
import platform.Foundation.NSUserDefaults
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
