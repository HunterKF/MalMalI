package core.data

import com.jaegerapps.malmali.BuildKonfig.API_KEY
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.compose.auth.ComposeAuth
import io.github.jan.supabase.compose.auth.googleNativeLogin
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest

class SupabaseClientFactory {
    fun createBase(): SupabaseClient {
        val supabase = createSupabaseClient(
            supabaseUrl = "https://diulepapyuslazawpzli.supabase.co",
            supabaseKey = API_KEY
        ) {
            install(Auth)
            install(Postgrest)
            install(ComposeAuth) {
                googleNativeLogin("259519412723-1lo1qag970kgj0vb8nr8a8g0n4qtrhj7.apps.googleusercontent.com") //Use the Web Client ID, not the Android one!
            }

        }

        return supabase
    }

}