package core.data

import com.jaegerapps.malmali.BuildKonfig
import io.github.jan.supabase.compose.auth.ComposeAuth
import io.github.jan.supabase.compose.auth.googleNativeLogin
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest

object SupabaseClient {
    /*Removed the SupabaseFactory because it would recreate a client many times
    * This keeps only a single instance of the factory */
    val client = createSupabaseClient(
        supabaseUrl = "https://diulepapyuslazawpzli.supabase.co",
        supabaseKey = BuildKonfig.API_KEY
    ) {
        install(Auth)
        install(Postgrest)
        install(ComposeAuth) {
            googleNativeLogin("259519412723-1lo1qag970kgj0vb8nr8a8g0n4qtrhj7.apps.googleusercontent.com") //Use the Web Client ID, not the Android one!
        }

    }
}