package com.jaegerapps.malmali

import App
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.retainedComponent
import com.jaegerapps.malmali.di.AppModule

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalDecomposeApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreference = getSharedPreferences("USER_SETTINGS", Context.MODE_PRIVATE)

        println("sharedPreference.all")
        println(sharedPreference.all)
        val appModule = AppModule(
            context = applicationContext,
            sharedPreferences = sharedPreference
        )

        val root = retainedComponent { componentContext ->
            RootComponent(componentContext, appModule = appModule)
        }
        setContent {
            App(darkTheme = isSystemInDarkTheme(), root)
        }
    }
}

