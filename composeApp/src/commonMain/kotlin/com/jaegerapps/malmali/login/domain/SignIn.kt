package com.jaegerapps.malmali.login.domain

import core.util.Resource

interface SignIn {
    suspend fun createUserLocally(email: String, userId: String): Resource<Boolean>
    suspend fun createUserWithGmailExternally(userId: String): Resource<UserData>
    suspend fun createUserWithEmailExternally(email: String, password: String): Resource<UserData>
}