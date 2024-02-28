package com.jaegerapps.malmali.di

import VocabularySetSourceFunctions
import android.content.Context
import android.content.SharedPreferences
import com.jaegerapps.malmali.chat.data.ChatRepoImpl
import com.jaegerapps.malmali.chat.domain.ChatRepo
import com.jaegerapps.malmali.composeApp.database.MalMalIDatabase
import com.jaegerapps.malmali.grammar.data.RootComponentUseCasesImpl
import com.jaegerapps.malmali.grammar.domain.RootComponentUseCases
import com.jaegerapps.malmali.login.data.SignInDataSourceImpl
import com.jaegerapps.malmali.login.domain.SignInDataSource
import com.jaegerapps.malmali.practice.data.PracticeDataSourceImpl
import com.jaegerapps.malmali.practice.domain.PracticeDataSource
import com.jaegerapps.malmali.vocabulary.data.VocabularySetSourceFunctionsImpl
import com.russhwolf.settings.SharedPreferencesSettings
import core.data.DatabaseDriverFactory
import core.data.KtorClient
import core.data.supabase.signin.SupabaseSignInFunctionsImpl
import core.data.supabase.account.SupabaseUserFunctionsImpl
import core.data.gpt.ChatGptApiImpl
import core.data.settings.SettingFunctionsImpl
import core.data.supabase.SupabaseClient
import core.domain.ChatGptApi
import core.domain.SettingFunctions
import core.domain.supabase.signin.SupabaseSignInFunctions
import core.domain.supabase.account.SupabaseUserFunctions

actual class AppModule(
    private val context: Context,
    private val sharedPreferences: SharedPreferences,
) : AppModuleInterface {

    private val supabaseClient = SupabaseClient.client
    private val okHttpClient = KtorClient.client
    private val database = MalMalIDatabase(
        driver = DatabaseDriverFactory(context).createDriver()
    )


    actual override val rootComponentUseCases: RootComponentUseCases by lazy {
        RootComponentUseCasesImpl(client = supabaseClient)
    }
    actual override val signInRepo: SignInDataSource by lazy {
        SignInDataSourceImpl(
            settings = settingFunctions,
            signInFunctions = supabaseSignInFunctions
        )
    }
    actual override val vocabFunctions: VocabularySetSourceFunctions by lazy {
        VocabularySetSourceFunctionsImpl(
            client = supabaseClient
        )
    }
    actual override val settingFunctions: SettingFunctions by lazy {
        SettingFunctionsImpl(
            settings = SharedPreferencesSettings(sharedPreferences)
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
}