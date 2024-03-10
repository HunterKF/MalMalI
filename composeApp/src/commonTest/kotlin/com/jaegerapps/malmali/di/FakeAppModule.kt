package com.jaegerapps.malmali.di

import com.jaegerapps.malmali.vocabulary.domain.repo.VocabularyRepo
import com.jaegerapps.malmali.chat.domain.ChatRepo
import com.jaegerapps.malmali.data.FakeRootComponentUseCases
import com.jaegerapps.malmali.data.FakeSettingsDataSource
import com.jaegerapps.malmali.data.FakeSignInRepo
import com.jaegerapps.malmali.data.FakeSupabaseSignInFunctions
import com.jaegerapps.malmali.data.FakeUserRepo
import com.jaegerapps.malmali.data.FakeVocabularyRepo
import com.jaegerapps.malmali.RootComponentUseCases
import com.jaegerapps.malmali.grammar.data.local.GrammarLocalDataSourceSettings
import com.jaegerapps.malmali.grammar.domain.repo.GrammarRepo
import com.jaegerapps.malmali.login.domain.SignInDataSource
import com.jaegerapps.malmali.practice.data.local.PracticeLocalDataSource
import com.jaegerapps.malmali.practice.data.rempote.PracticeRemoteDataSource
import com.jaegerapps.malmali.practice.domain.repo.PracticeRepo
import com.jaegerapps.malmali.vocabulary.data.local.VocabularyLocalDataSource
import com.jaegerapps.malmali.vocabulary.data.remote.VocabularyRemoteDataSource
import core.domain.ChatGptApi
import core.domain.SettingsDataSource
import core.domain.supabase.signin.SignInRepo
import core.domain.supabase.account.UserRepo

class FakeAppModule: AppModuleInterface {

    override val rootComponentUseCases: RootComponentUseCases = FakeRootComponentUseCases()
    override val signInRepo: SignInDataSource = FakeSignInRepo()
    override val vocabularyRepo: VocabularyRepo = FakeVocabularyRepo()
    override val settingsDataSource: SettingsDataSource = FakeSettingsDataSource()
    override val vocabularyRemoteDataSource: VocabularyRemoteDataSource
        get() = TODO("Not yet implemented")
    override val vocabularyLocalDataSource: VocabularyLocalDataSource
        get() = TODO("Not yet implemented")
    override val practiceRemoteDataSource: PracticeRemoteDataSource
        get() = TODO("Not yet implemented")
    override val practiceLocalDataSource: PracticeLocalDataSource
        get() = TODO("Not yet implemented")
    override val grammarLocalDataSourceSettings: GrammarLocalDataSourceSettings
        get() = TODO("Not yet implemented")
    override val userRepo: UserRepo = FakeUserRepo()
    override val supabaseSignInFunctions: SignInRepo = FakeSupabaseSignInFunctions()
    override val chatRepo: ChatRepo
        get() = TODO("Not yet implemented")
    override val chatGptApi: ChatGptApi
        get() = TODO("Not yet implemented")
    override val practiceRepo: PracticeRepo
        get() = TODO("Not yet implemented")
    override val grammarRepo: GrammarRepo
        get() = TODO("Not yet implemented")
}