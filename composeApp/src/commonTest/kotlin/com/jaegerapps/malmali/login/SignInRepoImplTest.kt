package com.jaegerapps.malmali.login

import com.jaegerapps.malmali.login.data.SignInRepoImpl
import com.jaegerapps.malmali.login.domain.SignInRepo
import com.russhwolf.settings.MapSettings
import com.russhwolf.settings.Settings
import io.github.jan.supabase.SupabaseClient
import io.mockative.Mock
import io.mockative.classOf
import io.mockative.mock
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class SignInRepoImplTest {


    @Mock
    val myMock = mock(classOf<SupabaseClient>())


    private lateinit var settings: Settings
    private lateinit var repo: SignInRepo

    @BeforeTest
    fun setup() {
        settings = MapSettings.Factory().create()

        repo = SignInRepoImpl(
            settings = settings,
            supabase = myMock
        )
    }

    @Test
    fun testing() = runBlocking {
        println("client")
        println(myMock)
        println("settings")
        println(settings)
        repo.createUserLocally("hunter.krez@gmail.com", "HunterK300")
        assertEquals("", "")
    }
}