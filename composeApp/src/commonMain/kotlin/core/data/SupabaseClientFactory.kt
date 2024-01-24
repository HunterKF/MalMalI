package core.data

import com.jaegerapps.malmali.BuildKonfig.API_KEY
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

class SupabaseClientFactory {
    fun createBase(): SupabaseClient {
        val supabase = createSupabaseClient(
            supabaseUrl = "https://diulepapyuslazawpzli.supabase.co",
            supabaseKey = API_KEY
        ) {
//            install(Auth)
            install(Postgrest)
        }

        return supabase
    }

}