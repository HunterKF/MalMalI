package com.jaegerapps.malmali

import App
import VocabularySetSourceFunctions
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.platform.LocalContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.retainedComponent
import com.jaegerapps.malmali.composeApp.database.MalMalIDatabase
import com.jaegerapps.malmali.navigation.RootComponent
import com.jaegerapps.malmali.vocabulary.data.VocabularySetSourceFunctionsImpl
import core.data.DatabaseDriverFactory

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalDecomposeApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val database = MalMalIDatabase(DatabaseDriverFactory(LocalContext.current).createDriver())
            val vocabFunctions = VocabularySetSourceFunctionsImpl(database)
            val root = retainedComponent {
                RootComponent(it, vocabFunctions)
            }
            App(darkTheme = isSystemInDarkTheme(), root)
        }
    }
}

