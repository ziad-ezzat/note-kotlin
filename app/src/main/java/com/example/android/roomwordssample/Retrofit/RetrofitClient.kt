package com.example.android.roomwordssample.Retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://test-13589-default-rtdb.firebaseio.com/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val noteService: NoteService by lazy {
        retrofit.create(NoteService::class.java)
    }
}