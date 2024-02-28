package com.jaegerapps.malmali.di

import VocabularySetSourceFunctions
import com.jaegerapps.malmali.chat.domain.ChatRepo
import com.jaegerapps.malmali.data.FakeRootComponentUseCases
import com.jaegerapps.malmali.data.FakeSettingFunctions
import com.jaegerapps.malmali.data.FakeSignInRepo
import com.jaegerapps.malmali.data.FakeSupabaseSignInFunctions
import com.jaegerapps.malmali.data.FakeSupabaseUserFunctions
import com.jaegerapps.malmali.data.FakeVocabularySetSourceFunctions
import com.jaegerapps.malmali.grammar.domain.RootComponentUseCases
import com.jaegerapps.malmali.login.domain.SignInDataSource
import com.jaegerapps.malmali.practice.domain.PracticeDataSource
import core.domain.ChatGptApi
import core.domain.SettingFunctions
import core.domain.supabase.signin.SupabaseSignInFunctions
import core.domain.supabase.account.SupabaseUserFunctions

class FakeAppModule: AppModuleInterface {

    override val rootComponentUseCases: RootComponentUseCases = FakeRootComponentUseCases()
    override val signInRepo: SignInDataSource = FakeSignInRepo()
    override val vocabFunctions: VocabularySetSourceFunctions = FakeVocabularySetSourceFunctions()
    override val settingFunctions: SettingFunctions = FakeSettingFunctions()
    override val userFunctions: SupabaseUserFunctions = FakeSupabaseUserFunctions()
    override val supabaseSignInFunctions: SupabaseSignInFunctions = FakeSupabaseSignInFunctions()
    override val chatFunctions: ChatRepo
        get() = TODO("Not yet implemented")
    override val chatGptApi: ChatGptApi
        get() = TODO("Not yet implemented")
    override val practiceFunctions: PracticeDataSource
        get() = TODO("Not yet implemented")
}