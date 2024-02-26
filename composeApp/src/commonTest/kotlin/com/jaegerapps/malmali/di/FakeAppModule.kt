package com.jaegerapps.malmali.di

import VocabularySetSourceFunctions
import com.jaegerapps.malmali.chat.domain.ChatRepo
import com.jaegerapps.malmali.data.FakeGrammarRepo
import com.jaegerapps.malmali.data.FakeSettingFunctions
import com.jaegerapps.malmali.data.FakeSignInRepo
import com.jaegerapps.malmali.data.FakeSupabaseSignInFunctions
import com.jaegerapps.malmali.data.FakeSupabaseUserFunctions
import com.jaegerapps.malmali.data.FakeVocabularySetSourceFunctions
import com.jaegerapps.malmali.grammar.domain.GrammarRepo
import com.jaegerapps.malmali.login.domain.SignInDataSource
import core.domain.ChatGptApi
import core.domain.SettingFunctions
import core.domain.SupabaseSignInFunctions
import core.domain.SupabaseUserFunctions

class FakeAppModule: AppModuleInterface {

    override val grammarRepo: GrammarRepo = FakeGrammarRepo()
    override val signInRepo: SignInDataSource = FakeSignInRepo()
    override val vocabFunctions: VocabularySetSourceFunctions = FakeVocabularySetSourceFunctions()
    override val settingFunctions: SettingFunctions = FakeSettingFunctions()
    override val userFunctions: SupabaseUserFunctions = FakeSupabaseUserFunctions()
    override val supabaseSignInFunctions: SupabaseSignInFunctions = FakeSupabaseSignInFunctions()
    override val chatFunctions: ChatRepo
        get() = TODO("Not yet implemented")
    override val chatGptApi: ChatGptApi
        get() = TODO("Not yet implemented")
}