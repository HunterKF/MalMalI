package com.jaegerapps.malmali.login.domain

import core.util.Resource

interface SignInRepo {
    suspend fun createUserLocally(email: String, userId: String): Resource<Boolean>
    suspend fun createUserWithGmailExternally(email: String): Resource<UserData>
    suspend fun createUserWithEmailExternally(email: String, password: String): Resource<UserData>
    suspend fun signInWithEmail(email: String, password: String): Resource<UserData>
}