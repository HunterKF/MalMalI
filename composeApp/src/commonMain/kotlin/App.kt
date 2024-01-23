import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.jaegerapps.malmali.navigation.RootComponent
import com.jaegerapps.malmali.vocabulary.create_set.presentation.CreateSetScreen
import com.jaegerapps.malmali.screen_roots.ScreenA
import com.jaegerapps.malmali.screen_roots.ScreenB
import com.jaegerapps.malmali.vocabulary.folders.presentation.FolderScreen
import com.jaegerapps.malmali.vocabulary.study_flashcards.StudyFlashcardsScreen
import core.presentation.MalMalITheme
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App(
    darkTheme: Boolean,
    root: RootComponent,
) {
    MalMalITheme(
        darkTheme
    ) {
        var showContent by remember { mutableStateOf(false) }
        val greeting = remember { Greeting().greet() }

        val childStack by root.childStack.subscribeAsState()

        Children(
            stack = childStack,
            animation = stackAnimation(slide())
        ) { child ->
            when (val instance = child.instance) {
                is RootComponent.Child.ScreenA -> ScreenA(component = instance.component)
                is RootComponent.Child.ScreenB -> ScreenB(component = instance.component)
                is RootComponent.Child.CreateSetScreen -> CreateSetScreen(component = instance.component)
                is RootComponent.Child.FlashcardHomeScreen -> FolderScreen(component = instance.component)
                is RootComponent.Child.StudyFlashcardsScreen -> StudyFlashcardsScreen(component = instance.component)
            }

        }
//        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
//            Button(onClick = { showContent = !showContent }) {
//                Text("Click me!")
//            }
//            Button(onClick = { viewModel.onEvent(HomeUiEvent.CreateUser("HunterK300")) }) {
//                Text("Create name")
//            }
//            Button(onClick = { viewModel.onEvent(HomeUiEvent.GetUserInfo)}) {
//                Text("Get name")
//            }
//            Button(onClick = { database.insertPlayer()}) {
//                Text("Insert player")
//            }
//            Button(onClick = { database.getAllPlayers()}) {
//                Text("Get all players")
//            }
//
//            Text(stringResource(MR.strings.hello))
//            AnimatedVisibility(showContent) {
//                Column(
//                    Modifier.fillMaxWidth(),
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Image(painterResource("compose-multiplatform.xml"), null)
//                    Text("Compose: $greeting")
//                }
//            }
//        }
    }
}