package com.jaegerapps.malmali.di

import VocabularySetSourceFunctions
import com.jaegerapps.malmali.data.FakeGrammarRepo
import com.jaegerapps.malmali.data.FakeSettingFunctions
import com.jaegerapps.malmali.data.FakeSignInRepo
import com.jaegerapps.malmali.data.FakeSupabaseSignInFunctions
import com.jaegerapps.malmali.data.FakeSupabaseUserFunctions
import com.jaegerapps.malmali.data.FakeVocabularySetSourceFunctions
import com.jaegerapps.malmali.grammar.domain.GrammarRepo
import com.jaegerapps.malmali.login.domain.SignInRepo
import core.domain.SettingFunctions
import core.domain.SupabaseSignInFunctions
import core.domain.SupabaseUserFunctions
import io.github.jan.supabase.SupabaseClient

class FakeAppModule: AppModuleInterface {

    override val grammarRepo: GrammarRepo = FakeGrammarRepo()
    override val signInRepo: SignInRepo = FakeSignInRepo()
    override val vocabFunctions: VocabularySetSourceFunctions = FakeVocabularySetSourceFunctions()
    override val settingFunctions: SettingFunctions = FakeSettingFunctions()
    override val userFunctions: SupabaseUserFunctions = FakeSupabaseUserFunctions()
    override val supabaseSignInFunctions: SupabaseSignInFunctions = FakeSupabaseSignInFunctions()
}