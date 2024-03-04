package com.jaegerapps.malmali.chat.data.remote.supabase

interface ChatRemoteDataSourceSupabase {
    suspend fun insertChatHistory()
}