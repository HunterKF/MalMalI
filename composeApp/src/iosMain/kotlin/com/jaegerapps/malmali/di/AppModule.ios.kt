package com.jaegerapps.malmali.di

import VocabularySetSourceFunctions
import com.jaegerapps.malmali.chat.data.ChatRepoImpl
import com.jaegerapps.malmali.chat.domain.ChatRepo
import com.jaegerapps.malmali.composeApp.database.MalMalIDatabase
import com.jaegerapps.malmali.grammar.data.GrammarRepoImpl
import com.jaegerapps.malmali.grammar.domain.GrammarRepo
import com.jaegerapps.malmali.login.data.SignInDataSourceImpl
import com.jaegerapps.malmali.login.domain.SignInDataSource
import com.jaegerapps.malmali.practice.data.PracticeDataSourceImpl
import com.jaegerapps.malmali.practice.domain.PracticeDataSource
import com.jaegerapps.malmali.vocabulary.data.VocabularySetSourceFunctionsImpl
import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import core.data.DatabaseDriverFactory
import core.data.supabase.grammar.GrammarDataSourceImpl
import core.data.KtorClient
import core.data.supabase.SupabaseClient
import core.data.supabase.signin.SupabaseSignInFunctionsImpl
import core.data.supabase.account.SupabaseUserFunctionsImpl
import core.data.gpt.ChatGptApiImpl
import core.data.settings.SettingFunctionsImpl
import core.domain.ChatGptApi
import core.domain.supabase.grammar.GrammarDataSource
import core.domain.SettingFunctions
import core.domain.supabase.signin.SupabaseSignInFunctions
import core.domain.supabase.account.SupabaseUserFunctions
import platform.Foundation.NSUserDefaults

actual class AppModule: AppModuleInterface {

    private val supabaseClient = SupabaseClient.client
    private val okHttpClient = KtorClient.client

    private val delegate = NSUserDefaults()
    private val settings: Settings = NSUserDefaultsSettings(delegate)
    actual override val grammarRepo: GrammarRepo by lazy {
        GrammarRepoImpl(
            client = supabaseClient
        )
    }
    private val database = MalMalIDatabase(
        driver = DatabaseDriverFactory().createDriver()
    )
    actual override val signInRepo: SignInDataSource by lazy {
        SignInDataSourceImpl(
            settings = settingFunctions,
            signInFunctions = supabaseSignInFunctions
        )
    }
    actual override val vocabFunctions: VocabularySetSourceFunctions by lazy {
        VocabularySetSourceFunctionsImpl(
            database = database
        )
    }
    actual override val settingFunctions: SettingFunctions by lazy {
        SettingFunctionsImpl(
            settings = settings
        )
    }
    actual override val userFunctions: SupabaseUserFunctions by lazy {
        SupabaseUserFunctionsImpl(
            client = supabaseClient
        )
    }
    actual override val supabaseSignInFunctions: SupabaseSignInFunctions by lazy {
        SupabaseSignInFunctionsImpl(
            client = supabaseClient
        )
    }
    actual override val chatFunctions: ChatRepo by lazy {
        ChatRepoImpl(
            client = supabaseClient,
            api = chatGptApi
        )
    }
    actual override val chatGptApi: ChatGptApi by lazy {
        ChatGptApiImpl(
            client = okHttpClient
        )
    }

    actual override val practiceFunctions: PracticeDataSource by lazy {
        PracticeDataSourceImpl(
            client = supabaseClient,
            database = database
        )
    }

    actual override val grammarFunctions: GrammarDataSource by lazy {
        GrammarDataSourceImpl(
            database = database
        )
    }
}