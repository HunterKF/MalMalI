package com.jaegerapps.malmali.di

import VocabularySetSourceFunctions
import com.jaegerapps.malmali.grammar.domain.GrammarRepo
import com.jaegerapps.malmali.login.domain.SignInRepo
import core.domain.SettingFunctions
import core.domain.SupabaseSignInFunctions
import core.domain.SupabaseUserFunctions
import io.github.jan.supabase.SupabaseClient

expect class AppModule: AppModuleInterface {

    //Used in grammar screen
    override val grammarRepo: GrammarRepo
    //Used in sign in screen
    override val signInRepo: SignInRepo
    //Used in vocabulary screen
    override  val vocabFunctions: VocabularySetSourceFunctions
    //All functions to do with the settings
    override val settingFunctions: SettingFunctions
    //All user supabase functions
    override  val userFunctions: SupabaseUserFunctions
    //Sign In Functions
    override  val supabaseSignInFunctions: SupabaseSignInFunctions
}

interface AppModuleInterface {
    //Used in grammar screen
    val grammarRepo: GrammarRepo
    //Used in sign in screen
    val signInRepo: SignInRepo
    //Used in vocabulary screen
    val vocabFunctions: VocabularySetSourceFunctions
    //All functions to do with the settings
    val settingFunctions: SettingFunctions
    //All user supabase functions
    val userFunctions: SupabaseUserFunctions
    //Sign In Functions
    val supabaseSignInFunctions: SupabaseSignInFunctions
}