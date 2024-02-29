package com.jaegerapps.malmali.di

import com.jaegerapps.malmali.vocabulary.domain.repo.VocabularyRepo
import com.jaegerapps.malmali.chat.domain.ChatRepo
import com.jaegerapps.malmali.data.FakeRootComponentUseCases
import com.jaegerapps.malmali.data.FakeSettingsDataSource
import com.jaegerapps.malmali.data.FakeSignInRepo
import com.jaegerapps.malmali.data.FakeSupabaseSignInFunctions
import com.jaegerapps.malmali.data.FakeUserRepo
import com.jaegerapps.malmali.data.FakeVocabularyRepo
import com.jaegerapps.malmali.grammar.domain.RootComponentUseCases
import com.jaegerapps.malmali.login.domain.SignInDataSource
import com.jaegerapps.malmali.practice.domain.repo.PracticeRepo
import core.domain.ChatGptApi
import core.domain.SettingsDataSource
import core.domain.supabase.signin.SignInRepo
import core.domain.supabase.account.UserRepo

class FakeAppModule: AppModuleInterface {

    override val rootComponentUseCases: RootComponentUseCases = FakeRootComponentUseCases()
    override val signInRepo: SignInDataSource = FakeSignInRepo()
    override val vocabularyRepo: VocabularyRepo = FakeVocabularyRepo()
    override val settingsDataSource: SettingsDataSource = FakeSettingsDataSource()
    override val userRepo: UserRepo = FakeUserRepo()
    override val supabaseSignInFunctions: SignInRepo = FakeSupabaseSignInFunctions()
    override val chatRepo: ChatRepo
        get() = TODO("Not yet implemented")
    override val chatGptApi: ChatGptApi
        get() = TODO("Not yet implemented")
    override val practiceRepo: PracticeRepo
        get() = TODO("Not yet implemented")
}