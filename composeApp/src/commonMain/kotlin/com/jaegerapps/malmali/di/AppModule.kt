package com.jaegerapps.malmali.di

import VocabularySetSourceFunctions
import com.jaegerapps.malmali.chat.domain.ChatRepo
import com.jaegerapps.malmali.grammar.domain.RootComponentUseCases
import com.jaegerapps.malmali.login.domain.SignInDataSource
import com.jaegerapps.malmali.practice.domain.PracticeDataSource
import core.domain.ChatGptApi
import core.domain.SettingFunctions
import core.domain.supabase.signin.SupabaseSignInFunctions
import core.domain.supabase.account.SupabaseUserFunctions

expect class AppModule: AppModuleInterface {

    //Used in grammar screen
    override val rootComponentUseCases: RootComponentUseCases
    //Used in sign in screen
    override val signInRepo: SignInDataSource
    //Used in vocabulary screen
    override  val vocabFunctions: VocabularySetSourceFunctions
    //All functions to do with the settings
    override val settingFunctions: SettingFunctions
    //All user supabase functions
    override  val userFunctions: SupabaseUserFunctions
    //Sign In Functions
    override  val supabaseSignInFunctions: SupabaseSignInFunctions
    //Chat functions
    override  val chatFunctions: ChatRepo
    //Chat Gpt functions
    override val chatGptApi: ChatGptApi
    //Practice functions
    override val practiceFunctions: PracticeDataSource

}

interface AppModuleInterface {
    //Used in grammar screen
    val rootComponentUseCases: RootComponentUseCases
    //Used in sign in screen
    val signInRepo: SignInDataSource
    //Used in vocabulary screen
    val vocabFunctions: VocabularySetSourceFunctions
    //All functions to do with the settings
    val settingFunctions: SettingFunctions
    //All user supabase functions
    val userFunctions: SupabaseUserFunctions
    //Sign In Functions
    val supabaseSignInFunctions: SupabaseSignInFunctions
    //Chat functions
    val chatFunctions: ChatRepo
    //Chat Gpt functions
    val chatGptApi: ChatGptApi
    //Practice functions
    val practiceFunctions: PracticeDataSource

}