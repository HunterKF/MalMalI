package com.jaegerapps.malmali.di

import VocabularySetSourceFunctions
import com.jaegerapps.malmali.composeApp.database.MalMalIDatabase
import com.jaegerapps.malmali.grammar.data.GrammarRepoImpl
import com.jaegerapps.malmali.grammar.domain.GrammarRepo
import com.jaegerapps.malmali.login.data.SignInRepoImpl
import com.jaegerapps.malmali.login.domain.SignInRepo
import com.jaegerapps.malmali.vocabulary.data.VocabularySetSourceFunctionsImpl
import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import core.data.DatabaseDriverFactory
import core.data.SupabaseClientFactory
import core.data.SupabaseSignInFunctionsImpl
import core.data.SupabaseUserFunctionsImpl
import core.data.settings.SettingFunctionsImpl
import core.domain.SettingFunctions
import core.domain.SupabaseSignInFunctions
import core.domain.SupabaseUserFunctions
import platform.Foundation.NSUserDefaults

actual class AppModule: AppModuleInterface {

    val client = SupabaseClientFactory().createBase()
    private val delegate = NSUserDefaults()
    private val settings: Settings = NSUserDefaultsSettings(delegate)
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
                driver = DatabaseDriverFactory().createDriver()
            )
        )
    }
    actual override val settingFunctions: SettingFunctions by lazy {
        SettingFunctionsImpl(
            settings = settings
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