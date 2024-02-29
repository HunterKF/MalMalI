package com.jaegerapps.malmali.di

import com.jaegerapps.malmali.vocabulary.domain.repo.VocabularyRepo
import com.jaegerapps.malmali.chat.domain.ChatRepo
import com.jaegerapps.malmali.grammar.domain.RootComponentUseCases
import com.jaegerapps.malmali.login.domain.SignInDataSource
import com.jaegerapps.malmali.practice.domain.repo.PracticeRepo
import core.domain.ChatGptApi
import core.domain.SettingsDataSource
import core.domain.supabase.signin.SignInRepo
import core.domain.supabase.account.UserRepo

expect class AppModule: AppModuleInterface {

    //Chat Gpt functions
    override val chatGptApi: ChatGptApi
    override val settingsDataSource: SettingsDataSource


    //Used in sign in screen
    override val signInRepo: SignInDataSource
    //Used in vocabulary screen
    override  val vocabularyRepo: VocabularyRepo
    //All functions to do with the settings
    //All user supabase functions
    override  val userRepo: UserRepo
    //Sign In Functions
    override  val supabaseSignInFunctions: SignInRepo
    //Chat functions
    override  val chatRepo: ChatRepo
    //Practice functions
    override val practiceRepo: PracticeRepo


    //Used in grammar screen
    override val rootComponentUseCases: RootComponentUseCases

}

interface AppModuleInterface {
    //Data sources - The data sources are put in manually inside the implementations
    val chatGptApi: ChatGptApi
    val settingsDataSource: SettingsDataSource


    //Used in grammar screen
    val rootComponentUseCases: RootComponentUseCases
    //Used in sign in screen
    val signInRepo: SignInDataSource
    //Used in vocabulary screen
    val vocabularyRepo: VocabularyRepo
    //All functions to do with the settings
    //All user supabase functions
    val userRepo: UserRepo
    //Sign In Functions
    val supabaseSignInFunctions: SignInRepo
    //Chat functions
    val chatRepo: ChatRepo
    //Practice functions
    val practiceRepo: PracticeRepo

}