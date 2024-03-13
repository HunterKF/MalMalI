package com.jaegerapps.malmali.di

import com.jaegerapps.malmali.vocabulary.domain.repo.VocabularyRepo
import android.content.Context
import android.content.SharedPreferences
import com.jaegerapps.malmali.chat.data.ChatRepoImpl
import com.jaegerapps.malmali.chat.domain.ChatRepo
import com.jaegerapps.malmali.composeApp.database.MalMalIDatabase
import com.jaegerapps.malmali.RootComponentUseCasesImpl
import com.jaegerapps.malmali.RootComponentUseCases
import com.jaegerapps.malmali.grammar.data.local.GrammarLocalDataSourceSettings
import com.jaegerapps.malmali.grammar.data.local.GrammarLocalDataSourceSettingsImpl
import com.jaegerapps.malmali.grammar.data.repo.GrammarRepoImpl
import com.jaegerapps.malmali.grammar.domain.repo.GrammarRepo
import com.jaegerapps.malmali.login.data.SignInDataSourceImpl
import com.jaegerapps.malmali.login.domain.SignInDataSource
import com.jaegerapps.malmali.practice.practice_settings.data.local.PracticeSettingLocalDataSourceSettings
import com.jaegerapps.malmali.practice.practice_settings.data.local.PracticeSettingLocalDataSourceSettingsImpl
import com.jaegerapps.malmali.practice.practice_settings.data.local.PracticeSettingLocalDataSourceSql
import com.jaegerapps.malmali.practice.practice_settings.data.local.PracticeSettingLocalDataSourceSqlImpl
import com.jaegerapps.malmali.practice.practice.data.local.PracticeLocalDataSourceSettings
import com.jaegerapps.malmali.practice.practice.data.local.PracticeLocalDataSourceSettingsImpl
import com.jaegerapps.malmali.practice.practice.data.local.PracticeLocalDataSourceSql
import com.jaegerapps.malmali.practice.practice.data.local.PracticeLocalDataSourceSqlImpl
import com.jaegerapps.malmali.practice.practice.data.rempote.PracticeRemoteDataSource
import com.jaegerapps.malmali.practice.practice.data.rempote.PracticeRemoteDataSourceImpl
import com.jaegerapps.malmali.practice.practice.data.repo.PracticeRepoImpl
import com.jaegerapps.malmali.practice.practice.domain.repo.PracticeRepo
import com.jaegerapps.malmali.vocabulary.data.local.VocabularyLocalDataSource
import com.jaegerapps.malmali.vocabulary.data.local.VocabularyLocalDataSourceImpl
import com.jaegerapps.malmali.vocabulary.data.remote.VocabularyRemoteDataSource
import com.jaegerapps.malmali.vocabulary.data.remote.VocabularyRemoteDataSourceImpl
import com.jaegerapps.malmali.vocabulary.data.repo.VocabularyRepoImpl
import com.russhwolf.settings.SharedPreferencesSettings
import core.data.DatabaseDriverFactory
import core.data.KtorClient
import core.data.supabase.signin.SupabaseSignInFunctionsImpl
import core.data.supabase.account.UserRepoImpl
import core.data.gpt.ChatGptApiImpl
import core.data.settings.SettingsDataSourceImpl
import core.data.supabase.SupabaseClient
import core.domain.ChatGptApi
import core.domain.SettingsDataSource
import core.domain.supabase.signin.SignInRepo
import core.domain.supabase.account.UserRepo

actual class AppModule(
    private val context: Context,
    private val sharedPreferences: SharedPreferences,
) : AppModuleInterface {

    private val supabaseClient = SupabaseClient.client
    private val okHttpClient = KtorClient.client
    private val database = MalMalIDatabase(
        driver = DatabaseDriverFactory(context).createDriver()
    )
    private val settings = SharedPreferencesSettings(sharedPreferences)


    actual override val vocabularyRemoteDataSource: VocabularyRemoteDataSource by lazy {
        VocabularyRemoteDataSourceImpl(
            client = supabaseClient
        )
    }
    actual override val vocabularyLocalDataSource: VocabularyLocalDataSource by lazy {
        VocabularyLocalDataSourceImpl(
            database = database
        )
    }

    actual override val practiceRemoteDataSource: PracticeRemoteDataSource by lazy {
        PracticeRemoteDataSourceImpl(
            client = supabaseClient
        )
    }
    actual override val practiceLocalDataSourceSql: PracticeLocalDataSourceSql by lazy {
        PracticeLocalDataSourceSqlImpl(
            database = database
        )
    }
    actual override val practiceLocalDataSourceSettings: PracticeLocalDataSourceSettings by lazy {
        PracticeLocalDataSourceSettingsImpl(
            settings = settings
        )
    }
    actual override val practiceSettingLocalDataSourceSettings: PracticeSettingLocalDataSourceSettings by lazy {
        PracticeSettingLocalDataSourceSettingsImpl(
            settings = settings
        )
    }
    actual override val practiceSettingLocalDataSourceSql: PracticeSettingLocalDataSourceSql by lazy {
        PracticeSettingLocalDataSourceSqlImpl(
            database = database
        )
    }

    actual override val grammarLocalDataSourceSettings: GrammarLocalDataSourceSettings by lazy {
        GrammarLocalDataSourceSettingsImpl(
            settings = settings
        )
    }

    actual override val rootComponentUseCases: RootComponentUseCases by lazy {
        RootComponentUseCasesImpl(client = supabaseClient)
    }
    actual override val signInRepo: SignInDataSource by lazy {
        SignInDataSourceImpl(
            settings = settingsDataSource,
            signInFunctions = supabaseSignInFunctions
        )
    }
    actual override val vocabularyRepo: VocabularyRepo by lazy {
        VocabularyRepoImpl(
            remote = vocabularyRemoteDataSource,
            local = vocabularyLocalDataSource
        )
    }
    actual override val settingsDataSource: SettingsDataSource by lazy {
        SettingsDataSourceImpl(
            settings = settings
        )
    }
    actual override val userRepo: UserRepo by lazy {
        UserRepoImpl(
            client = supabaseClient
        )
    }
    actual override val supabaseSignInFunctions: SignInRepo by lazy {
        SupabaseSignInFunctionsImpl(
            client = supabaseClient
        )
    }

    actual override val chatRepo: ChatRepo by lazy {
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

    actual override val practiceRepo: PracticeRepo by lazy {
        PracticeRepoImpl(
            remote = practiceRemoteDataSource,
            localSql = practiceLocalDataSourceSql,
            localSettings = practiceLocalDataSourceSettings
        )
    }
    actual override val grammarRepo: GrammarRepo by lazy {
        GrammarRepoImpl(
            local = grammarLocalDataSourceSettings
        )
    }
}