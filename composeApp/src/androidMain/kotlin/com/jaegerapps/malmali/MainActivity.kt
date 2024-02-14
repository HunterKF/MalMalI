package com.jaegerapps.malmali

import App
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.platform.LocalContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.retainedComponent
import com.jaegerapps.malmali.composeApp.database.MalMalIDatabase
import com.jaegerapps.malmali.di.AppModule
import com.jaegerapps.malmali.vocabulary.data.VocabularySetSourceFunctionsImpl
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import core.data.DatabaseDriverFactory

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalDecomposeApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val database =
                MalMalIDatabase(DatabaseDriverFactory(LocalContext.current).createDriver())
            val vocabFunctions = VocabularySetSourceFunctionsImpl(database)
            val sharedPreference = getSharedPreferences("USER_SETTINGS", Context.MODE_PRIVATE)
            val settings: Settings = SharedPreferencesSettings(sharedPreference)
            val appModule = AppModule(
                context = LocalContext.current.applicationContext,
                sharedPreferences = sharedPreference
            )
            val root = retainedComponent { componentContext ->
                RootComponent(componentContext, appModule = appModule)
            }
            App(darkTheme = isSystemInDarkTheme(), root)
        }
    }
}

