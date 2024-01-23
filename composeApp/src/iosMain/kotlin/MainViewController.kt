import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.jaegerapps.malmali.composeApp.database.MalMalIDatabase
import com.jaegerapps.malmali.navigation.RootComponent
import com.jaegerapps.malmali.vocabulary.data.VocabularySetSourceFunctionsImpl
import core.data.DatabaseDriverFactory
import platform.UIKit.UIScreen
import platform.UIKit.UIUserInterfaceStyle

val database = MalMalIDatabase(DatabaseDriverFactory().createDriver())
val vocabFunctions = VocabularySetSourceFunctionsImpl(database)

fun MainViewController() = ComposeUIViewController {
    val isDarkTheme =
        UIScreen.mainScreen.traitCollection.userInterfaceStyle ==
                UIUserInterfaceStyle.UIUserInterfaceStyleDark

    val root = remember {
        RootComponent(DefaultComponentContext(LifecycleRegistry()), vocabFunctions)
    }

    App(darkTheme = isDarkTheme, root)
}
