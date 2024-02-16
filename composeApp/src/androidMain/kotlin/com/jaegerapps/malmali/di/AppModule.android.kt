package com.jaegerapps.malmali.di

import VocabularySetSourceFunctions
import android.content.Context
import android.content.SharedPreferences
import com.jaegerapps.malmali.composeApp.database.MalMalIDatabase
import com.jaegerapps.malmali.grammar.data.GrammarRepoImpl
import com.jaegerapps.malmali.grammar.domain.GrammarRepo
import com.jaegerapps.malmali.login.data.SignInRepoImpl
import com.jaegerapps.malmali.login.domain.SignInRepo
import com.jaegerapps.malmali.vocabulary.data.VocabularySetSourceFunctionsImpl
import com.russhwolf.settings.SharedPreferencesSettings
import core.data.DatabaseDriverFactory
import core.data.SupabaseSignInFunctionsImpl
import core.data.SupabaseUserFunctionsImpl
import core.data.settings.SettingFunctionsImpl
import core.domain.SettingFunctions
import core.domain.SupabaseSignInFunctions
import core.domain.SupabaseUserFunctions

actual class AppModule(
    private val context: Context,
    private val sharedPreferences: SharedPreferences,
) : AppModuleInterface {

    private val client = core.data.SupabaseClient.client
    actual override val grammarRepo: GrammarRepo by lazy {
        GrammarRepoImpl(
            client = client
        )
    }
    actual override val signInRepo: SignInRepo by lazy {
        SignInRepoImpl(
            settings = settingFunctions,
            signInFunctions = supabaseSignInFunctions
        )
    }
    actual override val vocabFunctions: VocabularySetSourceFunctions by lazy {
        VocabularySetSourceFunctionsImpl(
            database = MalMalIDatabase(
                driver = DatabaseDriverFactory(context).createDriver()
            )
        )
    }
    actual override val settingFunctions: SettingFunctions by lazy {
        SettingFunctionsImpl(
            settings = SharedPreferencesSettings(sharedPreferences)
        )
    }
    actual override val userFunctions: SupabaseUserFunctions by lazy {
        SupabaseUserFunctionsImpl(
            client = client
        )
    }
    actual override val supabaseSignInFunctions: SupabaseSignInFunctions by lazy {
        SupabaseSignInFunctionsImpl(
            client = client
        )
    }

}